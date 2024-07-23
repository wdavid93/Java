// src/ArticleServlet.java
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleServlet extends HttpServlet {
    private List<Article> articles = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        // Initialiser avec quelques articles
        articles.add(new Article(1, "Laptop", 1200.00));
        articles.add(new Article(2, "Smartphone", 800.00));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                showNewForm(response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteArticle(request, response);
                break;
            default:
                listArticles(response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "insert":
                insertArticle(request, response);
                break;
            case "update":
                updateArticle(request, response);
                break;
            default:
                listArticles(response);
                break;
        }
    }

    private void listArticles(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>List of Articles</h1>");
        out.println("<a href='?action=new'>New Article</a>");
        out.println("<table border='1'>");
        out.println("<tr><th>ID</th><th>Name</th><th>Price</th><th>Actions</th></tr>");

        for (Article article : articles) {
            out.println("<tr>");
            out.println("<td>" + article.getId() + "</td>");
            out.println("<td>" + article.getName() + "</td>");
            out.println("<td>" + article.getPrice() + "</td>");
            out.println("<td>");
            out.println("<a href='?action=edit&id=" + article.getId() + "'>Edit</a> ");
            out.println("<a href='?action=delete&id=" + article.getId() + "'>Delete</a>");
            out.println("</td>");
            out.println("</tr>");
        }
        out.println("</table>");
        out.println("</body></html>");
    }

    private void showNewForm(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>New Article</h1>");
        out.println("<form method='post' action='?action=insert'>");
        out.println("Name: <input type='text' name='name'><br>");
        out.println("Price: <input type='text' name='price'><br>");
        out.println("<input type='submit' value='Create'>");
        out.println("</form>");
        out.println("</body></html>");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Article article = null;
        for (Article a : articles) {
            if (a.getId() == id) {
                article = a;
                break;
            }
        }
        if (article == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Edit Article</h1>");
        out.println("<form method='post' action='?action=update&id=" + article.getId() + "'>");
        out.println("Name: <input type='text' name='name' value='" + article.getName() + "'><br>");
        out.println("Price: <input type='text' name='price' value='" + article.getPrice() + "'><br>");
        out.println("<input type='submit' value='Update'>");
        out.println("</form>");
        out.println("</body></html>");
    }

    private void insertArticle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        int id = articles.size() + 1;
        articles.add(new Article(id, name, price));
        response.sendRedirect(request.getContextPath() + "/articles");
    }

    private void updateArticle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        for (Article article : articles) {
            if (article.getId() == id) {
                article = new Article(id, name, price);
                articles.set(id - 1, article);
                break;
            }
        }
        response.sendRedirect(request.getContextPath() + "/articles");
    }

    private void deleteArticle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        articles.removeIf(article -> article.getId() == id);
        response.sendRedirect(request.getContextPath() + "/articles");
    }
}
