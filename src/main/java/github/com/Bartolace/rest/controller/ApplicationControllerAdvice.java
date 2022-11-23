package github.com.Bartolace.rest.controller;

import github.com.Bartolace.exception.RegraNegocioException;
import github.com.Bartolace.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class) //marcar como tratador de erro
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handleRegraNegocioExeception(RegraNegocioException ex){
        String mensagemErro = ex.getMessage();
        return new ApiErrors(mensagemErro); //retorna o novo obj de erros criado para uso
    }
}
