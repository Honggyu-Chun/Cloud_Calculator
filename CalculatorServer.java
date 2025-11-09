import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CalculatorServer {
    public static void main(String[] args) throws IOException {
        //define welcomesocket
        ServerSocket welcomeSocket = null;

        //server port & create socket
        int port = 5678;
        welcomeSocket = new ServerSocket(port);
        System.out.println("Server Started: " + port);

        //define thread num
        int threadNum = 10;
        ExecutorService thread = Executors.newFixedThreadPool(threadNum);

        //accept
        try {
            while (true) {
                Socket socket = welcomeSocket.accept();
                System.out.println("Accepted from: " + socket.getInetAddress());
                thread.submit(new Calculatorthread(socket));
            }
        }
        catch(IOException e){
            System.out.println("IOException: " + e.getMessage());
        }
        finally{
            //close socket
            if(welcomeSocket != null){
                welcomeSocket.close();
            }
            //return thread
            if(thread != null){
                thread.shutdown();
            }
        }
    }
}

