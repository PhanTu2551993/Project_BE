package ra.pj05.advice;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ra.pj05.constants.EHttpStatus;
import ra.pj05.exception.CustomException;
import ra.pj05.model.dto.response.ResponseWrapper;


import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getFieldErrors().forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
		return ResponseEntity.badRequest().body(
				  ResponseWrapper.builder()
							 .eHttpStatus(EHttpStatus.FAILED)
							 .statusCode(HttpStatus.BAD_REQUEST.value())
							 .data(errors)
							 .build()
		);
	}
	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> handleCustomException(CustomException ex) {
		return new ResponseEntity<>(
				  ResponseWrapper.builder()
							 .eHttpStatus(EHttpStatus.FAILED)
							 .statusCode(ex.getHttpStatus().value())
							 .data(ex.getMessage())
							 .build(),
				  ex.getHttpStatus()
		);
	}
	
}
