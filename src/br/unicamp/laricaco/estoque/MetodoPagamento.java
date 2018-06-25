package br.unicamp.laricaco.estoque;

public enum MetodoPagamento {

    CARTAO_CREDITO,
    CARTAO_DEBITO,
    DINHEIRO;

    public static MetodoPagamento fromOrdinal(int ordinal) {
        for (MetodoPagamento metodoPagamento : values()) {
            if (metodoPagamento.ordinal() == ordinal) {
                return metodoPagamento;
            }
        }
        throw new NullPointerException("Não existe o tipo de pagamento com ordinal " + ordinal);
    }
}
