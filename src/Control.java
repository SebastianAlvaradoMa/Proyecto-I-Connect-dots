import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;

public class Control {

    private static SerialPort serialPort;
    private static InputStream inputStream;

    public static void main(String[] args) {
        String puerto = "COM3"; 
        int velocidad = 9600;  

        try {
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(puerto);
            if (portIdentifier.isCurrentlyOwned()) {
                System.err.println("El puerto está en uso.");
            } else {
                CommPort commPort = portIdentifier.open(Control.class.getName(), 2000);

                if (commPort instanceof SerialPort) {
                    serialPort = (SerialPort) commPort;
                    serialPort.setSerialPortParams(velocidad, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

                    inputStream = serialPort.getInputStream();

                    // Inicia un hilo para escuchar comandos desde Arduino
                    Thread thread = new Thread(() -> {
                        try {
                            while (true) {
                                if (inputStream.available() > 0) {
                                    char receivedChar = (char) inputStream.read();
                                    // Procesa el comando recibido
                                    processCommand(receivedChar);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    thread.start();
                } else {
                    System.err.println("El puerto seleccionado no es un puerto serie.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processCommand(char command) {
        // Realiza acciones en Java según el comando recibido desde Arduino
        switch (command) {
            case 'U':
                // Acción correspondiente a la flecha hacia arriba
                System.out.println("Flecha hacia arriba recibida en Java");
                break;
            case 'D':
                // Acción correspondiente a la flecha hacia abajo
                System.out.println("Flecha hacia abajo recibida en Java");
                break;
            case 'L':
                // Acción correspondiente a la flecha hacia la izquierda
                System.out.println("Flecha hacia la izquierda recibida en Java");
                break;
            case 'R':
                // Acción correspondiente a la flecha hacia la derecha
                System.out.println("Flecha hacia la derecha recibida en Java");
                break;
            case 'E':
                // Acción correspondiente a la tecla Enter
                System.out.println("Tecla Enter recibida en Java");
                break;
            default:
                // Comando no reconocido
                break;
        }
    }
}

