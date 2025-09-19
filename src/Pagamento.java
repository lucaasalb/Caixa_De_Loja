public interface Pagamento {
    void pix(double valor);

    void dinheiro(double valor);

    void cartao(double valor);
}
