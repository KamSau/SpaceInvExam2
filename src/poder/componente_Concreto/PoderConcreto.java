package poder.componente_Concreto;

import poder.TipoPoder;
import poder.componente.Poder;

public class PoderConcreto extends Poder {

    String nombre;

    public PoderConcreto(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String getNombre() {
        return this.nombre;
    }

    @Override
    public TipoPoder getTipo() {
        return TipoPoder.NINGUNO;
    }

    @Override
    public int getVencimiento() {
        return 0;
    }
}
