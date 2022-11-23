package github.com.Bartolace.rest;

import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

//classe guarda e mostra a mesagem de erro  dentro do campo errors

public class ApiErrors {
    @Getter
    private List<String> errors;

    public ApiErrors(String mensagemErro){
        this.errors = Arrays.asList(mensagemErro);
    }
}
