package cadastroee.controller;

import cadastroee.model.PessoaFisica;
import java.util.List;
import jakarta.ejb.Local;

@Local
public interface PessoaFisicaFacadeLocal {

    void criar(PessoaFisica pessoaFisica);

    void atualizar(PessoaFisica pessoaFisica);

    void remover(PessoaFisica pessoaFisica);

    PessoaFisica encontrar(Object id);

    List<PessoaFisica> encontrarTodos();

    List<PessoaFisica> encontrarRange(int[] range);

    int contar();
}