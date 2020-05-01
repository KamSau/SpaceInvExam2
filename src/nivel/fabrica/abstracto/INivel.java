package nivel.fabrica.abstracto;

import nivel.producto.abstracto.Nivel;
import org.newdawn.spaceinvaders.Game;

public interface INivel {
    public Nivel generarNivel(Game game);
}
