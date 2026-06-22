package lojainfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lojainfo.domain.model.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {
}