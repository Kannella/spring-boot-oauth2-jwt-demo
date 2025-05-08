package com.devsuperior.demo.repositories;

import com.devsuperior.demo.entities.User;
import com.devsuperior.demo.projections.UserDetailsProjections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    //Poderia fazer assim mais simples, somente com isso funciona
    User findByEmail(String email);

    //So que como estamos em um contexto do spring e a relacao manytomany do roles do user eh lazy, nao ira trazer os roles, mudar a relacao para eager no roles nao eh uma boa pratica, por isso nao fazemos isso.
    //Por isso eh melhor fazer uma consulta SQL raiz

    @Query(nativeQuery = true, value = """
            SELECT tb_user.email AS username, tb_user.password, tb_role.id AS roleId, tb_role.authority
            FROM tb_user
            INNER JOIN tb_user_role ON tb_user.id = tb_user_role.userId
            INNER JOIN tb_role ON tb_role.id = tb_user_role.role_id
            WHERE tb_user.email = :email
    """)
    List<UserDetailsProjections> searchUserAndRolesByEmail(String email); //retorna uma lista de objetos do tipo da projection

}
