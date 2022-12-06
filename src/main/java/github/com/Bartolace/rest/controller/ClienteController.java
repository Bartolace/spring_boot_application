package github.com.Bartolace.rest.controller;
//vai receber requisicoes http, dentro da arqtitura rest //ctrl alt o apaga os importes sem uso

import github.com.Bartolace.domain.entity.Cliente;
import github.com.Bartolace.domain.repository.Clientes;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private Clientes clientes;

    public ClienteController (Clientes clientes){
        this.clientes = clientes;
    }



    @GetMapping("{id}")
    public Cliente getClienteById(@PathVariable Integer id ){ //pathVariable indica que o parametro virá da url
        return clientes
                .findById(id)
                .orElseThrow( () ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Cliente não encontrado" ));

            // Por padrao RestController, retorna sozinho o status 200 //responseEntity traz um status do processo, o corpo da resposta
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save (@RequestBody @Valid Cliente cliente){ //resquestBody -> pendencia que vai receber como parametro
        return clientes.save(cliente);

    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id ){
        clientes.findById(id)
                .map( cliente -> {
                    clientes.delete(cliente);
                    return cliente;
                }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente não encontrado")
                );
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id,
                       @RequestBody @Valid Cliente cliente){
        clientes
                .findById(id)
                .map( clienteExistente -> {
                    cliente.setId( clienteExistente.getId()); //salva o id encontrado no cliente
                    clientes.save(cliente); // salva o resto do corpo do cliente
                    return clienteExistente;
                }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente não encontrado")
                );
    }

    @GetMapping
    public List<Cliente> find(Cliente filtro){
        ExampleMatcher matcher = ExampleMatcher // classe para estipular as maneira de encontrar as propriedades
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher); // classe Example já pega os parametros de cliente e verifica se são existentes e qual campo a entrada direciona // por nome ou por id..
        return clientes.findAll(example);
    }

}
