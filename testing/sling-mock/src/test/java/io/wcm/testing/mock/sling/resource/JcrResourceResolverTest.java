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
package io.wcm.testing.mock.sling.resource;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import io.wcm.testing.mock.sling.MockSling;
import io.wcm.testing.mock.sling.ResourceResolverType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.jackrabbit.JcrConstants;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

/**
 * Implements simple write and read resource and values test.
 * JCR API is used to create the test data.
 */
public class JcrResourceResolverTest {

  private static final String STRING_VALUE = "value1";
  private static final String[] STRING_ARRAY_VALUE = new String[] {
    "value1", "value2"
  };
  private static final int INTEGER_VALUE = 25;
  private static final double DOUBLE_VALUE = 3.555d;
  private static final boolean BOOLEAN_VALUE = true;
  private static final Date DATE_VALUE = new Date(10000);
  private static final Calendar CALENDAR_VALUE = Calendar.getInstance();
  private static final byte[] BINARY_VALUE = new byte[] {
    0x01, 0x02, 0x03, 0x04, 0x05, 0x06
  };

  private ResourceResolver resourceResolver;
  private Session session;
  protected Node testRoot;
  private static volatile long rootNodeCounter;

  protected ResourceResolverType getResourceResolverType() {
    return ResourceResolverType.JCR_MOCK;
  }

  protected ResourceResolver newResourceResolver() {
    return MockSling.newResourceResolver(getResourceResolverType());
  }

  @Before
  public final void setUp() throws RepositoryException {
    this.resourceResolver = newResourceResolver();
    this.session = this.resourceResolver.adaptTo(Session.class);

    // prepare some test data using JCR API
    Node rootNode = getTestRootNode();
    Node node1 = rootNode.addNode("node1", JcrConstants.NT_UNSTRUCTURED);

    node1.setProperty("stringProp", STRING_VALUE);
    node1.setProperty("stringArrayProp", STRING_ARRAY_VALUE);
    node1.setProperty("integerProp", INTEGER_VALUE);
    node1.setProperty("doubleProp", DOUBLE_VALUE);
    node1.setProperty("booleanProp", BOOLEAN_VALUE);
    node1.setProperty("dateProp", DateUtils.toCalendar(DATE_VALUE));
    node1.setProperty("calendarProp", CALENDAR_VALUE);
    node1.setProperty("binaryProp", this.session.getValueFactory().createBinary(new ByteArrayInputStream(BINARY_VALUE)));

    node1.addNode("node11", JcrConstants.NT_UNSTRUCTURED);
    node1.addNode("node12", JcrConstants.NT_UNSTRUCTURED);

    this.session.save();
  }

  @After
  public final void tearDown() {
    this.testRoot = null;
  }

  /**
   * Return a test root node, created on demand, with a unique path
   */
  private Node getTestRootNode() throws RepositoryException {
    if (this.testRoot == null) {
      final Node root = this.session.getRootNode();
      if (getResourceResolverType() == ResourceResolverType.JCR_JACKRABBIT) {
        final Node classRoot = root.addNode(getClass().getSimpleName());
        this.testRoot = classRoot.addNode(System.currentTimeMillis() + "_" + (rootNodeCounter++));
      }
      else {
        this.testRoot = root.addNode("test", JcrConstants.NT_UNSTRUCTURED);
      }
    }
    return this.testRoot;
  }

  @Test
  public void testGetResourcesAndValues() throws IOException, RepositoryException {
    Resource resource1 = this.resourceResolver.getResource(getTestRootNode().getPath() + "/node1");
    assertNotNull(resource1);
    assertEquals("node1", resource1.getName());

    ValueMap props = resource1.getValueMap();
    assertEquals(STRING_VALUE, props.get("stringProp", String.class));
    assertArrayEquals(STRING_ARRAY_VALUE, props.get("stringArrayProp", String[].class));
    assertEquals((Integer)INTEGER_VALUE, props.get("integerProp", Integer.class));
    assertEquals(DOUBLE_VALUE, props.get("doubleProp", Double.class), 0.0001);
    assertEquals(BOOLEAN_VALUE, props.get("booleanProp", Boolean.class));
    assertEquals(DATE_VALUE, props.get("dateProp", Date.class));
    assertEquals(CALENDAR_VALUE.getTime(), props.get("calendarProp", Calendar.class).getTime());

    Resource binaryPropResource = resource1.getChild("binaryProp");
    InputStream is = binaryPropResource.adaptTo(InputStream.class);
    byte[] dataFromResource = IOUtils.toByteArray(is);
    is.close();
    assertArrayEquals(BINARY_VALUE, dataFromResource);

    // read second time to ensure not the original input stream was returned
    InputStream is2 = binaryPropResource.adaptTo(InputStream.class);
    byte[] dataFromResource2 = IOUtils.toByteArray(is2);
    is2.close();
    assertArrayEquals(BINARY_VALUE, dataFromResource2);

    List<Resource> children = ImmutableList.copyOf(resource1.listChildren());
    assertEquals(2, children.size());
    assertEquals("node11", children.get(0).getName());
    assertEquals("node12", children.get(1).getName());
  }

}
