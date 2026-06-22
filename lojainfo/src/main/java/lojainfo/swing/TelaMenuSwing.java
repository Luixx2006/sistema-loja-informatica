package lojainfo.swing;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class TelaMenuSwing extends JFrame {

    public TelaMenuSwing() {
        setTitle("Menu Principal - Loja Informática");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));

        JLabel titulo = new JLabel("Sistema Loja Informática", SwingConstants.CENTER);
        add(titulo);

        JButton botaoProdutos = new JButton("Listar Produtos");
        add(botaoProdutos);

        JButton botaoVenda = new JButton("Registrar Venda");
        add(botaoVenda);

        JButton botaoRelatorios = new JButton("Relatórios");
        add(botaoRelatorios);

        JButton botaoSair = new JButton("Sair");
        add(botaoSair);

        botaoProdutos.addActionListener(e -> {
            new TelaProdutosSwing().setVisible(true);
        });

        botaoVenda.addActionListener(e -> {
            new TelaVendaSwing().setVisible(true);
        });

        botaoRelatorios.addActionListener(e -> {
            new TelaRelatoriosSwing().setVisible(true);
        });

        botaoSair.addActionListener(e -> {
            SessaoUsuario.limpar();
            dispose();
            new TelaLoginSwing().setVisible(true);
        });
    }
}