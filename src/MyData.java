import java.io.Serializable;

public class MyData implements Serializable {
    private String nombre;
    private int puntos;
    private boolean register;
    private String nowPlaying;
    private String winner;
    private boolean full;
    private boolean jugando;

    public boolean isJugando() {
        return jugando;
    }

    public void setJugando(boolean jugando) {
        this.jugando = jugando;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    public String getNowPlaying() {
        return nowPlaying;
    }

    public void setNowPlaying(String nowPlaying) {
        this.nowPlaying = nowPlaying;
    }

    // Constructor sin argumentos (necesario para Jackson)
    public MyData() {
    }

    public MyData(String nombre, int puntos, boolean register, String nowPlaying) {
        this.register = register;
        this.nombre = nombre;
        this.puntos = puntos;
        this.nowPlaying = nowPlaying;
        this.winner = null;
        this.full = false;
        this.jugando = false;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public boolean getRegister() {
        return this.register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }
}
