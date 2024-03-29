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
public class DAOExceptionTest {
  private static final  String  APPLICATIE  = "DAOExceptionTest";

  private static final  DuplicateObjectException  doe =
      new DuplicateObjectException(DoosLayer.PERSISTENCE,
                                   "Persistence exception");

  @Test
  public void testInit() {
    var de  = new DAOException(doe, APPLICATIE);

    assertEquals(APPLICATIE, de.getApplication());
    assertEquals("Persistence exception", de.getErrorMessage());
  }
}
