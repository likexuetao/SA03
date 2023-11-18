import Server.AddHttpServer;
import Server.DeleteHttpServer;
import Server.EditHttpServer;
import Server.ShowHttpServer;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * author:          ndg
 * date:            2023/11/3
 */
public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/add",new AddHttpServer());
        server.createContext("/delete",new DeleteHttpServer());
        server.createContext("/show",new ShowHttpServer());
        server.createContext("/edit",new EditHttpServer());
        server.start();
    }
}