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
package eu.debooy.doosutils.errorhandling.exception.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
 *
 * @author Marco de Booij
 */
public class DoosExceptionTest {
  private static final Throwable  t = new Throwable();

  @Test
  public void testGetStackTraceAsString() {
    var de        = new DoosException(DoosError.DUPLICATE_OBJECT,
                                      DoosLayer.BUSINESS,
                                      "GetStackTrace exception");
    var regel     = de.getStackTraceAsString().split(System.lineSeparator());
    var toString  = de.toString();

    assertEquals(toString, regel[0]);
    assertTrue(regel[1].contains(getClass().getName()));
  }

  @Test
  public void testInit1a() {
    var de  = new DoosException(DoosError.DUPLICATE_OBJECT, DoosLayer.BUSINESS,
                                true, "Init1a exception", t);

    assertEquals(t, de.getCause());
    assertEquals(DoosError.DUPLICATE_OBJECT, de.getDoosError());
    assertEquals(DoosLayer.BUSINESS, de.getDoosLayer());
    assertEquals("Init1a exception", de.getMessage());
    assertTrue(de.isLoggable());
    assertFalse(de.isLogged());
  }

  @Test
  public void testInit1b() {
    var de  = new DoosException(DoosError.DUPLICATE_OBJECT, DoosLayer.BUSINESS,
                                false, "Init1b exception", t);

    assertEquals(t, de.getCause());
    assertEquals(DoosError.DUPLICATE_OBJECT, de.getDoosError());
    assertEquals(DoosLayer.BUSINESS, de.getDoosLayer());
    assertEquals("Init1b exception", de.getMessage());
    assertFalse(de.isLoggable());
    assertFalse(de.isLogged());
  }

  @Test
  public void testInit2() {
    var de  = new DoosException(DoosError.DUPLICATE_OBJECT, DoosLayer.BUSINESS,
                                "Init2 exception", t);

    assertEquals(t, de.getCause());
    assertEquals(DoosError.DUPLICATE_OBJECT, de.getDoosError());
    assertEquals(DoosLayer.BUSINESS, de.getDoosLayer());
    assertEquals("Init2 exception", de.getMessage());
    assertTrue(de.isLoggable());
    assertFalse(de.isLogged());
  }

  @Test
  public void testInit3a() {
    var de  = new DoosException(DoosError.DUPLICATE_OBJECT, DoosLayer.BUSINESS,
                                true, "Init3a exception");

    assertNull(de.getCause());
    assertEquals(DoosError.DUPLICATE_OBJECT, de.getDoosError());
    assertEquals(DoosLayer.BUSINESS, de.getDoosLayer());
    assertEquals("Init3a exception", de.getMessage());
    assertTrue(de.isLoggable());
    assertFalse(de.isLogged());
  }

  @Test
  public void testInit3b() {
    var de  = new DoosException(DoosError.DUPLICATE_OBJECT, DoosLayer.BUSINESS,
                                false, "Init3b exception");

    assertNull(de.getCause());
    assertEquals(DoosError.DUPLICATE_OBJECT, de.getDoosError());
    assertEquals(DoosLayer.BUSINESS, de.getDoosLayer());
    assertEquals("Init3b exception", de.getMessage());
    assertFalse(de.isLoggable());
    assertFalse(de.isLogged());
  }

  @Test
  public void testInit4() {
    var de  = new DoosException(DoosError.DUPLICATE_OBJECT, DoosLayer.BUSINESS,
                                "Init4 exception");

    assertNull(de.getCause());
    assertEquals(DoosError.DUPLICATE_OBJECT, de.getDoosError());
    assertEquals(DoosLayer.BUSINESS, de.getDoosLayer());
    assertEquals("Init4 exception", de.getMessage());
    assertTrue(de.isLoggable());
    assertFalse(de.isLogged());
  }

  @Test
  public void testLoggable() {
    var de  = new DoosException(DoosError.DUPLICATE_OBJECT, DoosLayer.BUSINESS,
                                "Loggable exception");

    assertTrue(de.isLoggable());
    assertFalse(de.isLogged());

    de.setLoggable(false);
    de.setLoggedTrue();

    assertNull(de.getCause());
    assertEquals(DoosError.DUPLICATE_OBJECT, de.getDoosError());
    assertEquals(DoosLayer.BUSINESS, de.getDoosLayer());
    assertEquals("Loggable exception", de.getMessage());
    assertFalse(de.isLoggable());
    assertTrue(de.isLogged());
  }
}
