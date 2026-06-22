package lojainfo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import lojainfo.domain.model.Cliente;
import lojainfo.domain.model.ItemVenda;
import lojainfo.domain.model.Produto;
import lojainfo.domain.model.Venda;
import lojainfo.repository.ClienteRepository;
import lojainfo.repository.ProdutoRepository;
import lojainfo.repository.VendaRepository;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;

    public VendaService(VendaRepository vendaRepository,
                        ProdutoRepository produtoRepository,
                        ClienteRepository clienteRepository) {

        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<Venda> listarTodos() {
        return vendaRepository.findAll();
    }

    public Venda buscarPorId(Long id) {
        return vendaRepository.findById(id).orElse(null);
    }

    public Venda salvar(Venda venda) {

        if (venda.getCliente() == null || venda.getCliente().getId() == null) {
            throw new IllegalArgumentException("Cliente é obrigatório para realizar a venda.");
        }

        Cliente cliente = clienteRepository.findById(venda.getCliente().getId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado."));

        if (venda.getItens() == null || venda.getItens().isEmpty()) {
            throw new IllegalArgumentException("A venda precisa ter pelo menos um item.");
        }

        venda.setCliente(cliente);
        venda.setDataVenda(LocalDate.now());

        double valorTotal = 0.0;

        for (ItemVenda item : venda.getItens()) {

            if (item.getProduto() == null || item.getProduto().getId() == null) {
                throw new IllegalArgumentException("Produto é obrigatório no item da venda.");
            }

            if (item.getQuantidade() == null || item.getQuantidade() <= 0) {
                throw new IllegalArgumentException("Quantidade do item deve ser maior que zero.");
            }

            Produto produto = produtoRepository.findById(item.getProduto().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));

            Integer estoqueAtual = produto.getEstoque();

            if (estoqueAtual == null) {
                estoqueAtual = 0;
            }

            if (estoqueAtual < item.getQuantidade()) {
                throw new IllegalArgumentException(
                        "Estoque insuficiente para o produto: " + produto.getNome()
                );
            }

            produto.setEstoque(estoqueAtual - item.getQuantidade());

            produtoRepository.save(produto);

            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco().doubleValue());
            item.setVenda(venda);

            valorTotal += item.getPrecoUnitario() * item.getQuantidade();
        }

        venda.setValorTotal(valorTotal);

        return vendaRepository.save(venda);
    }

    public void excluir(Long id) {
        vendaRepository.deleteById(id);
    }
}