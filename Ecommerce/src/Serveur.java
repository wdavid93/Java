import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

public class Serveur implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "";
        String path = exchange.getRequestURI().getPath();

        System.out.println("Request path: " + path);

        switch (path) {
            case "/":
                response = getHTML("web/index.html");
                exchange.sendResponseHeaders(200, response.length());
                break;
            case "/produits":
                response = getHTML("web/produits.html");
                exchange.sendResponseHeaders(200, response.length());
                break;
            case "/panier":
                response = getHTML("web/panier.html");
                exchange.sendResponseHeaders(200, response.length());
                break;
            default:
                response = "404 Not Found";
                exchange.sendResponseHeaders(404, response.length());
                break;
        }

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String getHTML(String filePath) throws IOException {
        // Obtenir le répertoire parent du projet
        Path currentRelativePath = Paths.get("");
        String basePath = currentRelativePath.toAbsolutePath().toString();
        
        // Remonter d'un niveau
        Path basePathParent = Paths.get(basePath).getParent();
        System.out.println("basePathParent : " + basePathParent);
        
        // Construire le chemin complet vers le fichier HTML
        Path path = Paths.get(basePathParent.toString(), filePath);
        System.out.println("Trying to read file: " + path); // Afficher le chemin absolu
        System.out.println("File exists: " + Files.exists(path)); // Afficher si le fichier existe
        
        if (Files.exists(path)) {
            return new String(Files.readAllBytes(path));
        } else {
            return "<html><body><h1>404 Not Found</h1></body></html>";
        }
    }
}
