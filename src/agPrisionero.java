import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class agPrisionero {
	// =======================================Variables=================================================

	// La poblacion inicial
	private ArrayList<ArrayList<Integer>> poblacionInicial;
	private ArrayList<Integer> jugadasPrevias;
	private int nJugadores = 0;
	private int nJugadasPosibles/* Revisar */ = 0;
	private ArrayList<Double> aptitud; // [aptitud,valor,peso]
	private ArrayList<Integer> ruleta;
	private ArrayList<ArrayList<Integer>> poblacionPostRuleta;
	private ArrayList<ArrayList<Integer>> poblacionPostCrossOver;
	private int nJugadas = 0;
	//private int nMaximoIteraciones = 0;
	private int iteracionActual = 0;
	private int max = 0;
	private int min = 0;

	// La mochila
	ArrayList<ArrayList<Double>> mochila = new ArrayList<>();

	// ====================================GettersSetters===============================================
	public void setDatos() {

		this.nJugadasPosibles/* Revisar */ = this.poblacionInicial.get(0).size();
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
	 * Crea una generación de nJugadores con nJugadasPosibles (la de siempre
	 * abandona siempre estará presente?)
	 * 
	 * @param min
	 * @param max
	 * @param nJugadasPosibles
	 */
	public void crearPoblacionInicial(int nJugadores, int nJugadas, int nJugadasPosibles/* Revisar */, int max,
			int min) {
		;
		this.poblacionInicial = new ArrayList<>(nJugadores);
		ArrayList<Integer> aux = new ArrayList<>(nJugadas);
		this.nJugadasPosibles/* Revisar */ = nJugadasPosibles/* Revisar */;
		this.nJugadas = nJugadas;
		this.nJugadores = nJugadores;
		this.max = max;
		this.min = min;
		for (int i = 0; i < nJugadores; i++) {
			aux = new ArrayList<>(nJugadasPosibles/* Revisar */);
			for (int j = 0; j < nJugadasPosibles/* Revisar */; j++) {
				aux.add(j, (Integer) ThreadLocalRandom.current().nextInt(min, max + 1));
			}
			poblacionInicial.add(i, aux);
		}

		// También creamos las jugadas previas, es el mismo para todos los
		// posibles enfrentamientos en una generación
		this.jugadasPrevias = new ArrayList<>(3);
		for (int i = 0; i < 3 * 2; i++) {
			jugadasPrevias.add(i, (Integer) ThreadLocalRandom.current().nextInt(min, max + 1));
		}
	}

	/**
	 * Evaluamos la aptitud de cada individuo: en el caso del dilema del
	 * prisionero para calcularlo haremos lo siguiente: 1º Analizamos el
	 * historial de la generación 2º Comprobamos las jugadas realizadas y
	 * buscamos en sus jugadas ( generación ) la correspondiente usando para
	 * ello un índice que sacamos de pasar a binario el historial actual 3º
	 * Calculamos su aptitud en función de los puntos recibidos
	 */
	public void evaluar() {
		iteracionActual++;
		// Recalculamos las aptitudes desde 0
		this.aptitud = new ArrayList<>(this.nJugadores);
		for (int i = 0; i < this.nJugadores; i++) {
			this.aptitud.add(i, 0.0);
		}
		// Calculamos la aptitud para cada jugador
		for (int i = 0; i < this.nJugadores; i++) {
			// Para cada jugador lo enfrentamos a todos los demas y calculamos
			// su aptitud
			match(i);
		}
	}

	/**
	 * Para un jugador dado i lo enfrentamos 5 a todos los demás jugadores
	 * 
	 * @param i
	 * @return
	 */
	private void match(int jugadorActual) {
		ArrayList<Integer> jug1 = new ArrayList<>();
		ArrayList<Integer> jug2 = new ArrayList<>();
		Integer respuesta1, respuesta2;
		Double resultado;
		for (int i = jugadorActual; i < nJugadores; i++) {
			for (int k = 0; k < 5; k++) { // Cada partida consta de 5 jugadas
				respuesta1 = 0;
				respuesta2 = 0;
				resultado = 0.0;
				// No se va a enfrentar contra si mismo
				if (i != jugadorActual) {

					// Pasamos a binario y calculamos su valor decimal total
					// para el jugador 1
					for (int j = 0; j < jugadasPrevias.size(); j++) {
						respuesta1 = (int) (respuesta1
								+ (jugadasPrevias.get(jugadasPrevias.size() - j - 1)) * (Math.pow(2, j)));
					}
					// Buscamos en las jugadas de jugador1 la jugada dada por el
					// índice obtenido del binario
					jug1 = poblacionInicial.get(jugadorActual);

					for (int j = 0; j < jugadasPrevias.size(); j++) {

						if (j % 2 == 0) {
							respuesta2 = (int) (respuesta2
									+ (jugadasPrevias.get(jugadasPrevias.size() - j - 2)) * (Math.pow(2, j)));
						}
						if (j % 2 == 1) {
							respuesta2 = (int) (respuesta2
									+ (jugadasPrevias.get(jugadasPrevias.size() - j)) * (Math.pow(2, j)));
						}
					}
					// Buscamos en las jugadas de jugador2 la jugada dada por el
					// índice obtenido del binario
					jug2 = poblacionInicial.get(i);

					respuesta1 = jug1.get(respuesta1);
					respuesta2 = jug2.get(respuesta2);

					// Ahora calculamos la aptitud del jugador actual vs el
					// adversario 'i'
					resultado = calcularAptitud(respuesta1, respuesta2);

					// Actualizamos la aptitud del jugador actual
					this.aptitud.set(jugadorActual, this.aptitud.get(jugadorActual) + resultado);
				}
			}

		}
	}

	private Double calcularAptitud(Integer respuesta1, Integer respuesta2) {
		Double puntuacion = 0.0;
		if (respuesta1 == 0) {
			if (respuesta2 == 0) {
				puntuacion = 1.0;
			} else if (respuesta2 == 1) {
				puntuacion = 6.0;
			}
		} else if (respuesta1 == 1) {
			if (respuesta2 == 0) {
				puntuacion = 0.0;
			} else if (respuesta2 == 1) {
				puntuacion = 4.0;
			}
		}
		return puntuacion;
	}

	/**
	 * Metodo privado para calcular el peso segun el tipo de problema
	 * 
	 * @param individuo
	 * @return
	 */
	private Double[] calcularPeso(ArrayList<Integer> individuo) {
		Double[] parDatos = { 0.0, 0.0, 0.0 };
		for (int i = 0; i < individuo.size(); i++) {
			// Calculamos el valor de la mochila
			parDatos[1] = parDatos[0] + mochila.get(i).get(0);
			parDatos[2] = parDatos[1] + mochila.get(i).get(1);
			// Estamos en el caso de maximizar el valor y minimizar el peso,
			// calculamos su aptitud
			// a = alpha * V + beta * ( M - P )
		}
		return parDatos;
	}

	/**
	 * Algoritmo tipo ruleta para seleccionar los padres
	 */
	public void seleccionarParaReproduccion() {
		this.ruleta = new ArrayList<>();
		this.poblacionPostRuleta = new ArrayList<>();
		double aptitudTotal = 0;
		for (int i = 0; i < aptitud.size(); i++) {
			aptitudTotal = aptitudTotal + aptitud.get(i);
		}

		Double probabilidadAux = 0.0;
		// Realizamos la ruleta
		for (int i = 0; i < aptitud.size(); i++) {
			probabilidadAux = aptitud.get(i);
			probabilidadAux = probabilidadAux / aptitudTotal;
			probabilidadAux = (probabilidadAux * 100);
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

			this.poblacionPostCrossOver = new ArrayList<>(this.poblacionInicial);
			return;
		} else { // de par en par
			// Calculamos y realizamos el corte
			int corte = ThreadLocalRandom.current().nextInt(0, 1 + this.nJugadasPosibles);
			ArrayList<Integer> individuoAux = new ArrayList<>(this.nJugadasPosibles);

			// vamos pasando de pareja en pareja
			for (int j = 0; j < nInvididuosCrossover; j += 2) {
				for (int i = 0; i < corte; i++) {
					individuoAux.add(poblacionParejas.get(j).get(i));
				}
				for (int i = corte; i < this.nJugadasPosibles; i++) {
					individuoAux.add(poblacionParejas.get(j).get(i));
				}

				ArrayList<Integer> individuoAux2 = new ArrayList<>(individuoAux);
				poblacionAux.add(individuoAux2);

				individuoAux.clear();
				individuoAux = new ArrayList<>(this.nJugadasPosibles);
			}
			poblacionPostCrossOver.addAll(poblacionSolteros);
			poblacionPostCrossOver.addAll(poblacionAux);

		}
	}

	public boolean criterioParada(double mediaMax, int nMaximoIteraciones) {
		if (iteracionActual == nMaximoIteraciones) {
			System.out.println("max numero de it");
			return true;
		} else {
			Double contador = 0.0;
			if (this.getaptitud() == null) {
				return false;
			}
			for (int i = 0; i < this.getaptitud().size(); i++) {
				contador = contador + (this.getaptitud().get(i) / (double) this.nJugadasPosibles);
			}
			Double media = contador / (double) this.aptitud.size();
			if (media >= mediaMax) {
				System.out.println("La media vale:" + media);
				return true;
			}
			System.out.println(media);
			return false;
		}
	}

	public void mutar(Double prob) {
		for (int i = 0; i < this.nJugadores; i++) {
			for (int j = 0; j < this.nJugadasPosibles/* Revisar */; j++) {
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
