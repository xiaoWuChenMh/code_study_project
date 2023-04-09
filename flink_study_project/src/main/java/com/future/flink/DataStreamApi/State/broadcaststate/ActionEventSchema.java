package com.future.flink.DataStreamApi.State.broadcaststate;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.alibaba.fastjson.JSON;
import com.future.flink.DataStreamApi.State.broadcaststate.model.Action;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;

/**
 * The serialization schema for the {@link } type. This class defines how to transform a
 * Kafka record's bytes to a {@link }, and vice-versa.
 */
public class ActionEventSchema implements DeserializationSchema<Action>, SerializationSchema<Action> {

    private static final long serialVersionUID = 6154188370181669758L;

    @Override
    public byte[] serialize(Action event) {
        return event.toString().getBytes();
    }

    @Override
    public Action deserialize(byte[] message) throws IOException {
        return JSON.parseObject(message, Action.class);
    }

    @Override
    public boolean isEndOfStream(Action nextElement) {
        return false;
    }

    @Override
    public TypeInformation<Action> getProducedType() {
        return TypeInformation.of(Action.class);
    }
}
