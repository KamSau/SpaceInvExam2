package nivel.gestor;

import nivel.fabrica.abstracto.INivel;
import nivel.producto.abstracto.Nivel;
import org.newdawn.spaceinvaders.Game;

import java.util.ArrayList;


public class FabricaDeNiveles {
	private static ArrayList <Nivel> niveles = new ArrayList<Nivel>();
	
	public static String CrearFabricaDeNiveles(Nivel genNivel) {
		agregarNivel(genNivel);
		return genNivel.obtenerInformacion();
	}

	private static void agregarNivel(Nivel obj) {
		niveles.add(obj);
	}
	
	public static String obtenerInfoNiveles() {
		String msDatos="";
		for (int i=0 ; i < niveles.size() ; i++)
			msDatos = msDatos + i + ") " + niveles.get(i).obtenerInformacion() + "\n";
		return msDatos;
	}
}
