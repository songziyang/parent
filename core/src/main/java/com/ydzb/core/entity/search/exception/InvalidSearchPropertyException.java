package com.ydzb.core.entity.search.exception;

public final class InvalidSearchPropertyException extends SearchException {

	private static final long serialVersionUID = 2031578428372337026L;

	public InvalidSearchPropertyException(String searchProperty, String entityProperty) {
        this(searchProperty, entityProperty, null);
    }

    public InvalidSearchPropertyException(String searchProperty, String entityProperty, Throwable cause) {
        super("Invalid Search Property [" + searchProperty + "] Entity Property [" + entityProperty + "]", cause);
    }


}
