package saving;

import nivel.producto.abstracto.Nivel;
import org.newdawn.spaceinvaders.Dificultad;
import org.newdawn.spaceinvaders.Game;
import poder.componente.Poder;

public class Memento {
    private Game game;
    private Nivel nivelActual;
    private int nivel = 0;
    private Dificultad dificultad;
    private int vidas = 0;
    private float score = 0;
    private boolean perdio = false;
    private int contPoder = 0;
    private Poder poderActual = null;
    private long firingInterval = 250;

    public Memento(Game game, Nivel nivelActual, int nivel, Dificultad dificultad, int vidas, float score, boolean perdio, int contPoder, Poder poderActual, Long firingInterval) {
        this.game = game;
        this.nivelActual = nivelActual;
        this.nivel = nivel;
        this.dificultad = dificultad;
        this.vidas = vidas;
        this.score = score;
        this.perdio = perdio;
        this.contPoder = contPoder;
        this.poderActual = poderActual;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Nivel getNivelActual() {
        return nivelActual;
    }

    public void setNivelActual(Nivel nivelActual) {
        this.nivelActual = nivelActual;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public Dificultad getDificultad() {
        return dificultad;
    }

    public void setDificultad(Dificultad dificultad) {
        this.dificultad = dificultad;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public boolean getPerdio() {
        return perdio;
    }

    public void setPerdio(boolean perdio) {
        this.perdio = perdio;
    }

    public int getContPoder() {
        return contPoder;
    }

    public void setContPoder(int contPoder) {
        this.contPoder = contPoder;
    }

    public Poder getPoderActual() {
        return poderActual;
    }

    public void setPoderActual(Poder poderActual) {
        this.poderActual = poderActual;
    }

    public long getFiringInterval() {
        return firingInterval;
    }

    public void setFiringInterval(long firingInterval) {
        this.firingInterval = firingInterval;
    }
}
