package EmailExtractor;

public class ClientHandler {
    public void messageHandling(String fromServer) {
        String[] message = fromServer.split(" ");
        switch (message[0]) {
            case "0":
                String[] emailResponse = message[1].split(",");
                for (String s : emailResponse)
                    System.out.println("\t" + s);
                System.out.println();
                break;
            case "1":
                System.out.println("1: No Email Adresses found on the page.");
                break;
            case "2":
                System.out.println("2: Not a valid URL");
                break;
            case "3":
                System.out.println("Connection established to the server.");
                break;
            case "4":
                System.out.println("\n\tERROR:\tAn error occured.\n");
                break;
            default:
                System.out.println("\n\tERROR:\tThis makes no sense.");
                break;
        }
    }
}
