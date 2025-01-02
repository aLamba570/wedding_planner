package com.weddingplanner.wedding_planner.exception;


public class WeddingPlannerException extends RuntimeException {
  public WeddingPlannerException(String message) {
    super(message);
  }

  public WeddingPlannerException(String message, Throwable cause) {
    super(message, cause);
  }
}