package com.ktdsuniversity.edu.hello_spring.common.exceptions;

public class FileNotExistsException extends RuntimeException{

	private static final long serialVersionUID = -3321624745595131158L;
	
	public FileNotExistsException(String message) {
		super(message);
	}

}
