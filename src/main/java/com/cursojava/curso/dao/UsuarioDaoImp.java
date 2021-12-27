package com.cursojava.curso.dao;

import com.cursojava.curso.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository  /// se usa para implementar la conexion a la base de datos
@Transactional /// hace referencia a como va armar la consulta sql
public class UsuarioDaoImp implements UsuarioDao {

    @PersistenceContext
    EntityManager entityManager;  /// nos sirve para hacer la conexion con la base de datos

    @Override
    @Transactional
    public List<Usuario> getUsuarios() {
       String query = "FROM Usuario"; // esta hace la consulta en Hibernate
    return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        entityManager.remove(usuario);
    }

    @Override
    public void registrar(Usuario usuario) {
        entityManager.merge(usuario);
    }

    @Override
    public Usuario obtenerUsuarioPorCredenciales(Usuario usuario) {
        String query = "FROM Usuario WHERE email = :email"; /// esta hace la consulta en Hibernate
       List<Usuario> lista =  entityManager.createQuery(query)
                .setParameter( "email", usuario.getEmail())
                .getResultList();

       if(lista.isEmpty()){
           return null;
       }

       String passwordHashed = lista.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if (argon2.verify(passwordHashed, usuario.getPassword())){

            return lista.get(0);
        }

        return null;

    }


}
