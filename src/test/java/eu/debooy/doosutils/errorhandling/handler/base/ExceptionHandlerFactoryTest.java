/*
 * Copyright (c) 2022 Marco de Booij
 *
 * Licensed under the EUPL, Version 1.2 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the Licence. You may
 * obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 */

package eu.debooy.doosutils.errorhandling.handler.base;

import eu.debooy.doosutils.errorhandling.handler.DefaultEJBExceptionHandler;
import eu.debooy.doosutils.errorhandling.handler.PersistenceEJBExceptionHandler;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class ExceptionHandlerFactoryTest {
  private static final  String  BUSINESSHANDLER     =
      "Business EJB Exception Handler";
  private static final  String  DEFAULTHANDLER      =
      "Default EJB Exception Handler";
  private static final  String  PERSISTENCEHANDLER  =
      "Persistence EJB Exception Handler";
  private static final  String  PRESENTATIONHANDLER =
      "Presentation Exception Handler";

  @Test
  public void testGetBusinessHandler() {
    var handler = ExceptionHandlerFactory.getBusinessHandler();

    assertEquals(BUSINESSHANDLER, handler.getName());
    assertTrue(handler instanceof DefaultEJBExceptionHandler);
  }

  @Test
  public void testGetDefaultHandler() {
    var handler = ExceptionHandlerFactory.getDefaultHandler();

    assertEquals(DEFAULTHANDLER, handler.getName());
    assertTrue(handler instanceof DefaultEJBExceptionHandler);
  }

  @Test
  public void testGetPersistenceHandler() {
    var handler = ExceptionHandlerFactory.getPersistenceHandler();

    assertEquals(PERSISTENCEHANDLER, handler.getName());
    assertTrue(handler instanceof PersistenceEJBExceptionHandler);
  }

  @Test
  public void testGetPresentationHandler() {
    var handler = ExceptionHandlerFactory.getPresentationHandler();

    assertEquals(PRESENTATIONHANDLER, handler.getName());
    assertTrue(handler instanceof DefaultEJBExceptionHandler);
  }
}
