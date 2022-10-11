package github.com.Bartolace.domain.repository;

import com.sun.xml.bind.v2.runtime.property.StructureLoaderBuilder;
import github.com.Bartolace.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//@Repository //Classe que encapsula todas as operações referentes as entitys. Que acessa a base de dados.
public interface Clientes extends JpaRepository<Cliente, Integer>{

    //query Methods //findByNomeLike
    // passar os paramentros na sequencia //List<Cliente> findByNomeOrIdOrderBy(String nome, Integer id);
    @Query(value = "select * from Cliente c where c.nome like '%:nome%' ", nativeQuery = true)
    List<Cliente> encontraPorNome(@Param("nome") String nome);

    @Query(value = " delete from Cliente c where c.nome =:nome ")
    @Modifying
    void deleteByNome(String nome);

    boolean existsByNome(String nome); // por convenção

    @Query(" select c from Cliente c left join fetch c.pedidos where c.id =:id ")
    Cliente findClienteFetchPedidos (@Param("id") Integer id);





   /* @Autowired
    private EntityManager entityManager;// faz todas as operações da base com as entidades;


    @Transactional // pacote annotation para persistir transações
    public Cliente salvar(Cliente cliente){
        entityManager.persist(cliente);// para salvar
        return cliente;
    }

    @Transactional
    public Cliente atualizar(Cliente cliente){
        entityManager.merge(cliente);
        return cliente;
    }

    @Transactional
    public void deletar (Cliente cliente){
        if (!entityManager.contains(cliente)){
            cliente = entityManager.merge(cliente);
        }
        entityManager.remove(cliente);
    }
    @Transactional
    public void deletar(Integer id){
        Cliente cliente = entityManager.find(Cliente.class, id);
        deletar(cliente);
    }

    @Transactional(readOnly = true) // transação de apenas leitura;
    public List<Cliente> buscarPorNome(String nome){
        String jpql = " select c from Cliente c where c.nome like :nome ";
        TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class);
        query.setParameter("nome", "%" + nome +"%");
        return query.getResultList();
    }
    @Transactional(readOnly = true)
    public List<Cliente> obterTodos(){
        return entityManager
                .createQuery("from Cliente", Cliente.class)
                .getResultList();
    }
*/
  /*  private static RowMapper<Cliente> obterClienteMapper() {
        return new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
                Integer id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");

                return new Cliente(id, nome);
            }
        };
    }*/


}
