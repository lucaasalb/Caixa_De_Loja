import javax.swing.JOptionPane;

public class MetodosDePagamento implements Pagamento {

    @Override
    public void pix(double valor) {
        double finalComDesconto = valor * 0.98; // 2% de desconto
        JOptionPane.showMessageDialog(null,
                String.format("Pagamento via PIX (2%% de desconto).\nTotal a pagar: R$ %.2f", finalComDesconto));
    }

    @Override
    public void dinheiro(double valor) {
        String in = JOptionPane.showInputDialog(null,
                String.format("Valor total: R$ %.2f\nInforme o valor entregue pelo cliente:", valor),
                "Dinheiro", JOptionPane.QUESTION_MESSAGE);
        if (in == null) {
            JOptionPane.showMessageDialog(null, "Pagamento cancelado.");
            return;
        }
        double entregue;
        try {
            entregue = Double.parseDouble(in.replace(",", ".").trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor entregue inválido.");
            return;
        }

        double troco = entregue - valor;
        if (troco < 0) {
            JOptionPane.showMessageDialog(null,
                    String.format("Valor insuficiente. Faltam R$ %.2f.", -troco));
        } else {
            JOptionPane.showMessageDialog(null,
                    String.format("Pagamento em dinheiro confirmado.\nTroco: R$ %.2f", troco));
        }
    }

    @Override
    public void cartao(double valor) {
        JOptionPane.showMessageDialog(null,
                String.format("Pagamento no cartão confirmado.\nTotal: R$ %.2f", valor));
    }
}
