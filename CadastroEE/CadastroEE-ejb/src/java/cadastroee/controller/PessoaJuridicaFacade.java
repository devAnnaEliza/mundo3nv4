package cadastroee.controller;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import cadastroee.model.PessoaJuridica;

@Stateless
public class PessoaJuridicaFacade implements PessoaJuridicaFacadeLocal {

    @PersistenceContext(unitName = "CadastroEE-ejbPU")
    private EntityManager em;

    //cria uma nova pessoa
    @Override
    public void create(PessoaJuridica pessoaJuridica) {
        em.persist(pessoaJuridica);
    }

    //atualiza uma pessoa jurídica existente
    @Override
    public void edit(PessoaJuridica pessoaJuridica) {
        em.merge(pessoaJuridica);
    }

    //remove uma pessoa jurídica
    @Override
    public void remove(PessoaJuridica pessoaJuridica) {
        em.remove(em.merge(pessoaJuridica));
    }

    //encontra uma pessoa jurídica pelo ID
    @Override
    public PessoaJuridica find(Object id) {
        return em.find(PessoaJuridica.class, id);
    }

    //obtém todas as pessoas jurídicas cadastradas
    @Override
    public List<PessoaJuridica> findAll() {
        //usando uma consulta JPQL para obter todas as pessoas jurídicas
        return em.createQuery("SELECT p FROM PessoaJuridica p").getResultList();
    }

    //obtém uma lista de pessoas jurídicas em um intervalo específico
    @Override
    public List<PessoaJuridica> findRange(int[] range) {
        jakarta.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(PessoaJuridica.class));
        jakarta.persistence.Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    //obtém o número total de pessoas jurídicas cadastradas
    @Override
    public int count() {
        jakarta.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        jakarta.persistence.criteria.Root<PessoaJuridica> rt = cq.from(PessoaJuridica.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        jakarta.persistence.Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    //métodos abaixo lançam UnsupportedOperationException para serem implementados
    @Override
    public void criar(PessoaJuridica pessoaJuridica) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void atualizar(PessoaJuridica pessoaJuridica) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void remover(PessoaJuridica pessoaJuridica) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PessoaJuridica encontrar(Object id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<PessoaJuridica> encontrarTodos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<PessoaJuridica> encontrarRange(int[] range) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int contar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
