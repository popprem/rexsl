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
package com.rexsl.maven;

import java.io.File;

/**
 * Abstract check.
 *
 * @author Yegor Bugayenko (yegor@rexsl.com)
 * @version $Id$
 */
public abstract class AbstractCheck implements Check {

    /**
     * Base directory of maven project.
     */
    private File basedir;

    /**
     * Reporter.
     */
    private Reporter reporter;

    /**
     * Classloader, pre-configured by Maven.
     */
    private ClassLoader loader;

    /**
     * Public ctor.
     * @param dir Home directory of maven project
     * @param rep The reporter
     * @param ldr Classloader
     */
    public AbstractCheck(final File dir, final Reporter rep,
        final ClassLoader ldr) {
        if (dir == null) {
            throw new IllegalArgumentException("basedir can't be NULL");
        }
        this.basedir = dir;
        if (rep == null) {
            throw new IllegalArgumentException("reporter can't be NULL");
        }
        this.reporter = rep;
        if (ldr == null) {
            throw new IllegalArgumentException("loader can't be NULL");
        }
        this.loader = ldr;
    }

    /**
     * Get basedir.
     * @return The directory
     */
    protected final File basedir() {
        return this.basedir;
    }

    /**
     * Get reporter.
     * @return The reporter
     */
    protected final Reporter reporter() {
        return this.reporter;
    }

    /**
     * Get classloader.
     * @return The classloader
     */
    protected final ClassLoader classloader() {
        return this.loader;
    }

}
