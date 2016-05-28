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

package com.questdb.ql.ops;

import com.questdb.factory.configuration.ColumnMetadata;
import com.questdb.factory.configuration.RecordColumnMetadata;
import com.questdb.ql.AggregatorFunction;
import com.questdb.ql.Record;
import com.questdb.ql.impl.map.MapRecordValueInterceptor;
import com.questdb.ql.impl.map.MapValues;
import com.questdb.std.ObjList;
import com.questdb.std.ObjectFactory;
import com.questdb.store.ColumnType;

public final class VwapAggregator extends AbstractBinaryOperator implements AggregatorFunction, MapRecordValueInterceptor {

    public static final ObjectFactory<Function> FACTORY = new ObjectFactory<Function>() {
        @Override
        public Function newInstance() {
            return new VwapAggregator();
        }
    };

    private final static ColumnMetadata INTERNAL_COL_AMOUNT = new ColumnMetadata().setName("$sumAmt").setType(ColumnType.DOUBLE);
    private final static ColumnMetadata INTERNAL_COL_QUANTITY = new ColumnMetadata().setName("$sumQty").setType(ColumnType.DOUBLE);
    private int sumAmtIdx;
    private int sumQtyIdx;
    private int vwap;

    private VwapAggregator() {
        super(ColumnType.DOUBLE);
    }

    @Override
    public void beforeRecord(MapValues values) {
        values.putDouble(vwap, values.getDouble(sumAmtIdx) / values.getDouble(sumQtyIdx));
    }

    @Override
    public void calculate(Record rec, MapValues values) {
        double price = lhs.getDouble(rec);
        double quantity = rhs.getDouble(rec);
        if (values.isNew()) {
            values.putDouble(sumAmtIdx, price * quantity);
            values.putDouble(sumQtyIdx, quantity);
        } else {
            values.putDouble(sumAmtIdx, values.getDouble(sumAmtIdx) + price * quantity);
            values.putDouble(sumQtyIdx, values.getDouble(sumQtyIdx) + quantity);
        }
    }

    @Override
    public void prepare(ObjList<RecordColumnMetadata> columns, int offset) {
        columns.add(INTERNAL_COL_AMOUNT);
        columns.add(INTERNAL_COL_QUANTITY);
        columns.add(new ColumnMetadata().setName(getName()).setType(ColumnType.DOUBLE));
        sumAmtIdx = offset;
        sumQtyIdx = offset + 1;
        vwap = offset + 2;
    }
}
