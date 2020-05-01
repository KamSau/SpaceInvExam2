package nivel.producto.concreto;

import nivel.producto.abstracto.Nivel;
import org.newdawn.spaceinvaders.*;

import java.net.URL;
import java.util.ArrayList;

public class NivelCuatro implements Nivel {

    private Game game;

    public NivelCuatro(Game game) {
        this.game = game;
    }

    @Override
    public int numeroDeNivel() {
        return 4;
    }

    @Override
    public ArrayList<Entity> initEntities() {
        ArrayList<Entity> entities = new ArrayList<>();
        int alienCount = 0;

        for (int row=0;row<5;row++) {
            for (int x=0;x<12;x++) {
                Entity alien;
                switch (row){
                    case 1:
                    case 2:
                    case 3:
                        alien = new AlienT2Entity(game, 100+(x*50),(50)+row*30);
                        break;
                    default:
                        alien = new AlienT3Entity(game,100+(x*50),(50)+row*30);
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
        return "Nivel CUATRO";
    }


    @Override
    public URL musica() {
        URL url = this.getClass().getClassLoader().getResource("sprites/nivel4.wav");
        return url;
    }
}
