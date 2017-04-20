package com.ydzb.core.entity.search.exception;

import com.ydzb.core.entity.search.SearchOperator;

public final class InvlidSearchOperatorException extends SearchException {

	private static final long serialVersionUID = 4281740487177305315L;

	public InvlidSearchOperatorException(String searchProperty, String operatorStr) {
        this(searchProperty, operatorStr, null);
    }

    public InvlidSearchOperatorException(String searchProperty, String operatorStr, Throwable cause) {
        super("Invalid Search Operator searchProperty [" + searchProperty + "], " +
                "operator [" + operatorStr + "], must be one of " + SearchOperator.toStringAllOperator(), cause);
    }
}
