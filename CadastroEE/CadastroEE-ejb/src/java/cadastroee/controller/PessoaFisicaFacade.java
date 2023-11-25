package cadastroee.controller;

import cadastroee.model.PessoaFisica;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class PessoaFisicaFacade implements PessoaFisicaFacadeLocal {

    @PersistenceContext(unitName = "CadastroEE-ejbPU")
    private EntityManager em;

    //cria uma nova pessoa
    @Override
    public void criar(PessoaFisica pessoaFisica) {
        em.persist(pessoaFisica);
    }

    //atualiza uma pessoa existente
    @Override
    public void atualizar(PessoaFisica pessoaFisica) {
        em.merge(pessoaFisica);
    }

    //remove uma pessoa
    @Override
    public void remover(PessoaFisica pessoaFisica) {
        em.remove(em.merge(pessoaFisica));
    }

    //encontra uma pessoa pelo ID
    @Override
    public PessoaFisica encontrar(Object id) {
        return em.find(PessoaFisica.class, id);
    }

    //obtém todas as pessoas cadastradas
    @Override
    public List<PessoaFisica> encontrarTodos() {
        return em.createNamedQuery("PessoaFisica.findAll").getResultList();
    }

    //obtém uma lista de pessoas em um intervalo específico
    @Override
    public List<PessoaFisica> encontrarRange(int[] range) {
        jakarta.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(PessoaFisica.class));
        jakarta.persistence.Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    //obtém o número total de pessoas cadastradas
    @Override
    public int contar() {
        jakarta.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        jakarta.persistence.criteria.Root<PessoaFisica> rt = cq.from(PessoaFisica.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        jakarta.persistence.Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
} 