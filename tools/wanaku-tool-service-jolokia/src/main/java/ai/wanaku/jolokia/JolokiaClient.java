package ai.wanaku.jolokia;

import jakarta.enterprise.context.ApplicationScoped;

import javax.management.MalformedObjectNameException;

import ai.wanaku.api.exceptions.WanakuException;
import ai.wanaku.core.exchange.ToolInvokeRequest;
import ai.wanaku.core.service.discovery.util.DiscoveryUtil;
import ai.wanaku.core.services.tool.Client;

import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.eclipse.microprofile.config.ConfigProvider;
import org.jboss.logging.Logger;
import org.jolokia.client.BasicAuthenticator;
import org.jolokia.client.J4pClient;
import org.jolokia.client.exception.J4pException;
import org.jolokia.client.request.J4pReadRequest;
import org.jolokia.client.request.J4pReadResponse;

@ApplicationScoped
public class JolokiaClient implements Client {
    private static final Logger LOG = Logger.getLogger(JolokiaClient.class);

    private record Attribute(String objectName, String attributeName) {}

    private Map<String, Attribute> attributes = Map.of(
            "memory",  new Attribute("java.lang:type=Memory", "HeapMemoryUsage"),
            "operating_system", new Attribute("java.lang:type=OperatingSystem", null),
            "os", new Attribute("java.lang:type=OperatingSystem", null),
            "operating system", new Attribute("java.lang:type=OperatingSystem", null)
            );

    public JolokiaClient() {

    }

    @Override
    public Object exchange(ToolInvokeRequest request) {
        String managedHost = ConfigProvider.getConfig().getConfigValue("jolokia.service.url").getValue();
        LOG.infof("Requesting attribute %s from %s", request.getArgumentsMap().get("attribute"), managedHost);

        String user = ConfigProvider.getConfig().getConfigValue("jolokia.user").getValue();
        String password = ConfigProvider.getConfig().getConfigValue("jolokia.password").getValue();

        // Execute and return the result
        Header originHeader = new BasicHeader("Origin", DiscoveryUtil.resolveRegistrationAddress());

        J4pClient client = J4pClient.url(managedHost)
                .user(user)
                .password(password)
                .authenticator(new BasicAuthenticator().preemptive())
                .setDefaultHttpHeaders(List.of(originHeader))
                .build();

        try {
            String askedAttribute = request.getArgumentsMap().get("attribute");
            Attribute attributeValue = attributes.get(askedAttribute.toLowerCase());

            J4pReadRequest joloRequest;
            if (attributeValue.attributeName != null) {
                joloRequest = new J4pReadRequest(attributeValue.objectName, attributeValue.attributeName);
            } else {
                joloRequest = new J4pReadRequest(attributeValue.objectName);
            }
            J4pReadResponse response = client.execute(joloRequest);
            return response.getValue().toString();
        } catch (MalformedObjectNameException | J4pException e) {
            throw new WanakuException(e);
        }
    }
}