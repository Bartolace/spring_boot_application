package github.com.Bartolace.domain.repository;

import github.com.Bartolace.domain.entity.Cliente;
import github.com.Bartolace.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface Pedidos extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByCliente(Cliente cliente); //queryMethods para carregar Clientes dos pedidos
}
