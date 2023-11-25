package cadastroee.controller;

import cadastroee.model.Movimento;
import jakarta.ejb.Local;
import java.util.List;

@Local
public interface MovimentoFacadeLocal {

    void criarMovimento(Movimento movimento);

    void atualizarMovimento(Movimento movimento);

    void excluirMovimento(int idMovimento);

    Movimento encontrarMovimentoPorId(int idMovimento);

    List<Movimento> listarTodosMovimentos();
}
