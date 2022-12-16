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

package eu.debooy.doosutils.errorhandling.handler.interceptor;

import eu.debooy.doosutils.errorhandling.handler.base.ExceptionHandlerFactory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class PersistenceExceptionHandlerInterceptorTest {
  @Test
  public void testInit1() {
    var pehi  = new PersistenceExceptionHandlerInterceptor();

    assertNotNull(pehi);
    assertEquals("PersistenceExceptionHandlerInterceptor",
                 pehi.getClass().getSimpleName());
  }

  @Test
  public void testInit2() {
    var bh    = ExceptionHandlerFactory.getBusinessHandler();
    var pehi  = new PersistenceExceptionHandlerInterceptor(bh);

    assertNotNull(pehi);
    assertEquals("PersistenceExceptionHandlerInterceptor",
                 pehi.getClass().getSimpleName());
  }
}
