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

import eu.debooy.doosutils.DoosConstants;
import eu.debooy.doosutils.errorhandling.exception.DuplicateObjectException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class DoosExceptionHelperTest {
  private final DuplicateObjectException doe =
      new DuplicateObjectException(DoosLayer.PERSISTENCE,
                                   "Persistence exception");

  @Test
  public void testConvertParameter1() {
    String  param = null;

    assertEquals(DoosConstants.NULL,
                 DoosExceptionHelper.convertParameter(param));
  }

  @Test
  public void testConvertParameter2() {
    var param = "parameter";

    assertEquals(param, DoosExceptionHelper.convertParameter(param));
  }

  @Test
  public void testConvertParameter3() {
    var param = 0L;

    assertEquals("0", DoosExceptionHelper.convertParameter(param));
  }

  @Test
  public void testConvertParameter4() {
    boolean param = true;

    assertEquals(Boolean.TRUE.toString(),
                 DoosExceptionHelper.convertParameter(param));
  }

  @Test
  public void testConvertParameter5() {
    double  param = 1.2;

    assertEquals(Double.toString(1.2),
                 DoosExceptionHelper.convertParameter(param));
  }

  @Test
  public void testConvertParameter6() {
    float param = 1.2f;

    assertEquals(Float.toString(1.2f),
                 DoosExceptionHelper.convertParameter(param));
  }

  @Test
  public void testConvertParameter9() {
    assertEquals("", DoosExceptionHelper.convertParameter(doe));
  }

  @Test
  public void testConvertParameters1() {
    var       param   = DoosConstants.NULL + ", parameter, 0, ";

    assertEquals(param,
                 DoosExceptionHelper.convertParameters(new Object[] {null, "parameter", 0L, doe}));
  }

  @Test
  public void testConvertParameters2() {
    assertEquals("",
                 DoosExceptionHelper.convertParameters(new Object[] {}));
  }

  @Test
  public void testConvertParameters3() {
    assertEquals(DoosConstants.NULL,
                 DoosExceptionHelper.convertParameters(null));
  }

  @Test
  public void testGetStackTrace() {
    var regel     = DoosExceptionHelper.getStackTrace(doe)
                                       .split(System.lineSeparator());
    var toString  = doe.toString();

    assertEquals(toString, regel[0]);
    assertTrue(regel[1].contains(getClass().getName()));
  }
}
