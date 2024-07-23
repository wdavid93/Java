// import java.io.IOException;
// import java.net.InetSocketAddress;
// import com.sun.net.httpserver.HttpServer;

// public class Main {
//     public static void main(String[] args) {
//         try {
//             HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
//             server.createContext("/", new Serveur());
//             server.createContext("/produits", new Serveur());
//             server.createContext("/panier", new Serveur());
//             // server.setExecutor(null); // crée un exécuteur par défaut
//             server.start();
//             System.out.println("Serveur démarré sur http://localhost:8081");
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
// }

import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

public class Main {
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
            server.createContext("/", new Serveur());
            server.createContext("/produits", new Serveur());
            server.createContext("/panier", new Serveur());
            server.setExecutor(null); // crée un exécuteur par défaut
            server.start();
            System.out.println("Serveur démarré sur http://localhost:8081");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

