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

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.ComponentInstance;

/**
 * Mock {@link ComponentContext} implementation.
 */
class MockComponentContext implements ComponentContext {

  private final MockBundleContext bundleContext;
  private final Dictionary<String, Object> properties;

  public MockComponentContext(final MockBundleContext mockBundleContext) {
    this(mockBundleContext, new Hashtable<String, Object>());
  }

  public MockComponentContext(final MockBundleContext mockBundleContext, final Dictionary<String, Object> properties) {
    this.bundleContext = mockBundleContext;
    this.properties = properties;
  }

  @Override
  public Dictionary<String, Object> getProperties() {
    return this.properties;
  }

  @Override
  public Object locateService(final String name, final ServiceReference reference) {
    return this.bundleContext.locateService(name, reference);
  }

  @Override
  public BundleContext getBundleContext() {
    return this.bundleContext;
  }

  // --- unsupported operations ---
  @Override
  public void disableComponent(final String name) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void enableComponent(final String name) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ComponentInstance getComponentInstance() {
    throw new UnsupportedOperationException();
  }

  @Override
  public ServiceReference getServiceReference() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Bundle getUsingBundle() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Object locateService(final String name) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Object[] locateServices(final String name) {
    throw new UnsupportedOperationException();
  }

}
