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

package eu.debooy.doosutils.errorhandling.handler;

import eu.debooy.doosutils.errorhandling.exception.base.DoosLayer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class PersistenceEJBExceptionHandlerTest {
  private static final  String  PERSISTENCEHANDLER  =
      "Persistence EJB Exception Handler";

  @Test
  public void testInit1() {
    var pee =
        new PersistenceEJBExceptionHandler("Persistence EJB Exception Handler",
                                           DoosLayer.PERSISTENCE, true);

    assertEquals(DoosLayer.PERSISTENCE, pee.getLayer());
    assertEquals(PERSISTENCEHANDLER, pee.getName());
    assertTrue(pee.isObjectNotFoundPattern());
    assertTrue(pee instanceof PersistenceEJBExceptionHandler);
  }

  @Test
  public void testInit2() {
    var pee =
        new PersistenceEJBExceptionHandler("Persistence EJB Exception Handler",
                                           DoosLayer.PERSISTENCE, false);

    assertEquals(DoosLayer.PERSISTENCE, pee.getLayer());
    assertEquals(PERSISTENCEHANDLER, pee.getName());
    assertFalse(pee.isObjectNotFoundPattern());
    assertTrue(pee instanceof PersistenceEJBExceptionHandler);
  }
}
