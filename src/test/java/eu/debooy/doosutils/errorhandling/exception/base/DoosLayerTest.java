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
import static org.junit.Assert.assertEquals;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class DoosLayerTest {
  @Test
  public void testBusiness() {
    var layer = DoosLayer.BUSINESS;

    assertEquals("BUSINESS", layer.name());
    assertEquals(DoosConstants.NA, layer.getLayer());
  }

  @Test
  public void testPersistence() {
    var layer = DoosLayer.PERSISTENCE;

    assertEquals("PERSISTENCE", layer.name());
    assertEquals(DoosConstants.NA, layer.getLayer());
  }

  @Test
  public void testPresentation() {
    var layer = DoosLayer.PRESENTATION;

    assertEquals("PRESENTATION", layer.name());
    assertEquals(DoosConstants.NA, layer.getLayer());
  }

  @Test
  public void testSystem() {
    var layer = DoosLayer.SYSTEM;

    assertEquals("SYSTEM", layer.name());
    assertEquals(DoosConstants.NA, layer.getLayer());
  }

  @Test
  public void testUndefined() {
    var layer = DoosLayer.UNDEFINED;

    assertEquals("UNDEFINED", layer.name());
    assertEquals(DoosConstants.NA, layer.getLayer());
  }
}
