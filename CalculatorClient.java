import java.net.*;
import java.io.*;
import java.util.*;

public class CalculatorClient {
    public static void main(String[] args) throws IOException {
        String command;
        String answer;
        String IP;
        int port;

        //file io to parsing server:port
        String configFileName = "server_info.dat";
        try (BufferedReader configReader = new BufferedReader(new FileReader(configFileName))) {
            IP = configReader.readLine();
            port = Integer.parseInt(configReader.readLine());
            System.out.println("Config file loaded: " + IP + ":" + port);
        } catch (FileNotFoundException e) {
            System.out.println(configFileName + " not found. Using default settings.");
            IP = "localhost";
            port = 5678;
        } catch (Exception e) {
            System.out.println("Config file error: " + e.getMessage() + ". Using default settings.");
            IP = "localhost";
            port = 5678;
        }

        //create socket & connect to server
        try {
            Socket socket = new Socket(IP, port);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            System.out.println("Connecting to " + IP + ":" + port);
            System.out.println("Please enter operation you want:");
            System.out.println("ADD n n, SUB n n, MUL n n, DIV n n");
            //message send to server
            command = br.readLine();
            out.writeBytes(command + "\n");
            //message received by server
            answer = in.readLine();
            if (answer == null) {
                System.out.println("Server Response: Invalid Input...\n");
                return;
            }
            System.out.println("Server Response: " + answer);
            socket.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
