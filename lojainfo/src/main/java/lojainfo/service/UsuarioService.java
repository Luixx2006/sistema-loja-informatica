package lojainfo.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lojainfo.domain.model.Usuario;
import lojainfo.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository,
                          PasswordEncoder passwordEncoder) {

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    // ==========================
    // BUSCAS
    // ==========================

    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Usuario buscarPorUsername(String username) {
        return repository.findByUsername(username).orElse(null);
    }

    public boolean existeUsuario(String username) {
        return repository.existsByUsername(username);
    }

    // ==========================
    // SALVAR
    // ==========================

    public Usuario salvar(Usuario usuario) {

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        return repository.save(usuario);
    }

    // ==========================
    // ATUALIZAR
    // ==========================

    public Usuario atualizar(Long id, Usuario usuario) {

        Usuario existente = repository.findById(id).orElse(null);

        if (existente == null) {
            return null;
        }

        existente.setUsername(usuario.getUsername());

        // Criptografa a nova senha
        existente.setPassword(passwordEncoder.encode(usuario.getPassword()));

        existente.setPerfil(usuario.getPerfil());

        return repository.save(existente);
    }

    // ==========================
    // EXCLUIR
    // ==========================

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}