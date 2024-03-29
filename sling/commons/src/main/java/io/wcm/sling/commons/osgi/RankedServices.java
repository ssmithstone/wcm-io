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
package io.wcm.sling.commons.osgi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.sling.commons.osgi.ServiceUtil;

/**
 * Helper class that collects all services registered via OSGi bind/unbind methods.
 * Implementation is thread-safe.
 * @param <T> Service type
 */
public final class RankedServices<T> implements Iterable<T> {

  private final SortedMap<Comparable<Object>, T> serviceMap = new TreeMap<>();
  private volatile Collection<T> sortedServices = Collections.emptyList();

  /**
   * Handle bind service event.
   * @param service Service instance
   * @param props Service reference properties
   */
  public void bind(T service, Map<String, Object> props) {
    synchronized (serviceMap) {
      serviceMap.put(ServiceUtil.getComparableForServiceRanking(props), service);
      updateSortedServices();
    }
  }

  /**
   * Handle unbind service event.
   * @param service Service instance
   * @param props Service reference properties
   */
  public void unbind(T service, Map<String, Object> props) {
    synchronized (serviceMap) {
      serviceMap.remove(ServiceUtil.getComparableForServiceRanking(props));
      updateSortedServices();
    }
  }

  /**
   * Update list of sorted services by copying it from the array and making it unmodifiable.
   */
  private void updateSortedServices() {
    List<T> copiedList = new ArrayList<T>(serviceMap.values());
    sortedServices = Collections.unmodifiableList(copiedList);
  }

  /**
   * @return List of all services registered in OSGi, sorted by service ranking.
   */
  public Collection<T> get() {
    return sortedServices;
  }

  @Override
  public Iterator<T> iterator() {
    return sortedServices.iterator();
  }

}
