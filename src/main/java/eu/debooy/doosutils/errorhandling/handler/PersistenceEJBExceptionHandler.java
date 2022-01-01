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
import eu.debooy.doosutils.errorhandling.exception.SerializableException;
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

  @Override
  public void handle(Throwable t) {
    try {
      throw t;
    } catch (DoosException ie) {
      log(ie);
      throw new WrappedException(getLayer(), ie);
    } catch (DoosRuntimeException ire) {
      log(ire);
      if ((ire instanceof WrappedException)) {
        handle(unwrapException((WrappedException) ire));
      } else {
        throw ire;
      }
    } catch (NoResultException e) {
      DoosRuntimeException  de  =
          new ObjectNotFoundException(DoosLayer.PERSISTENCE,
                                      e.getMessage(), e);
      log(de);
      throw de;
    } catch (NonUniqueResultException e) {
      DoosRuntimeException  de  =
          new MultipleObjectFoundException(DoosLayer.PERSISTENCE,
                                           e.getMessage(), e);
      log(de);
      throw de;
    } catch (EntityExistsException eee){
      DoosRuntimeException  de  =
          new DuplicateObjectException(DoosLayer.PERSISTENCE,
                                       eee.getMessage(), eee);
      log(de);
      throw de;
    } catch (ReportingSQLException rse) {
      log(new TechnicalException(DoosError.RUNTIME_EXCEPTION,
                                       DoosLayer.PERSISTENCE,
                                       rse.getMessage(), rse));
      DoosRuntimeException  de;
      switch (rse.getSQLState()) {
        case "23505":
          de  =
              new DuplicateObjectException(DoosLayer.PERSISTENCE,
                                           rse.getMessage(), rse);
          break;
        default:
          de  = new TechnicalException(DoosError.RUNTIME_EXCEPTION,
                                       DoosLayer.PERSISTENCE,
                                       rse.getMessage(), rse);
      }
      log(de);
      throw de;
    } catch (PersistenceException pe) {
      Throwable thr = findRootCause(pe, 5);

      DoosRuntimeException ir;

      ir = new TechnicalException(DoosError.RUNTIME_EXCEPTION, getLayer(),
          thr.getMessage(), thr);

      log(ir);
      throw ir;
    } catch (RuntimeException rt) {
      DoosRuntimeException ir;
      if (shouldBeSerialized(rt)) {
        ir = new SerializableException(rt);
      } else {
        ir = new TechnicalException(DoosError.RUNTIME_EXCEPTION, getLayer(),
            rt.getMessage(), rt);
      }
      log(ir);
      throw ir;
    } catch (Throwable th) {
      var te = new TechnicalException(DoosError.RUNTIME_EXCEPTION, getLayer(),
                                      th.getMessage(), th);
      log(te);
      throw te;
    }
  }

  private boolean shouldBeSerialized(Throwable t) {
    var pack  = t.getClass().getPackage();
    if (pack == null) {
      return false;
    }
    if (pack.getName().startsWith("java.")) {
      return false;
    }
    if (pack.getName().startsWith("javax.")) {
      return false;
    }

    return !(t instanceof RuntimeException);
  }

  private Throwable unwrapException(WrappedException e) {
    Throwable t = e;
    while ((t instanceof WrappedException)) {
      t = t.getCause();
    }

    return t;
  }

  public static Throwable findRootCause(Throwable t, int nbTimes) {
    Throwable targetException = t;
    if (null != targetException
        && null != targetException.getCause()
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
