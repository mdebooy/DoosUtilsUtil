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
public class FileNotFoundExceptionTest {
  private static final Throwable  t = new Throwable();

  @Test
  public void testInit1() {
    var fnfe  = new FileNotFoundException(DoosLayer.BUSINESS,
                                          "Init1 exception");

    assertNull(fnfe.getCause());
    assertEquals(DoosError.FILE_NOT_FOUND, fnfe.getDoosError());
    assertEquals(DoosLayer.BUSINESS, fnfe.getDoosLayer());
    assertEquals("Init1 exception", fnfe.getMessage());
    assertTrue(fnfe.isLoggable());
    assertFalse(fnfe.isLogged());
  }

  @Test
  public void testInit2() {
    var fnfe  = new FileNotFoundException(DoosLayer.BUSINESS,
                                          "Init2 exception", t);

    assertEquals(t, fnfe.getCause());
    assertEquals(DoosError.FILE_NOT_FOUND, fnfe.getDoosError());
    assertEquals(DoosLayer.BUSINESS, fnfe.getDoosLayer());
    assertEquals("Init2 exception", fnfe.getMessage());
    assertTrue(fnfe.isLoggable());
    assertFalse(fnfe.isLogged());
  }

  @Test
  public void testLoggable() {
    var fnfe  = new FileNotFoundException(DoosLayer.BUSINESS,
                                          "Loggable exception", t);

    assertTrue(fnfe.isLoggable());
    assertFalse(fnfe.isLogged());

    fnfe.setLoggable(false);
    fnfe.setLoggedTrue();

    assertEquals(t, fnfe.getCause());
    assertEquals(DoosError.FILE_NOT_FOUND, fnfe.getDoosError());
    assertEquals(DoosLayer.BUSINESS, fnfe.getDoosLayer());
    assertEquals("Loggable exception", fnfe.getMessage());
    assertFalse(fnfe.isLoggable());
    assertTrue(fnfe.isLogged());
  }
}
