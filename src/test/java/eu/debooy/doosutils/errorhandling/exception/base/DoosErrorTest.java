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
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
 *
 * @author Marco de Booij
 */
public class DoosErrorTest {
  @Test
  public void testInit1() {
    var de  = new DoosError();

    assertEquals("DEFAULT", de.getCode());
    assertEquals("DEFAULT_CONSTRUCTOR", de.getDescription());
  }

  @Test
  public void testInit2a() {
    var de  = new DoosError("errorcode", "omschrijving");

    assertEquals("errorcode", de.getCode());
    assertEquals("omschrijving", de.getDescription());
  }

  @Test
  public void testInit2b() {
    var de            = new DoosError("errorcode", "omschrijving");
    var toString      = de.toString();
    var toStringEnd   = "    code = errorcode    description = omschrijving )";
    var toStringStart = "DoosError ( eu.debooy.doosutils.errorhandling."
                        + "exception.base.DoosError@";

    assertTrue(toString.startsWith(toStringStart));
    assertTrue(toString.endsWith(toStringEnd));
  }

  @Test
  public void testInit3() {
    var de  = new DoosError("errorcode");

    assertEquals("errorcode", de.getCode());
    assertEquals("errorcode", de.getDescription());
  }

  @Test
  public void testDuplicateObject() {
    var de  = DoosError.DUPLICATE_OBJECT;

    assertEquals("DUPLICATE_OBJECT", de.getCode());
    assertEquals("DUPLICATE_OBJECT", de.getDescription());
  }

  @Test
  public void testFileNotFound() {
    var de  = DoosError.FILE_NOT_FOUND;

    assertEquals("FILE_NOT_FOUND", de.getCode());
    assertEquals("FILE_NOT_FOUND", de.getDescription());
  }

  @Test
  public void testIllegalArgument() {
    var de  = DoosError.ILLEGAL_ARGUMENT;

    assertEquals("ILLEGAL_ARGUMENT", de.getCode());
    assertEquals("ILLEGAL_ARGUMENT", de.getDescription());
  }

  @Test
  public void testMultipleObjectFound() {
    var de  = DoosError.MULTIPLE_OBJECT_FOUND;

    assertEquals("MULTIPLE_OBJECT_FOUND", de.getCode());
    assertEquals("MULTIPLE_OBJECT_FOUND", de.getDescription());
  }

  @Test
  public void testObjectNotFound() {
    var de  = DoosError.OBJECT_NOT_FOUND;

    assertEquals("OBJECT_NOT_FOUND", de.getCode());
    assertEquals("OBJECT_NOT_FOUND", de.getDescription());
  }

  @Test
  public void testRuntimeException() {
    var de  = DoosError.RUNTIME_EXCEPTION;

    assertEquals("RUNTIME_EXCEPTION", de.getCode());
    assertEquals("RUNTIME_EXCEPTION", de.getDescription());
  }

  @Test
  public void testWrappedException() {
    var de  = DoosError.WRAPPED_EXCEPTION;

    assertEquals("WRAPPED_EXCEPTION", de.getCode());
    assertEquals("WRAPPED_EXCEPTION", de.getDescription());
  }
}
