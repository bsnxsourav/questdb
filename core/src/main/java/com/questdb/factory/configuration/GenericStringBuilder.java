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

package com.questdb.factory.configuration;

import com.questdb.misc.Numbers;
import com.questdb.store.ColumnType;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("CD_CIRCULAR_DEPENDENCY")
public class GenericStringBuilder extends AbstractGenericMetadataBuilder {

    public GenericStringBuilder(JournalStructure parent, ColumnMetadata meta) {
        super(parent, meta);
        meta.type = ColumnType.STRING;
        size(this.meta.avgSize);
    }

    public GenericStringBuilder buckets(int buckets) {
        this.meta.distinctCountHint = Numbers.ceilPow2(buckets) - 1;
        return this;
    }

    public GenericStringBuilder index() {
        this.meta.indexed = true;
        return this;
    }

    public GenericStringBuilder recordCountHint(int count) {
        parent.recordCountHint(count);
        return this;
    }

    private GenericStringBuilder size(int size) {
        this.meta.avgSize = size;
        this.meta.size = size + 4;
        return this;
    }
}
