/**
 * Copyright 2010 Marco de Booij
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the Licence. You may
 * obtain a copy of the Licence at:
 *
 * http://www.osor.eu/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 */
package eu.debooy.doosutils.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.RemoteException;


/**
 * @author Marco de Booij
 */
public final class ExceptionLogUtil {
  private static final int MAX_DEPTH = 5;

  private ExceptionLogUtil() {}

  public static Throwable getMainException(Exception e) {
    if (e instanceof RemoteException) {
      RemoteException re    = (RemoteException) e;
      Throwable       cause = re.detail;
      if (cause instanceof Exception) {
        return cause;
      }
    }

    return e;
  }

  public static Throwable getRootCause(Throwable cause) {
    if (null == cause) {
      return null;
    }

    if (null != cause.getCause()) {
      return getRootCause(cause, 1);
    }

    return cause;
  }

  private static Throwable getRootCause(Throwable cause, int level) {
    if (level >= MAX_DEPTH) {
      return cause;
    }

    if (null != cause.getCause()) {
      return getRootCause(cause.getCause(), (level + 1));
    }

    return cause;
  }

  public static String getStackTrace(Throwable cause) {
    var     sw      = new StringWriter();
    cause.printStackTrace(new PrintWriter(sw));
    var     sb      = sw.getBuffer();
    String  result;
    if (sb.length() > 4000) {
      result  = sb.substring(0, 3999);
    } else {
      result  = sb.toString();
    }

    return result;
  }
}
