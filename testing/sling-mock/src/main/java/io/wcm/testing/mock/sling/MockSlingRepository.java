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
package io.wcm.testing.mock.sling;

import io.wcm.testing.mock.jcr.MockJcr;

import javax.jcr.Credentials;
import javax.jcr.LoginException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;

import org.apache.sling.jcr.api.SlingRepository;

/**
 * Mock {@link SlingRepository} implementation.
 */
class MockSlingRepository implements SlingRepository {

  private final Repository delegate;

  public MockSlingRepository(final Repository delegate) {
    this.delegate = delegate;
  }

  @Override
  public Session loginAdministrative(final String workspace) throws RepositoryException {
    return login();
  }

  @Override
  public Session loginService(final String subServiceName, final String workspace) throws LoginException, RepositoryException {
    return login();
  }

  @Override
  public String getDefaultWorkspace() {
    return MockJcr.DEFAULT_WORKSPACE;
  }

  // delegated methods
  @Override
  public String[] getDescriptorKeys() {
    return this.delegate.getDescriptorKeys();
  }

  @Override
  public boolean isStandardDescriptor(final String key) {
    return this.delegate.isStandardDescriptor(key);
  }

  @Override
  public boolean isSingleValueDescriptor(final String key) {
    return this.delegate.isSingleValueDescriptor(key);
  }

  @Override
  public Value getDescriptorValue(final String key) {
    return this.delegate.getDescriptorValue(key);
  }

  @Override
  public Value[] getDescriptorValues(final String key) {
    return this.delegate.getDescriptorValues(key);
  }

  @Override
  public String getDescriptor(final String key) {
    return this.delegate.getDescriptor(key);
  }

  @Override
  public Session login(final Credentials credentials, final String workspaceName) throws LoginException, NoSuchWorkspaceException, RepositoryException {
    return this.delegate.login(credentials, workspaceName);
  }

  @Override
  public Session login(final Credentials credentials) throws LoginException, RepositoryException {
    return this.delegate.login(credentials);
  }

  @Override
  public Session login(final String workspaceName) throws LoginException, NoSuchWorkspaceException, RepositoryException {
    return this.delegate.login(workspaceName);
  }

  @Override
  public Session login() throws LoginException, RepositoryException {
    return this.delegate.login();
  }

}
