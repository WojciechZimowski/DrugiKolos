package server;

import game.Duel;
import game.Gesture;
import game.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Player implements Runnable {
    private Socket clientSocket;
    private Server server;
    private PrintWriter out;
    private BufferedReader in;
    private String savedLogin;

    public ClientHandler(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }

    @Override
    public void run() {
        try{
            out= new PrintWriter(clientSocket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out.println("Enter login");
            String login= in.readLine();
            out.println("Enter password");
            String password=in.readLine();
            if(! server.getDatabase().authenticate(login,password)){
                clientSocket.close();
                return;
            }
            this.savedLogin=login;
            out.println("Hello in server!"+savedLogin);
            String msg;
            while((msg=in.readLine())!=null){
                if(isDueling()){
                    handleDuelMessage(msg);
                }else {
                    System.out.println(savedLogin + ":" + msg);
                    server.challengeToDuel(this, msg);
                }
            }
        }catch(Exception e){
            System.err.println("Error in ClientHandler");
        }finally {
            closeConnection();
        }
    }
    public void sendMessage(String message){
        if(out!=null){
            out.println(message);
        }
    }
    private void closeConnection(){
        try{
            server.removeClient(this);
            if(in!=null) in.close();
            if(out!=null) out.close();
            if(clientSocket!=null&& !clientSocket.isClosed())clientSocket.close();
            System.out.println("Connection closed");
        }catch(Exception e){
            System.err.println("Error in ClientHandler");
        }
    }
    public String getSavedLogin() {
        return savedLogin;
    }
    private void handleDuelMessage(String msg){
        try{
            Gesture gesture = Gesture.fromString(msg);
            makeGesture(gesture);
            sendMessage("Move:"+gesture);
        }catch(IllegalArgumentException e){
            sendMessage("Invalid move");
        }
    }
}
