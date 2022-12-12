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
public class WrappedExceptionTest {
  private static final Throwable  t = new Throwable();

  @Test
  public void testInit1() {
    var we  = new WrappedException(DoosLayer.SYSTEM, t);

    assertEquals(t, we.getCause());
    assertEquals(DoosError.WRAPPED_EXCEPTION, we.getDoosError());
    assertEquals(DoosLayer.SYSTEM, we.getDoosLayer());
    assertNull(we.getMessage());
    assertTrue(we.isLoggable());
    assertFalse(we.isLogged());
  }

  @Test
  public void testLoggable() {
    var we  = new WrappedException(DoosLayer.PERSISTENCE, t);

    assertTrue(we.isLoggable());
    assertFalse(we.isLogged());

    we.setLoggable(false);
    we.setLoggedTrue();

    assertEquals(t, we.getCause());
    assertEquals(DoosError.WRAPPED_EXCEPTION, we.getDoosError());
    assertEquals(DoosLayer.PERSISTENCE, we.getDoosLayer());
    assertNull(we.getMessage());
    assertFalse(we.isLoggable());
    assertTrue(we.isLogged());
  }
}
