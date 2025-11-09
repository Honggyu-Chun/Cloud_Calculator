import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class Calculatorthread implements Runnable {
    
    Socket socket;
    
    public  Calculatorthread(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        //define variable
        String clientSentence;
        StringTokenizer st;

        //inout stream create
        try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());)
        {
            //read command
            clientSentence = in.readLine();

            //check message
            if(clientSentence == null) {
                out.writeBytes("Error: No Sentence");
                return;
            }

            //client message print
            System.out.println("Client: " + clientSentence);

            //tokenize command
            st = new StringTokenizer(clientSentence);
            
            String operator = st.nextToken();
            operator = operator.toUpperCase();
            //operate calculation
            if(operator.equals("ADD")){
                double n1 = Double.parseDouble(st.nextToken());
                double n2 = Double.parseDouble(st.nextToken());
                if(st.hasMoreTokens()){
                    out.writeBytes("Too Many Numbers\n");
                    return;
                }
                double sum = n1 + n2;
                out.writeBytes(n1 + " + " + n2 + " = " + sum + "\n");
                out.flush();
            }
            else if(operator.equals("SUB")){
                double n1 = Double.parseDouble(st.nextToken());
                double n2 = Double.parseDouble(st.nextToken());
                if(st.hasMoreTokens()){
                    out.writeBytes("Too Many Numbers\n");
                    return;
                }
                double sub = n1 - n2;
                out.writeBytes(n1 + " - " + n2 + " = " + sub + "\n");
                out.flush();
            }
            else if(operator.equals("MUL")){
                double n1 = Double.parseDouble(st.nextToken());
                double n2 = Double.parseDouble(st.nextToken());
                if(st.hasMoreTokens()){
                    out.writeBytes("Too Many Numbers\n");
                    return;
                }
                double mul = n1 * n2;
                out.writeBytes(n1 + " * " + n2 + " = " + mul + "\n");
                out.flush();
            }
            else if(operator.equals("DIV")){
                double n1 = Double.parseDouble(st.nextToken());
                double n2 = Double.parseDouble(st.nextToken());
                if(st.hasMoreTokens()){
                    out.writeBytes("Too Many Numbers\n");
                    return;
                }
                if(n2 == 0){
                    //division by zero execption
                    out.writeBytes("Error: Division by Zero\n");
                    return;
                }
                double div = n1 / n2;
                out.writeBytes(n1 + " / " + n2 + " = " + div + "\n");
                out.flush();
            }
            //wrong operator
            else{
                out.writeBytes("Error: Invalid Operation...\n");
            }
        }
        //io error catch
        catch(IOException e){
            System.out.println("Something went wrong...\n");

        }
        //invalid number input exeception
        catch(NoSuchElementException e){
            System.out.println("Invalid Input...\n");
        }
    }
}
