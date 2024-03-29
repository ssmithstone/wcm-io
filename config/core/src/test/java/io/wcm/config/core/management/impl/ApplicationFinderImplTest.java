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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import io.wcm.config.api.Application;
import io.wcm.config.spi.ApplicationProvider;

import java.util.Map;

import org.apache.sling.api.resource.Resource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.Constants;

import com.google.common.collect.ImmutableMap;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationFinderImplTest {

  @Mock
  private Resource resource;

  @Mock
  private ApplicationProvider applicationProvider1;
  private static final Map<String, Object> SERVICE_PROPS_1 =
      ImmutableMap.<String, Object>of(Constants.SERVICE_ID, 1L,
          Constants.SERVICE_RANKING, 10);
  private static final String APPLICATION_ID_1 = "app1";
  private static final String APPLICATION_LABEL_1 = "Application #1";

  @Mock
  private ApplicationProvider applicationProvider2;
  private static final Map<String, Object> SERVICE_PROPS_2 =
      ImmutableMap.<String, Object>of(Constants.SERVICE_ID, 2L,
          Constants.SERVICE_RANKING, 5);
  private static final String APPLICATION_ID_2 = "app2";
  private static final String APPLICATION_LABEL_2 = "Application #2";

  @InjectMocks
  private ApplicationFinderImpl underTest;

  @Before
  public void setUp() {
    underTest.bindApplicationProvider(applicationProvider1, SERVICE_PROPS_1);
    underTest.bindApplicationProvider(applicationProvider2, SERVICE_PROPS_2);

    when(applicationProvider1.getApplicationId()).thenReturn(APPLICATION_ID_1);
    when(applicationProvider1.getLabel()).thenReturn(APPLICATION_LABEL_1);
    when(applicationProvider1.matches(resource)).thenReturn(true);

    when(applicationProvider2.getApplicationId()).thenReturn(APPLICATION_ID_2);
    when(applicationProvider2.getLabel()).thenReturn(APPLICATION_LABEL_2);
    when(applicationProvider2.matches(resource)).thenReturn(false);
  }

  @After
  public void tearDown() {
    underTest.unbindApplicationProvider(applicationProvider1, SERVICE_PROPS_1);
    underTest.unbindApplicationProvider(applicationProvider2, SERVICE_PROPS_2);
  }

  @Test
  public void testFindResource() {
    Application app = underTest.find(resource);
    assertNotNull(app);
    assertEquals(APPLICATION_ID_1, app.getApplicationId());
    assertEquals(APPLICATION_LABEL_1, app.getLabel());

    verify(applicationProvider2, times(1)).matches(resource);
  }

}
