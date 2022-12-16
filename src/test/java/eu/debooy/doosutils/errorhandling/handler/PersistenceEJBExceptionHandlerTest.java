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

import eu.debooy.doosutils.errorhandling.exception.DuplicateObjectException;
import eu.debooy.doosutils.errorhandling.exception.FileNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.IllegalArgumentException;
import eu.debooy.doosutils.errorhandling.exception.MultipleObjectFoundException;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.TechnicalException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosError;
import eu.debooy.doosutils.errorhandling.exception.base.DoosLayer;
import java.sql.SQLException;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class PersistenceEJBExceptionHandlerTest {
  private static final  String  PERSISTENCEHANDLER  =
      "Persistence EJB Exception Handler";

  private static final  DuplicateObjectException        doe   =
      new DuplicateObjectException(DoosLayer.PERSISTENCE,
                                   "Persistence exception");
  private static final  FileNotFoundException           fnfe  =
      new FileNotFoundException(DoosLayer.BUSINESS,
                                "File exception", doe);
  private static final  IllegalArgumentException        iae   =
      new IllegalArgumentException(DoosLayer.BUSINESS,
                                   "Illegal Argument exception", fnfe);
  private static final  MultipleObjectFoundException    mofe  =
      new MultipleObjectFoundException(DoosLayer.PERSISTENCE,
                                       "Multiple Object Found exception", iae);
  private static final  ObjectNotFoundException         onfe  =
      new ObjectNotFoundException(DoosLayer.PERSISTENCE,
                                  "Object Not Found exception", mofe);
  private static final  SQLException                    se    =
      new SQLException("SQLException exception");
  private static final  PersistenceEJBExceptionHandler  spee  =
      new PersistenceEJBExceptionHandler("Persistence EJB Exception Handler",
                                         DoosLayer.PERSISTENCE, true);
//  private static final  Throwable                       t     = new Throwable();
  private static final  TechnicalException              te    =
      new TechnicalException(DoosError.RUNTIME_EXCEPTION, DoosLayer.PERSISTENCE,
                             "Technical exception", onfe);

  @Test
  public void testFindRootCause0() {
    var t = PersistenceEJBExceptionHandler.findRootCause(te, 0);

    assertEquals("Technical exception", t.getMessage());
  }

  @Test
  public void testFindRootCause1() {
    var t = PersistenceEJBExceptionHandler.findRootCause(te, 1);

    assertEquals("Object Not Found exception", t.getMessage());
  }

  @Test
  public void testFindRootCause2() {
    var t = PersistenceEJBExceptionHandler.findRootCause(te, 2);

    assertEquals("Multiple Object Found exception", t.getMessage());
  }

  @Test
  public void testFindRootCause3() {
    var t = PersistenceEJBExceptionHandler.findRootCause(te, 3);

    assertEquals("Illegal Argument exception", t.getMessage());
  }

  @Test
  public void testFindRootCause4() {
    var t = PersistenceEJBExceptionHandler.findRootCause(te, 4);

    assertEquals("File exception", t.getMessage());
  }

  @Test
  public void testFindRootCause5() {
    var t = PersistenceEJBExceptionHandler.findRootCause(te, 5);

    assertEquals("Persistence exception", t.getMessage());
  }

  @Test
  public void testFindRootCause8() {
    var t = PersistenceEJBExceptionHandler.findRootCause(se, 0);

    assertEquals("SQLException exception", t.getMessage());
  }

  @Test
  public void testFindRootCause9() {
    var t = PersistenceEJBExceptionHandler.findRootCause(te, 9);

    assertEquals("Persistence exception", t.getMessage());
  }

  @Test
  public void testHandle1() {
    try {
      spee.handle(doe);
      fail("DuplicateObjectException not thrown");
    } catch (DuplicateObjectException e) {
      // OK
    }
  }

  @Test
  public void testHandle2() {
    try {
      spee.handle(fnfe);
      fail("FileNotFoundException not thrown");
    } catch (FileNotFoundException e) {
      // OK
    }
  }

  @Test
  public void testHandle3() {
    try {
      spee.handle(iae);
      fail("IllegalArgumentException not thrown");
    } catch (IllegalArgumentException e) {
      // OK
    }
  }

  @Test
  public void testHandle4() {
    try {
      spee.handle(mofe);
      fail("MultipleObjectFoundException not thrown");
    } catch (MultipleObjectFoundException e) {
      // OK
    }
  }

  @Test
  public void testHandle5() {
    try {
      spee.handle(onfe);
      fail("ObjectNotFoundException not thrown");
    } catch (ObjectNotFoundException e) {
      System.out.println(e.getMessage());
      // OK
    }
  }

  @Test
  public void testHandle7() {
    try {
      spee.handle(te);
      fail("TechnicalException not thrown");
    } catch (TechnicalException e) {
      // OK
    }
  }

  @Test
  public void testHandle11() {
    var he  = new NoResultException("No Result exception");

    try {
      spee.handle(he);
      fail("ObjectNotFoundException not thrown");
    } catch (ObjectNotFoundException e) {
      // OK
    }
  }

  @Test
  public void testHandle12() {
    var he  = new NonUniqueResultException("Non Unique Result exception");

    try {
      spee.handle(he);
      fail("MultipleObjectFoundException not thrown");
    } catch (MultipleObjectFoundException e) {
      // OK
    }
  }

  @Test
  public void testHandle13() {
    var he  = new EntityExistsException("Entity Exists exception");

    try {
      spee.handle(he);
      fail("DuplicateObjectException not thrown");
    } catch (DuplicateObjectException e) {
      // OK
    }
  }

  @Test
  public void testHandle15() {
    var he  = new PersistenceException("Persistence exception");

    try {
      spee.handle(he);
      fail("TechnicalException not thrown");
    } catch (TechnicalException e) {
      // OK
    }
  }

  @Test
  public void testHandle16() {
    var he  = new RuntimeException("Runtime exception");

    try {
      spee.handle(he);
      fail("TechnicalException not thrown");
    } catch (TechnicalException e) {
      // OK
    }
  }

  @Test
  public void testHandle17() {
    var he  = new Throwable("Throwable");

    try {
      spee.handle(he);
      fail("Throwable not thrown");
    } catch (Throwable e) {
      // OK
    }
  }

  @Test
  public void testInit1() {
    var pee =
        new PersistenceEJBExceptionHandler("Persistence EJB Exception Handler",
                                           DoosLayer.PERSISTENCE, true);

    assertEquals(DoosLayer.PERSISTENCE, pee.getLayer());
    assertEquals(PERSISTENCEHANDLER, pee.getName());
    assertTrue(pee.isObjectNotFoundPattern());
    assertTrue(pee instanceof PersistenceEJBExceptionHandler);
  }

  @Test
  public void testInit2() {
    var pee =
        new PersistenceEJBExceptionHandler("Persistence EJB Exception Handler",
                                           DoosLayer.PERSISTENCE, false);

    assertEquals(DoosLayer.PERSISTENCE, pee.getLayer());
    assertEquals(PERSISTENCEHANDLER, pee.getName());
    assertFalse(pee.isObjectNotFoundPattern());
    assertTrue(pee instanceof PersistenceEJBExceptionHandler);
  }
}
