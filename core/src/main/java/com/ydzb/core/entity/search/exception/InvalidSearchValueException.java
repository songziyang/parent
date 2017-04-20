package com.ydzb.core.entity.search.exception;


public final class InvalidSearchValueException extends SearchException {
	
	private static final long serialVersionUID = 2909768555495451777L;

	public InvalidSearchValueException(String searchProperty, String entityProperty, Object value) {
        this(searchProperty, entityProperty, value, null);
    }

    public InvalidSearchValueException(String searchProperty, String entityProperty, Object value, Throwable cause) {
        super("Invalid Search Value, searchProperty [" + searchProperty + "], " +
                "entityProperty [" + entityProperty + "], value [" + value + "]", cause);
    }

}
