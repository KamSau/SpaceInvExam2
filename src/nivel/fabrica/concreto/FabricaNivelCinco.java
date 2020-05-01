package nivel.fabrica.concreto;

import nivel.fabrica.abstracto.INivel;
import nivel.producto.abstracto.Nivel;
import nivel.producto.concreto.NivelCinco;
import org.newdawn.spaceinvaders.Game;

public class FabricaNivelCinco implements INivel {
    @Override
    public Nivel generarNivel(Game game) {
        NivelCinco nCinco = new NivelCinco(game);
        return nCinco;
    }
}
