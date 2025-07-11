package ai.wanaku.tool.jira;

import jakarta.enterprise.context.ApplicationScoped;

import ai.wanaku.core.capabilities.common.ParsedToolInvokeRequest;
import ai.wanaku.core.capabilities.tool.Client;
import ai.wanaku.core.config.provider.api.ConfigResource;
import ai.wanaku.core.exchange.ToolInvokeRequest;
import ai.wanaku.core.runtime.camel.CamelQueryParameterBuilder;

import java.util.Map;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.jira.JiraConstants;

@ApplicationScoped
public class JiraClient implements Client {

    private final ProducerTemplate producer;

    public JiraClient(CamelContext camelContext) {
        this.producer = camelContext.createProducerTemplate();
        this.producer.start();
    }

    @Override
    public Object exchange(ToolInvokeRequest request, ConfigResource configResource) {
        CamelQueryParameterBuilder parameterBuilder = new CamelQueryParameterBuilder(configResource);

        ParsedToolInvokeRequest parsedRequest =
                ParsedToolInvokeRequest.parseRequest(request.getUri(), request, parameterBuilder::build);

        final Map<String, Object> headers = Map.of(JiraConstants.ISSUE_KEY, request.getBody());

        return producer.requestBodyAndHeaders(parsedRequest.uri(), null, headers);
    }
}