package com.weddingplanner.wedding_planner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PaymentProcessingException extends WeddingPlannerException {
  public PaymentProcessingException(String message) {
    super(message);
  }

  public PaymentProcessingException(String message, Throwable cause) {
    super(message, cause);
  }
}