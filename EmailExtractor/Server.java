package EmailExtractor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private Socket socket;
    private int port;
    Server(Socket socket) {
       this.socket = socket;
       this.port = this.socket.getPort();
    }
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java KnockKnockServer <port number>");
            System.exit(1);
        }
         int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(String.valueOf(port)));
        while (true) {
            Socket soc = serverSocket.accept();
            System.out.println("New connection from " +
                               soc.getInetAddress().getHostName() +
                               ":"+soc.getPort());
            new Thread(new Server(soc)).start();
        }
    }

    @Override
    public void run() {
        try {
            PrintWriter out =
                    new PrintWriter(this.socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(this.socket.getInputStream()));
            String inputLine;
            String outputLine;
            // Initiate conversation with client
            ServerResponse serverResponse = new ServerResponse();
            outputLine = serverResponse.processInput("3");
            out.println(outputLine);
            while ((inputLine = in.readLine()) != null) {
                outputLine = serverResponse.processInput(inputLine);
                out.println(outputLine);
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + this.port + " or listening for a connection");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
