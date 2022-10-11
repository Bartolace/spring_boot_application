package github.com.Bartolace;

import github.com.Bartolace.domain.entity.Cliente;
import github.com.Bartolace.domain.entity.Pedido;
import github.com.Bartolace.domain.repository.Clientes;
import github.com.Bartolace.domain.repository.Pedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@RestController
public class VendasApplication {

    @Bean
    public CommandLineRunner init(
            @Autowired Clientes clientes,
            @Autowired Pedidos pedidos
    ){
        return args -> {
            System.out.println("Salvando clientes");
            Cliente fulano = new Cliente("Fulano");
            clientes.save(fulano);

            Pedido p = new Pedido();
            p.setCliente(fulano);
            p.setDataPedido(LocalDate.now());
            p.setTotal(BigDecimal.valueOf(100));
            pedidos.save(p);

            /*Cliente cliente = clientes.findClienteFetchPedidos(fulano.getId());
            System.out.println(cliente);
            System.out.println(cliente.getPedidos());*/

            pedidos.findByCliente(fulano).forEach(System.out::println);



            /*System.out.println("Atualizando clientes");
            todosCientes.forEach( c -> {
                c.setNome(c.getNome() + " Atualizado");
                clientes.save(c); // save salva e atualiza
            });
            todosCientes = clientes.findAll();
            todosCientes.forEach(System.out::println);

            System.out.println("Buscando clientes");
            clientes.findByNomeLike("bri").forEach(System.out::println);

            System.out.println("Deletando clientes");
            clientes.findAll().forEach(c -> {
                clientes.delete(c);
            });

            todosCientes = clientes.findAll();
            if (todosCientes.isEmpty()){
                System.out.println("Nenhum cliente encontrado");
            }else{
                todosCientes.forEach(System.out::println);
            }*/


        };
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
