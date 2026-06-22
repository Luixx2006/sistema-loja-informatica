package lojainfo.swing;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TelaRelatoriosSwing extends JFrame {

    private JTextArea areaRelatorio;

    public TelaRelatoriosSwing() {
        setTitle("Relatórios - Loja Informática");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        areaRelatorio = new JTextArea();
        areaRelatorio.setEditable(false);

        JPanel painelBotoes = new JPanel(new GridLayout(1, 3, 10, 10));

        JButton botaoVendas = new JButton("Vendas");
        JButton botaoTotal = new JButton("Total Vendido");
        JButton botaoEstoque = new JButton("Estoque");

        painelBotoes.add(botaoVendas);
        painelBotoes.add(botaoTotal);
        painelBotoes.add(botaoEstoque);

        add(painelBotoes, BorderLayout.NORTH);
        add(new JScrollPane(areaRelatorio), BorderLayout.CENTER);

        botaoVendas.addActionListener(e -> carregarVendas());
        botaoTotal.addActionListener(e -> carregarTotalVendido());
        botaoEstoque.addActionListener(e -> carregarEstoque());
    }

    private void carregarVendas() {
        try {
            String resposta = fazerGet("http://localhost:8080/relatorios/vendas");

            ObjectMapper mapper = new ObjectMapper();
            JsonNode vendas = mapper.readTree(resposta);

            StringBuilder texto = new StringBuilder();
            texto.append("RELATÓRIO DE VENDAS\n\n");

            for (JsonNode venda : vendas) {
                texto.append("Venda ID: ")
                        .append(venda.path("id").asLong())
                        .append("\n");

                texto.append("Data: ")
                        .append(venda.path("dataVenda").asText())
                        .append("\n");

                texto.append("Cliente ID: ")
                        .append(venda.path("cliente").path("id").asLong())
                        .append("\n");

                texto.append("Valor Total: R$ ")
                        .append(venda.path("valorTotal").asDouble())
                        .append("\n");

                texto.append("-----------------------------\n");
            }

            areaRelatorio.setText(texto.toString());

        } catch (Exception e) {
            mostrarErro();
        }
    }

    private void carregarTotalVendido() {
        try {
            String resposta = fazerGet("http://localhost:8080/relatorios/vendas/total");

            areaRelatorio.setText(
                    "TOTAL VENDIDO\n\nR$ " + resposta
            );

        } catch (Exception e) {
            mostrarErro();
        }
    }

    private void carregarEstoque() {
        try {
            String resposta = fazerGet("http://localhost:8080/relatorios/estoque");

            ObjectMapper mapper = new ObjectMapper();
            JsonNode produtos = mapper.readTree(resposta);

            StringBuilder texto = new StringBuilder();
            texto.append("RELATÓRIO DE ESTOQUE\n\n");

            for (JsonNode produto : produtos) {
                texto.append("Produto ID: ")
                        .append(produto.path("id").asLong())
                        .append("\n");

                texto.append("Nome: ")
                        .append(produto.path("nome").asText())
                        .append("\n");

                texto.append("Categoria: ")
                        .append(produto.path("categoria").asText())
                        .append("\n");

                texto.append("Marca: ")
                        .append(produto.path("marca").asText())
                        .append("\n");

                texto.append("Estoque: ")
                        .append(produto.path("estoque").asInt())
                        .append("\n");

                texto.append("Preço: R$ ")
                        .append(produto.path("preco").asDouble())
                        .append("\n");

                texto.append("-----------------------------\n");
            }

            areaRelatorio.setText(texto.toString());

        } catch (Exception e) {
            mostrarErro();
        }
    }

    private String fazerGet(String url) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + SessaoUsuario.getToken())
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new IOException("Erro na API. Status: " + response.statusCode());
        }

        return response.body();
    }

    private void mostrarErro() {
        JOptionPane.showMessageDialog(
                this,
                "Erro ao carregar relatório. Verifique se a API está rodando e se o login foi feito."
        );
    }
}