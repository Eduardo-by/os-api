package com.eduardo.os.resource.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessade> errors = new ArrayList<>();


	public List<FieldMessade> getErrors() {
		return errors;
	}

	public void addErrors(String fieldName, String message) {
		this.errors.add(new FieldMessade(fieldName,message));
	}

	public ValidationError(List<FieldMessade> errors) {
		super();
		this.errors = errors;
	}

	public ValidationError() {
		super();
	}

	public ValidationError(Long timestamp, Integer status, String error) {
		super(timestamp, status, error);
		// TODO Auto-generated constructor stub
	}
	}
