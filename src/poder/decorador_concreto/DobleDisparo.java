package poder.decorador_concreto;

import poder.TipoPoder;
import poder.componente.Poder;
import poder.decorador.ObjetoDecorado;

public class DobleDisparo extends ObjetoDecorado {
    public DobleDisparo(Poder poder) {
        this.cPoder = poder;
    }

    @Override
    public String getNombre() {
        return "Doble Disparo";
    }

    @Override
    public TipoPoder getTipo() {
        return TipoPoder.DOBLEDISPARO;
    }

    @Override
    public int getVencimiento() {
        return 10;
    }
}
