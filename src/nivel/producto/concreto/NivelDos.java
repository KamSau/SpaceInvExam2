package nivel.producto.concreto;

import org.newdawn.spaceinvaders.*;
import nivel.producto.abstracto.Nivel;

import java.net.URL;
import java.util.ArrayList;

public class NivelDos implements Nivel {

    private Game game;

    public NivelDos(Game game) {
        this.game = game;
    }

    @Override
    public int numeroDeNivel() {
        return 2;
    }

    @Override
    public ArrayList<Entity> initEntities() {
        ArrayList<Entity> entities = new ArrayList<>();
        int alienCount = 0;

        for (int row=0;row<5;row++) {
            for (int x=0;x<12;x++) {
                Entity alien;
                switch (row){
                    case 0:
                    case 4:
                        alien = new AlienT2Entity(game,100+(x*50),(50)+row*30);
                        break;
                    default:
                        alien = new AlienT1Entity(game,100+(x*50),(50)+row*30);
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
        return "Nivel DOS";
    }


    @Override
    public URL musica() {
        URL url = this.getClass().getClassLoader().getResource("sprites/nivel2.wav");
        return url;
    }
}
