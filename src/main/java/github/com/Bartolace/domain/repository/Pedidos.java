package github.com.Bartolace.domain.repository;

import github.com.Bartolace.domain.entity.Cliente;
import github.com.Bartolace.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface Pedidos extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByCliente(Cliente cliente); //queryMethods para carregar Clientes dos pedidos

    @Query(" select p from Pedido p left join fetch p.itens where p.id = :id ")
    Optional<Pedido> findByIdFetchItens (@Param("id") Integer id); //encontrar pedidos por id e carregar todos os itens

}
