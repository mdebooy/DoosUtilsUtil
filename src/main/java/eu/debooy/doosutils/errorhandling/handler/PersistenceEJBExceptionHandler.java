/**
 * Copyright (c) 2011 Marco de Booij
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the Licence. You may
 * obtain a copy of the Licence at:
 *
 * http://www.osor.eu/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 */
package eu.debooy.doosutils.errorhandling.handler;

import eu.debooy.doosutils.errorhandling.exception.DuplicateObjectException;
import eu.debooy.doosutils.errorhandling.exception.MultipleObjectFoundException;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.TechnicalException;
import eu.debooy.doosutils.errorhandling.exception.WrappedException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosError;
import eu.debooy.doosutils.errorhandling.exception.base.DoosException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosLayer;
import eu.debooy.doosutils.errorhandling.exception.base.DoosRuntimeException;
import eu.debooy.doosutils.errorhandling.handler.base.ExceptionHandlerBase;
import java.sql.SQLException;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import org.apache.openjpa.lib.jdbc.ReportingSQLException;


/**
 * @author Marco de Booij
 */
public class PersistenceEJBExceptionHandler extends ExceptionHandlerBase {
  private static final  long  serialVersionUID  = 1L;

  private static  PersistenceEJBExceptionUtil util  = null;

  public PersistenceEJBExceptionHandler(String name, DoosLayer layer,
                                        boolean objectNotFoundPattern) {
    super(name, layer, objectNotFoundPattern);
  }

  private static PersistenceEJBExceptionUtil getUtil() {
    if (null == util) {
      util  = new PersistenceEJBExceptionUtil();
    }

    return util;
  }

  private DoosRuntimeException
      caughtEntityExistsException(EntityExistsException eee) {
    DoosRuntimeException  dre =
        new DuplicateObjectException(DoosLayer.PERSISTENCE,
                                     eee.getMessage(), eee);
    log(dre);

    return dre;
  }

  private DoosRuntimeException
      caughtNonUniqueResultException(NonUniqueResultException nure) {
    DoosRuntimeException  dre =
        new MultipleObjectFoundException(DoosLayer.PERSISTENCE,
                                         nure.getMessage(), nure);
    log(dre);

    return dre;
  }

  private DoosRuntimeException caughtNoResultException(NoResultException nre) {
      DoosRuntimeException  dre =
          new ObjectNotFoundException(DoosLayer.PERSISTENCE,
                                      nre.getMessage(), nre);
      log(dre);

      return dre;
  }

  private DoosRuntimeException
      caughtPersistenceException(PersistenceException pe) {
    DoosRuntimeException  dre;
    Throwable             t   = findRootCause(pe, 5);

    dre = new TechnicalException(DoosError.RUNTIME_EXCEPTION, getLayer(),
                                 (null == t) ? "" : t.getMessage(), t);

    log(dre);

    return dre;
  }

  @SuppressWarnings("java:S1301")
  private DoosRuntimeException
      caughtReportingSQLException(ReportingSQLException rse) {
    log(new TechnicalException(DoosError.RUNTIME_EXCEPTION,
                                     DoosLayer.PERSISTENCE,
                                     rse.getMessage(), rse));
    DoosRuntimeException  dre;
    switch (rse.getSQLState()) {
      case "23505":
        dre =
            new DuplicateObjectException(DoosLayer.PERSISTENCE,
                                         rse.getMessage(), rse);
        break;
      default:
        dre = new TechnicalException(DoosError.RUNTIME_EXCEPTION,
                                     DoosLayer.PERSISTENCE,
                                     rse.getMessage(), rse);
    }
    log(dre);

    return dre;
  }

  @SuppressWarnings("java:S1181")
  @Override
  public void handle(Throwable t) {
    try {
      throw t;
    } catch (DoosException de) {
      log(de);
      throw new WrappedException(getLayer(), de);
    } catch (DoosRuntimeException dre) {
      log(dre);
      if ((dre instanceof WrappedException)) {
        handle(unwrapException((WrappedException) dre));
      } else {
        throw dre;
      }
    } catch (NoResultException nre) {
      throw caughtNoResultException(nre);
    } catch (NonUniqueResultException nure) {
      throw caughtNonUniqueResultException(nure);
    } catch (EntityExistsException eee){
      throw caughtEntityExistsException(eee);
    } catch (ReportingSQLException rse) {
      throw caughtReportingSQLException(rse);
    } catch (PersistenceException pe) {
      throw caughtPersistenceException(pe);
    } catch (RuntimeException re) {
      throw caughtRuntimeException(re);
    } catch (Throwable th) {
      throw caughtThrowable(th);
    }
  }

  private Throwable unwrapException(WrappedException e) {
    Throwable t = e;
    while ((t instanceof WrappedException)) {
      t = t.getCause();
    }

    return t;
  }

  public static Throwable findRootCause(Throwable t, int nbTimes) {
    if (null == t) {
      return null;
    }

    Throwable targetException = t;
    if (null != targetException.getCause()
        && nbTimes != 0) {
      targetException = t.getCause();
      if ((targetException instanceof SQLException)) {
        return getUtil().transform((SQLException) targetException);
      }
      targetException = findRootCause(targetException, nbTimes-1);
    }

    return targetException;
  }
}
