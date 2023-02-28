package com.bank.antifraud.Exception;

import com.bank.antifraud.util.ErrorDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Controller
public class CustomErrorHandler implements ErrorController {
    private static final String PATH = "/error";
    ErrorAttributes errorAttributes;

    /**
     * Метод error сопоставляется с путем «/error» и возвращает объект ответа с подробностями ошибки в теле.
     * @param webRequest — объект веб-запроса.
     * @return ResponseEntity — Объект ответа с подробностями об ошибке.
     */
    @RequestMapping(CustomErrorHandler.PATH)
    public ResponseEntity<ErrorDTO> error(WebRequest webRequest) {
        Map<String, Object> attributes = errorAttributes.getErrorAttributes(
                webRequest,
                ErrorAttributeOptions.of(ErrorAttributeOptions.Include.EXCEPTION, ErrorAttributeOptions.Include.MESSAGE)
        );
        return ResponseEntity
                .status((Integer) attributes.get("status"))
                .body(ErrorDTO
                        .builder()
                        .error((String) attributes.get("error"))
                        .errorDescription((String) attributes.get("message"))
                        .build()
                );
    }
}
