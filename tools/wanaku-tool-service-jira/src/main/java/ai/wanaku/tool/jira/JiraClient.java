package ai.wanaku.tool.jira;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import ai.wanaku.core.exchange.ParsedToolInvokeRequest;
import ai.wanaku.core.exchange.ToolInvokeRequest;
import ai.wanaku.core.services.tool.Client;

import java.util.Map;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.jira.JiraConstants;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

@ApplicationScoped
public class JiraClient implements Client {
    private static final Logger LOG = Logger.getLogger(JiraClient.class);

    private final ProducerTemplate producer;

    @ConfigProperty(name = "wanaku.tool.jira.url")
    String jiraUrl;

    @ConfigProperty(name = "wanaku.tool.jira.access.token")
    String accessToken;

    public JiraClient(CamelContext camelContext) {
        this.producer = camelContext.createProducerTemplate();
    }

    @Override
    public Object exchange(ToolInvokeRequest request) {
        ParsedToolInvokeRequest parsedRequest = ParsedToolInvokeRequest.parseRequest(request);
        LOG.infof("Invoking tool at URI: %s", parsedRequest.uri());

        final Map<String, Object> headers = Map.of(JiraConstants.ISSUE_KEY, request.getBody());
        final String url = String.format("jira://fetchIssue?jiraUrl=%s&accessToken=%s", jiraUrl, accessToken);
        return producer.requestBodyAndHeaders(url, null, headers);
    }
}