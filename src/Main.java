import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		AlgoritmoGegentico algoritmoUno = new AlgoritmoGegentico();
		algoritmoUno.crearPoblacionInicial(0, 2, 17, 50);
		/*ArrayList<ArrayList<Double>> mochila = new ArrayList<>();
		
		mochila.add(new ArrayList<>(Arrays.asList(10.0, 0.0)));
		mochila.add(new ArrayList<>(Arrays.asList(20.0, 0.5)));
		mochila.add(new ArrayList<>(Arrays.asList(20.0, 1.0)));
		mochila.add(new ArrayList<>(Arrays.asList(20.0, 1.5)));
		mochila.add(new ArrayList<>(Arrays.asList(20.0, 2.0)));
		mochila.add(new ArrayList<>(Arrays.asList(10.0, 2.5)));
		mochila.add(new ArrayList<>(Arrays.asList(10.0, 3.0)));
		mochila.add(new ArrayList<>(Arrays.asList(10.0, 3.5)));
		mochila.add(new ArrayList<>(Arrays.asList(10.0, 3.0)));
		mochila.add(new ArrayList<>(Arrays.asList(10.0, 4.5)));
		
		algoritmoUno.crearPoblacionInicial(0, 9, 9, 4);
		algoritmoUno.setDatos();*/
		
		
		System.out.println(algoritmoUno.getPoblacionInicial());
		
		
		while ( !algoritmoUno.criterioParada()){
			System.out.println(algoritmoUno.getPoblacionInicial());
			//System.out.println("\nLa población inicial es:\n" + algoritmoUno.getPoblacionInicial());
			
			algoritmoUno.evaluar();
			//System.out.println("\nLa aptitud de cada individuo es:\n" + algoritmoUno.getBuenaForma());

			algoritmoUno.seleccionarParaReproduccion();
			//System.out.println("\nLa población resultado de la ruleta es:\n" + algoritmoUno.getRuleta() + "\n" + algoritmoUno.getPoblacionPostRuleta());
		
			algoritmoUno.crossover(0.9);
			//System.out.println("\nLa población tras el crossover es:\n" + algoritmoUno.getPoblacionPostCrossOver());
			
			algoritmoUno.mutar(0.9);
		}
		
		
	}

}
