import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketServer {
    private static List<String> players = new ArrayList<>();
    private static int connectionCount = 0;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Servidor esperando conexiones en el puerto 12345...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado desde " + clientSocket.getInetAddress());
                connectionCount++;

                // Manejar la conexión en un subproceso
                Thread clientThread = new Thread(() -> {
                    try {
                        // Crear flujos de entrada y salida para la comunicación
                        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

                        // Enviar la lista de jugadores y la variable de conteo de conexiones
                        // Data data = new MyData(players, connectionCount);
                        ObjectMapper objectMapper = new ObjectMapper();
                        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

                        // Recibir el objeto JSON del cliente
                        String receivedJson = (String) in.readObject();
                        ObjectReader objectReader = objectMapper.readerFor(MyData.class);
                        MyData receivedData = objectReader.readValue(receivedJson);

                        System.out.println(connectionCount);
                        System.out.println(players.size());

                        if (connectionCount == 1) {
                            receivedData.setJugando(true);
                        }

                        while (!receivedData.isFull() || connectionCount != players.size()) {
                            if (receivedData.getRegister()) {
                                players.add(receivedData.getNombre());
                            }
                            System.out.println(players.toString());
                            for (int i = 0; i < players.size(); i++) {
                                System.out.println(players.get(i));
                                // Serializar el objeto modificado y enviarlo de vuelta al cliente'
                                System.out.println(receivedJson);
                                receivedData.setNowPlaying(players.get(i));
                                if (!receivedData.isJugando()) {
                                    receivedData.setJugando(false);
                                    String modifiedJson = objectWriter.writeValueAsString(receivedData);
                                    out.writeObject(modifiedJson);
                                    out.flush();
                                    receivedJson = (String) in.readObject();
                                    receivedData = objectReader.readValue(receivedJson);
                                    receivedData.setJugando(true);
                                }

                            }
                        }

                        System.out.println("ESTOY FUERA");

                        // Cerrar conexiones
                        in.close();
                        out.close();
                        clientSocket.close();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

                // Iniciar el subproceso para atender al cliente
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Data {
    private List<String> players;
    private int connectionCount;

    public Data(List<String> players, int connectionCount) {
        this.players = players;
        this.connectionCount = connectionCount;
    }

    public List<String> getPlayers() {
        return players;
    }

    public int getConnectionCount() {
        return connectionCount;
    }
}

