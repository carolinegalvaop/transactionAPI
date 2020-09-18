package caroline.transactionAPI.services;

import caroline.transactionAPI.domain.Estatisticas;
import caroline.transactionAPI.domain.Transacao;

public interface TransactionService{

    void addTransacao (Transacao transacao);

    void deleteTransacaos ();

    Estatisticas getEstatisticas ();

}
