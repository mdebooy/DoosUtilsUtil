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
import eu.debooy.doosutils.errorhandling.exception.base.DoosException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosLayer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class SerializableExceptionTest {
    private static final  DoosException sde =
        new DoosException(DoosError.DUPLICATE_OBJECT, DoosLayer.BUSINESS,
                          "DoosException exception");

    @Test
  public void testInit1() {
    var de  = new DoosException(DoosError.DUPLICATE_OBJECT,
                                      DoosLayer.PERSISTENCE,
                                      "DoosException exception");
    var se  = new SerializableException(de);


    assertTrue(se.toString()
                 .endsWith(de + " [Wrapped in SerializableException]"));
    assertEquals(DoosError.DUPLICATE_OBJECT, se.getDoosError());
    assertEquals(DoosLayer.PERSISTENCE, se.getDoosLayer());
    assertTrue(se.isLoggable());
    assertFalse(se.isLogged());
  }

    @Test
  public void testInit2() {
    var se  = new SerializableException(sde);

    assertTrue(se.toString()
                 .endsWith(sde + " [Wrapped in SerializableException]"));
    assertEquals(DoosError.DUPLICATE_OBJECT, se.getDoosError());
    assertEquals(DoosLayer.BUSINESS, se.getDoosLayer());
    assertTrue(se.isLoggable());
    assertFalse(se.isLogged());
  }

  @Test
  public void testInit3() {
    var t   = new Throwable(sde);
    var se  = new SerializableException(t);


    assertTrue(se.toString()
                 .endsWith(sde + " [Wrapped in SerializableException]"));
    assertEquals(DoosError.SERIALIZED_EXCEPTION, se.getDoosError());
    assertEquals(DoosLayer.UNDEFINED, se.getDoosLayer());
    assertTrue(se.getMessage().contains(sde.toString()));
    assertEquals(sde.toString(), se.getMessage());
    assertTrue(se.isLoggable());
    assertFalse(se.isLogged());
  }
}
