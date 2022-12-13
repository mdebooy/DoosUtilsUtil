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

import eu.debooy.doosutils.errorhandling.exception.base.DoosLayer;
import eu.debooy.doosutils.errorhandling.handler.DefaultEJBExceptionHandler;
import eu.debooy.doosutils.errorhandling.handler.PersistenceEJBExceptionHandler;


/**
 * @author Marco de Booij
 */
public final class ExceptionHandlerFactory {
  private ExceptionHandlerFactory() {}

  private static final  IExceptionHandler businessHandler     =
      new DefaultEJBExceptionHandler("Business EJB Exception Handler",
                                     DoosLayer.BUSINESS, true);
  private static final  IExceptionHandler defaultHandler      =
      new DefaultEJBExceptionHandler("Default EJB Exception Handler",
                                     DoosLayer.UNDEFINED, true);
  private static final  IExceptionHandler persistenceHandler  =
      new PersistenceEJBExceptionHandler("Persistence EJB Exception Handler",
                                         DoosLayer.PERSISTENCE, true);
  private static final  IExceptionHandler presentationHandler =
      new DefaultEJBExceptionHandler("Presentation Exception Handler",
                                     DoosLayer.PRESENTATION, true);

  public static IExceptionHandler getBusinessHandler() {
    return businessHandler;
  }

  public static IExceptionHandler getDefaultHandler() {
    return defaultHandler;
  }

  public static IExceptionHandler getPersistenceHandler() {
    return persistenceHandler;
  }

  public static IExceptionHandler getPresentationHandler() {
    return presentationHandler;
  }
}
