/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.hbase.index.covered.filter;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.filter.FilterBase;

/**
 * Server-side only class used in the indexer to filter out keyvalues newer than a given timestamp
 * (so allows anything <code><=</code> timestamp through).
 * <p>
 * Note,<tt>this</tt> doesn't support {@link #write(DataOutput)} or {@link #readFields(DataInput)}.
 */
public class NewerTimestampFilter extends FilterBase {

  private long timestamp;

  public NewerTimestampFilter(long timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public ReturnCode filterKeyValue(KeyValue ignored) {
    return ignored.getTimestamp() > timestamp ? ReturnCode.SKIP : ReturnCode.INCLUDE;
  }

  @Override
  public void write(DataOutput out) throws IOException {
    throw new UnsupportedOperationException("TimestampFilter is server-side only!");
  }
  @Override
  public void readFields(DataInput in) throws IOException {
    throw new UnsupportedOperationException("TimestampFilter is server-side only!");
  }
}