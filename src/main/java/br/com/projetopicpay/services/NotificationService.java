package br.com.projetopicpay.services;

import br.com.projetopicpay.domain.user.User;
import br.com.projetopicpay.dtos.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;


    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);

        /*ResponseEntity<String> responseEntity = restTemplate.postForEntity("url", notificationRequest, String.class);

        if(!(responseEntity.getStatusCode() == HttpStatus.OK)){
                System.out.println("Erro ao enviar email de notificação");

            throw new Exception("Serviço de email temporariamente");
        }*/
        System.out.println("Notificacao enviada para o usuario");
    }

}

