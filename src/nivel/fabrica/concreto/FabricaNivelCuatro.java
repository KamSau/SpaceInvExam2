package nivel.fabrica.concreto;

import nivel.fabrica.abstracto.INivel;
import nivel.producto.abstracto.Nivel;
import nivel.producto.concreto.NivelCuatro;
import org.newdawn.spaceinvaders.Game;

public class FabricaNivelCuatro implements INivel {
    @Override
    public Nivel generarNivel(Game game) {
        NivelCuatro nCuatro = new NivelCuatro(game);
        return nCuatro;
    }
}
