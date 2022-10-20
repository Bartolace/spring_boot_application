package github.com.Bartolace.rest.controller;
//vai receber requisicoes http, dentro da arqtitura rest //ctrl alt o apaga os importes sem uso

import github.com.Bartolace.domain.entity.Cliente;
import github.com.Bartolace.domain.repository.Clientes;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ClienteController {
    /*@RequestMapping(
            value = "/hello/{nome}", // pode receber uma array de string
            method = RequestMethod.GET //url do metodo e o tipo de requisicao
    )
    é substituido por @GetMapping
    */
    private Clientes clientes;

    public ClienteController (Clientes clientes){
        this.clientes = clientes;
    }



    @GetMapping("/api/clientes/{id}")
    @ResponseBody // definicao da reposta, o retorno
    //pathVariable indica que o parametro virá da url
    public ResponseEntity<Cliente> getClienteById(@PathVariable Integer id ){
        Optional<Cliente> cliente = clientes.findById(id);

        if (cliente.isPresent()){
            return ResponseEntity.ok(cliente.get()); // responseEntity traz um status do processo, o corpo da resposta

        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/api/clientes")
    @ResponseBody
    public ResponseEntity save(@RequestBody Cliente cliente){ //resquestBody -> pendencia que vai receber como parametro
        Cliente clienteSalvo  = clientes.save(cliente);
        return ResponseEntity.ok(clienteSalvo);
    }

    @DeleteMapping("/api/clientes/{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable Integer id ){
        Optional<Cliente> cliente = clientes.findById(id);

        if (cliente.isPresent()){
            clientes.delete(cliente.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/api/clientes/{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable Integer id,
                                 @RequestBody Cliente cliente){
        return clientes
                .findById(id)
                .map( clienteExistente -> {
                    cliente.setId( clienteExistente.getId());
                    clientes.save(cliente);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/api/clientes/")
    public ResponseEntity find(Cliente filtro){
        ExampleMatcher matcher = ExampleMatcher // classe para estipular as maneira de encontrar as propriedades
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher); // classe Example já pega os parametros de cliente e verifica se são existentes e qual campo a entrada direciona
        List<Cliente> lista = clientes.findAll(example);
        return ResponseEntity.ok(lista);
    }

}
