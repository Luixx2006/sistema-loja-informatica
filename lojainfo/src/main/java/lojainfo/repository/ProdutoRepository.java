package lojainfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lojainfo.domain.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}