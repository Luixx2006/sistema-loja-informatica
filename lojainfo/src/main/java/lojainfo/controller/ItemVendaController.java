package lojainfo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lojainfo.domain.model.ItemVenda;
import lojainfo.service.ItemVendaService;

@RestController
@RequestMapping("/itens-venda")
public class ItemVendaController {

    private final ItemVendaService service;

    public ItemVendaController(ItemVendaService service) {
        this.service = service;
    }

    @GetMapping
    public List<ItemVenda> listarTodos() {
        return service.listarTodos();
    }

    @PostMapping
    public ItemVenda salvar(@RequestBody ItemVenda itemVenda) {
        return service.salvar(itemVenda);
    }
}