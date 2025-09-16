package br.com.hospidata.gateway_service.service.exceptions;

public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException(String message){
        super(message);
    }

}
