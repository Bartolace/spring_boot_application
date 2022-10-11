package github.com.Bartolace.domain.repository;

import github.com.Bartolace.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsPedidos extends JpaRepository<ItemPedido, Integer> {
}
