package cadastroee.controller;

import cadastroee.model.Usuario;
import jakarta.ejb.Stateless;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class UsuarioFacade implements UsuarioFacadeLocal {

    @PersistenceContext(unitName = "CadastroEE-ejbPU")
    private EntityManager em;

    @Override
    public void create(Usuario usuario) {
        em.persist(usuario);
    }

    @Override
    public void edit(Usuario usuario) {
        em.merge(usuario);
    }

    @Override
    public void remove(Usuario usuario) {
        Usuario mergedUsuario = em.merge(usuario);
        em.remove(mergedUsuario);
    }

    @Override
    public Usuario find(Object id) {
        return em.find(Usuario.class, id);
    }

    @Override
    public List<Usuario> findAll() {
        return em.createNamedQuery("Usuario.findAll", Usuario.class).getResultList();
    }

    @Override
    public List<Usuario> findRange(int[] range) {
        jakarta.persistence.criteria.CriteriaQuery<Usuario> cq = em.getCriteriaBuilder().createQuery(Usuario.class);
        jakarta.persistence.criteria.Root<Usuario> rt = cq.from(Usuario.class);
        cq.select(rt);
        jakarta.persistence.Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    @Override
    public int count() {
        jakarta.persistence.criteria.CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
        jakarta.persistence.criteria.Root<Usuario> rt = cq.from(Usuario.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        jakarta.persistence.Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    //adicionando métodos extras para demonstrar expansão
    @Override
    public Usuario findByUsername(String username) {
        jakarta.persistence.criteria.CriteriaQuery<Usuario> cq = em.getCriteriaBuilder().createQuery(Usuario.class);
        jakarta.persistence.criteria.Root<Usuario> rt = cq.from(Usuario.class);
        cq.where(em.getCriteriaBuilder().equal(rt.get("login"), username));
        return em.createQuery(cq).getSingleResult();
    }

    @Override
    public List<Usuario> findByRole(String role) {
        jakarta.persistence.criteria.CriteriaQuery<Usuario> cq = em.getCriteriaBuilder().createQuery(Usuario.class);
        jakarta.persistence.criteria.Root<Usuario> rt = cq.from(Usuario.class);
        cq.where(em.getCriteriaBuilder().equal(rt.get("role"), role));
        return em.createQuery(cq).getResultList();
    }
}
