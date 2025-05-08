package com.devsuperior.demo.services;

import com.devsuperior.demo.entities.Role;
import com.devsuperior.demo.entities.User;
import com.devsuperior.demo.projections.UserDetailsProjections;
import com.devsuperior.demo.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //para registrar como um componente
public class UserService implements UserDetailsService {

    private UserRepository repository;

    @Override //busca o usuario correspondedte ao nome de usuario passado e se ele nao existe eu lanco a excessao UsernameNotFoundException
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //So que como estamos em um contexto do spring e a relacao manytomany do roles do user eh lazy, nao ira trazer os roles, mudar a relacao para eager no roles nao eh uma boa pratica, por isso nao fazemos isso.
        //Por isso eh melhor fazer uma consulta SQL raiz
        //User user = repository.findByEmail(username);
        //if (user == null){
            //se o usuariio nao existir
        //    throw new UsernameNotFoundException("User not found")
        //}

        List<UserDetailsProjections> result = repository.searchUserAndRolesByEmail(username);
        if (result.size() == 0){
            throw new UsernameNotFoundException("User not found");
        }
        //Se nao existir ele cria
        User user = new User();
        user.setEmail(username);
        user.setPassword(result.get(0).getPassword());
        //Para cada elemento do tipo UserDetailsProjections na lista result eu vou cosntruir um objeto Role e adicionar na lista de Roles do usuario
        for (UserDetailsProjections projections : result) {
            user.addRole(new Role(projections.getRoleId(), projections.getAuthority()));
        }

        return user;
    }
}
