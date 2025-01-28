import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;
    private String clientUID;
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String[] userInfo = bufferedReader.readLine().split(":", 2);
            this.clientUID = userInfo[0];
            this.clientUsername = userInfo[1];

            clientHandlers.add(this);
            broadcastMessage("SERVER: " + clientUsername + " подключился");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String messageFromClient;
        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();
                if (messageFromClient != null) {
                    broadcastMessage(messageFromClient);
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void broadcastMessage(String messageToSend) {
        for (ClientHandler client : clientHandlers) {
            try {
                if (!client.clientUID.equals(clientUID)) {
                    client.bufferedWriter.write(messageToSend);
                    client.bufferedWriter.newLine();
                    client.bufferedWriter.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
