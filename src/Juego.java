import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Juego extends JFrame {
    private JRadioButton[][] buttonArray = new JRadioButton[8][8];
    private boolean h[][] = new boolean[8][8];
    private boolean v[][] = new boolean[8][8];
    private String playername1;
    private String playername2;
    private JLabel player1;
    private JLabel player2;
    private int playerscore1 = 0;
    private int playerscore2 = 0;
    private int count = 0;
    private int win[][] = new int[8][8];
    private int count2;
    private int count3 = 0;
    private int cursorX = 0;
    private int cursorY = 0;

    public Juego() {
        setSize(900, 700);
        setLocationRelativeTo(null);
        setTitle("Connect the dots");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Player players = new Player();
        add(players, BorderLayout.NORTH);

        Game game = new Game();
        add(game, BorderLayout.CENTER);
        Thread th = new Thread(game);
        th.start();

        setResizable(false);
        setVisible(true);
    }

    public void getWinner() {
        if (playerscore1 > playerscore2) {
            JOptionPane.showMessageDialog(null, "Player 1 " + playername1 + " is the winner!");
        } else if (playerscore1 < playerscore2) {
            JOptionPane.showMessageDialog(null, "Player 2 " + playername2 + " is the winner!");
        } else if (playerscore1 == playerscore2) {
            JOptionPane.showMessageDialog(null, "The game is a tie with " + playerscore1 + " points!");
        }
    }

    protected class Player extends JPanel {
        private Font font = new Font("Serif", Font.BOLD, 30);
        private int allow = 0;
        private int allow2 = 0;

        public Player() {
            playername1 = JOptionPane.showInputDialog("Player 1 enter your name: ");
            if (playername1.equals("")) {
                while (allow == 0) {
                    JOptionPane.showMessageDialog(null, "You have to enter a name!");
                    playername1 = JOptionPane.showInputDialog("Player 1 enter your name: ");
                    if (!playername1.equals("")) {
                        allow = 1;
                    }
                }
            }

            playername2 = JOptionPane.showInputDialog("Player 2 enter your name: ");
            if (playername2.equals("")) {
                while (allow2 == 0) {
                    JOptionPane.showMessageDialog(null, "You have to enter a name!");
                    playername2 = JOptionPane.showInputDialog("Player 2 enter your name: ");
                    if (!playername2.equals("")) {
                        allow2 = 1;
                    }
                }
            }

            player1 = new JLabel(playername1 + ": " + playerscore1 + "                              ");
            player2 = new JLabel(playername2 + ": " + playerscore2);
            player1.setFont(font);
            player2.setFont(font);
            add(player1);
            add(player2);
        }
    }

    protected class Game extends JPanel implements Runnable, KeyListener {

        public Game() {
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

            addKeyListener(this);
            setFocusable(true);
            setFocusTraversalKeysEnabled(false);
        }

        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    if (cursorY > 0) // Izquierda
                        cursorY--;
                    break;
                case KeyEvent.VK_RIGHT:
                    if (cursorY < 7) // Derecha
                        cursorY++;
                    break;
                case KeyEvent.VK_UP:
                    if (cursorX > 0) // Arriba
                        cursorX--;
                    break;
                case KeyEvent.VK_DOWN:
                    if (cursorX < 7) // Abajo
                        cursorX++;
                    break;
                case KeyEvent.VK_ENTER:
                    buttonArray[cursorX][cursorY].setSelected(true);
                    break;
            }
            repaint();
        }

        public void keyTyped(KeyEvent e) {
        }

        public void keyReleased(KeyEvent e) {
        }

        public void paint(Graphics g) {
            super.paint(g);

            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 7; j++) {
                    if (buttonArray[i][j].isSelected() && buttonArray[i][j + 1].isSelected() && h[i][j] == false) {
                        h[i][j] = true;
                        count++;
                        count3++;
                        deselect();
                    } else if (buttonArray[i][j].isSelected() && buttonArray[i][j + 1].isSelected() && h[i][j] == true) {
                        deselect();
                        JOptionPane.showMessageDialog(null, "You can not connect these two dots!");
                    }
                }

            for (int i = 0; i < 7; i++)
                for (int j = 0; j < 8; j++) {
                    if (buttonArray[i][j].isSelected() && buttonArray[i + 1][j].isSelected() && v[i][j] == false) {
                        v[i][j] = true;
                        count++;
                        count3++;
                        deselect();
                    } else if (buttonArray[i][j].isSelected() && buttonArray[i + 1][j].isSelected() && v[i][j] == true) {
                        deselect();
                        JOptionPane.showMessageDialog(null, "You can not connect these two dots!");
                    }
                }

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 7; j++) {
                    if (h[i][j] == true) {
                        g.drawLine(buttonArray[i][j].getX() + 55, buttonArray[i][j].getY() + 38, buttonArray[i][j].getX() + 168, buttonArray[i][j].getY() + 38);
                    }
                }
            }

            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 8; j++) {
                    if (v[i][j] == true) {
                        g.drawLine(buttonArray[i][j].getX() + 55, buttonArray[i][j].getY() + 38, buttonArray[i][j].getX() + 55, buttonArray[i][j].getY() + 113);
                    }
                }
            }

            // Dibuja un rectangulo
            int dotSize = 30; // Ajusta el valor que necesita para los puntos
            int x = buttonArray[cursorX][cursorY].getX() + (buttonArray[cursorX][cursorY].getWidth() - dotSize) / 2;
            int y = buttonArray[cursorX][cursorY].getY() + (buttonArray[cursorX][cursorY].getHeight() - dotSize) / 2;
            g.setColor(Color.RED); // Color del rectangulo
            g.drawRect(x, y, dotSize, dotSize);

        }

        public void deselect() {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    buttonArray[i][j].setSelected(false);
                }
            }
        }

        public void run() {
            while (true) {
                if (count % 2 == 0) {
                    player2.setForeground(Color.BLACK);
                    player1.setForeground(Color.RED);
                    count2 = 1;
                } else if (count % 2 == 1) {
                    player1.setForeground(Color.BLACK);
                    player2.setForeground(Color.RED);
                    count2 = 2;
                }

                repaint();

                for (int i = 0; i < 7; i++) {
                    for (int j = 0; j < 7; j++) {
                        if (h[i][j] == true && h[i + 1][j] == true && v[i][j] == true && v[i][j + 1] == true && count2 == 1 && win[i][j] == 0) {
                            playerscore1++;
                            player1.setText(playername1 + ": " + playerscore1 + "                              ");
                            win[i][j] = 1;
                            count--;
                        } else if (h[i][j] == true && h[i + 1][j] == true && v[i][j] == true && v[i][j + 1] == true && count2 == 2 && win[i][j] == 0) {
                            playerscore2++;
                            player2.setText(playername2 + ": " + playerscore2);
                            win[i][j] = 1;
                            count--;
                        }
                    }
                }

                if (count3 == 112) {
                    getWinner();
                }
            }
        }
    }

    public static void main(String[] args) {
        new Juego();
    }
}

