import java.io.*;
import java.net.*;
//Importamos las librerias
class Scratch {
    public static void main(String[] args) {
        final int puerto = 12345;

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
class Server extends JFrame {
    private JRadioButton[][] buttonArray = new JRadioButton[8][8];
    private boolean h[][] = new boolean[8][8];
    private boolean v[][] = new boolean[8][8];
    private String nombrejugador1;
    private String nombrejugador2;
    private JLabel jugador1;
    private JLabel jugador2;
    private int puntajeJugador1 = 0;
    private int puntajeJugador2 = 0;
    //private boolean sigue = true;
    private int count = 0;
    private int ganador[][] = new int[8][8];
    private int contador2;
    private int contador3 = 0;
    private String nombre = "PlayerData.bin";
}
public Server() {
    setSize(900, 700);
    setLocationRelativeTo(null);
    setTitle("Conectar el punto");
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    JMenuBar jmb = new JMenuBar();
    JMenu file = new JMenu("Archivo");
    jmb.add(file);
    JMenuItem newgame = new JMenuItem("Nuevo Juego");
    JMenuItem save = new JMenuItem("Guardar");
    JMenuItem exit = new JMenuItem("Salir");
    JMenuItem help = new JMenuItem("Regla");
    JMenuItem load = new JMenuItem("Cargar");
    file.add(newgame);
    file.add(save);
    file.add(load);
    file.add(help);
    file.add(exit);
}
