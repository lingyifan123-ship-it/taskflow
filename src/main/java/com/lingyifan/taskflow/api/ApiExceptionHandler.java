package com.lingyifan.taskflow.api;
import org.springframework.http.*; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.annotation.*; import java.time.Instant; import java.util.*;
@RestControllerAdvice
public class ApiExceptionHandler {
    record ErrorBody(Instant timestamp,int status,String message,Map<String,String> fields){}
    @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<ErrorBody> validation(MethodArgumentNotValidException e){Map<String,String> fields=new LinkedHashMap<>();e.getBindingResult().getFieldErrors().forEach(x->fields.put(x.getField(),x.getDefaultMessage()));return ResponseEntity.badRequest().body(new ErrorBody(Instant.now(),400,"参数校验失败",fields));}
    @ExceptionHandler({IllegalArgumentException.class,IllegalStateException.class}) ResponseEntity<ErrorBody> bad(RuntimeException e){return ResponseEntity.badRequest().body(new ErrorBody(Instant.now(),400,e.getMessage(),Map.of()));}
    @ExceptionHandler(SecurityException.class) ResponseEntity<ErrorBody> forbidden(SecurityException e){return ResponseEntity.status(403).body(new ErrorBody(Instant.now(),403,e.getMessage(),Map.of()));}
}
