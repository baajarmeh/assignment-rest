package io.baay.assignment.exceptions;

import io.baay.assignment.models.APIErrorResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * GlobalExceptionHandler helps in handling exceptions across application with spring provided
 *
 * @see org.springframework.web.bind.annotation.ControllerAdvice
 */
@ControllerAdvice
public class GlobalExceptionHandler {
  private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(ServiceException.class)
  public ResponseEntity<APIErrorResponse> handleException(ServiceException exception) {
    LOGGER.error(exception.getMessage());
    APIErrorResponse apiErrorResponse =
        new APIErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Sorry...Please try again.");
    return new ResponseEntity<>(apiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(InvalidCriteriaException.class)
  public ResponseEntity<APIErrorResponse> handleInvalidCriteriaException(
      InvalidCriteriaException exception) {
    LOGGER.error(exception.getMessage());
    APIErrorResponse apiErrorResponse =
        new APIErrorResponse(
            HttpStatus.BAD_REQUEST, "Please validate filter criteria before sending request");
    return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
  }
}
