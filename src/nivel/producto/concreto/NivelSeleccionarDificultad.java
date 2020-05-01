package nivel.producto.concreto;

import nivel.producto.abstracto.Nivel;
import org.newdawn.spaceinvaders.*;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

public class NivelSeleccionarDificultad implements Nivel {

    private Game game;

    public NivelSeleccionarDificultad(Game game) {
        this.game = game;
    }

    @Override
    public int numeroDeNivel() {
        return 0;
    }

    @Override
    public ArrayList<Entity> initEntities() {
        ArrayList<Entity> entities = new ArrayList<>();
        int alienCount = 0;
        for (int row=0;row<1;row++) {
            for (int x=0;x<3;x++) {
                Entity alien;
                switch (x){
                    case 1:
                        alien = new AlienT2Entity(100, 0, game, 200+(x*150),(50)+row*30);
                        break;
                    case 2:
                        alien = new AlienT3Entity(100, 0, game,200+(x*150),(50)+row*30);
                        break;
                    case 0:
                    default:
                        alien = new AlienT1Entity(100, 0, game,200+(x*150),(50)+row*30);
                        break;
                }
                entities.add(alien);
                alienCount++;
            }
        }
        return entities;
    }

    @Override
    public String obtenerInformacion() {
        return "Seleccionar Dificultad";
    }

    @Override
    public URL musica() {
        URL url = this.getClass().getClassLoader().getResource("sprites/mainmenu.wav");
        return url;
    }
}
