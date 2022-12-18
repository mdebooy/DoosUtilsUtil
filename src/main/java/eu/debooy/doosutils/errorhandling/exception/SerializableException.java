/**
 * Copyright (c) 2011 Marco de Booij
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
package eu.debooy.doosutils.errorhandling.exception;

import eu.debooy.doosutils.errorhandling.exception.base.DoosError;
import eu.debooy.doosutils.errorhandling.exception.base.DoosException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosLayer;
import eu.debooy.doosutils.errorhandling.exception.base.DoosRuntimeException;
import javax.ejb.ApplicationException;


/**
 * @author Marco de Booij
 */
@ApplicationException
public class SerializableException extends DoosRuntimeException {
  private static final  long  serialVersionUID  = 1L;

  private String  string;

  public SerializableException(DoosException source) {
    super(source.getDoosError(), source.getDoosLayer(),
          source.getMessage(), source);

    setString(source);
  }

  public SerializableException(DoosRuntimeException source) {
    super(source.getDoosError(), source.getDoosLayer(),
          source.getMessage(), source);

    setString(source);
  }

  public SerializableException(Throwable source) {
    super(DoosError.SERIALIZED_EXCEPTION, DoosLayer.UNDEFINED,
          source.getMessage(), wrap(source.getCause()));

    setString(source);
  }

  private void setString(Throwable source) {
    string = source.toString() + " [Wrapped in SerializableException]";
    setStackTrace(source.getStackTrace());
  }

  @Override
  public String toString() {
    return string;
  }

  private static SerializableException wrap(Throwable t) {
    if (null != t) {
      return new SerializableException(t);
    }

    return null;
  }
}
