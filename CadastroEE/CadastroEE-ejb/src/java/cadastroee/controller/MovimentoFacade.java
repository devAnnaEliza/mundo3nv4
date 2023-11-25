package cadastroee.controller;

import cadastroee.model.Movimento;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Stateless
public class MovimentoFacade implements MovimentoFacadeLocal {

    @PersistenceContext(unitName = "CadastroEE-ejbPU")
    private EntityManager em;

    @Override
    public void criarMovimento(Movimento movimento) {
        em.persist(movimento);
    }

    @Override
    public void atualizarMovimento(Movimento movimento) {
        em.merge(movimento);
    }

    @Override
    public void excluirMovimento(int idMovimento) {
        Movimento movimento = encontrarMovimentoPorId(idMovimento);
        if (movimento != null) {
            em.remove(movimento);
        }
    }

    @Override
    public Movimento encontrarMovimentoPorId(int idMovimento) {
        return em.find(Movimento.class, idMovimento);
    }

    @Override
    public List<Movimento> listarTodosMovimentos() {
        TypedQuery<Movimento> query = em.createQuery("SELECT m FROM Movimento m", Movimento.class);
        return query.getResultList();
    }
}
