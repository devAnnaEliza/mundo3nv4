package cadastroee.controller;

import cadastroee.model.Pessoa;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class PessoaFacade implements PessoaFacadeLocal {

    @PersistenceContext(unitName = "CadastroEE-ejbPU")
    private EntityManager em;

    @Override
    public void criar(Pessoa pessoa) {
        em.persist(pessoa);
    }

    @Override
    public void atualizar(Pessoa pessoa) {
        em.merge(pessoa);
    }

    @Override
    public void remover(Pessoa pessoa) {
        Pessoa pessoaParaRemover = em.find(Pessoa.class, pessoa.getIdPessoa());
        if (pessoaParaRemover != null) {
            em.remove(pessoaParaRemover);
        }
    }

    @Override
    public Pessoa encontrar(Object id) {
        return em.find(Pessoa.class, id);
    }

    @Override
    public List<Pessoa> encontrarTodos() {
        return em.createNamedQuery("Pessoa.findAll", Pessoa.class).getResultList();
    }

    @Override
    public List<Pessoa> encontrarRange(int[] range) {
        jakarta.persistence.criteria.CriteriaQuery<Pessoa> cq = em.getCriteriaBuilder().createQuery(Pessoa.class);
        cq.select(cq.from(Pessoa.class));
        jakarta.persistence.Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    @Override
    public int contar() {
        jakarta.persistence.criteria.CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
        jakarta.persistence.criteria.Root<Pessoa> rt = cq.from(Pessoa.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        jakarta.persistence.Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}
