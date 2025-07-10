package ai.wanaku.tool.duckduckgo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import ai.wanaku.core.capabilities.common.ParsedToolInvokeRequest;
import ai.wanaku.core.capabilities.config.WanakuServiceConfig;
import ai.wanaku.core.capabilities.tool.Client;
import ai.wanaku.core.config.provider.api.ConfigResource;
import ai.wanaku.core.exchange.ToolInvokeRequest;
import ai.wanaku.core.runtime.camel.CamelQueryParameterBuilder;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.jboss.logging.Logger;

@ApplicationScoped
public class DuckduckgoClient implements Client {
    private static final Logger LOG = Logger.getLogger(DuckduckgoClient.class);

    private final ProducerTemplate producer;

    @Inject
    WanakuServiceConfig config;

    public DuckduckgoClient(CamelContext camelContext) {
        this.producer = camelContext.createProducerTemplate();
    }

    @Override
    public Object exchange(ToolInvokeRequest request, ConfigResource configResource) {
        producer.start();

        CamelQueryParameterBuilder parameterBuilder = new CamelQueryParameterBuilder(configResource);
        ParsedToolInvokeRequest parsedRequest =
                ParsedToolInvokeRequest.parseRequest(request.getUri(), request, parameterBuilder::build);

        LOG.infof("Invoking tool at URI: %s", parsedRequest.uri());

        return producer.requestBody(parsedRequest.uri(), parsedRequest.body(), String.class);
    }
}