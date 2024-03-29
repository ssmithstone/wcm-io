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
package io.wcm.config.core.impl;

import static org.junit.Assert.assertEquals;
import io.wcm.config.api.Application;

import org.junit.Before;
import org.junit.Test;

public class ApplicationImplTest {

  private static final String APPLICATION_ID = "app1";
  private static final String LABEL = "Application #1";

  private Application underTest;

  @Before
  public void setUp() throws Exception {
    underTest = new ApplicationImpl(APPLICATION_ID, LABEL);
  }

  @Test
  public void testGetApplicationId() throws Exception {
    assertEquals(APPLICATION_ID, underTest.getApplicationId());
  }

  @Test
  public void testGetLabel() throws Exception {
    assertEquals(LABEL, underTest.getLabel());
  }

}
