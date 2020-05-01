package nivel.fabrica.concreto;

import nivel.fabrica.abstracto.INivel;
import nivel.producto.abstracto.Nivel;
import nivel.producto.concreto.NivelTres;
import org.newdawn.spaceinvaders.Game;

public class FabricaNivelTres implements INivel {
    @Override
    public Nivel generarNivel(Game game) {
        NivelTres nTres = new NivelTres(game);
        return nTres;
    }
}
