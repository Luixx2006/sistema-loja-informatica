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
import javax.swing.JTextField;

public class TelaVendaSwing extends JFrame {

    private JTextField campoClienteId;
    private JTextField campoProdutoId;
    private JTextField campoQuantidade;

    public TelaVendaSwing() {
        setTitle("Registrar Venda");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("ID do Cliente:"));
        campoClienteId = new JTextField();
        add(campoClienteId);

        add(new JLabel("ID do Produto:"));
        campoProdutoId = new JTextField();
        add(campoProdutoId);

        add(new JLabel("Quantidade:"));
        campoQuantidade = new JTextField();
        add(campoQuantidade);

        JButton botaoRegistrar = new JButton("Registrar Venda");
        add(botaoRegistrar);

        botaoRegistrar.addActionListener(e -> registrarVenda());
    }

    private void registrarVenda() {
        String clienteId = campoClienteId.getText();
        String produtoId = campoProdutoId.getText();
        String quantidade = campoQuantidade.getText();

        String json = """
                {
                  "cliente": {
                    "id": %s
                  },
                  "itens": [
                    {
                      "produto": {
                        "id": %s
                      },
                      "quantidade": %s
                    }
                  ]
                }
                """.formatted(clienteId, produtoId, quantidade);

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/vendas"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + SessaoUsuario.getToken())
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() == 201) {
                JOptionPane.showMessageDialog(
                        this,
                        "Venda registrada com sucesso!"
                );

                campoClienteId.setText("");
                campoProdutoId.setText("");
                campoQuantidade.setText("");

            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao registrar venda.\nStatus: "
                                + response.statusCode()
                                + "\nResposta: "
                                + response.body()
                );
            }

        } catch (IOException | InterruptedException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao conectar com a API."
            );

            Thread.currentThread().interrupt();
        }
    }
}