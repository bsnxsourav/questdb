/*******************************************************************************
 *    ___                  _   ____  ____
 *   / _ \ _   _  ___  ___| |_|  _ \| __ )
 *  | | | | | | |/ _ \/ __| __| | | |  _ \
 *  | |_| | |_| |  __/\__ \ |_| |_| | |_) |
 *   \__\_\\__,_|\___||___/\__|____/|____/
 *
 * Copyright (C) 2014-2016 Appsicle
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 ******************************************************************************/

package com.questdb.net.http.handlers;

import com.questdb.factory.JournalFactory;
import com.questdb.factory.configuration.JournalConfiguration;
import com.questdb.misc.Chars;
import com.questdb.net.http.ContextHandler;
import com.questdb.net.http.IOContext;
import com.questdb.net.http.ResponseSink;

import java.io.IOException;

public class ExistenceCheckHandler implements ContextHandler {

    private final JournalConfiguration configuration;

    public ExistenceCheckHandler(final JournalFactory factory) {
        this.configuration = factory.getConfiguration();
    }

    @Override
    public void handle(IOContext context) throws IOException {
        CharSequence journalName = context.request.getUrlParam("j");
        if (journalName == null) {
            context.simpleResponse().send(400);
        } else {
            JournalConfiguration.JournalExistenceCheck check = configuration.exists(journalName);
            if (Chars.equalsNc("json", context.request.getUrlParam("f"))) {
                ResponseSink r = context.responseSink();
                r.status(200, "application/json");
                r.put('{').putQuoted("status").put(':').putQuoted(check.name()).put('}');
                r.flush();
            } else {
                context.simpleResponse().send(200, check.name());
            }
        }
    }

    @Override
    public void resume(IOContext context) throws IOException {
        // nothing to do
    }

    @Override
    public void setupThread() {
    }
}
