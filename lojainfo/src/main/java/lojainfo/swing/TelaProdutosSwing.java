package lojainfo.swing;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TelaProdutosSwing extends JFrame {

    private JTable tabela;
    private DefaultTableModel modelo;

    public TelaProdutosSwing() {
        setTitle("Produtos - Loja Informática");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Nome");
        modelo.addColumn("Categoria");
        modelo.addColumn("Marca");
        modelo.addColumn("Estoque");
        modelo.addColumn("Preço");

        tabela = new JTable(modelo);

        JButton botaoAtualizar = new JButton("Atualizar Produtos");

        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(botaoAtualizar, BorderLayout.SOUTH);

        botaoAtualizar.addActionListener(e -> carregarProdutos());

        carregarProdutos();
    }

    private void carregarProdutos() {
        try {
            modelo.setRowCount(0);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/produtos"))
                    .header("Authorization", "Bearer " + SessaoUsuario.getToken())
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() == 200) {

                ObjectMapper mapper = new ObjectMapper();
                JsonNode produtos = mapper.readTree(response.body());

                for (JsonNode produto : produtos) {
                    modelo.addRow(new Object[] {
                            produto.path("id").asLong(),
                            produto.path("nome").asText(),
                            produto.path("categoria").asText(),
                            produto.path("marca").asText(),
                            produto.path("estoque").asInt(),
                            produto.path("preco").asDouble()
                    });
                }

            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao buscar produtos. Status: " + response.statusCode()
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