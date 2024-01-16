import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Locale;

public class DistterServer {

    /**
     * Initialise a new server. To run the server, call run().
     */
    public DistterServer() {}

    /**
     * Runs the server.
     * @throws IOException
     */
    public void run() throws IOException {

        /*** Set up to accept incoming TCP connections ***/

        int port = 20111;

        System.out.println("Opening the server socket on port " + port);
        ServerSocket serverSocket = new ServerSocket(port);


        /*** Receive client connection ***/

        // Waits until a client connects
        System.out.println("Server waiting for client...");
        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected!");

        // Set up readers and writers for convenience
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        Writer writer = new OutputStreamWriter(clientSocket.getOutputStream());



        /*** Output what the client says ***/

        String msg;
        String cmd;
        boolean exit=false;
        while (true) {
            msg = reader.readLine();
            cmd = msg.substring(0,msg.indexOf(' '));//get the command
            cmd.toUpperCase(Locale.ROOT);
            switch(cmd){//switch for handling the requests
                case "HELLO?":
                    System.out.println("You handled a HELLO request \n" +
                            "You made a correct HELLO request!\n");
                    break;
                case "POSTS?":
                    System.out.println("Looks like a POSTS? request... \n" +
                            "You made a correct POSTS request!\n");
                    break;
                case "WHEN?":
                    System.out.println("You made a correct WHEN request! \n");
                    break;
                case "GOODBYE!":
                    System.out.println("You made a correct GOODBYE request!\n" +
                            "Saying GOODBYE \n Interaction done");
                    exit = true;
                    break;
                case "FETCH":
                    System.out.println("Looks like a FETCH? request...\n" +
                            "It even has the SHA-256!\n You made a correct FETCH request!");
                    break;
            }
            if(exit)
                break;
        }

        // Close down the connection
        clientSocket.close();
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        DistterServer server1 = new DistterServer();
        server1.run();
    }
}