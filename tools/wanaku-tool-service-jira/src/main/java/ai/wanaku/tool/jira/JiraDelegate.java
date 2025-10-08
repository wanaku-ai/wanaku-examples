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

package ai.wanaku.tool.jira;

import ai.wanaku.api.exceptions.InvalidResponseTypeException;
import ai.wanaku.api.exceptions.NonConvertableResponseException;
import ai.wanaku.core.capabilities.tool.AbstractToolDelegate;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.Version;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class JiraDelegate extends AbstractToolDelegate {

    @Override
    protected List<String> coerceResponse(Object response)
            throws InvalidResponseTypeException, NonConvertableResponseException {
        if (response == null) {
            throw new InvalidResponseTypeException("Invalid response type from the consumer: null");
        }

        if (response instanceof Issue issue) {
            JsonObject ret = new JsonObject();
            ret.put("summary", issue.getSummary());
            ret.put("status", issue.getStatus().getName());
            ret.put("resolution", issue.getResolution().getName());
            ret.put("description", issue.getDescription());
            ret.put("assignee", issue.getAssignee().getDisplayName());
            JsonArray fixVersionsArray = new JsonArray();
            for (Version version : issue.getFixVersions()) {
                JsonObject fixedVersion = new JsonObject();
                fixedVersion.put("version", version.getName());
                fixVersionsArray.add(fixedVersion);
            }
            ret.put("fixVersions", fixVersionsArray);

            JsonArray affectedVersions = new JsonArray();
            for (Version version : issue.getAffectedVersions()) {
                JsonObject affectedVersion = new JsonObject();
                affectedVersion.put("version", version.getName());
                affectedVersions.add(affectedVersion);
            }
            ret.put("affectedVersions", affectedVersions);

            return List.of(ret.toString());
        }

        // Here, convert the response from whatever format it is, to a String instance.
        throw new InvalidResponseTypeException(
                "The downstream service has not implemented the response coercion method");
    }
}
