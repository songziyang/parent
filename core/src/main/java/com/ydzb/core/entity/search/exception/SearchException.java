package com.ydzb.core.entity.search.exception;

import org.springframework.core.NestedRuntimeException;

public class SearchException extends NestedRuntimeException {

	private static final long serialVersionUID = 2947177424773154695L;

	public SearchException(String msg) {
        super(msg);
    }

    public SearchException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
