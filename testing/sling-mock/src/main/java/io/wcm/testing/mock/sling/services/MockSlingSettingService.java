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

import java.net.URL;
import java.util.Set;

import org.apache.sling.settings.SlingSettingsService;

import com.google.common.collect.ImmutableSet;

/**
 * Mock implementation of {@link SlingSettingsService}.
 */
public final class MockSlingSettingService implements SlingSettingsService {

  private Set<String> runModes;

  /**
   * Instantiate with no default run modes.
   */
  public MockSlingSettingService() {
    this(ImmutableSet.<String>of());
  }

  /**
   * Instantiate with given run modes
   * @param defaultRunModes Run modes
   */
  public MockSlingSettingService(Set<String> defaultRunModes) {
    this.runModes = defaultRunModes;
  }

  @Override
  public Set<String> getRunModes() {
    return ImmutableSet.copyOf(this.runModes);
  }

  public void setRunModes(Set<String> runModes) {
    this.runModes = runModes;
  }

  // --- unsupported operations ---
  @Override
  public String getAbsolutePathWithinSlingHome(String relativePath) {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getSlingId() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getSlingHomePath() {
    throw new UnsupportedOperationException();
  }

  @Override
  public URL getSlingHome() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getSlingName() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getSlingDescription() {
    throw new UnsupportedOperationException();
  }

}
