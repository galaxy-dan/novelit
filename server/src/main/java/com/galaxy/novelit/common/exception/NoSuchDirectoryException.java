package com.galaxy.novelit.common.exception;

public class NoSuchDirectoryException extends RuntimeException{
	public NoSuchDirectoryException(){
		super("존재하지 않는 디렉토리 혹은 파일입니다! 확인 후 다시 시도해주세요.");
	}
}
