import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class AlgoritmoGenetico {
	// =======================================Variables=================================================

	// La poblacion inicial
	private ArrayList<ArrayList<Integer>> poblacionInicial;
	private int tamanoPoblacion = 0;
	private int tamanoIndividuo = 0;
	private ArrayList<Double[]> aptitud;							// [aptitud,valor,peso]
	private ArrayList<Double[]> aptitudActualizada;
	private ArrayList<Integer> ruleta;
	private ArrayList<ArrayList<Integer>> poblacionPostRuleta;
	private ArrayList<ArrayList<Integer>> poblacionPostCrossOver;
	private int nMaximoIteraciones = 10000;
	private int iteracionActual = 0;
	private int max = 0;
	private int min = 0;
	private double coeficientesNormalizacion[] = {0.0 , 0.0};

	// La mochila
	ArrayList<ArrayList<Double>> mochila = new ArrayList<>();

	// ====================================GettersSetters===============================================
	public void setDatos() {

		this.tamanoIndividuo = this.poblacionInicial.get(0).size();
		this.tamanoPoblacion = this.poblacionInicial.size();
	}

	public ArrayList<Double[]> getaptitud() {
		return aptitud;
	}

	public void setaptitud(ArrayList<Double[]> aptitud) {
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

	private void setMochila() {
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
	}

	// ========================================Metodos==================================================
	/**
	 * Crea una población de un numero de invividuos con una longitud entre el
	 * min y el max
	 * @param min
	 * @param max
	 * @param len
	 */
	public void crearPoblacionInicial(int num, int len, int max, int min) {
		this.setMochila(); // datos especiales para calcular la aptitud en el problema de la mochila
		normalizarDatos();
		this.poblacionInicial = new ArrayList<>(num);
		ArrayList<Integer> aux = new ArrayList<>(len);
		this.tamanoIndividuo = len;
		this.tamanoPoblacion = num;
		this.max = max;
		this.min = min;
		for (int i = 0; i < num; i++) {
			aux = new ArrayList<>(len);
			for (int j = 0; j < len; j++) {
				aux.add(j, (Integer) ThreadLocalRandom.current().nextInt(min, max + 1));
			}
			poblacionInicial.add(i, aux);
		}
	}

	/**
	 * Dada una mochila calculamos los coeficientes alpha y beta para normalizar 
	 */
	private void normalizarDatos() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Evaluamos la aptitud de cada individuo
	 */
	public void evaluar() {
		iteracionActual++;
		this.aptitud = new ArrayList<>(this.tamanoPoblacion);
		for (int i = 0; i < this.tamanoPoblacion; i++) {
			aptitud.add(i, calcularPeso(this.poblacionInicial.get(i)));
			// Parte especifica de la mochila
			if (aptitud.get(i)[2] <= 105) {
				// Ha ido bien, es un valor válido
			}
			if (aptitud.get(i)[2] > 105) {
				Double[] zeros = { 0.0, 0.0 ,0.0};
				this.aptitud.set(i, zeros);
			}
		}
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
		for (int i = 0; i < this.tamanoPoblacion; i++) {
			ruleta.add(i, ThreadLocalRandom.current().nextInt(0, this.tamanoPoblacion));
		}

		for (int i = 0; i < this.tamanoPoblacion; i++) {
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
		for (int i = 0; i < this.tamanoPoblacion; i++) {
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
			int corte = ThreadLocalRandom.current().nextInt(0, 1 + this.tamanoIndividuo);
			ArrayList<Integer> individuoAux = new ArrayList<>(this.tamanoIndividuo);

			// vamos pasando de pareja en pareja
			for (int j = 0; j < nInvididuosCrossover; j+=2) {
				for (int i = 0; i < corte; i++) {
					individuoAux.add(poblacionParejas.get(j).get(i));
				}
				for (int i = corte; i < this.tamanoIndividuo; i++) {
					individuoAux.add(poblacionParejas.get(j).get(i));
				}

				ArrayList<Integer> individuoAux2 = new ArrayList<>(individuoAux);
				poblacionAux.add(individuoAux2);

				individuoAux.clear();
				individuoAux = new ArrayList<>(this.tamanoIndividuo);
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
				contador = contador + (this.getaptitud().get(i)[0] / (double) this.tamanoIndividuo);
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
		for (int i = 0; i < this.tamanoPoblacion; i++) {
			for (int j = 0; j < this.tamanoIndividuo; j++) {
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
	
	public static void pruebaAg(String[] args) {
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
