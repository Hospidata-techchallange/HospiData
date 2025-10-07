package br.com.hospidata.gateway_service.config;

import br.com.hospidata.gateway_service.service.exceptions.ResourceNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 404:
                return new ResourceNotFoundException("Resource not found for method: " + methodKey);
            case 400:
                return new BadRequestException("Bad request for method: " + methodKey);
            case 500:
                return new Exception("Internal server error for method: " + methodKey);
            default:
                return defaultDecoder.decode(methodKey, response);
        }
    }
}
