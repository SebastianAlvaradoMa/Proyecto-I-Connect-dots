import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectReader;

import java.io.*;
import java.net.Socket;

import java.awt.*;
import javax.swing.*;

public class SocketClient extends JFrame {

    //Variables para los sockets
    public static String playername1;
    static int allow = 0;
    static int allow2 = 0;
    static boolean full = false;
    public static ObjectOutputStream out;
    public static MyData receivedData;
    public static ObjectMapper objectMapper;
    public static ObjectInputStream in;
    public static Socket socket;

    public static void enviar(MyData objeto) throws IOException {
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        String json = objectWriter.writeValueAsString(objeto);
        out.writeObject(json);
        out.flush();
    }

    public static void leer() throws ClassNotFoundException, IOException {
        String receivedJson = (String) SocketClient.in.readObject();
        ObjectReader objectReader = SocketClient.objectMapper.readerFor(MyData.class);
        SocketClient.receivedData = objectReader.readValue(receivedJson);
        System.out.println(receivedJson);
    }

    public static void main(String[] args) throws ClassNotFoundException {
        try {

            socket = new Socket("localhost", 12345);

            // Crear flujos de entrada y salida para la comunicación
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            playername1 = JOptionPane.showInputDialog("Player 1 enter your name: ");
            if (playername1.equals("")) {
                while (allow == 0) {
                    JOptionPane.showMessageDialog(null, "You have to enter name!");
                    playername1 = JOptionPane.showInputDialog("Player 1 enter your name: ");
                    if (!playername1.equals("")) {
                        allow = 1;
                    }
                }
            }

            // Crear un objeto MyData y llenarlo
            MyData dataToSend = new MyData(playername1, 42, true, null);

            // Serializar el objeto y enviarlo al servidor
            objectMapper = new ObjectMapper();
            ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
            String json = objectWriter.writeValueAsString(dataToSend);
            out.writeObject(json);
            out.flush();

            new project();

            // Recibir el objeto JSON del servidor
            // String receivedJson = (String) in.readObject();
            // ObjectReader objectReader = objectMapper.readerFor(MyData.class);
            // SocketClient.receivedData = objectReader.readValue(receivedJson);
            // System.out.println(
            // "Objeto JSON recibido del servidor: " + SocketClient.receivedData.getNombre()
            // + ", "
            // + receivedData.getPuntos());

            // Cerrar conexiones

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

//Variables de interfaz
class project extends JFrame {
    private JRadioButton[][] buttonArray = new JRadioButton[8][8];
    private boolean h[][] = new boolean[8][8];
    private boolean v[][] = new boolean[8][8];
    private String playername1;
    private JLabel player1;
    private JLabel player2;
    private int playerscore1 = 0;
    private int playerscore2 = 0;
    private int count = 0;
    private int win[][] = new int[8][8];
    private int count2;
    private int count3 = 0;


    //Caracteristicas de Ventana
    public project() {

        setSize(900, 700);
        setLocationRelativeTo(null);
        setTitle("Connect the dot");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Parte de logica
        Player players = new Player();
        add(players, BorderLayout.NORTH);

        Game game = new Game();
        add(game, BorderLayout.CENTER);
        Thread th = new Thread(game);
        th.start();

        setResizable(false);
        setVisible(true);

    }

    //Obtener el Ganador
    public void getWinner() throws IOException {
        SocketClient.full = true;
        SocketClient.receivedData.setFull(true);
        SocketClient.enviar(SocketClient.receivedData);
        if (playerscore1 > playerscore2) {
            JOptionPane.showMessageDialog(null, "Player 1 " + playername1 + " is the winner!");
        } else if (playerscore1 == playerscore2) {
            JOptionPane.showMessageDialog(null, "The game is a tie with " + playerscore1 + " points!");
        }

    }


    //Clase padre JPanel, clase hijo Player
    protected class Player extends JPanel {

        private Font font = new Font("Serif", Font.BOLD, 30);
        private int allow = 0;
        private int allow2 = 0;

        //Creador clase jugador
        public Player() {
            playername1 = JOptionPane.showInputDialog("Player 1 enter your name: ");
            if (playername1.equals("")) {
                while (allow == 0) {
                    JOptionPane.showMessageDialog(null, "You have to enter name!");
                    playername1 = JOptionPane.showInputDialog("Player 1 enter your name: ");
                    if (!playername1.equals("")) {
                        allow = 1;
                    }
                }
            }
            player1 = new JLabel(SocketClient.playername1 + ": " + playerscore1);
            player1.setFont(font);
            add(player1);
        }

    }

    protected class Game extends JPanel implements Runnable {
        public Game() {
            // algunas partes server
            setLayout(new GridLayout(8, 8));
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {

                    buttonArray[i][j] = new JRadioButton();
                    buttonArray[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                    h[i][j] = false;
                    v[i][j] = false;
                    win[i][j] = 0;
                    add(buttonArray[i][j]);
                }
            }

        }

        public void paint(Graphics g) {

            super.paint(g);
            //Matriz de botones

            // algunas partes server
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 7; j++) {
                    if (buttonArray[i][j].isSelected() && buttonArray[i][j + 1].isSelected() && h[i][j] == false) {
                        h[i][j] = true;
                        count++;
                        count3++;
                        deselect();
                    } else if (buttonArray[i][j].isSelected() && buttonArray[i][j + 1].isSelected()
                            && h[i][j] == true) {
                        deselect();
                        JOptionPane.showMessageDialog(null, "No puedes conectar estos dos!");
                    }
                }

            for (int i = 0; i < 7; i++)
                for (int j = 0; j < 8; j++) {
                    if (buttonArray[i][j].isSelected() && buttonArray[i + 1][j].isSelected() && v[i][j] == false) {
                        v[i][j] = true;
                        count++;
                        count3++;
                        deselect();
                    } else if (buttonArray[i][j].isSelected() && buttonArray[i + 1][j].isSelected()
                            && v[i][j] == true) {
                        deselect();
                        JOptionPane.showMessageDialog(null, "You can not connect this two!");
                    }
                }

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 7; j++) {
                    if (h[i][j] == true) {
                        g.drawLine(buttonArray[i][j].getX() + 55, buttonArray[i][j].getY() + 38,
                                buttonArray[i][j].getX() + 168, buttonArray[i][j].getY() + 38);
                    }
                }
            }
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 8; j++) {
                    if (v[i][j] == true) {
                        g.drawLine(buttonArray[i][j].getX() + 55, buttonArray[i][j].getY() + 38,
                                buttonArray[i][j].getX() + 55, buttonArray[i][j].getY() + 113);
                    }
                }
            }
        }

        public void deselect() {

            // algunas server
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    buttonArray[i][j].setSelected(false);
                }

            }
        }

        public void run() {

            System.out.println("HOLA");
            // algunas server
            //Corre el juego
            while (true)
            {
                try {
                    System.out.println("adentro");
                    SocketClient.leer();
                    System.out.println("despues");
                    System.out.println(SocketClient.receivedData.toString());

                    if (SocketClient.receivedData.getNowPlaying() == SocketClient.playername1) {
                        // ......
                        player1.setForeground(Color.RED);
                        play();
                    } else {
                        player1.setForeground(Color.BLACK);
                    }
                    SocketClient.enviar(SocketClient.receivedData);
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }

            }

        }

        private void play() {
            repaint();
            // jugar y obtener valores de ganador
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    if (h[i][j] == true && h[i + 1][j] == true && v[i][j] == true && v[i][j + 1] == true
                            && count2 == 1 && win[i][j] == 0) {
                        playerscore1++;
                        player1.setText(playername1 + ": " + playerscore1 + "                              ");
                        win[i][j] = 1;
                        count--;
                    } 
                    else if (h[i][j] == true && h[i + 1][j] == true && v[i][j] == true && v[i][j + 1] == true
                            && count2 == 2 && win[i][j] == 0) {
                        playerscore2++;
                        player2.setText(": " + playerscore2);
                        win[i][j] = 1;
                        count--;
                    }
                    
                }
            }

            if (count3 == 112) {
                try {
                    getWinner();
                    SocketClient.socket.close();
                    SocketClient.in.close();
                    SocketClient.out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
