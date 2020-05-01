package nivel.producto.abstracto;

import org.newdawn.spaceinvaders.Entity;

import java.net.URL;
import java.util.ArrayList;

public interface Nivel {
    public int numeroDeNivel();
    public ArrayList<Entity> initEntities();
    public String obtenerInformacion();
    public URL musica();
}
