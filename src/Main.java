import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Problema de la mochila
		AlgoritmoGenetico algoritmoUno = new AlgoritmoGenetico();
		algoritmoUno.crearPoblacionInicial(4,10,1,0);		
		System.out.println(algoritmoUno.getPoblacionInicial());
		
		ArrayList<Integer> aux = new ArrayList<>(10);
		for ( int i = 0; i < 7 ; i ++){
			aux.add(1);
		}
		aux.add(0);
		aux.add(0);
		aux.add(0);
		
		while ( !algoritmoUno.criterioParada()){
			System.out.println(algoritmoUno.getPoblacionInicial());
			for ( int i = 0; i < algoritmoUno.getPoblacionInicial().size();i++){
				if ( algoritmoUno.getPoblacionInicial().get(i).equals(aux)) {
					System.out.println(algoritmoUno.getPoblacionInicial().get(i));
					System.out.println("Eureka");
					System.exit(0);
				}
			}
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
