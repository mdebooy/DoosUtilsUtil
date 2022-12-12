/**
 * Copyright 2009 Marco de Booy
 *
 * Licensed under the EUPL, Version 1.0 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the Licence. You may
 * obtain a copy of the Licence at:
 *
 * http://ec.europa.eu/idabc/7330l5
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 */
package eu.debooy.doosutils.errorhandling.exception.base;

import eu.debooy.doosutils.DoosConstants;
import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * @author Marco de Booij
 */
public final class DoosExceptionHelper {
  private DoosExceptionHelper() {}

  public static String getStackTrace(Throwable t) {
    var sw  = new StringWriter();
    var pw  = new PrintWriter(sw);
    t.printStackTrace(pw);

    return sw.toString();
  }

  public static String convertParameter(Object object) {
    if (null == object) {
      return DoosConstants.NULL;
    }
    if (object instanceof String) {
      return ((String)object);
    }
    if (object instanceof Long) {
      return ((Long) object).toString();
    }
    if (object instanceof Double) {
      return ((Double)object).toString();
    }
    if (object instanceof Boolean) {
      return Boolean.toString((Boolean)object);
    }
    if (object instanceof Float) {
      return ((Float)object).toString();
    }
    return "";
  }

  public static String convertParameters(Object[] objects) {
    if (null == objects) {
      return DoosConstants.NULL;
    }

    var params  = new StringBuilder();
    for (var object : objects) {
      if (params.length() > 0) {
        params.append(", ");
      }
      params.append(convertParameter(object));
    }
    return params.toString();
  }
}
