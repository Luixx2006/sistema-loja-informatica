package lojainfo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lojainfo.domain.model.Usuario;
import lojainfo.security.JwtService;

@Service
public class AuthenticationService {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(
            UsuarioService usuarioService,
            JwtService jwtService,
            PasswordEncoder passwordEncoder) {

        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(String username, String password) {

        Usuario usuario = usuarioService.buscarPorUsername(username);

        if (usuario == null) {
            return null;
        }

        boolean senhaValida = passwordEncoder.matches(
                password,
                usuario.getPassword()
        );

        if (!senhaValida) {
            return null;
        }

        return jwtService.gerarToken(usuario.getUsername());
    }
}