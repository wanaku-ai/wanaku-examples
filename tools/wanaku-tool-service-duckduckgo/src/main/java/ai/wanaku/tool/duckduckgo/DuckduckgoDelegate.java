/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.wanaku.tool.duckduckgo;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import ai.wanaku.api.exceptions.InvalidResponseTypeException;
import ai.wanaku.api.exceptions.NonConvertableResponseException;
import ai.wanaku.core.services.tool.AbstractToolDelegate;


@ApplicationScoped
public class DuckduckgoDelegate extends AbstractToolDelegate {

    @Override
    protected List<String> coerceResponse(Object response) throws InvalidResponseTypeException, NonConvertableResponseException {
        if (response == null) {
            throw new InvalidResponseTypeException("Invalid response type from the consumer: null");
        }

        if (response instanceof String json) {
            JsonObject fullResponse = new JsonObject(json);

            JsonObject ret = new JsonObject();
            final String anAbstract = fullResponse.getString("Abstract");
            if (anAbstract == null || anAbstract.isEmpty()) {
                return List.of("There were no results for the provided search term");
            }

            ret.put("abstract", anAbstract);
            ret.put("abstractSource", fullResponse.getString("AbstractSource"));
            ret.put("abstractSource", fullResponse.getString("AbstractURL"));
            ret.put("entity", fullResponse.getString("Entity"));
            final JsonArray relatedTopics = fullResponse.getJsonArray("RelatedTopics");
            if (relatedTopics != null) {
                ret.put("firstUrl", relatedTopics.getValue(0));
            }

            return List.of(ret.toString());
        }

        // Here, convert the response from whatever format it is, to a String instance.
        throw new InvalidResponseTypeException("The downstream service has not implemented the response coercion method");
    }
}
