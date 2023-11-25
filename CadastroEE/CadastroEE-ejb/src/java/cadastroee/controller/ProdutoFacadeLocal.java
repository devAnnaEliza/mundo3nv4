package cadastroee.controller;

import java.util.List;
import jakarta.ejb.Local;
import cadastroee.model.Produto;

@Local
public interface ProdutoFacadeLocal {

    void create(Produto produto);

    void edit(Produto produto);

    void remove(Produto produto);

    Produto find(Object idProduto);

    List<Produto> findAll();

    List<Produto> findRange(int[] range);

    int count();

    //adicionando métodos extras para demonstrar expansão
    List<Produto> findByName(String name);

    List<Produto> findByQuantity(int quantity);

    List<Produto> findByPriceRange(float minPrice, float maxPrice);
}
