package saving;

import nivel.fabrica.concreto.*;
import nivel.producto.abstracto.Nivel;
import org.newdawn.spaceinvaders.*;
import poder.TipoPoder;
import poder.componente.Poder;
import poder.componente_Concreto.PoderConcreto;
import poder.decorador_concreto.Ninguno;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class Estado {

    private Game game;
    private Nivel nivelActual;
    private int nivel = 0;
    private Dificultad dificultad;
    private int vidas = 0;
    private float score = 0;
    private boolean perdio = false;
    private int contPoder = 0;
    private Poder poderActual = new Ninguno(new PoderConcreto("Ninguno"));
    private long firingInterval = 250;

    private Clip clip;

    {
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public Estado(Game game, Nivel nivelActual, int nivel, Dificultad dificultad, int vidas, float score, boolean perdio, int contPoder, Poder poderActual, long fireInterval) {
        this.game = game;
        this.nivelActual = nivelActual;
        this.nivel = nivel;
        this.dificultad = dificultad;
        this.vidas = vidas;
        this.score = score;
        this.perdio = perdio;
        this.contPoder = contPoder;
        this.poderActual = poderActual;
        this.firingInterval = firingInterval;
    }

    public Estado(Game game) {
        this.game = game;
        setNivelActual(new FabricaSeleccionarDificutlad().generarNivel(this.game));
        musica();
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

    public boolean isPerdio() {
        return perdio;
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


    public void getSiguienteNivel() {
        switch (getNivel()) {
            case 1:
                setNivelActual(new FabricaNivelUno().generarNivel(game));
                break;
            case 2:
                setNivelActual(new FabricaNivelDos().generarNivel(game));
                break;
            case 3:
                setNivelActual(new FabricaNivelTres().generarNivel(game));
                break;
            case 4:
                setNivelActual(new FabricaNivelCuatro().generarNivel(game));
                break;
            case 5:
                setNivelActual(new FabricaNivelCinco().generarNivel(game));
                break;
            default:
                setNivelActual(new FabricaSeleccionarDificutlad().generarNivel(game));
                break;
        }
        musica();
    }

    public void musica(){
        if(getNivelActual() == null) return;
        try {
            clip.close();
            if(poderActual.getTipo() == TipoPoder.DISPARORAPIDO){
                //Easter egg con un poco de humor en estos tiempos. Deseo lo mejor para todos :)!
                clip.open(AudioSystem.getAudioInputStream(Paths.get(this.getClass().getClassLoader().getResource("sprites/corona.wav").toURI()).toFile()));
            }else{
                clip.open(AudioSystem.getAudioInputStream(Paths.get(getNivelActual().musica().toURI()).toFile()));
            }
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void dificultadSeleccionada(Entity entidad) {
        if (entidad instanceof AlienT1Entity) {
            setDificultad(Dificultad.FACIL);
            setVidas(7);
        } else if (entidad instanceof AlienT2Entity) {
            setDificultad(Dificultad.INTERMEDIO);
            setVidas(5);
        } else if (entidad instanceof AlienT3Entity) {
            setDificultad(Dificultad.MASTER);
            setVidas(3);
        }
    }

    public void setMemento(Memento m) {
        this.game = m.getGame();
        this.nivelActual = m.getNivelActual();
        this.nivel = m.getNivel();
        this.dificultad = m.getDificultad();
        this.vidas = m.getVidas();
        this.score = m.getScore();
        this.perdio = m.getPerdio();
        this.contPoder = m.getContPoder();
        this.poderActual = m.getPoderActual();
        this.firingInterval = m.getFiringInterval();
    }

    public Memento createMemento() {
        return new Memento(game, nivelActual, nivel, dificultad, vidas, score, perdio, contPoder, poderActual, firingInterval);
    }
}
