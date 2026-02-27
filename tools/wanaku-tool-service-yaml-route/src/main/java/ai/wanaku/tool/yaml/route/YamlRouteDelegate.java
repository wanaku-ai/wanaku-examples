package ai.wanaku.tool.yaml.route;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import ai.wanaku.capabilities.sdk.api.exceptions.InvalidResponseTypeException;
import ai.wanaku.core.capabilities.tool.AbstractToolDelegate;

@ApplicationScoped
public class YamlRouteDelegate extends AbstractToolDelegate {

    protected List<String> coerceResponse(Object response) throws InvalidResponseTypeException {
        if (response != null) {
            return List.of(response.toString());
        }

        throw new InvalidResponseTypeException("The response is null");
    }
}
