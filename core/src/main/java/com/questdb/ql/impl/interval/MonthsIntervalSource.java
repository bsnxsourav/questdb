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

package com.questdb.ql.impl.interval;

import com.questdb.misc.Dates;
import com.questdb.misc.Interval;
import com.questdb.std.AbstractImmutableIterator;
import com.questdb.std.CharSink;

public class MonthsIntervalSource extends AbstractImmutableIterator<Interval> implements IntervalSource {
    private final Interval start;
    private final Interval next;
    private final int period;
    private final int count;
    private int pos;

    public MonthsIntervalSource(Interval start, int period, int count) {
        this.start = start;
        this.period = period;
        this.count = count;
        this.next = new Interval(start.getLo(), start.getHi());
    }

    @Override
    public boolean hasNext() {
        return pos < count;
    }

    @Override
    public Interval next() {
        if (pos++ == 0) {
            return start;
        } else {
            next.update(Dates.addMonths(next.getLo(), period), Dates.addMonths(next.getHi(), period));
            return next;
        }
    }

    @Override
    public void reset() {
        pos = 0;
        next.update(start.getLo(), start.getHi());
    }

    @Override
    public void toSink(CharSink sink) {
        sink.put('{');
        sink.putQuoted("op").put(':').putQuoted("MonthsIntervalSource").put(',');
        sink.putQuoted("interval").put(':').put(start).put(',');
        sink.putQuoted("period").put(':').put(period).put(',');
        sink.putQuoted("count").put(':').put(count);
        sink.put('}');
    }
}