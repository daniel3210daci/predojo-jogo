package com.jogo.exception;

import java.io.Serializable;
import java.util.List;

public class BusinessEntityViolationException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<String> messages;
	
	public BusinessEntityViolationException(String message) {
		super(message);
	}
	
	public BusinessEntityViolationException(List<String> messages) {
		this.messages = messages;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
}

