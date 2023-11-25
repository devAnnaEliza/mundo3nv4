package cadastroee.controller;

import cadastroee.model.Pessoa;
import jakarta.ejb.Local;
import java.util.List;

@Local
public interface PessoaFacadeLocal {

    void criar(Pessoa pessoa);

    void atualizar(Pessoa pessoa);

    void remover(Pessoa pessoa);

    Pessoa encontrar(Object id);

    List<Pessoa> encontrarTodos();

    List<Pessoa> encontrarRange(int[] range);

    int contar();
} 