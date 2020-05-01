package poder.decorador_concreto;

import poder.TipoPoder;
import poder.componente.Poder;
import poder.decorador.ObjetoDecorado;

public class Ninguno extends ObjetoDecorado {

    public Ninguno(Poder poder) {
        this.cPoder = poder;
    }
    @Override
    public String getNombre() {
        return "Ninguno";
    }

    @Override
    public TipoPoder getTipo() {
        return TipoPoder.NINGUNO;
    }

    @Override
    public int getVencimiento() {
        return 10;
    }
}
