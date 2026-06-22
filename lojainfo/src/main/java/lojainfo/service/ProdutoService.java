package lojainfo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lojainfo.domain.model.Produto;
import lojainfo.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public List<Produto> listarTodos() {
        return repository.findAll();
    }

    public Produto buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }

    public Produto atualizar(Long id, Produto produto) {

        Produto existente = repository.findById(id).orElse(null);

        if (existente == null) {
            return null;
        }

        existente.setNome(produto.getNome());
        existente.setCategoria(produto.getCategoria());
        existente.setMarca(produto.getMarca());
        existente.setEstoque(produto.getEstoque());
        existente.setPreco(produto.getPreco());

        return repository.save(existente);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}