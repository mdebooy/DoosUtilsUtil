/**
 * Copyright (c) 2009 Marco de Booy
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


/**
 * @author Marco de Booij
 */
public abstract class DoosRuntimeException extends RuntimeException
    implements IDoosException {
  private static final long serialVersionUID = 1L;

  private final DoosError error;
  private final DoosLayer layer;
  private       boolean   loggable;
  private       boolean   logged    = false;

  protected DoosRuntimeException(DoosError error, DoosLayer layer,
                                 boolean loggable, String message,
                                 Throwable cause) {
    super(message, cause);
    this.error    = error;
    this.layer    = layer;
    this.loggable = loggable;
  }

  protected DoosRuntimeException(DoosError error, DoosLayer layer,
                                 String message, Throwable cause) {
    this(error, layer, true, message, cause);
  }

  protected DoosRuntimeException(DoosError error, DoosLayer layer,
                                 boolean loggable, String message) {
    this(error, layer, loggable, message, null);
  }

  protected DoosRuntimeException(DoosError error, DoosLayer layer,
                                 String message) {
    this(error, layer, true, message, null);
  }

  @Override
  public DoosError getDoosError() {
    return error;
  }

  @Override
  public DoosLayer getDoosLayer() {
    return layer;
  }

  @Override
  public boolean isLogged() {
    return logged;
  }

  @Override
  public boolean isLoggable() {
    return loggable;
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
    return "DoosRuntimeException ( " + super.toString() + "    "  + "layer = "
           + layer + "    " + "error = " + error + " )";
  }
}
