package poder.decorador_concreto;

import poder.TipoPoder;
import poder.componente.Poder;
import poder.decorador.ObjetoDecorado;

public class DisparoRapido extends ObjetoDecorado {

    private long firingInterval = 150;

    public DisparoRapido(Poder poder) {
        this.cPoder = poder;
    }
    @Override
    public String getNombre() {
        return "Disparo Rápido";
    }

    @Override
    public TipoPoder getTipo() {
        return TipoPoder.DISPARORAPIDO;
    }

    @Override
    public int getVencimiento() {
        return 10;
    }
}
