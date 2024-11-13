package com.ktdsuniversity.edu.hello_spring.common.exceptions;

public class PageNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -3742242088263318176L;

	public PageNotFoundException(String message) {
		super(message);
	}
}
