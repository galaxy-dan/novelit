package com.galaxy.novelit.common.exception;

public class EditRefusedException extends RuntimeException{
	public EditRefusedException(){
		super("현재 편집이 불가능합니다!");
	}
}
