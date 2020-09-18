package caroline.transactionAPI.services.impl;

import caroline.transactionAPI.domain.Estatisticas;
import caroline.transactionAPI.domain.Transacao;
import caroline.transactionAPI.services.TransactionService;
import lombok.Builder;
import lombok.Singular;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    @Singular
    List<Transacao> listaDeTransacoes = new ArrayList<Transacao>();

    List<Transacao> transacoesUltimosSessentaSegundos = new ArrayList<Transacao>();

    @Override
    public void addTransacao (Transacao transacao){

        listaDeTransacoes.add(transacao);

    }

    @Override
    public void deleteTransacaos (){

        listaDeTransacoes.clear();

    }

    @Override
    public Estatisticas getEstatisticas(){

        filtraTransacoesUltimosSessentaSegundos();

        return Estatisticas.builder()
                .count(transacoesUltimosSessentaSegundos.size())
                .sum(somaTotal())
                .avg(calcularAMedia())
                .min(menorTransacao())
                .max(maiorTransacao())
                .build();
    }

    public double somaTotal(){

        double soma = 0;

        if(listaDeTransacoes.size() > 0){

            for (Transacao transacao : transacoesUltimosSessentaSegundos) {
                soma = soma + transacao.getValor();
            }

        }

        return soma;
    }

    public double calcularAMedia(){

        double media = 0;

        double soma = 0;

        for (Transacao transacao : transacoesUltimosSessentaSegundos) {
            soma = soma + transacao.getValor();
        }

        if (soma > 0){
            media = soma / transacoesUltimosSessentaSegundos.size();
        }

        return media;
    }

    public double menorTransacao(){

            double valorMenor = 0;

            for (Transacao transacao : transacoesUltimosSessentaSegundos){

                if (transacao.getValor() < valorMenor || valorMenor == 0){
                    valorMenor = transacao.getValor();
                }

            }
        return valorMenor;

    }

    public double maiorTransacao(){

        double valorMaior = 0;

        for (Transacao transacao : transacoesUltimosSessentaSegundos){

            if (transacao.getValor() > valorMaior){
                valorMaior = transacao.getValor();
            }
        }
        return valorMaior;
    }

    private boolean ultimosSessentaSegundos(Transacao transacao){
        ZoneId z = ZoneId.of("America/Sao_Paulo");

        ZonedDateTime dataEHoraDaTransacaoUTC = ZonedDateTime.ofInstant(transacao.getDataHora().toInstant(), z);

        ZonedDateTime dataEHoraDaTransacao = dataEHoraDaTransacaoUTC.plusHours(3);

        ZonedDateTime horarioAtual = ZonedDateTime.now( z );

        Duration transacaoDuration = Duration.between(dataEHoraDaTransacao, horarioAtual);

        long diferencaDeSegundos = transacaoDuration.getSeconds();

        //Se não for do futuro
        if(diferencaDeSegundos > 0){
            //Se estiver dentro dos ultimos 60 segundos
            if(diferencaDeSegundos <= 60){
                return true;
            }
        }

        return false;
    }
    
    private void filtraTransacoesUltimosSessentaSegundos(){

        for (Transacao transacao : listaDeTransacoes){

            if (ultimosSessentaSegundos(transacao)){

                transacoesUltimosSessentaSegundos.add(transacao);
            }
        }
    }

}


