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

package eu.debooy.doosutils.exception;

import eu.debooy.doosutils.errorhandling.exception.DuplicateObjectException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosLayer;
import static org.junit.Assert.assertEquals;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class IllegalArgumentExceptionTest {
  private static final  String  APPLICATIE  = "IllegalArgumentExceptionTest";

  private static final  DuplicateObjectException  doe   =
      new DuplicateObjectException(DoosLayer.PERSISTENCE,
                                   "Persistence exception");
  private static final  IllegalArgumentException  siae  =
      new IllegalArgumentException("static LoggableException", APPLICATIE);

  @Test
  public void testInit1() {
    var iae = new IllegalArgumentException(siae);

    assertEquals(siae.getErrorMessage(), iae.getErrorMessage());
    assertEquals(siae.getApplication(), iae.getApplication());
  }

  @Test
  public void testInit2() {
    var iae = new IllegalArgumentException(doe, APPLICATIE+"2");

    assertEquals(doe.getMessage(), iae.getErrorMessage());
    assertEquals(APPLICATIE+"2", iae.getApplication());
  }

  @Test
  public void testInit3() {
    var iae = new IllegalArgumentException("message", APPLICATIE);

    assertEquals("message", iae.getErrorMessage());
    assertEquals(APPLICATIE, iae.getApplication());
  }
}
