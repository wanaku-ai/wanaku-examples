package ai.wanaku.tool.duckduckgo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import ai.wanaku.core.exchange.ParsedToolInvokeRequest;
import ai.wanaku.core.exchange.ToolInvokeRequest;
import ai.wanaku.core.services.config.WanakuToolConfig;
import ai.wanaku.core.services.tool.Client;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.jboss.logging.Logger;

@ApplicationScoped
public class DuckduckgoClient implements Client {
    private static final Logger LOG = Logger.getLogger(DuckduckgoClient.class);

    private final ProducerTemplate producer;

    @Inject
    WanakuToolConfig config;

    public DuckduckgoClient(CamelContext camelContext) {
        this.producer = camelContext.createProducerTemplate();
    }

    @Override
    public Object exchange(ToolInvokeRequest request) {
        producer.start();

        String baseUri = config.baseUri();
        ParsedToolInvokeRequest parsedRequest = ParsedToolInvokeRequest.parseRequest(baseUri, request);

        LOG.infof("Invoking tool at URI: %s", parsedRequest.uri());

        return producer.requestBody(parsedRequest.uri(), parsedRequest.body(), String.class);
    }
}