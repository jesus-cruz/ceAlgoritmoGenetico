
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AlgoritmoGegentico algoritmoUno = new AlgoritmoGegentico();
		algoritmoUno.crearPoblacionInicial(0, 1, 5, 3);
		System.out.println("\nLa población inicial es:\n" + algoritmoUno.getPoblacionInicial());
		
		algoritmoUno.evaluar();
		System.out.println("\nLa aptitud de cada individuo es:\n" + algoritmoUno.getBuenaForma());
		
		algoritmoUno.seleccionarParaReproduccion();
		System.out.println("\nLa población resultado de la ruleta es:\n" + algoritmoUno.getRuleta() + "\n" + algoritmoUno.getPoblacionPostRuleta());
	
		algoritmoUno.crossover(0.6);
		System.out.println("\nLa población tras el crossover es:\n" + algoritmoUno.getPoblacionPostCrossOver());
	}

}
