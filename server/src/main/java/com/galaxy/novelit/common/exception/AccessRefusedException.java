package com.galaxy.novelit.common.exception;

public class AccessRefusedException extends RuntimeException{
	public AccessRefusedException(){
		super("권한이 없습니다!");
	}
}