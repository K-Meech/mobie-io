/*
 * #%L
 * BigDataViewer core classes with minimal dependencies
 * %%
 * Copyright (C) 2012 - 2016 Tobias Pietzsch, Stephan Saalfeld, Stephan Preibisch,
 * Jean-Yves Tinevez, HongKee Moon, Johannes Schindelin, Curtis Rueden, John Bogovic
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package org.embl.mobie.io.ome.zarr.loaders.xml;


import mpicbg.spim.data.XmlHelpers;
import mpicbg.spim.data.generic.sequence.AbstractSequenceDescription;
import mpicbg.spim.data.generic.sequence.ImgLoaderIo;
import mpicbg.spim.data.generic.sequence.XmlIoBasicImgLoader;
import org.embl.mobie.io.ome.zarr.loaders.N5S3OMEZarrImageLoader;
import org.jdom2.Element;

import java.io.File;
import java.io.IOException;

import static mpicbg.spim.data.XmlKeys.IMGLOADER_FORMAT_ATTRIBUTE_NAME;

@ImgLoaderIo(format = "ome.n5.zarr.s3", type = N5S3OMEZarrImageLoader.class)
public class XmlIoN5S3ZarrImageLoader implements XmlIoBasicImgLoader<N5S3OMEZarrImageLoader> {
    public static final String SERVICE_ENDPOINT = "ServiceEndpoint";
    public static final String SIGNING_REGION = "SigningRegion";
    public static final String BUCKET_NAME = "BucketName";
    public static final String KEY = "Key";

    @Override
    public Element toXml(final N5S3OMEZarrImageLoader imgLoader, final File basePath) {
        final Element elem = new Element("ImageLoader");
        elem.setAttribute(IMGLOADER_FORMAT_ATTRIBUTE_NAME, "ome.n5.zarr.s3");
        elem.setAttribute("version", "1.0");
        elem.setAttribute(SERVICE_ENDPOINT, imgLoader.getServiceEndpoint());
        elem.setAttribute(SIGNING_REGION, imgLoader.getSigningRegion());
        elem.setAttribute(BUCKET_NAME, imgLoader.getBucketName());
        elem.setAttribute(KEY, imgLoader.getKey());

        return elem;
    }

    @Override
    public N5S3OMEZarrImageLoader fromXml(final Element elem, final File basePath, final AbstractSequenceDescription<?, ?, ?> sequenceDescription) {
//		final String version = elem.getAttributeValue( "version" );

        final String serviceEndpoint = XmlHelpers.getText(elem, SERVICE_ENDPOINT);
        final String signingRegion = XmlHelpers.getText(elem, SIGNING_REGION);
        final String bucketName = XmlHelpers.getText(elem, BUCKET_NAME);
        final String key = XmlHelpers.getText(elem, KEY);
        try {
            return new N5S3OMEZarrImageLoader(serviceEndpoint, signingRegion, bucketName, key, "/", sequenceDescription);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
