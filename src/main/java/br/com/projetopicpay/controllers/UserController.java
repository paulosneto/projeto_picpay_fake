package br.com.projetopicpay.controllers;

import br.com.projetopicpay.domain.user.User;
import br.com.projetopicpay.dtos.UserDTO;
import br.com.projetopicpay.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Rota de usuários
@RestController()
@RequestMapping("/users")
public class UserController {


    // Instância para o service
    @Autowired
    private UserService userService;

    // Cria usuário
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO){
        // recupera a instância de criação
        User nUser = userService.createUser(userDTO);
        return new ResponseEntity<>(nUser, HttpStatus.CREATED);
    }

    // Listar todos os usuários
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        // recupera a instância de listagem
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
