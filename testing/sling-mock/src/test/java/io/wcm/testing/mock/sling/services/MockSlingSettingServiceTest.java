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
package io.wcm.testing.mock.sling.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;

public class MockSlingSettingServiceTest {

  @Test
  public void testDefaultRunModes() {
    Set<String> defaultRunModes = ImmutableSet.<String>builder().add("mode0").build();
    MockSlingSettingService underTest = new MockSlingSettingService(defaultRunModes);
    assertEquals(defaultRunModes, underTest.getRunModes());

    Set<String> newRunModes = ImmutableSet.<String>builder().add("mode1").add("mode2").build();
    underTest.setRunModes(newRunModes);
    assertEquals(newRunModes, underTest.getRunModes());
  }

  @Test
  public void testNoDefaultRunModes() {
    MockSlingSettingService underTest = new MockSlingSettingService();
    assertTrue(underTest.getRunModes().isEmpty());

    Set<String> newRunModes = ImmutableSet.<String>builder().add("mode1").add("mode2").build();
    underTest.setRunModes(newRunModes);
    assertEquals(newRunModes, underTest.getRunModes());
  }

}
