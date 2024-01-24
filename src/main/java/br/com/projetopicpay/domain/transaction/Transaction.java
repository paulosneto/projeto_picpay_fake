package br.com.projetopicpay.domain.transaction;

import br.com.projetopicpay.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity(name = "transactions")
@Table(name = "transactions")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amaount;

    @ManyToOne // Esta anotação quer dizer que o usuario pode ter varios envios, mas o contrario nao é verdadeiro
    @JoinColumn(name = "receiver_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id ")
    private User receiver;

}
