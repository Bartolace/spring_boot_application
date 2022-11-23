package github.com.Bartolace.rest.controller;

import github.com.Bartolace.domain.entity.Produto;
import github.com.Bartolace.domain.repository.Produtos;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.*; // importa para não ficar passando HttpStatus.


@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private Produtos repository;

    public ProdutoController(Produtos repository){
        this.repository = repository;
    }


    @PostMapping
    @ResponseStatus(CREATED)
    public Produto save( @RequestBody Produto produto){
        return repository.save(produto);
    }

    @GetMapping("{id}")
    public Produto getProdutoById(@PathVariable Integer id){
        return repository
                .findById(id)
                .orElseThrow( () ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Produto não encontrado") );
    }

    //Buscar por filtros
    @GetMapping
    public List<Produtos> find(Produto filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id){
        repository.findById(id)
                .map( produto -> {
                    repository.delete(produto);
                    return Void.TYPE;
                }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Produto não encontrado"));

    }

    @PutMapping({"{id}"})
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable Integer id,
                       @RequestBody Produto produto){
        repository
                .findById(id)
                .map( produtoExistente -> {
                    produto.setId( produtoExistente.getId());
                    repository.save(produto);
                    return produtoExistente;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente não encontrado"));
    }




}
