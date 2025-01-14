package com.tz.uandes.utils;

//Manejo de error personal
@SuppressWarnings("serial")
public class Error extends RuntimeException {
	public Error(String message) {
        super(message);
    }
}
