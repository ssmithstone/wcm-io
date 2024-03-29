/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2014 wcm.io
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package io.wcm.testing.mock.osgi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.Dictionary;
import java.util.Hashtable;

import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;

public class MockComponentContextTest {

  private ComponentContext componentContext;

  @Before
  public void setUp() {
    this.componentContext = MockOsgi.newComponentContext();
  }

  @Test
  public void testBundleContext() {
    assertNotNull(this.componentContext.getBundleContext());
  }

  @Test
  public void testInitialProperties() {
    assertEquals(0, this.componentContext.getProperties().size());
  }

  @Test
  public void testProvidedProperties() {
    Dictionary<String,Object> props = new Hashtable<>();
    props.put("prop1", "value1");
    props.put("prop2", 25);
    ComponentContext componentContextWithProperties = MockOsgi.newComponentContext(props);

    Dictionary contextProps = componentContextWithProperties.getProperties();
    assertEquals(2, contextProps.size());
    assertEquals("value1", contextProps.get("prop1"));
    assertEquals(25, contextProps.get("prop2"));
  }

  @Test
  public void testLocateService() {
    // prepare test service
    String clazz = String.class.getName();
    Object service = new Object();
    this.componentContext.getBundleContext().registerService(clazz, service, null);
    ServiceReference ref = this.componentContext.getBundleContext().getServiceReference(clazz);

    // test locate service
    Object locatedService = this.componentContext.locateService(null, ref);
    assertSame(service, locatedService);
  }

}
