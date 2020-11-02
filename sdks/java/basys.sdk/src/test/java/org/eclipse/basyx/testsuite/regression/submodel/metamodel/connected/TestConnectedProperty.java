package org.eclipse.basyx.testsuite.regression.submodel.metamodel.connected;

import static org.junit.Assert.assertEquals;
import java.util.Map;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.ConnectedProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.submodel.restapi.PropertyProvider;
import org.eclipse.basyx.testsuite.regression.vab.manager.VABConnectionManagerStub;
import org.eclipse.basyx.vab.modelprovider.map.VABMapProvider;
import org.eclipse.basyx.vab.support.TypeDestroyer;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if a ConnectedSingleProperty can be created and used correctly
 *
 * @author schnicke
 *
 */
public class TestConnectedProperty {

  IProperty prop;
  private static final int VALUE = 10;

  @Before
  public void build() {
    // Create PropertySingleValued containing the simple value
    Property propertyMeta = new Property(VALUE);
    Map<String, Object> destroyType = TypeDestroyer.destroyType(propertyMeta);
    prop =
        new ConnectedProperty(new VABConnectionManagerStub(new PropertyProvider(new VABMapProvider(destroyType))).connectToVABElement(""));
  }

  @Test
  public void testEmptyProperty() throws Exception {
    Property propertyMeta = new Property();
    propertyMeta.setValueType(PropertyValueTypeDef.String);
    Map<String, Object> destroyType = TypeDestroyer.destroyType(propertyMeta);
    prop =
        new ConnectedProperty(new VABConnectionManagerStub(new PropertyProvider(new VABMapProvider(destroyType))).connectToVABElement(""));
    prop.set("content");
    assertEquals("content", prop.get());
  }

  /**
   * Tests getting the value
   *
   * @throws Exception
   */
  @Test
  public void testGet() throws Exception {
    int val = (int) prop.get();
    assertEquals(VALUE, val);
  }

  /**
   * Tests if the value type can be correctly retrieved
   */
  @Test
  public void testValueTypeRetrieval() {
    String valueType = prop.getValueType();
    assertEquals(PropertyValueTypeDef.Integer.toString(), valueType);
  }

  /**
   * Test not failing with NullPointerException when valueType is null
   */
  @Test
  public void testGetValueTypeNull() {
    Property propertyMeta = new Property(null);
    Map<String, Object> destroyType = TypeDestroyer.destroyType(propertyMeta);
    IProperty conProp =
        new ConnectedProperty(new VABConnectionManagerStub(new PropertyProvider(new VABMapProvider(destroyType))).connectToVABElement(""));
    assertEquals(PropertyValueTypeDef.Null.getStandardizedLiteral(), conProp.getValueType());
  }

  /**
   * Tests setting the value
   *
   * @throws Exception
   */
  @Test
  public void testSet() throws Exception {
    prop.set(123);
    int val = (int) prop.get();
    assertEquals(123, val);
  }

}
