package br.com.projetopicpay.services;

import br.com.projetopicpay.domain.user.User;
import br.com.projetopicpay.domain.user.UserType;
import br.com.projetopicpay.dtos.UserDTO;
import br.com.projetopicpay.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception{
        if (sender.getUserType() == UserType.MERCHANT) {
            throw  new Exception("Usuario do tipo Lojista nao está autorizado a ralizar a transação");
        }

        if(sender.getBalance().compareTo(amount) < 0){
            throw new Exception("Saldo insuficiente");
        }
    }

    public User findUserById(Long id) throws Exception{
        return this.repository.findUserById(id).orElseThrow(() -> new Exception("Usurário não encontrado"));
    }

    public User createUser(UserDTO data){
        User nUser = new User(data);
        this.saveUser(nUser);
        return nUser;
    }

    public void saveUser(User user){
        this.repository.save(user);
    }


    public List<User> getAllUsers(){
        return this.repository.findAll();
    }

}
