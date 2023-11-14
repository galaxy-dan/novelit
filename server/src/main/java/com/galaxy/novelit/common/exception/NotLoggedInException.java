package com.galaxy.novelit.common.exception;

public class NotLoggedInException extends RuntimeException{
	public NotLoggedInException() {
		super("로그인 하지 않은 사용자입니다.");
	}
}
