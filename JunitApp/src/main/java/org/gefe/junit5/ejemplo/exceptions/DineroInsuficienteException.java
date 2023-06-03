package org.gefe.junit5.ejemplo.exceptions;

public class DineroInsuficienteException extends RuntimeException{
    public DineroInsuficienteException(String message){
        super(message);
    }
}
