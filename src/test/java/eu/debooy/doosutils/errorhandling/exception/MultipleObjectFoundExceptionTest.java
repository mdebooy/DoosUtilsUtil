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
package eu.debooy.doosutils.errorhandling.exception;

import eu.debooy.doosutils.errorhandling.exception.base.DoosError;
import eu.debooy.doosutils.errorhandling.exception.base.DoosLayer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
 *
 * @author Marco de Booij
 */
public class MultipleObjectFoundExceptionTest {
  private static final Throwable  t = new Throwable();

  @Test
  public void testInit1() {
    var mofe  = new MultipleObjectFoundException(DoosLayer.PERSISTENCE,
                                                 "Init1 exception");

    assertNull(mofe.getCause());
    assertEquals(DoosError.MULTIPLE_OBJECT_FOUND, mofe.getDoosError());
    assertEquals(DoosLayer.PERSISTENCE, mofe.getDoosLayer());
    assertEquals("Init1 exception", mofe.getMessage());
    assertFalse(mofe.isLoggable());
    assertFalse(mofe.isLogged());
  }

  @Test
  public void testInit2() {
    var mofe  = new MultipleObjectFoundException(DoosLayer.PERSISTENCE,
                                                 "Init2 exception", t);

    assertEquals(t, mofe.getCause());
    assertEquals(DoosError.MULTIPLE_OBJECT_FOUND, mofe.getDoosError());
    assertEquals(DoosLayer.PERSISTENCE, mofe.getDoosLayer());
    assertEquals("Init2 exception", mofe.getMessage());
    assertFalse(mofe.isLoggable());
    assertFalse(mofe.isLogged());
  }

  @Test
  public void testLoggable() {
    var mofe  = new MultipleObjectFoundException(DoosLayer.PERSISTENCE,
                                                 "Loggable exception", t);

    assertFalse(mofe.isLoggable());
    assertFalse(mofe.isLogged());

    mofe.setLoggable(true);
    mofe.setLoggedTrue();

    assertEquals(t, mofe.getCause());
    assertEquals(DoosError.MULTIPLE_OBJECT_FOUND, mofe.getDoosError());
    assertEquals(DoosLayer.PERSISTENCE, mofe.getDoosLayer());
    assertEquals("Loggable exception", mofe.getMessage());
    assertTrue(mofe.isLoggable());
    assertTrue(mofe.isLogged());
  }
}
