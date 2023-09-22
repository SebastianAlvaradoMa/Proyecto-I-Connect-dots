import java.io.*;
import java.net.*;
//Importamos las librerias de archivos
class ClienteSocket {
    public static void main(String[] args) {
        final String servidorIP = "host local"; // el host local se puede cambiar por la ip local
        final int puerto = 12345; //Igualmente el puerto se puede cambiar

        try (Socket clienteSocket = new Socket(servidorIP, puerto)) {

            BufferedReader entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
            // Entrada para recibir datos del server

            PrintWriter salida = new PrintWriter(clienteSocket.getOutputStream(), true);
            // Salida para enviar datos al server

            salida.println("Conexion con el server!");
            // Enviar un mensaje al server

            String respuestaServidor = entrada.readLine();
            // Leer la respuesta del server y mostrarla en el cliente
            System.out.println("Servidor dice: " + respuestaServidor);

        } catch (IOException e) {
            System.out.println("error");
        }
    }
}

