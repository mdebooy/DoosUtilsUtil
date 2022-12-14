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
import eu.debooy.doosutils.errorhandling.exception.FileNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.IllegalArgumentException;
import eu.debooy.doosutils.errorhandling.exception.MultipleObjectFoundException;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.TechnicalException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosError;
import eu.debooy.doosutils.errorhandling.exception.base.DoosLayer;
import java.rmi.AccessException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class ExceptionLogUtilTest {
  private static final  DuplicateObjectException      doe   =
      new DuplicateObjectException(DoosLayer.PERSISTENCE,
                                   "Persistence exception");
  private static final  FileNotFoundException         fnfe  =
      new FileNotFoundException(DoosLayer.BUSINESS,
                                "File exception", doe);
  private static final  IllegalArgumentException      iae =
      new IllegalArgumentException(DoosLayer.BUSINESS,
                                   "Illegal Argument exception", fnfe);
  private static final  MultipleObjectFoundException  mofe  =
      new MultipleObjectFoundException(DoosLayer.PERSISTENCE,
                                       "Multiple Object Found exception", iae);
  private static final  ObjectNotFoundException       onfe  =
      new ObjectNotFoundException(DoosLayer.PERSISTENCE,
                                  "Object Not Found exception", mofe);
  private static final  TechnicalException            te  =
      new TechnicalException(DoosError.RUNTIME_EXCEPTION, DoosLayer.PERSISTENCE,
                             "Technical exception", onfe);

  @Test
  public void testGetMainException1() {
    var me  = ExceptionLogUtil.getMainException(doe);

    assertTrue(me instanceof DuplicateObjectException);
  }

  @Test
  public void testGetMainException2() {
    var me  = ExceptionLogUtil.getMainException(fnfe);

    assertTrue(me instanceof FileNotFoundException);
  }

  @Test
  public void testGetMainException3() {
    var re  = new AccessException("Access Exception");
    var me  = ExceptionLogUtil.getMainException(re);

    assertTrue(me instanceof AccessException);
  }

  @Test
  public void testGetMainException4() {
    var re  = new AccessException("Access Exception", doe);
    var me  = ExceptionLogUtil.getMainException(re);

    assertTrue(me instanceof DuplicateObjectException);
  }

  @Test
  public void testGetRootCause0() {
    var rc  = ExceptionLogUtil.getRootCause(null);

    assertNull(rc);
  }

  @Test
  public void testGetRootCause1() {
    var rc  = ExceptionLogUtil.getRootCause(doe);

    assertTrue(rc instanceof DuplicateObjectException);
  }

  @Test
  public void testGetRootCause2() {
    var rc  = ExceptionLogUtil.getRootCause(fnfe);

    assertTrue(rc instanceof DuplicateObjectException);
  }

  @Test
  public void testGetRootCause3() {
    var rc  = ExceptionLogUtil.getRootCause(iae);

    assertTrue(rc instanceof DuplicateObjectException);
  }

  @Test
  public void testGetRootCause4() {
    var rc  = ExceptionLogUtil.getRootCause(mofe);

    assertTrue(rc instanceof DuplicateObjectException);
  }

  @Test
  public void testGetRootCause5() {
    var rc  = ExceptionLogUtil.getRootCause(onfe);

    assertTrue(rc instanceof DuplicateObjectException);
  }

  @Test
  public void testGetRootCause9() {
    var rc  = ExceptionLogUtil.getRootCause(te);

    assertTrue(rc instanceof FileNotFoundException);
  }

  @Test
  public void testGetStackTrace1() {
    var regel     = ExceptionLogUtil.getStackTrace(doe)
                                    .split(System.lineSeparator());
    var toString  = doe.toString();

    assertEquals(toString, regel[0]);
    assertTrue(regel[1].contains(getClass().getName()));
  }

  @Test
  public void testGetStackTrace2() {
    var regel     = ExceptionLogUtil.getStackTrace(fnfe)
                                    .split(System.lineSeparator());
    var toString  = fnfe.toString();

    assertEquals(toString, regel[0]);
    assertTrue(regel[1].contains(getClass().getName()));
  }

  @Test
  public void testGetStackTrace3() {
    var regel     = ExceptionLogUtil.getStackTrace(te)
                                    .split(System.lineSeparator());
    var toString  = te.toString();

    assertEquals(toString, regel[0]);
    assertTrue(regel[1].contains(getClass().getName()));
  }
}
