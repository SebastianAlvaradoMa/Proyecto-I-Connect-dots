import java.io.*;
import java.net.*;
//Importamos las librerias
class Scratch {
    public static void main(String[] args) {
        final int puerto = 12345;
        //String nombres[] = new String[];

        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Esperando conexion con el puerto " + puerto);

            while (true) {
                try (Socket clienteSocket = serverSocket.accept()) {
                    System.out.println("Cliente conectado desde " + clienteSocket.getInetAddress().getHostAddress());

                    BufferedReader entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
                    // Entrada para recibir datos del cliente

                    PrintWriter salida = new PrintWriter(clienteSocket.getOutputStream(), true);
                    // Salida para enviar datos al cliente

                    String mensajeCliente = entrada.readLine();
                    // Leer datos del cliente y mostrarlos en la consola del servidor
                    System.out.println("Cliente dice: " + mensajeCliente);

                    // Enviar una respuesta al cliente
                    salida.println("Hola cliente");

                    // Cerrar la conexión con el cliente
                    clienteSocket.close();


                }
            }
        } catch (IOException e) {
            System.out.println("error");
        }
    }
}
//Interfaz grafica, hace falta implementarla bien


