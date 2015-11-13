package cs412.project.controller;

import cs412.project.response.JSONResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.xml.ws.Response;

/**
 * Created by Tom on 13/11/2015.
 */
@ControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<String> handle404(){

		JSONResponse<String> response = new JSONResponse<>();

		response.setSuccessful(false);
		response.setMessage("404, not found at all");

		return new ResponseEntity<>(response.toString(), HttpStatus.NOT_FOUND);

	}
}
