import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class agPrisionero {
	// =======================================Variables=================================================

	// La poblacion inicial
	private ArrayList<ArrayList<Integer>> poblacionInicial;
	private ArrayList<ArrayList<Integer>> jugadasPrevias;
	private int nJugadores = 0;
	private int nJugadasPosibles/*Revisar*/ = 0;
	private ArrayList<Double> aptitud;							// [aptitud,valor,peso]
	private ArrayList<Integer> ruleta;
	private ArrayList<ArrayList<Integer>> poblacionPostRuleta;
	private ArrayList<ArrayList<Integer>> poblacionPostCrossOver;
	private int nJugadas = 0;
	private int nMaximoIteraciones = 0;
	private int iteracionActual = 0;
	private int max = 0;
	private int min = 0;

	// La mochila
	ArrayList<ArrayList<Double>> mochila = new ArrayList<>();

	// ====================================GettersSetters===============================================
	public void setDatos() {

		this.nJugadasPosibles/*Revisar*/ = this.poblacionInicial.get(0).size();
		this.nJugadores = this.poblacionInicial.size();
	}

	public ArrayList<Double> getaptitud() {
		return aptitud;
	}

	public void setaptitud(ArrayList<Double> aptitud) {
		this.aptitud = aptitud;
	}

	public ArrayList<ArrayList<Integer>> getPoblacionInicial() {
		return poblacionInicial;
	}

	public void setPoblacionInicial(ArrayList<ArrayList<Integer>> poblacionInicial) {
		this.poblacionInicial = poblacionInicial;
	}

	public ArrayList<Integer> getRuleta() {
		return ruleta;
	}

	public void setRuleta(ArrayList<Integer> ruleta) {
		this.ruleta = ruleta;
	}

	public ArrayList<ArrayList<Integer>> getPoblacionPostRuleta() {
		return poblacionPostRuleta;
	}

	public void setPoblacionPostRuleta(ArrayList<ArrayList<Integer>> poblacionPostRuleta) {
		this.poblacionPostRuleta = poblacionPostRuleta;
	}

	public ArrayList<ArrayList<Integer>> getPoblacionPostCrossOver() {
		return poblacionPostCrossOver;
	}

	public void setPoblacionPostCrossOver(ArrayList<ArrayList<Integer>> poblacionPostCrossOver) {
		this.poblacionPostCrossOver = poblacionPostCrossOver;
	}



	// ========================================Metodos==================================================
	/**
	 * Crea una generación de nJugadores con nJugadasPosibles (la de siempre abandona siempre estará presente?) 
	 * @param min
	 * @param max
	 * @param nJugadasPosibles
	 */
	public void crearPoblacionInicial(int nJugadores, int nJugadas, int nJugadasPosibles/*Revisar*/, 
			int max, int min) {;
		this.poblacionInicial = new ArrayList<>(nJugadores);
		ArrayList<Integer> aux = new ArrayList<>(nJugadas);
		this.nJugadasPosibles/*Revisar*/ = nJugadasPosibles/*Revisar*/;
		this.nJugadas = nJugadas;
		this.nJugadores = nJugadores;
		this.max = max;
		this.min = min;
		for (int i = 0; i < nJugadores; i++) {
			aux = new ArrayList<>(nJugadasPosibles/*Revisar*/);
			for (int j = 0; j < nJugadasPosibles/*Revisar*/; j++) {
				aux.add(j, (Integer) ThreadLocalRandom.current().nextInt(min, max + 1));
			}
			poblacionInicial.add(i, aux);
		}
		
		// También creamos las jugadas previas, siempre serán 3 y entre 2 jugadores
		// El número de jugadas previas es n(n-1)/2?
		int nJugadasPrevias = (this.nJugadores*(nJugadores-1))/2;
		this.jugadasPrevias = new ArrayList<>();
		for ( int i = 0; i < nJugadasPrevias ; i++){
			aux = new ArrayList<>();
			for ( int j = 0; j < 6 ; j++ ){
				aux.add(j, (Integer) ThreadLocalRandom.current().nextInt(min, max + 1));
			}
			jugadasPrevias.add(i,aux);
		}
	}


	/**
	 * Evaluamos la aptitud de cada individuo: en el caso del dilema del prisionero para calcularlo haremos
	 * lo siguiente:
	 * 	1º	Analizamos el historial actual de la iteración
	 * 	2º	Comprobamos las jugadas realizadas y buscamos en sus jugadas ( generación ) la correspondiente
	 * 		usando para ello un índice que sacamos de pasar a binario el historial actual 
	 * 	3º 	Calculamos su aptitud en función de los puntos recibidos
	 */
	public void evaluar() {
		iteracionActual++;
		// Recalculamos las aptitudes desde 0
		this.aptitud = new ArrayList<>(this.nJugadores);
		// Calculamos la aptitud para cada jugador
		for (int i = 0; i < this.nJugadores; i++) {
			// Para cada jugador lo enfrentamos a todos los demas y calculamos su aptitud
			match(i);
		}
	}

	/**
	 * Para un jugador dado i lo enfrentamos a todos los demás jugadores
	 * @param i
	 * @return
	 */
	private int match(int jugadorActual) {
		ArrayList<Integer> aux;
		int valorDecimal = 0;
		for ( int i = jugadorActual ; i < nJugadores; i++){
			valorDecimal = 0;
			if ( i!= jugadorActual){									// No se va a enfrentar contra si mismo
				aux = new ArrayList<>();
				// Calculamos el indice para acceder a sus jugadas posibles
				int indice = 0;
				// Accedemos a las jugadas previas del jugadorActual con el i
				aux = jugadasPrevias.get(i);
				System.out.println(aux);
				// Pasamos a binario y calculamos su valor decimal total
				for ( int j = aux.size()-1 ; j >= 0 ; j--){
					valorDecimal = (int) (valorDecimal + (aux.get(j))*(Math.pow(2, j)));
				}
				System.out.println(valorDecimal);
			}
		}
		return 0;
	}

	/**
	 * Metodo privado para calcular el peso segun el tipo de problema
	 * 
	 * @param individuo
	 * @return
	 */
	private Double[] calcularPeso(ArrayList<Integer> individuo) {
		Double[] parDatos = { 0.0, 0.0 , 0.0};
		for (int i = 0; i < individuo.size(); i++) {
			// Calculamos el valor de la mochila
			parDatos[1] = parDatos[0] + mochila.get(i).get(0);
			parDatos[2] = parDatos[1] + mochila.get(i).get(1);
			// Estamos en el caso de maximizar el valor y minimizar el peso, calculamos su aptitud
			// a = alpha * V + beta * ( M - P ) 
			//parDatos[0] = 
		}
		return parDatos;
	}

	/**
	 * Algoritmo tipo ruleta para seleccionar los padres
	 */
	public void seleccionarParaReproduccion() {
		// TODO Auto-generated method stub
		this.ruleta = new ArrayList<>();
		this.poblacionPostRuleta = new ArrayList<>();
		double aptitudTotal = 0;
		for (int i = 0; i < aptitud.size(); i++) {
			aptitudTotal = aptitudTotal + aptitud.get(i)[0];
		}

		Double probabilidadAux = 0.0;
		// Realizamos la ruleta
		for (int i = 0; i < aptitud.size(); i++) {
			probabilidadAux = aptitud.get(i)[0];
			probabilidadAux = probabilidadAux / aptitudTotal;
			probabilidadAux = (probabilidadAux * 100);
			// probabilidadAux--;
			probabilidadAux++;
			// Ahora tenemos el número de casillas para esa cadena en la ruleta
			if (probabilidadAux == -1.0) {
				probabilidadAux = 1.0;
			}
			if (i == 0) {
				for (int j = ruleta.size() - 1; j < probabilidadAux; j++) {
					ruleta.add(i);
				}
			} else {
				double tamannoAux = ruleta.size() + probabilidadAux;
				for (int j = ruleta.size() - 1; j < tamannoAux; j++) {
					ruleta.add(i);
				}
			}
		}

		// Lanzamos la ruleta para cada individuo de la población
		for (int i = 0; i < this.nJugadores; i++) {
			ruleta.add(i, ThreadLocalRandom.current().nextInt(0, this.nJugadores));
		}

		for (int i = 0; i < this.nJugadores; i++) {
			poblacionPostRuleta.add(i, poblacionInicial.get(ruleta.get(i)));
		}
	}

	public void crossover(Double prob) {
		// Seleccionamos los individuos para el crossver
		this.poblacionPostCrossOver = new ArrayList<>();
		ArrayList<ArrayList<Integer>> poblacionSolteros = new ArrayList<>(poblacionPostCrossOver);
		ArrayList<ArrayList<Integer>> poblacionParejas = new ArrayList<>(poblacionPostCrossOver);
		ArrayList<ArrayList<Integer>> poblacionAux = new ArrayList<>(poblacionPostCrossOver);
		Double aux = 0.0;
		for (int i = 0; i < this.nJugadores; i++) {
			aux = ThreadLocalRandom.current().nextDouble(0, 1 + 1);
			if (aux <= prob) { // Vi cruza
				poblacionParejas.add(this.poblacionPostRuleta.get(i));
				// System.out.println("El individuo " + i + " cruza " );
			} else if (aux > prob) { // Vi no cruza
				poblacionSolteros.add(this.poblacionPostRuleta.get(i));
			}
		}

		// Nos preparamos para hacer el crossover con una pareja
		int nInvididuosCrossover = poblacionParejas.size();
		if (nInvididuosCrossover == 1 || nInvididuosCrossover == 0) { // 1
																		// individuo
																		// o
																		// menos
			// System.out.println("Crossover de un único individuo no se
			// realiza");
			this.poblacionPostCrossOver = new ArrayList<>(this.poblacionInicial);
			return;
		} else { // de par en par
			// Calculamos y realizamos el corte
			int corte = ThreadLocalRandom.current().nextInt(0, 1 + this.nJugadasPosibles/*Revisar*/);
			ArrayList<Integer> individuoAux = new ArrayList<>(this.nJugadasPosibles/*Revisar*/);

			// vamos pasando de pareja en pareja
			for (int j = 0; j < nInvididuosCrossover; j+=2) {
				for (int i = 0; i < corte; i++) {
					individuoAux.add(poblacionParejas.get(j).get(i));
				}
				for (int i = corte; i < this.nJugadasPosibles/*Revisar*/; i++) {
					individuoAux.add(poblacionParejas.get(j).get(i));
				}

				ArrayList<Integer> individuoAux2 = new ArrayList<>(individuoAux);
				poblacionAux.add(individuoAux2);

				individuoAux.clear();
				individuoAux = new ArrayList<>(this.nJugadasPosibles/*Revisar*/);
			}
			poblacionPostCrossOver.addAll(poblacionSolteros);
			poblacionPostCrossOver.addAll(poblacionAux);

		}
	}

	public boolean criterioParada() {
		if (iteracionActual == nMaximoIteraciones) {
			return true;
		} else {
			Double contador = 0.0;
			if (this.getaptitud() == null) {
				return false;
			}
			for (int i = 0; i < this.getaptitud().size(); i++) {
				contador = contador + (this.getaptitud().get(i)[0] / (double) this.nJugadasPosibles/*Revisar*/);
			}
			Double media = contador / (double) this.aptitud.size();
			if (media >= 14.1) {
				//System.out.println("La media vale:" + media);
				//return true;
			}
			// System.out.println(media);
			return false;
		}
	}

	public void mutar(Double prob) {
		for (int i = 0; i < this.nJugadores; i++) {
			for (int j = 0; j < this.nJugadasPosibles/*Revisar*/; j++) {
				Double probGen = ThreadLocalRandom.current().nextDouble(0, 1 + 1);
				if (probGen >= prob) {
					// entonces mutamos el gen
					Integer nuevoGen = (Integer) ThreadLocalRandom.current().nextInt(this.min, this.max + 1);
					ArrayList<Integer> nuevoIndividuo = new ArrayList<>();
					nuevoIndividuo = this.poblacionInicial.get(i);
					nuevoIndividuo.set(j, nuevoGen);
				}
			}
		}
	}

}
