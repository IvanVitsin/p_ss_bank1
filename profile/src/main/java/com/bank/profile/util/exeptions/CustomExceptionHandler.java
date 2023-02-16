package com.bank.profile.util.exeptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Этот класс представляет собой CustomExceptionHandler, который расширяет класс ResponseEntityExceptionHandler из среды Spring.
 * <p>
 * Он обеспечивает настраиваемую обработку ошибок для исключений, возникающих в приложении.
 * Логирование происходит с помощью ламбока аннотацией @Slf4j с выводом в консоль.
 * <p>
 *
 * @author Sazonov Vladimir
 * @version 1.0
 * @since 12.02.2023
 */
@Slf4j
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * Этот метод обрабатывает общее исключение, которое может возникнуть в приложении.
     * Метод регистрирует сообщение об ошибке и возвращает ответ  об ошибке.
     *
     * @param ex      Выброшенное исключение
     * @param request Объект WebRequest, содержащий информацию о запросе.
     * @return ResponseEntity, обьъект содержащий сведения об ошибке
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception ex, WebRequest request) throws Exception {
        log.error("Exception during execution of application", ex);
        return handleException(ex, request);
    }

    /**
     * Этот метод обрабатывает HttpMessageNotReadableException. Это исключение возникает, когда  запроса не
     * в ожидаемом формате.
     *
     * @param ex      Выброшенное исключение HttpMessageNotReadableException
     * @param headers Объект HttpHeaders, содержащий информацию заголовка запроса.
     * @param status  HttpStatus, указывающий код состояния ответа.
     * @param request Объект WebRequest, содержащий информацию о запросе.
     * @return ResponseEntity, содержащий сведения об ошибке
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDto apiError = new ErrorDto("Неправильный формат  ожидаемого JSON формата", ex.getMessage());
        log.error("Ошибка до получения данных в контроллер", ex);
        return new ResponseEntity<>(apiError, status);
    }

    /**
     * Этот метод обрабатывает исключение MethodArgumentNotValidException. Это исключение возникает, когда аргумент метода не
     * невалидный.
     *
     * @param ex      Выброшенное исключение MethodArgumentNotValidException
     * @param headers Объект HttpHeaders, содержащий информацию заголовка запроса.
     * @param status  HttpStatus, указывающий код состояния ответа.
     * @param request Объект WebRequest, содержащий информацию о запросе.
     * @return ResponseEntity, содержащий сведения об ошибке
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        if (bindingResult.hasErrors()) {
            log.error("Ошибка до получения данных в контроллер (валидация)", ex);
            ErrorDto apiError = new ErrorDto("Передоваемый аргумент в методе не валидный", ex.getMessage());
            return new ResponseEntity<>(apiError, status);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Обрабатывает исключения  MethodArgumentTypeMismatchException.
     *
     * @param ex     Исключение, которое нужно обработать.
     * @param status Код состояния HTTP для ответа.
     * @return объект ResponseEntity, содержащий сведения об ошибке
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      HttpStatus status) {
        ErrorDto apiError = new ErrorDto();
        apiError.setError("The parameter  of value  could not be converted to type");
        apiError.setErrorDescription(ex.getMessage());
        log.error("Ошибка до получения данных в контроллер");
        return new ResponseEntity<>(apiError, status);
    }
}
