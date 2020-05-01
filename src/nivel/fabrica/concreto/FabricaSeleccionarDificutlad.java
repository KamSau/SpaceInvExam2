package nivel.fabrica.concreto;

import nivel.fabrica.abstracto.INivel;
import nivel.producto.abstracto.Nivel;
import nivel.producto.concreto.NivelSeleccionarDificultad;
import org.newdawn.spaceinvaders.Game;

public class FabricaSeleccionarDificutlad implements INivel {
    @Override
    public Nivel generarNivel(Game game) {
        NivelSeleccionarDificultad nSeleccionarDificultad = new NivelSeleccionarDificultad(game);
        return nSeleccionarDificultad;
    }
}
