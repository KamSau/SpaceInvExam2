package nivel.fabrica.concreto;

import nivel.fabrica.abstracto.INivel;
import nivel.producto.abstracto.Nivel;
import nivel.producto.concreto.NivelUno;
import org.newdawn.spaceinvaders.Game;

public class FabricaNivelUno implements INivel {
    @Override
    public Nivel generarNivel(Game game) {
        NivelUno nUno = new NivelUno(game);
        return nUno;
    }
}
