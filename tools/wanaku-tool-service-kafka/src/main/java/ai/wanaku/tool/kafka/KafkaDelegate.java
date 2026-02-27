package ai.wanaku.tool.kafka;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import ai.wanaku.capabilities.sdk.api.exceptions.InvalidResponseTypeException;
import ai.wanaku.capabilities.sdk.api.exceptions.NonConvertableResponseException;
import ai.wanaku.core.capabilities.tool.AbstractToolDelegate;

@ApplicationScoped
public class KafkaDelegate extends AbstractToolDelegate {

    @Override
    protected List<String> coerceResponse(Object response)
            throws InvalidResponseTypeException, NonConvertableResponseException {
        if (response != null) {
            return List.of(response.toString());
        }

        throw new InvalidResponseTypeException("The response is null");
    }
}
