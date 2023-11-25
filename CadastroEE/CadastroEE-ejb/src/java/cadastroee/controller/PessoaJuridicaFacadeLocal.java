package cadastroee.controller;

import java.util.List;
import cadastroee.model.PessoaJuridica;
import jakarta.ejb.Local;

@Local
public interface PessoaJuridicaFacadeLocal {

    //cria uma nova pessoa jurídica
    void criar(PessoaJuridica pessoaJuridica);

    //atualiza uma pessoa jurídica existente
    void atualizar(PessoaJuridica pessoaJuridica);

    //remove uma pessoa jurídica
    void remover(PessoaJuridica pessoaJuridica);

    //encontra uma pessoa jurídica pelo ID
    PessoaJuridica encontrar(Object id);

    //obtém todas as pessoas jurídicas cadastradas
    List<PessoaJuridica> encontrarTodos();

    //obtém uma lista de pessoas jurídicas em um intervalo específico
    List<PessoaJuridica> encontrarRange(int[] range);

    //obtém o número total de pessoas jurídicas cadastradas
    int contar();
}
