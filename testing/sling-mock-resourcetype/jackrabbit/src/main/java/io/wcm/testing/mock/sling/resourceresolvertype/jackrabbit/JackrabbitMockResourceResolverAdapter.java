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
package io.wcm.testing.mock.sling.resourceresolvertype.jackrabbit;

import io.wcm.testing.mock.sling.spi.ResourceResolverTypeAdapter;

import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.testing.jcr.RepositoryProvider;
import org.apache.sling.jcr.api.SlingRepository;

/**
 * Resource resolver type adapter for Jackrabbit repository.
 */
public class JackrabbitMockResourceResolverAdapter implements ResourceResolverTypeAdapter {

  @Override
  public ResourceResolverFactory newResourceResolverFactory() {
    return null;
  }

  @Override
  public SlingRepository newSlingRepository() {
    try {
      return RepositoryProvider.instance().getRepository();
    }
    catch (RepositoryException ex) {
      throw new RuntimeException("Unable to get jackrabbit SlingRepository instance.", ex);
    }
  }

}
