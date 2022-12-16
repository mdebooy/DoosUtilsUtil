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
import eu.debooy.doosutils.errorhandling.exception.base.DoosError;
import eu.debooy.doosutils.errorhandling.exception.base.DoosLayer;
import java.sql.SQLException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class PersistenceEJBExceptionUtilTest {
  @Test
  public void testTransform() {
    var peeu  = new PersistenceEJBExceptionUtil();
    var se    = new SQLException("Transform exception");
    var te    = peeu.transform(se);

    assertEquals(se, te.getCause());
    assertEquals(DoosError.RUNTIME_EXCEPTION, te.getDoosError());
    assertEquals(DoosLayer.PERSISTENCE, te.getDoosLayer());
    assertEquals("Transform exception", te.getMessage());
    assertTrue(te.isLoggable());
    assertFalse(te.isLogged());
    assertTrue(te instanceof TechnicalException);
    assertFalse(((TechnicalException)te).isRecoverableInTime());
 }
}
