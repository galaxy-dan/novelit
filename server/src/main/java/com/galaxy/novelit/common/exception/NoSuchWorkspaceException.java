package com.galaxy.novelit.common.exception;

public class NoSuchWorkspaceException extends RuntimeException{

	public NoSuchWorkspaceException() {
		super("없는 작품입니다!");
	}
}
