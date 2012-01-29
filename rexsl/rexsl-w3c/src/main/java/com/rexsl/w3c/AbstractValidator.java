/**
 * Copyright (c) 2011, ReXSL.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the ReXSL.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.rexsl.w3c;

import com.rexsl.test.RestTester;
import com.rexsl.test.TestResponse;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.Charset;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.CharEncoding;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;

/**
 * Abstract implementation of (X)HTML validator.
 *
 * @author Yegor Bugayenko (yegor@rexsl.com)
 * @version $Id$
 */
abstract class AbstractValidator {

    /**
     * Boundary for HTTP POST form data.
     */
    protected static final String BOUNDARY = "vV9olNqRj00PC4OIlM7";

    /**
     * Send request and return response.
     * @param
     */
    protected final TestResponse send(final URI uri, final String entity) {
        return RestTester.start(uri)
            .header(HttpHeaders.USER_AGENT, "ReXSL")
            .header(HttpHeaders.ACCEPT, "application/soap+xml")
            .header(HttpHeaders.CONTENT_LENGTH, entity.length())
            .header(
                HttpHeaders.CONTENT_TYPE,
                String.format(
                    "%s; boundary=%s",
                    MediaType.MULTIPART_FORM_DATA,
                    this.BOUNDARY
                )
            )
            .post("validating through W3C validator", entity)
            .assertStatus(HttpURLConnection.HTTP_OK);
    }

    /**
     * Convert HTML to HTTP FORM entity.
     * @param html The HTML document
     * @return The HTTP post body
     */
    protected String entity(final String name, final String file,
        final String content, final String type) {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            final MultipartEntity entity = new MultipartEntity(
                HttpMultipartMode.STRICT,
                this.BOUNDARY,
                Charset.forName(CharEncoding.UTF_8)
            );
            entity.addPart(
                name,
                new InputStreamBody(
                    IOUtils.toInputStream(content),
                    type,
                    file
                )
            );
            entity.writeTo(stream);
            return stream.toString(CharEncoding.UTF_8);
        } catch (java.io.UnsupportedEncodingException ex) {
            throw new IllegalArgumentException(ex);
        } catch (java.io.IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

}
