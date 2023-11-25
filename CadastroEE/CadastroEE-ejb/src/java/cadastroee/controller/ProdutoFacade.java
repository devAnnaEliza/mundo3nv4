package cadastroee.controller;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import cadastroee.model.Produto;
import java.util.List;

@Stateless
public class ProdutoFacade implements ProdutoFacadeLocal {

    @PersistenceContext(unitName = "CadastroEE-ejbPU")
    private EntityManager em;

    @Override
    public void create(Produto produto) {
        em.persist(produto);
    }

    @Override
    public void edit(Produto produto) {
        em.merge(produto);
    }

    @Override
    public void remove(Produto produto) {
        em.remove(em.merge(produto));
    }

    @Override
    public Produto find(Object idProduto) {
        return em.find(Produto.class, idProduto);
    }

    @Override
    public List<Produto> findAll() {
        return em.createNamedQuery("Produto.findAll", Produto.class).getResultList();
    }

    @Override
    public List<Produto> findRange(int[] range) {
        return em.createNamedQuery("Produto.findAll", Produto.class)
                .setMaxResults(range[1] - range[0] + 1)
                .setFirstResult(range[0])
                .getResultList();
    }

    @Override
    public int count() {
        return em.createQuery("SELECT COUNT(p) FROM Produto p", Long.class).getSingleResult().intValue();
    }

    @Override
    public List<Produto> findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Produto> findByQuantity(int quantity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Produto> findByPriceRange(float minPrice, float maxPrice) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
