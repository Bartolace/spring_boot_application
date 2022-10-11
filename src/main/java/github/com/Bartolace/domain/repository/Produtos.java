package github.com.Bartolace.domain.repository;

import github.com.Bartolace.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto, Integer> {
}
