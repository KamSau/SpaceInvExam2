package nivel.fabrica.concreto;

import nivel.fabrica.abstracto.INivel;
import nivel.producto.abstracto.Nivel;
import nivel.producto.concreto.NivelDos;
import org.newdawn.spaceinvaders.Game;

public class FabricaNivelDos implements INivel {
    @Override
    public Nivel generarNivel(Game game) {
        NivelDos nDos = new NivelDos(game);
        return nDos;
    }
}
