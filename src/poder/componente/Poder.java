package poder.componente;

import poder.TipoPoder;

public abstract class Poder {
	public abstract String getNombre() ;
	public abstract TipoPoder getTipo();
	public abstract int getVencimiento();
}
