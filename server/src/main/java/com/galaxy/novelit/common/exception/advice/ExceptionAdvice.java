package com.galaxy.novelit.common.exception.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.galaxy.novelit.common.exception.AccessRefusedException;
import com.galaxy.novelit.common.exception.NoSuchDirectoryException;
import com.galaxy.novelit.common.exception.WrongDirectoryTypeException;
import com.galaxy.novelit.common.exception.dto.ExceptionResDTO;

@ControllerAdvice
public class ExceptionAdvice {
	//존재하지 않는 directory
	@ExceptionHandler(NoSuchDirectoryException.class)
	public ResponseEntity<ExceptionResDTO> noSuchUserException(NoSuchDirectoryException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResDTO(e.getMessage()));
	}

	//권한 없을 때
	@ExceptionHandler(AccessRefusedException.class)
	public ResponseEntity<ExceptionResDTO> accessRefusedException(AccessRefusedException e) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionResDTO(e.getMessage()));
	}

	//파일이 필요한데 디렉토리가 나오거나 혹은 그 반대 경우
	@ExceptionHandler(WrongDirectoryTypeException.class)
	public ResponseEntity<ExceptionResDTO> wrongDirectoryTypeException(WrongDirectoryTypeException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResDTO(e.getMessage()));
	}
}
