package lojainfo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lojainfo.domain.model.Produto;
import lojainfo.domain.model.Venda;
import lojainfo.service.ProdutoService;
import lojainfo.service.VendaService;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final VendaService vendaService;
    private final ProdutoService produtoService;

    public RelatorioController(VendaService vendaService,
                               ProdutoService produtoService) {

        this.vendaService = vendaService;
        this.produtoService = produtoService;
    }

    // Relatório geral de vendas
    @GetMapping("/vendas")
    public ResponseEntity<List<Venda>> relatorioVendas() {
        return ResponseEntity.ok(vendaService.listarTodos());
    }

    // Relatório de vendas por período
    @GetMapping("/vendas/periodo")
    public ResponseEntity<List<Venda>> relatorioVendasPorPeriodo(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate inicio,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fim) {

        List<Venda> vendasFiltradas = vendaService.listarTodos()
                .stream()
                .filter(venda -> venda.getDataVenda() != null)
                .filter(venda -> !venda.getDataVenda().isBefore(inicio))
                .filter(venda -> !venda.getDataVenda().isAfter(fim))
                .toList();

        return ResponseEntity.ok(vendasFiltradas);
    }

    // Total vendido em todas as vendas
    @GetMapping("/vendas/total")
    public ResponseEntity<Double> totalVendido() {

        double total = vendaService.listarTodos()
                .stream()
                .filter(venda -> venda.getValorTotal() != null)
                .mapToDouble(Venda::getValorTotal)
                .sum();

        return ResponseEntity.ok(total);
    }

    // Relatório de estoque
    @GetMapping("/estoque")
    public ResponseEntity<List<Produto>> relatorioEstoque() {
        return ResponseEntity.ok(produtoService.listarTodos());
    }
}