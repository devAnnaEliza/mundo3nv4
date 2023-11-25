package cadastroee.servlets;

import cadastroee.model.Produto;
import cadastroee.controller.ProdutoFacadeLocal; // Assuming DadosProdutoLocal is the correct interface
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
    private ProdutoFacadeLocal dadosProduto;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        String destino = "ListaProduto.jsp"; // Default destination page

        if (acao != null) {
            switch (acao) {
                case "listar":
                    List<Produto> produtos = dadosProduto.obterTodosProdutos(); // Retrieve all products
                    request.setAttribute("produtos", produtos);
                    break;
                case "formAlterar":
                    String idProdutoStr = request.getParameter("idProduto");
                    if (idProdutoStr != null) {
                        Integer idProduto = Integer.parseInt(idProdutoStr);
                        Produto produto = dadosProduto.obterProdutoPorId(idProduto); // Retrieve product by ID
                        request.setAttribute("produto", produto);
                        destino = "DadosProduto.jsp";
                    }
                    break;
                case "formIncluir":
                    // Action to display the inclusion form
                    destino = "DadosProduto.jsp";
                    break;
                case "excluir":
                    String idProdutoStr = request.getParameter("idProduto");
                    if (idProdutoStr != null) {
                        Integer idProduto = Integer.parseInt(idProdutoStr);
                        Produto produto = dadosProduto.obterProdutoPorId(idProduto); // Retrieve product by ID
                        if (produto != null) {
                            dadosProduto.excluirProduto(produto);
                        }
                        // Reload the list after deletion
                        produtos = dadosProduto.obterTodosProdutos(); // Retrieve all products
                        request.setAttribute("produtos", produtos);
                    }
                    break;
                case "alterar":
                case "incluir":
                    // Retrieve form parameters
                    Integer idProduto = parseIntegerParameter(request, "idProduto");
                    String nome = request.getParameter("nome");
                    Integer quantidade = parseIntegerParameter(request, "quantidade");
                    Float precoVenda = parseFloatParameter(request, "precoVenda");

                    // Create or update the product
                    Produto produto;
                    if (acao.equals("alterar")) {
                        produto = dadosProduto.obterProdutoPorId(idProduto); // Retrieve product by ID
                        produto.setNome(nome);
                        produto.setQuantidade(quantidade);
                        produto.setPrecoVenda(precoVenda);
                    } else {
                        produto = new Produto(idProduto, nome, quantidade, precoVenda);
                    }
                    dadosProduto.salvarProduto(produto); // Insert or update a product

                    // Reload the list after inclusion or update
                    produtos = dadosProduto.obterTodosProdutos(); // Retrieve all products
                    request.setAttribute("produtos", produtos);
                    break;
                default:
                    // Unknown action
                    break;
            }
        }

        // Forward to the appropriate destination
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
