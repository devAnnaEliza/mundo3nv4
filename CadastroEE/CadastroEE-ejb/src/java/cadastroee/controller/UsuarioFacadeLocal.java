package cadastroee.controller;

import java.util.List;
import jakarta.ejb.Local;
import cadastroee.model.Usuario;

@Local
public interface UsuarioFacadeLocal {

    void create(Usuario usuario);

    void edit(Usuario usuario);

    void remove(Usuario usuario);

    Usuario find(Object id);

    List<Usuario> findAll();

    List<Usuario> findRange(int[] range);

    int count();

    //adicionando métodos extras para demonstrar expansão
    Usuario findByUsername(String username);

    List<Usuario> findByRole(String role);
}
