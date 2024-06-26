/**
 * Copyright (c) 2012 Marco de Booij
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
package eu.debooy.doosutils.conversie;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * @deprecated Vervangen door SerializationUtils ((de)serialization)
 *              van org.apache.commons.lang3
 *
 * @author Marco de Booij
 */
@Deprecated(since = "2.1.3", forRemoval = true)
public final class ByteArray {
  private ByteArray() {}

  /**
   * @deprecated
   *
   * @param byteArray
   * @return
   * @throws IOException
   * @deprecated
   */
  @Deprecated(since = "2.1.3", forRemoval = true)
  public static Object byteArrayToObject(byte[] byteArray) throws IOException {
    var               bais    = new ByteArrayInputStream(byteArray);
    ObjectInputStream ois;
    Object            object  = null;
    try {
      ois     = new ObjectInputStream(bais);
      object  = ois.readObject();
    } catch (ClassNotFoundException e) {
      // Hoogst onwaarschijnlijk. Maak er dus maar een IO Exception van.
      throw new IOException(e);
    }

    return object;
  }

  @Deprecated(since = "2.1.3", forRemoval = true)
  public static byte[] toByteArray(Object object) throws IOException {
    var baos  = new ByteArrayOutputStream();
    var oos   = new ObjectOutputStream(baos);
    oos.writeObject(object);

    return baos.toByteArray();
  }
}