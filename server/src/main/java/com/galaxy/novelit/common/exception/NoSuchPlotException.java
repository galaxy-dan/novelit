package com.galaxy.novelit.common.exception;

public class NoSuchPlotException extends RuntimeException{

    public NoSuchPlotException() {
        super("해당 플롯이 없습니다! 찾으려는 플롯을 다시 확인해주세요!");
    }
}
