package lojainfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lojainfo.domain.model.ItemVenda;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {
}