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
package io.wcm.testing.mock.sling.loader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import io.wcm.testing.mock.osgi.MockOsgi;
import io.wcm.testing.mock.sling.MockSling;
import io.wcm.testing.mock.sling.ResourceResolverType;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import org.apache.commons.io.IOUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.mime.MimeTypeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.BundleContext;

import com.day.cq.commons.jcr.JcrConstants;

@RunWith(MockitoJUnitRunner.class)
public class ContentLoaderBinaryTest {

  private static final int SAMPLE_IMAGE_FILESIZE = 62;

  private BundleContext bundleContext;
  private ResourceResolver resourceResolver;
  private ContentLoader contentLoader;

  @Mock
  private MimeTypeService mimeTypeService;

  protected ResourceResolverType getResourceResolverType() {
    return ResourceResolverType.JCR_MOCK;
  }

  protected ResourceResolver newResourceResolver() {
    return MockSling.newResourceResolver(getResourceResolverType());
  }

  @Before
  public final void setUp() {
    bundleContext = MockOsgi.newBundleContext();
    bundleContext.registerService(MimeTypeService.class.getName(), mimeTypeService, new Hashtable());
    resourceResolver = newResourceResolver();
    contentLoader = new ContentLoader(this.resourceResolver, this.bundleContext);

    when(mimeTypeService.getMimeType("gif")).thenReturn("image/gif");
  }

  @Test
  public void testBinaryFile() throws IOException {
    contentLoader.binaryFile("/sample-image.gif", "/content/binary/sample-image.gif");

    Resource fileResource = resourceResolver.getResource("/content/binary/sample-image.gif");
    assertSampleImageFileSize(fileResource);
    assertMimeType(fileResource.getChild(JcrConstants.JCR_CONTENT), "image/gif");
  }

  @Test
  public void testBinaryFileWithMimeType() throws IOException {
    contentLoader.binaryFile("/sample-image.gif", "/content/binary/sample-image.gif", "mime/test");

    Resource fileResource = resourceResolver.getResource("/content/binary/sample-image.gif");
    assertSampleImageFileSize(fileResource);
    assertMimeType(fileResource.getChild(JcrConstants.JCR_CONTENT), "mime/test");
  }

  @Test
  public void testBinaryResource() throws IOException {
    contentLoader.binaryResource("/sample-image.gif", "/content/binary/sample-image.gif");

    Resource fileResource = resourceResolver.getResource("/content/binary/sample-image.gif");
    assertSampleImageFileSize(fileResource);
    assertMimeType(fileResource, "image/gif");
  }

  @Test
  public void testBinaryResourceWithMimeType() throws IOException {
    contentLoader.binaryResource("/sample-image.gif", "/content/binary/sample-image.gif", "mime/test");

    Resource fileResource = resourceResolver.getResource("/content/binary/sample-image.gif");
    assertSampleImageFileSize(fileResource);
    assertMimeType(fileResource, "mime/test");
  }

  private void assertSampleImageFileSize(Resource resource) throws IOException {
    InputStream is = resource.adaptTo(InputStream.class);
    assertNotNull("InputSteam is null for " + resource.getPath(), is);
    byte[] binaryData = IOUtils.toByteArray(is);
    assertEquals(SAMPLE_IMAGE_FILESIZE, binaryData.length);
  }

  private void assertMimeType(Resource resource, String mimeType) {
    assertNotNull(resource);
    assertEquals(mimeType, resource.getValueMap().get(JcrConstants.JCR_MIMETYPE, String.class));
  }

}