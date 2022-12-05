package github.com.Bartolace.rest.controller;

import github.com.Bartolace.exception.PedidoNaoEncontradoExceprion;
import github.com.Bartolace.exception.RegraNegocioException;
import github.com.Bartolace.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;
//teste
@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class) //marcar como tratador de erro
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handleRegraNegocioExeception(RegraNegocioException ex){
        String mensagemErro = ex.getMessage();
        return new ApiErrors(mensagemErro); //retorna o novo obj de erros criado para uso
    }

    @ExceptionHandler(PedidoNaoEncontradoExceprion.class)
    @ResponseStatus(NOT_FOUND)
    public ApiErrors handlePedidoNotFoundException (PedidoNaoEncontradoExceprion ex) {
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handleMethodNotValidException (MethodArgumentNotValidException ex){
        List<String> erros = ex.getBindingResult().getAllErrors()
                .stream()
                .map(erro -> erro.getDefaultMessage())
                .collect(Collectors.toList());
        return new ApiErrors(erros);
    }
}
