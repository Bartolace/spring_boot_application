package github.com.Bartolace.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // JPA - subistitui a jdbcTemplate
@Table(name = "cliente")
public class Cliente {
    @Id // do javaPersistence, liga representacao na tabela do bd
    @GeneratedValue(strategy = GenerationType.AUTO) // representa o auto-incremento
    @Column(name = "id") // quando o nome for diferente do campo bd
    private Integer id;
    @Column(name = "nome", length = 100)
    private String nome;

    @Column(name = "cpf", length = 11)
    private String cpf;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY) // mappedBy:nome que está mapeado o cliente em pedido, fetch: traz consigo os pedidos
    private Set<Pedido> pedidos;




    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Cliente(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }


    public Cliente(String nome) {
        this.nome = nome;
    }

}
