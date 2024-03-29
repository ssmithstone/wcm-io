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
package io.wcm.config.api;

import io.wcm.sling.commons.wrappers.ImmutableValueMapDecorator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Fluent API for building configuration parameter definitions.
 * @param <T> Parameter value type
 */
public final class ParameterBuilder<T> {

  private static final Pattern PARAMETER_NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9\\-\\_\\.]+$");
  private static final Pattern APPLICATION_ID_PATTERN = Pattern.compile("^(/[a-zA-Z0-9\\-\\_]+)+$");
  private static final Pattern OSGI_CONFIG_PROPERTY_PATTERN =
      Pattern.compile("^[a-zA-Z0-9\\-\\_\\.\\$]+\\:[a-zA-Z0-9\\-\\_\\.]+$");

  private static final Set<Class<?>> SUPPORTED_TYPES = new HashSet<>();
  static {
    SUPPORTED_TYPES.add(String.class);
    SUPPORTED_TYPES.add(String[].class);
    SUPPORTED_TYPES.add(Integer.class);
    SUPPORTED_TYPES.add(Long.class);
    SUPPORTED_TYPES.add(Double.class);
    SUPPORTED_TYPES.add(Boolean.class);
    SUPPORTED_TYPES.add(Map.class);
  }

  private String name;
  private Class<T> type;
  private String applicationId;
  private final Map<String, Object> properties = new HashMap<>();
  private String defaultOsgiConfigProperty;
  private T defaultValue;

  private ParameterBuilder() {
    // private constructor
  }

  /**
   * Create a new parameter builder.
   * @return Parameter builder
   */
  public static <T> ParameterBuilder<T> create() {
    return new ParameterBuilder<T>();
  }

  /**
   * Create a new parameter builder.
   * @param name Parameter name. Only characters, numbers, hyphen, underline and point are allowed.
   * @return Parameter builder
   */
  public static <T> ParameterBuilder<T> create(String name) {
    return new ParameterBuilder<T>()
        .name(name);
  }

  /**
   * Create a new parameter builder.
   * @param name Parameter name. Only characters, numbers, hyphen, underline and point are allowed.
   * @param type Parameter value type
   * @return Parameter builder
   */
  public static <T> ParameterBuilder<T> create(String name, Class<T> type) {
    return new ParameterBuilder<T>()
        .name(name)
        .type(type);
  }

  /**
   * Create a new parameter builder.
   * @param name Parameter name. Only characters, numbers, hyphen, underline and point are allowed.
   * @param type Parameter Value type.
   * @param applicationId Application Id. Has to be a conent path starting with "/".
   * @return Parameter builder
   */
  public static <T> ParameterBuilder<T> create(String name, Class<T> type, String applicationId) {
    return new ParameterBuilder<T>()
        .name(name)
        .type(type)
        .applicationId(applicationId);
  }

  /**
   * @param value Parameter name. Only characters, numbers, hyphen, underline and point are allowed.
   * @return this
   */
  public ParameterBuilder<T> name(String value) {
    if (value == null || !PARAMETER_NAME_PATTERN.matcher(value).matches()) {
      throw new IllegalArgumentException("Invalid name: " + value);
    }
    this.name = value;
    return this;
  }

  /**
   * @param value Value type.
   * @return this
   */
  public ParameterBuilder<T> type(Class<T> value) {
    if (value == null || !SUPPORTED_TYPES.contains(value)) {
      throw new IllegalArgumentException("Invalid type: " + value);
    }
    this.type = value;
    return this;
  }

  /**
   * @param value Application Id. Has to be a conent path starting with "/".
   * @return this
   */
  public ParameterBuilder<T> applicationId(String value) {
    if (value == null || !APPLICATION_ID_PATTERN.matcher(value).matches()) {
      throw new IllegalArgumentException("Invalid applicaitonId: " + value);
    }
    this.applicationId = value;
    return this;
  }

  /**
   * References OSGi configuration property which is checked for default value if this parameter is not set
   * in any configuration.
   * @param value OSGi configuration parameter name with syntax {serviceClassName}:{propertyName}
   * @return this
   */
  public ParameterBuilder<T> defaultOsgiConfigProperty(String value) {
    if (value == null || !OSGI_CONFIG_PROPERTY_PATTERN.matcher(value).matches()) {
      throw new IllegalArgumentException("Invalid value: " + value);
    }
    this.defaultOsgiConfigProperty = value;
    return this;
  }

  /**
   * @param value Default value if parameter is not set for configuration
   *          and no default value is defined in OSGi configuration
   * @return this
   */
  public ParameterBuilder<T> defaultValue(T value) {
    this.defaultValue = value;
    return this;
  }

  /**
   * Further properties for documentation and configuration of behavior in configuration editor.
   * @param map Property map. Is merged with properties already set in builder.
   * @return this
   */
  public ParameterBuilder<T> properties(Map<String, Object> map) {
    if (map == null) {
      throw new IllegalArgumentException("Map argument must not be null.");
    }
    this.properties.putAll(map);
    return this;
  }

  /**
   * Further property for documentation and configuration of behavior in configuration editor.
   * @param key Property key
   * @param value Property value
   * @return this
   */
  public ParameterBuilder<T> property(String key, Object value) {
    if (key == null) {
      throw new IllegalArgumentException("Key argument must not be null.");
    }
    this.properties.put(key, value);
    return this;
  }

  /**
   * Builds the parameter definition.
   * @return Parameter definition
   */
  public Parameter<T> build() {
    if (this.name == null) {
      throw new IllegalArgumentException("Name is missing.");
    }
    if (this.type == null) {
      throw new IllegalArgumentException("Type is missing.");
    }
    if (this.applicationId == null) {
      throw new IllegalArgumentException("Application Id is missing.");
    }
    return new Parameter<T>(
        this.name,
        this.type,
        this.applicationId,
        this.defaultOsgiConfigProperty,
        this.defaultValue,
        new ImmutableValueMapDecorator(this.properties));
  }

}
