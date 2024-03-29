/**
 * Copyright 2011 Marco de Booij
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

import eu.debooy.doosutils.errorhandling.exception.WrappedException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosLayer;
import eu.debooy.doosutils.errorhandling.exception.base.DoosRuntimeException;
import eu.debooy.doosutils.errorhandling.handler.base.ExceptionHandlerBase;


/**
 * @author Marco de Booij
 */
public class DefaultEJBExceptionHandler extends ExceptionHandlerBase {
  private static final  long  serialVersionUID  = 1L;

  public DefaultEJBExceptionHandler(String name, DoosLayer layer,
                                    boolean objectNotFoundPattern) {
    super(name, layer, objectNotFoundPattern);
  }

  @SuppressWarnings("java:S1181")
  @Override
  public void handle(Throwable t) {
    try {
      throw t;
    } catch (DoosException e) {
      log(e);
      throw new WrappedException(getLayer(), e);
    } catch (DoosRuntimeException e) {
      log(e);
      if ((e instanceof WrappedException)) {
        handle(unwrapException((WrappedException) e));
      } else {
        throw e;
      }
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
}
