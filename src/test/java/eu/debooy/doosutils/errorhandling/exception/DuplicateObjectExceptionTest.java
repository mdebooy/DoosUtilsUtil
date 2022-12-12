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
public class DuplicateObjectExceptionTest {
  private static final String     FAILEDOBJECT  = "failedObject";
  private static final Throwable  t             = new Throwable();

  @Test
  public void testInit1() {
    var doe = new DuplicateObjectException(DoosLayer.PERSISTENCE,
                                           "Init1 exception");

    assertNull(doe.getCause());
    assertEquals(DoosError.DUPLICATE_OBJECT, doe.getDoosError());
    assertEquals(DoosLayer.PERSISTENCE, doe.getDoosLayer());
    assertNull(doe.getFailedObject());
    assertEquals("Init1 exception", doe.getMessage());
    assertFalse(doe.isLoggable());
    assertFalse(doe.isLogged());
  }

  @Test
  public void testInit2() {
    var doe = new DuplicateObjectException(DoosLayer.PERSISTENCE,
                                           "Init2 exception", t);

    assertEquals(t, doe.getCause());
    assertEquals(DoosError.DUPLICATE_OBJECT, doe.getDoosError());
    assertEquals(DoosLayer.PERSISTENCE, doe.getDoosLayer());
    assertNull(doe.getFailedObject());
    assertEquals("Init2 exception", doe.getMessage());
    assertFalse(doe.isLoggable());
    assertFalse(doe.isLogged());
  }

  @Test
  public void testInit3() {
    var doe = new DuplicateObjectException(DoosLayer.PERSISTENCE,
                                           FAILEDOBJECT,
                                           "Init3 exception");

    assertNull(doe.getCause());
    assertEquals(DoosError.DUPLICATE_OBJECT, doe.getDoosError());
    assertEquals(DoosLayer.PERSISTENCE, doe.getDoosLayer());
    assertEquals(FAILEDOBJECT, doe.getFailedObject());
    assertEquals("Init3 exception", doe.getMessage());
    assertFalse(doe.isLoggable());
    assertFalse(doe.isLogged());
  }

  @Test
  public void testInit4() {
    var doe = new DuplicateObjectException(DoosLayer.PERSISTENCE,
                                           FAILEDOBJECT,
                                           "Init4 exception", t);

    assertEquals(t, doe.getCause());
    assertEquals(DoosError.DUPLICATE_OBJECT, doe.getDoosError());
    assertEquals(DoosLayer.PERSISTENCE, doe.getDoosLayer());
    assertEquals(FAILEDOBJECT, doe.getFailedObject());
    assertEquals("Init4 exception", doe.getMessage());
    assertFalse(doe.isLoggable());
    assertFalse(doe.isLogged());
  }

  @Test
  public void testLoggable() {
    var doe = new DuplicateObjectException(DoosLayer.PERSISTENCE,
                                           FAILEDOBJECT,
                                           "Loggable exception", t);

    assertFalse(doe.isLoggable());
    assertFalse(doe.isLogged());

    doe.setLoggable(true);
    doe.setLoggedTrue();

    assertEquals(t, doe.getCause());
    assertEquals(DoosError.DUPLICATE_OBJECT, doe.getDoosError());
    assertEquals(DoosLayer.PERSISTENCE, doe.getDoosLayer());
    assertEquals(FAILEDOBJECT, doe.getFailedObject());
    assertEquals("Loggable exception", doe.getMessage());
    assertTrue(doe.isLoggable());
    assertTrue(doe.isLogged());
  }
}
