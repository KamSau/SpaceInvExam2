package poder.decorador_concreto;

import poder.TipoPoder;
import poder.componente.Poder;
import poder.decorador.ObjetoDecorado;

public class MasDanno extends ObjetoDecorado {

    public MasDanno(Poder poder) {
        this.cPoder = poder;
    }
    @Override
    public String getNombre() {
        return "Más Daño";
    }

    @Override
    public TipoPoder getTipo() {
        return TipoPoder.MASDANNO;
    }

    @Override
    public int getVencimiento() {
        return 10;
    }
}
