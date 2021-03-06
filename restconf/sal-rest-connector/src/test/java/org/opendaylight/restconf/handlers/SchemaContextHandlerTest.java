/*
 * Copyright (c) 2016 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.restconf.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.opendaylight.controller.md.sal.rest.common.TestRestconfUtils;
import org.opendaylight.restconf.handlers.SchemaContextHandler;
import org.opendaylight.yangtools.yang.model.api.SchemaContext;

/**
 * Tests for handling {@link SchemaContext}
 */
public class SchemaContextHandlerTest {

    private static final String PATH_FOR_ACTUAL_SCHEMA_CONTEXT = "/modules";
    private static final String PATH_FOR_NEW_SCHEMA_CONTEXT = "/modules/modules-behind-mount-point";

    private SchemaContextHandler schemaContextHandler;
    private SchemaContext schemaContext;

    @Before
    public void setup() throws Exception {
        this.schemaContextHandler = new SchemaContextHandler();

        this.schemaContext = TestRestconfUtils.loadSchemaContext(PATH_FOR_ACTUAL_SCHEMA_CONTEXT);
        this.schemaContextHandler.onGlobalContextUpdated(this.schemaContext);
    }

    /**
     * Testing init of {@link SchemaContextHandler}
     */
    @Test
    public void schemaContextHandlerImplInitTest() {
        assertNotNull("Handler should be created and not null", this.schemaContextHandler);
    }

    /**
     * Test getting actual {@link SchemaContext}.
     * <p>
     * Get <code>SchemaContext</code> from <code>SchemaContextHandler</code> and compare it to actual
     * <code>SchemaContext</code>.
     */
    @Test
    public void getSchemaContextTest() {
        assertEquals("SchemaContextHandler should has reference to actual SchemaContext",
                this.schemaContext, this.schemaContextHandler.get());
    }

    /**
     * Test updating of {@link SchemaContext}.
     * <p>
     * Create new <code>SchemaContext</code>, set it to <code>SchemaContextHandler</code> and check if
     * <code>SchemaContextHandler</code> reference to new <code>SchemaContext</code> instead of old one.
     */
    @Test
    public void onGlobalContextUpdateTest() throws Exception {
        // create new SchemaContext and update SchemaContextHandler
        final SchemaContext newSchemaContext = TestRestconfUtils.loadSchemaContext(PATH_FOR_NEW_SCHEMA_CONTEXT);
        this.schemaContextHandler.onGlobalContextUpdated(newSchemaContext);

        assertNotEquals("SchemaContextHandler should not has reference to old SchemaContext",
                this.schemaContext, this.schemaContextHandler.get());
        assertEquals("SchemaContextHandler should has reference to new SchemaContext",
                newSchemaContext, this.schemaContextHandler.get());
    }
}
