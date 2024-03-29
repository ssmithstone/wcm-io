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

import static org.junit.Assert.assertNotNull;
import io.wcm.testing.mock.jcr.MockJcr;

import javax.jcr.RepositoryException;

import org.apache.sling.jcr.api.SlingRepository;
import org.junit.Before;
import org.junit.Test;

public class MockSlingRepositoryTest {

  private SlingRepository repository;

  @Before
  public void setUp() {
    this.repository = new MockSlingRepository(MockJcr.newRepository());
  }

  @SuppressWarnings("deprecation")
  @Test
  public void testLogin() throws RepositoryException {
    assertNotNull(this.repository.loginAdministrative(MockJcr.DEFAULT_WORKSPACE));
    assertNotNull(this.repository.loginService("test", MockJcr.DEFAULT_WORKSPACE));
  }

}
