package caroline.transactionAPI.controller;

import caroline.transactionAPI.domain.Transacao;
import caroline.transactionAPI.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class transactionController {


    @Autowired
    public TransactionService transactionService;

    @RequestMapping(value = "/transacao", method =  RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> SalvarTransacao(@RequestBody Transacao dados)
    {
        transactionService.addTransacao(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body("Transação Salva!" + " " + dados);
    }

    @RequestMapping(value = "/transacao", method =  RequestMethod.DELETE)
    public  ResponseEntity<?> DeletarTransacao()
    {
        transactionService.deleteTransacaos();
        return  ResponseEntity.status(HttpStatus.CREATED).body("Deletado com Sucesso!");
    }

    @RequestMapping(value = "/transacao", method = RequestMethod.GET )
    public ResponseEntity<?> EstatisticaTransacao()
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.getEstatisticas());
    }

}
