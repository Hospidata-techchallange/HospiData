package br.com.hospidata.gateway_service.service.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource, String field, String value) {
        super(String.format("%s not found with %s = '%s'", resource, field, value));
    }

    public ResourceNotFoundException(String resource) {
        super(String.format("%s", resource));
    }
}
