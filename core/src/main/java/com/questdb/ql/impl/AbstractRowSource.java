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

package com.questdb.ql.impl;

import com.questdb.ql.CancellationHandler;
import com.questdb.ql.RowCursor;
import com.questdb.ql.RowSource;
import com.questdb.ql.StorageFacade;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public abstract class AbstractRowSource implements RowSource, RowCursor {
    @SuppressFBWarnings({"ACEM_ABSTRACT_CLASS_EMPTY_METHODS"})
    @Override
    public void prepare(StorageFacade storageFacade, CancellationHandler cancellationHandler) {
    }
}
