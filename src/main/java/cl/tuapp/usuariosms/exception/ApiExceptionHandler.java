package cl.tuapp.usuariosms.exception;

import org.springframework.http.*; import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*; import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<?> notFound(NotFoundException ex){
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
  }
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> validation(MethodArgumentNotValidException ex){
    var first = ex.getBindingResult().getFieldErrors().stream().findFirst();
    var msg = first.map(f->f.getField()+" "+f.getDefaultMessage()).orElse("Validation error");
    return ResponseEntity.badRequest().body(Map.of("error", msg));
  }
  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> general(Exception ex){
    return ResponseEntity.status(500).body(Map.of("error", ex.getMessage()));
  }
}
