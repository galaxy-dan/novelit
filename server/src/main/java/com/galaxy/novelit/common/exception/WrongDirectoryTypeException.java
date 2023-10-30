package com.galaxy.novelit.common.exception;

public class WrongDirectoryTypeException extends RuntimeException{
	public WrongDirectoryTypeException(){
		super("잘못된 요청입니다!");
	}
}
