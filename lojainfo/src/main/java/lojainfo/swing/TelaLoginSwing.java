package lojainfo.swing;

import java.awt.GridLayout;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TelaLoginSwing extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoSenha;

    public TelaLoginSwing() {
        setTitle("Login - Sistema Loja Informática");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Usuário:"));
        campoUsuario = new JTextField();
        add(campoUsuario);

        add(new JLabel("Senha:"));
        campoSenha = new JPasswordField();
        add(campoSenha);

        JButton botaoLogin = new JButton("Entrar");
        add(botaoLogin);

        botaoLogin.addActionListener(e -> fazerLogin());
    }

    private void fazerLogin() {
        String username = campoUsuario.getText();
        String password = new String(campoSenha.getPassword());

        String json = """
                {
                  "username": "%s",
                  "password": "%s"
                }
                """.formatted(username, password);

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/auth/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() == 200) {

                ObjectMapper mapper = new ObjectMapper();

                String token = mapper
                        .readTree(response.body())
                        .get("token")
                        .asText();

                SessaoUsuario.setToken(token);

                JOptionPane.showMessageDialog(
                        this,
                        "Login realizado com sucesso!"
                );

                new TelaMenuSwing().setVisible(true);
                dispose();

            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Usuário ou senha inválidos."
                );
            }

        } catch (IOException | InterruptedException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao conectar com a API. Verifique se o Spring Boot está rodando."
            );

            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        new TelaLoginSwing().setVisible(true);
    }
}