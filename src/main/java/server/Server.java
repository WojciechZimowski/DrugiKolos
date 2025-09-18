package server;

import com.almasb.fxgl.net.Client;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArraySet;

public class Server {
    private static final int port = 5000;
    private CopyOnWriteArraySet<ClientHandler> clients = new CopyOnWriteArraySet<>();
    private Database database;
    public Server(){
        this.database = new Database();
    }
    public Database getDatabase() {
        return database;
    }
    public void listen(){
        try(ServerSocket server = new ServerSocket(port)){
            while(true){
                Socket clientSocket = server.accept();
                System.out.println("Connected"+clientSocket.getInetAddress()        );
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }
    public void removeClient(ClientHandler client){
        clients.remove(client);
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.listen();
    }
}
