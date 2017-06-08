
public class Main {
	
	public static void main(String[] args) {
		agPrisionero a = new agPrisionero();
		// El número de jugadas posibles es de 2⁶(64) para dos jugadores con un historial de 3
		// Las jugadas posibles son 2(por ser binario) ^ (los jugadores juegan de dos en dos * 3 jugadas)
		// crearPoblacionInicial(nJugadores, nJugadas, max ,min)
		int nPosiblesRespuestas = 2;									// Abandona o Coopera
		int nJugadoresMatch = 2;										// Los juegadores juegan de 2 en 2
		int nJugadasPrevias = 3;										// En nuestro caso es 3
		int nJugadores = 4;												// Numero de jugadores totales
		int max = 1;													// Hay dos jugadas luego 0,1
		int min = 0;
		int nJugadas = 5;												// Veces que se enfrentan los jugadores
		int nJugadasPosibles = (int) Math.pow(nPosiblesRespuestas, nJugadoresMatch*nJugadasPrevias);
		a.crearPoblacionInicial(nJugadores,  nJugadas, nJugadasPosibles, max, min);
		
		for ( int i = 0; i < 100 ;i++){
			System.out.println(a.getPoblacionInicial());
			
			a.evaluar();
			
			a.seleccionarParaReproduccion();
			
			a.crossover(0.9);
			
			a.mutar(0.9);
		}
		
	}

	

}
