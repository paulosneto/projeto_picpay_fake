package br.com.projetopicpay.services;

import br.com.projetopicpay.domain.transaction.Transaction;
import br.com.projetopicpay.domain.user.User;
import br.com.projetopicpay.dtos.TransactionDTO;
import br.com.projetopicpay.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    public Transaction createTransaction(TransactionDTO transactionDTO) throws Exception {

        User sender = this.userService.findUserById(transactionDTO.senderId());
        User receiver = this.userService.findUserById(transactionDTO.receiverId());

        // validando se o saldo da transação é valido
        userService.validateTransaction(sender, transactionDTO.value());

        boolean isAuthorized = this.authorizeTransaction(sender, transactionDTO.value());
        if(!isAuthorized){
            throw new Exception("Transação não autorizada");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setAmaount(transactionDTO.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        // Faz a subtração no valor de saldo da transação
        sender.setBalance(sender.getBalance().subtract(transactionDTO.value()));

        // Faz a adição no valor de recebimento da transação
        receiver.setBalance(receiver.getBalance().add(transactionDTO.value()));

        this.repository.save(newTransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        this.notificationService.sendNotification(sender, "Transação realizada com sucesso");
        this.notificationService.sendNotification(receiver, "Transação recebida com sucesso");

        return newTransaction;

    }

    public boolean authorizeTransaction(User sender, BigDecimal value){
        //ResponseEntity<Map> autResponseEntity = restTemplate.getForEntity("https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6",Map.class);
        ResponseEntity<Map> autResponseEntity = restTemplate.getForEntity("https://run.mocky.io/v3/60a52d40-4bab-45fc-ac73-128b7c42aa2b",Map.class);

        if(autResponseEntity.getStatusCode() == HttpStatus.OK){
            String message = (String) autResponseEntity.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message);
        }else return false;
    }

    // url usada para testar o retorno da api mocky : https://designer.mocky.io/design/confirmation    // https://run.mocky.io/v3/60a52d40-4bab-45fc-ac73-128b7c42aa2b

}
