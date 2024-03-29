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
package io.wcm.config.core.management.impl;

import io.wcm.config.api.Application;
import io.wcm.config.api.management.ApplicationFinder;
import io.wcm.config.core.impl.ApplicationImpl;
import io.wcm.config.spi.ApplicationProvider;
import io.wcm.sling.commons.osgi.RankedServices;

import java.util.Map;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;

/**
 * Default implementation of {@link ApplicationFinder}.
 */
@Component(metatype = false, immediate = true)
@Service(ApplicationFinder.class)
public final class ApplicationFinderImpl implements ApplicationFinder {

  @Reference(name = "applicationProvider", referenceInterface = ApplicationProvider.class,
      cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, policy = ReferencePolicy.DYNAMIC)
  private final RankedServices<ApplicationProvider> applicationProviders = new RankedServices<>();

  @Override
  public Application find(Resource resource) {
    for (ApplicationProvider provider : applicationProviders) {
      if (provider.matches(resource)) {
        return new ApplicationImpl(provider.getApplicationId(), provider.getLabel());
      }
    }
    return null;
  }

  void bindApplicationProvider(ApplicationProvider service, Map<String, Object> props) {
    applicationProviders.bind(service, props);
  }

  void unbindApplicationProvider(ApplicationProvider service, Map<String, Object> props) {
    applicationProviders.unbind(service, props);
  }

}
