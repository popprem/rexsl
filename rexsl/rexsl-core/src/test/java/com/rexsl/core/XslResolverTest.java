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
package com.rexsl.core;

import javax.ws.rs.ext.ContextResolver;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * XslResolver test case.
 * @author Yegor Bugayenko (yegor@rexsl.com)
 * @version $Id$
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ XslResolver.class, JAXBContext.class })
public final class XslResolverTest {

    /**
     * Let's test.
     * @throws Exception If something goes wrong
     */
    @Test
    public void testInstantiatesMarshaller() throws Exception {
        final ContextResolver<Marshaller> resolver = new XslResolver();
        final Marshaller mrsh = resolver.getContext(XslResolverTest.Page.class);
        MatcherAssert.assertThat(mrsh, Matchers.notNullValue());
    }

    /**
     * Let's test.
     * @throws Exception If something goes wrong
     */
    @Test(expected = IllegalStateException.class)
    public void testMarshallerException() throws Exception {
        PowerMockito.mockStatic(JAXBContext.class);
        Mockito.when(JAXBContext.newInstance((Class) Mockito.anyObject()))
            .thenThrow(new JAXBException(""));
        new XslResolver().getContext(Object.class);
    }

    /**
     * Let's test.
     * @throws Exception If something goes wrong
     */
    @Test(expected = IllegalStateException.class)
    public void testCreateMarshallerException() throws Exception {
        PowerMockito.mockStatic(JAXBContext.class);
        final JAXBContext context = Mockito.mock(JAXBContext.class);
        Mockito.when(context.createMarshaller())
            .thenThrow(new JAXBException(""));
        Mockito.when(JAXBContext.newInstance(Mockito.any(Class.class)))
            .thenReturn(context);
        new XslResolver().getContext(Object.class);
    }

    /**
     * Let's verify that JAXBContext is not created twice.
     * @throws Exception If something goes wrong
     * @todo #3 This test is not working at the moment.
     */
    @Ignore
    @Test
    public void testDuplicatedMarshallerCreation() throws Exception {
        // PowerMockito.mockStatic(JAXBContext.class);
        // final JAXBContext context = mock(JAXBContext.class);
        // final Marshaller mrsh = mock(Marshaller.class);
        // doReturn(mrsh).when(context).createMarshaller();
        // when(JAXBContext.newInstance(anyString())).thenReturn(context);
        // final XslResolver resolver = new XslResolver();
        // final XslResolver spy = spy(resolver);
        // spy.getContext(Object.class);
        // verify(spy, times(1)).createContext();
        // reset(spy);
        // spy.getContext(Object.class);
        // verify(spy, times(0)).createContext();
    }

    @XmlRootElement(name = "page")
    @XmlAccessorType(XmlAccessType.NONE)
    public static final class Page {
        /**
         * Simple name.
         * @return The name
         */
        @XmlElement(name = "name")
        public String getName() {
            return "some name";
        }
    }

}
