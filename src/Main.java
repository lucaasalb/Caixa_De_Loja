import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        int numero = -1; // controle do menu
        List<Produto> carrinho = new ArrayList<Produto>();

        while (numero != 0) {
            // Menu
            String menu = "Digite um número:\n"
                    + " 1 - Registrar Produto\n"
                    + " 2 - Finalizar Compra\n"
                    + " 3 - Visualizar Carrinho\n"
                    + " 4 - Apagar Carrinho\n"
                    + " 0 - Sair";

            String entrada = JOptionPane.showInputDialog(null, menu, "Menu", JOptionPane.QUESTION_MESSAGE);
            if (entrada == null) { // cancelou
                numero = 0;
                continue;
            }

            try {
                numero = Integer.parseInt(entrada.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Erro! Digite um número válido!");
                continue;
            }

            switch (numero) {
                case 1:
                    // Registrar produto
                    String nome = JOptionPane.showInputDialog(null, "Digite o nome do produto:", "Registrar Produto",
                            JOptionPane.QUESTION_MESSAGE);
                    if (nome == null || nome.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Nome inválido ou operação cancelada.");
                        break;
                    }

                    String precoStr = JOptionPane.showInputDialog(null, "Digite o preço do produto:", "Registrar Produto",
                            JOptionPane.QUESTION_MESSAGE);
                    if (precoStr == null) {
                        JOptionPane.showMessageDialog(null, "Operação cancelada.");
                        break;
                    }

                    double preco;
                    try {
                        preco = Double.parseDouble(precoStr.replace(",", ".").trim());
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Preço inválido.");
                        break;
                    }

                    Produto novo = new Produto(nome, preco);
                    carrinho.add(novo);
                    JOptionPane.showMessageDialog(null, "Produto adicionado:\n" + novo.toString());
                    break;

                case 2:
                    // Finalizar compra
                    if (carrinho.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Carrinho vazio. Adicione produtos antes de finalizar.");
                        break;
                    }

                    // lista + total
                    StringBuilder sb = new StringBuilder("Itens do carrinho:\n");
                    double total = 0.0;
                    for (Produto p : carrinho) {
                        sb.append(p.toString()).append("\n");
                        total += p.getPreco();
                    }
                    sb.append("\nTotal: R$ ").append(String.format("%.2f", total));
                    JOptionPane.showMessageDialog(null, sb.toString(), "Finalizar Compra", JOptionPane.INFORMATION_MESSAGE);

                    // forma de pagamento
                    int forma = -1;
                    while (forma != 1 && forma != 2 && forma != 3) {
                        String fp = JOptionPane.showInputDialog(null,
                                "Qual a forma de pagamento?\n 1 - Dinheiro\n 2 - Pix\n 3 - Cartão",
                                "Pagamento", JOptionPane.QUESTION_MESSAGE);
                        if (fp == null) {
                            JOptionPane.showMessageDialog(null, "Operação cancelada.");
                            break;
                        }
                        try {
                            forma = Integer.parseInt(fp.trim());
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Digite uma opção válida (1, 2 ou 3).");
                        }
                    }
                    if (forma != 1 && forma != 2 && forma != 3) {
                        break; // cancelou pagamento
                    }

                    MetodosDePagamento pagamento = new MetodosDePagamento();
                    if (forma == 1) {
                        pagamento.dinheiro(total); // vai perguntar o valor entregue via JOptionPane
                    } else if (forma == 2) {
                        pagamento.pix(total);
                    } else if (forma == 3) {
                        pagamento.cartao(total);
                    }

                    // esvazia carrinho após pagamento
                    carrinho.clear();
                    JOptionPane.showMessageDialog(null, "Compra finalizada e carrinho esvaziado.");
                    break;

                case 3:
                    // Visualizar carrinho
                    if (carrinho.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Seu carrinho está vazio.");
                    } else {
                        StringBuilder lista = new StringBuilder("Seu carrinho:\n");
                        for (Produto p : carrinho) {
                            lista.append(p.toString()).append("\n");
                        }
                        JOptionPane.showMessageDialog(null, lista.toString());
                    }
                    break;

                case 4:
                    // Apagar carrinho
                    if (carrinho.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Seu carrinho já está vazio.");
                    } else {
                        int conf = JOptionPane.showConfirmDialog(null, "Deseja esvaziar o carrinho?", "Confirmar",
                                JOptionPane.YES_NO_OPTION);
                        if (conf == JOptionPane.YES_OPTION) {
                            carrinho.clear();
                            JOptionPane.showMessageDialog(null, "Carrinho esvaziado.");
                        }
                    }
                    break;

                case 0:
                    JOptionPane.showMessageDialog(null, "Saindo...");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Erro! Digite um número válido!");
                    break;
            }
        }
    }
}
