package lojainfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lojainfo.domain.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}