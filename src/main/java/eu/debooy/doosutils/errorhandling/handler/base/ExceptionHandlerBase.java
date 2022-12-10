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
package eu.debooy.doosutils.errorhandling.handler.base;

import eu.debooy.doosutils.errorhandling.exception.SerializableException;
import eu.debooy.doosutils.errorhandling.exception.TechnicalException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosError;
import eu.debooy.doosutils.errorhandling.exception.base.DoosLayer;
import eu.debooy.doosutils.errorhandling.exception.base.DoosRuntimeException;
import eu.debooy.doosutils.errorhandling.exception.base.IDoosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
public abstract class ExceptionHandlerBase implements IExceptionHandler {
  private static final  long  serialVersionUID  = 1L;

  private static final Logger LOGGER                =
      LoggerFactory.getLogger(ExceptionHandlerBase.class);
  private final String    name;
  private final DoosLayer layer;
  private       boolean   objectNotFoundPattern = true;

  protected ExceptionHandlerBase(String name, DoosLayer layer,
                              boolean objectNotFoundPattern) {
    this.name                   = name;
    this.layer                  = layer;
    this.objectNotFoundPattern  = objectNotFoundPattern;
  }

  protected DoosRuntimeException caughtRuntimeException(RuntimeException re) {
    DoosRuntimeException dre;
    if (shouldBeSerialized(re)) {
      dre = new SerializableException(re);
    } else {
      dre = new TechnicalException(DoosError.RUNTIME_EXCEPTION, getLayer(),
                                   re.getMessage(), re);
    }
    log(dre);

    return dre;
  }

  protected TechnicalException caughtThrowable(Throwable th) {
    var te = new TechnicalException(DoosError.RUNTIME_EXCEPTION, getLayer(),
                                    th.getMessage(), th);
    log(te);

    return te;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public DoosLayer getLayer() {
    return layer;
  }

  @Override
  public boolean isObjectNotFoundPattern() {
    return objectNotFoundPattern;
  }

  @Override
  public void log(IDoosException e) {
    if (e.isLoggable()
        && (!e.isLogged())) {
      LOGGER.error("IDoosException logged by " + getName() + " handler",
                   (Throwable) e);
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
}
