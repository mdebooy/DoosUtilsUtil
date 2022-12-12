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
public class TechnicalExceptionTest {
  private static final Throwable  t = new Throwable();

  @Test
  public void testInit1a() {
    var te  = new TechnicalException(DoosError.RUNTIME_EXCEPTION,
                                     DoosLayer.PERSISTENCE, true,
                                     "Init1a exception", t);

    assertEquals(t, te.getCause());
    assertEquals(DoosError.RUNTIME_EXCEPTION, te.getDoosError());
    assertEquals(DoosLayer.PERSISTENCE, te.getDoosLayer());
    assertEquals("Init1a exception", te.getMessage());
    assertTrue(te.isLoggable());
    assertFalse(te.isLogged());
    assertFalse(te.isRecoverableInTime());
  }

  @Test
  public void testInit1b() {
    var te  = new TechnicalException(DoosError.RUNTIME_EXCEPTION,
                                     DoosLayer.PERSISTENCE, false,
                                     "Init1b exception", t);

    assertEquals(t, te.getCause());
    assertEquals(DoosError.RUNTIME_EXCEPTION, te.getDoosError());
    assertEquals(DoosLayer.PERSISTENCE, te.getDoosLayer());
    assertEquals("Init1b exception", te.getMessage());
    assertFalse(te.isLoggable());
    assertFalse(te.isLogged());
    assertFalse(te.isRecoverableInTime());
  }

  @Test
  public void testInit2a() {
    var te  = new TechnicalException(DoosError.RUNTIME_EXCEPTION,
                                     DoosLayer.PERSISTENCE, true,
                                     "Init2a exception");

    assertNull(te.getCause());
    assertEquals(DoosError.RUNTIME_EXCEPTION, te.getDoosError());
    assertEquals(DoosLayer.PERSISTENCE, te.getDoosLayer());
    assertEquals("Init2a exception", te.getMessage());
    assertTrue(te.isLoggable());
    assertFalse(te.isLogged());
    assertFalse(te.isRecoverableInTime());
  }

  @Test
  public void testInit2b() {
    var te  = new TechnicalException(DoosError.RUNTIME_EXCEPTION,
                                     DoosLayer.PERSISTENCE, false,
                                     "Init2b exception");

    assertNull(te.getCause());
    assertEquals(DoosError.RUNTIME_EXCEPTION, te.getDoosError());
    assertEquals(DoosLayer.PERSISTENCE, te.getDoosLayer());
    assertEquals("Init2b exception", te.getMessage());
    assertFalse(te.isLoggable());
    assertFalse(te.isLogged());
    assertFalse(te.isRecoverableInTime());
  }

  @Test
  public void testInit3() {
    var te  = new TechnicalException(DoosError.RUNTIME_EXCEPTION,
                                     DoosLayer.PERSISTENCE,
                                     "Init3 exception", t);

    assertEquals(t, te.getCause());
    assertEquals(DoosError.RUNTIME_EXCEPTION, te.getDoosError());
    assertEquals(DoosLayer.PERSISTENCE, te.getDoosLayer());
    assertEquals("Init3 exception", te.getMessage());
    assertTrue(te.isLoggable());
    assertFalse(te.isLogged());
    assertFalse(te.isRecoverableInTime());
  }

  @Test
  public void testInit4() {
    var te  = new TechnicalException(DoosError.RUNTIME_EXCEPTION,
                                     DoosLayer.PERSISTENCE,
                                     "Init4 exception");

    assertNull(te.getCause());
    assertEquals(DoosError.RUNTIME_EXCEPTION, te.getDoosError());
    assertEquals(DoosLayer.PERSISTENCE, te.getDoosLayer());
    assertEquals("Init4 exception", te.getMessage());
    assertTrue(te.isLoggable());
    assertFalse(te.isLogged());
    assertFalse(te.isRecoverableInTime());
  }

  @Test
  public void testLoggable() {
    var te  = new TechnicalException(DoosError.RUNTIME_EXCEPTION,
                                     DoosLayer.PERSISTENCE,
                                     "Init1 exception", t);

    assertTrue(te.isLoggable());
    assertFalse(te.isLogged());
    assertFalse(te.isRecoverableInTime());

    te.setLoggable(false);
    te.setLoggedTrue();
    te.setRecoverableInTime(true);

    assertEquals(t, te.getCause());
    assertEquals(DoosError.RUNTIME_EXCEPTION, te.getDoosError());
    assertEquals(DoosLayer.PERSISTENCE, te.getDoosLayer());
    assertEquals("Init1 exception", te.getMessage());
    assertFalse(te.isLoggable());
    assertTrue(te.isLogged());
    assertTrue(te.isRecoverableInTime());
  }
}
