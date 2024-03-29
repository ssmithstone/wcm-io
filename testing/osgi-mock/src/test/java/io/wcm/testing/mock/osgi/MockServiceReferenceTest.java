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
import io.wcm.testing.mock.osgi.OsgiMetadataUtilTest.ServiceWithMetadata;

import java.util.Dictionary;
import java.util.Hashtable;

import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;

public class MockServiceReferenceTest {

  private BundleContext bundleContext;
  private ServiceReference serviceReference;
  private Object service;

  @Before
  public void setUp() {
    this.bundleContext = MockOsgi.newBundleContext();

    this.service = new Object();
    String clazz = String.class.getName();
    Dictionary<String, Object> props = new Hashtable<>();
    props.put("customProp1", "value1");

    this.bundleContext.registerService(clazz, this.service, props);
    this.serviceReference = this.bundleContext.getServiceReference(clazz);
  }

  @Test
  public void testBundle() {
    assertSame(this.bundleContext.getBundle(), this.serviceReference.getBundle());
  }

  @Test
  public void testServiceId() {
    assertNotNull(this.serviceReference.getProperty(Constants.SERVICE_ID));
  }

  @Test
  public void testProperties() {
    assertEquals(2, this.serviceReference.getPropertyKeys().length);
    assertEquals("value1", this.serviceReference.getProperty("customProp1"));
  }

  @Test
  public void testWithOsgiMetadata() {
    ServiceWithMetadata serviceWithMetadata = new OsgiMetadataUtilTest.ServiceWithMetadata();
    bundleContext.registerService((String)null, serviceWithMetadata, null);
    ServiceReference reference = this.bundleContext.getServiceReference(Comparable.class.getName());

    assertEquals(5000, reference.getProperty("service.ranking"));
    assertEquals("The Apache Software Foundation", reference.getProperty("service.vendor"));
    assertEquals("org.apache.sling.models.impl.injectors.OSGiServiceInjector", reference.getProperty("service.pid"));
  }

}
