package cadastroee.servlets;

import cadastroee.model.Produto;
import cadastroee.controller.ProdutoFacadeLocal;
import java.io.IOException;
import java.util.List;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ServletProdutoFC", urlPatterns = {"/ServletProdutoFC"})
public class ServletProdutoFC extends HttpServlet {

    @EJB
    private ProdutoFacadeLocal facade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String acao = request.getParameter("acao");
        String destino = "ProdutoLista.jsp"; // Página padrão de destino
        
        if (acao != null) {
            switch (acao) {
                case "listar":
                    List<Produto> produtos = facade.findAll(); // Recuperando todos os produtos
                    request.setAttribute("produtos", produtos);
                    break;
                case "formAlterar":
                    String idProdutoStr = request.getParameter("idProduto");
                    if (idProdutoStr != null) {
                        Integer idProduto = Integer.parseInt(idProdutoStr);
                        Produto produto = facade.find(idProduto); // Recuperando um produto pelo ID
                        request.setAttribute("produto", produto);
                        destino = "ProdutoDados.jsp";
                    }
                    break;
                case "formIncluir":
                    // Ação para exibir o formulário de inclusão
                    destino = "ProdutoDados.jsp";
                    break;
                case "excluir":
                    String idProdutoStr = request.getParameter("idProduto");
                    if (idProdutoStr != null) {
                        Integer idProduto = Integer.parseInt(idProdutoStr);
                        Produto produto = facade.find(idProduto); // Recuperando um produto pelo ID
                        if (produto != null) {
                            facade.remove(produto);
                        }
                        // Recarregando a lista após a exclusão
                        produtos = facade.findAll(); // Recuperando todos os produtos
                        request.setAttribute("produtos", produtos);
                    }
                    break;
                case "alterar":
                case "incluir":
                    // Recuperando os parâmetros do formulário
                    Integer idProduto = null;
                    String nome = request.getParameter("nome");
                    Integer quantidade = parseIntegerParameter(request, "quantidade");
                    Float precoVenda = parseFloatParameter(request, "precoVenda");
                    
                    // Convertendo valores se estiverem disponíveis
                    idProduto = parseIntegerParameter(request, "idProduto");

                    // Criando ou atualizando o produto
                    Produto produto;
                    if (acao.equals("alterar")) {
                        produto = facade.find(idProduto); // Recuperando um produto pelo ID
                        produto.setNome(nome);
                        produto.setQuantidade(quantidade);
                        produto.setPrecoVenda(precoVenda);
                    } else {
                        produto = new Produto(idProduto, nome, quantidade, precoVenda);
                    }
                    facade.edit(produto); // Inserindo ou atualizando um produto
                    
                    // Recarregando a lista após a inclusão ou atualização
                    produtos = facade.findAll(); // Recuperando todos os produtos
                    request.setAttribute("produtos", produtos);
                    break;
                default:
                    // Ação desconhecida
                    break;
            }
        }
        
        // Redirecionamento para o destino apropriado
        request.getRequestDispatcher(destino).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "ServletProdutoFC";
    }

    private Integer parseIntegerParameter(HttpServletRequest request, String paramName) {
        String paramValue = request.getParameter(paramName);
        if (paramValue != null && !paramValue.isEmpty()) {
            return Integer.parseInt(paramValue);
        }
        return null;
    }

    private Float parseFloatParameter(HttpServletRequest request, String paramName) {
        String paramValue = request.getParameter(paramName);
        if (paramValue != null && !paramValue.isEmpty()) {
            return Float.parseFloat(paramValue);
        }
        return null;
    }
}
