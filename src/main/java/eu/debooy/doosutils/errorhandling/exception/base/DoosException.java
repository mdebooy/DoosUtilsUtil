/**
 * Copyright (c) 2009 Marco de Booij
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
package eu.debooy.doosutils.errorhandling.exception.base;


/**
 * @author Marco de Booij
 */
public class DoosException extends Exception
    implements IDoosException {
  private static final long serialVersionUID = 1L;

  private final DoosError error;
  private final DoosLayer layer;
  @SuppressWarnings("java:S1165")
  private       boolean   loggable;
  @SuppressWarnings("java:S1165")
  private       boolean   logged    = false;

  public DoosException(DoosError error, DoosLayer layer, boolean loggable,
                       String message, Throwable cause) {
    super(message, cause);
    this.error    = error;
    this.layer    = layer;
    this.loggable = loggable;
  }

  public DoosException(DoosError error, DoosLayer layer, String message,
                       Throwable cause) {
    this(error, layer, true, message, cause);
  }

  public DoosException(DoosError error, DoosLayer layer, boolean loggable,
                       String message) {
    this(error, layer, loggable, message, null);
  }

  public DoosException(DoosError error, DoosLayer layer, String message) {
    this(error, layer, true, message, null);
  }

  @Override
  public DoosError getDoosError() {
    return this.error;
  }

  @Override
  public DoosLayer getDoosLayer() {
    return this.layer;
  }

  @Override
  public boolean isLogged() {
    return this.logged;
  }

  @Override
  public boolean isLoggable() {
    return this.loggable;
  }

  @Override
  public String getStackTraceAsString() {
    return DoosExceptionHelper.getStackTrace(this);
  }

  @Override
  public void setLoggable(boolean loggable) {
    this.loggable = loggable;
  }

  @Override
  public void setLoggedTrue() {
    logged  = true;
  }

  @Override
  public String toString() {
    return "DoosException ( " + super.toString() + "    " + "    "
           + "layer = " + this.layer + "    " + "error = " + this.error + " )";
  }
}
