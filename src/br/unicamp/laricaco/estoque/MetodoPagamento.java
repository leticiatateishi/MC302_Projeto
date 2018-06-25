package br.unicamp.laricaco.estoque;

public enum TipoPagamento {

    CARTAO_CREDITO, CARTAO_DEBITO, DINHEIRO;

    public static TipoPagamento fromOrdinal(int ordinal){
        for (TipoPagamento tipoPagamento : values()) {
            if (tipoPagamento.ordinal() == ordinal) {
                return tipoPagamento;
            }
        }
        throw new NullPointerException("Não existe o tipo de pagamento com ordinal " + ordinal);
    }
}
