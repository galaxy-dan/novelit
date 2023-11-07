package com.galaxy.novelit.common.exception;

public class LengthOutOfLimit extends RuntimeException{
    public LengthOutOfLimit(int length){
        super("정해진 길이를 벗어났습니다. " + length + "이하로 작성 해주세요");
    }
}
