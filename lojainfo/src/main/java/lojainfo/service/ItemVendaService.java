package lojainfo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lojainfo.domain.model.ItemVenda;
import lojainfo.repository.ItemVendaRepository;

@Service
public class ItemVendaService {

    private final ItemVendaRepository repository;

    public ItemVendaService(ItemVendaRepository repository) {
        this.repository = repository;
    }

    public List<ItemVenda> listarTodos() {
        return repository.findAll();
    }

    public ItemVenda salvar(ItemVenda itemVenda) {
        return repository.save(itemVenda);
    }
}