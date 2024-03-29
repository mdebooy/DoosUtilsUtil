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

package eu.debooy.doosutils.errorhandling.handler;

import eu.debooy.doosutils.errorhandling.exception.TechnicalException;
import eu.debooy.doosutils.errorhandling.exception.WrappedException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosError;
import eu.debooy.doosutils.errorhandling.exception.base.DoosException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosLayer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class DefaultEJBExceptionHandlerTest {
  private static final  String  BUSINESSHANDLER =
      "Business EJB Exception Handler";

  private static final  DefaultEJBExceptionHandler  sdee  =
      new DefaultEJBExceptionHandler("Default EJB Exception Handler",
                                     DoosLayer.PERSISTENCE, true);

  @Test
  public void testHandle16() {
    var he  = new RuntimeException("Runtime exception");

    try {
      sdee.handle(he);
      fail("TechnicalException not thrown");
    } catch (TechnicalException e) {
      // OK
    }
  }

  @Test
  public void testHandle17() {
    var he  = new Throwable("Throwable");

    try {
      sdee.handle(he);
    } catch (Throwable e) {
      return;
    }

    fail("Throwable not thrown");
  }

  @Test
  public void testHandle18() {
    var de  = new DoosException(DoosError.DUPLICATE_OBJECT, DoosLayer.BUSINESS,
                                "dOOS exception");

    try {
      sdee.handle(de);
      fail("WrappedException not thrown");
    } catch (WrappedException e) {
      // OK
    }
  }

  @Test
  public void testHandle19() {
    var t   = new Throwable();
    var we  = new WrappedException(DoosLayer.SYSTEM, t);

    try {
      sdee.handle(we);
      fail("TechnicalException not thrown");
    } catch (TechnicalException e) {
      // OK
    }
  }

  @Test
  public void testInit1() {
    var dee = new DefaultEJBExceptionHandler("Business EJB Exception Handler",
                                             DoosLayer.BUSINESS, true);

    assertEquals(DoosLayer.BUSINESS, dee.getLayer());
    assertEquals(BUSINESSHANDLER, dee.getName());
    assertTrue(dee.isObjectNotFoundPattern());
    assertTrue(dee instanceof DefaultEJBExceptionHandler);
  }

  @Test
  public void testInit2() {
    var dee = new DefaultEJBExceptionHandler("Business EJB Exception Handler",
                                             DoosLayer.PERSISTENCE, false);

    assertEquals(DoosLayer.PERSISTENCE, dee.getLayer());
    assertEquals(BUSINESSHANDLER, dee.getName());
    assertFalse(dee.isObjectNotFoundPattern());
    assertTrue(dee instanceof DefaultEJBExceptionHandler);
  }
}
