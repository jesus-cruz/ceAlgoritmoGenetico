import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class AlgoritmoGegentico {
	//=======================================Variables=================================================
	
	// La poblacion inicial 
	private ArrayList<ArrayList<Integer>> poblacionInicial;
	private int tamanoPoblacion = 0;
	private int tamanoIndividuo = 0;
	private ArrayList<Integer> buenaForma;
	private ArrayList<Integer> buenaFormaActualizada;
	private ArrayList<Integer> ruleta;
	private ArrayList<ArrayList<Integer>> poblacionPostRuleta;
	private ArrayList<ArrayList<Integer>> poblacionPostCrossOver;
	private int nMaximoIteraciones = 1000;
	private int iteracionActual = 0;
	private int max = 0;
	private int min = 0;

	//====================================GettersSetters===============================================
	public void setDatos(){

		this.tamanoIndividuo = this.poblacionInicial.get(0).size();
		this.tamanoPoblacion = this.poblacionInicial.size();
	}
	public ArrayList<Integer> getBuenaForma() {
		return buenaForma;
	}
	public void setBuenaForma(ArrayList<Integer> buenaForma) {
		this.buenaForma = buenaForma;
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
	
	//========================================Metodos==================================================
	/**
	 * Crear una poblacion de num individuos de longitud len, formado por vectores len con valores 
	 * entre el min y el max
	 * @param min
	 * @param max
	 * @param len
	 */
	public void crearPoblacionInicial(int min,int max, int len, int num){
		this.poblacionInicial = new ArrayList<>(num);
		ArrayList<Integer> aux = new ArrayList<>(len) ;
		this.tamanoIndividuo = len;
		this.tamanoPoblacion = num;
		this.max = max;
		this.min = min;
		for ( int i = 0; i < num; i++ ){
			aux = new ArrayList<>(len) ;
			for ( int j = 0; j < len; j++){
				aux.add(j, (Integer) ThreadLocalRandom.current().nextInt(min,max + 1));
			}
			poblacionInicial.add(i, aux);
		}
	}

	/**
	 * Evaluamos la buena forma de cada individuo ( numero de 1's )
	 */
	public void evaluar() {
		iteracionActual++;
		this.buenaForma = new ArrayList<>(this.tamanoPoblacion);
		for ( int i = 0; i < this.tamanoPoblacion ; i++){
			buenaForma.add(i, contarUnos(this.poblacionInicial.get(i)));
		}
	}
	
	/**
	 * Metodo privado para contar los bits con valor 1 de cada individuo
	 * @param individuo
	 * @return
	 */
	private Integer contarUnos(ArrayList<Integer> individuo){
		Integer contador= 0;
		for ( int i = 0; i < individuo.size(); i++){
			if ( individuo.get(i) == 2.0 ){		// contamos doses
				contador++;
			}
		}
		//contador = individuo.get(0);
		return contador;
	}

	/**
	 * Algoritmo tipo ruleta para seleccionar los padres
	 */
	public void seleccionarParaReproduccion() {
		// TODO Auto-generated method stub
		this.ruleta = new ArrayList<>();
		this.poblacionPostRuleta = new ArrayList<>();
		Integer buenaFormaTotal = 0;
		for ( int i = 0; i < buenaForma.size(); i++){
			buenaFormaTotal = buenaFormaTotal + buenaForma.get(i);
		}
		
		Integer probabilidadAux = 0;
		// Realizamos la ruleta
		for ( int i = 0; i < buenaForma.size() ; i++){
			probabilidadAux = buenaForma.get(i);
			probabilidadAux = probabilidadAux / buenaFormaTotal;
			probabilidadAux = ( probabilidadAux * 100 ) ;
			//probabilidadAux--;
			probabilidadAux++;
			// Ahora tenemos el número de casillas para esa cadena en la ruleta
			if ( probabilidadAux == -1.0){
				probabilidadAux = 1;
			}
			if ( i == 0 ){
				for ( int j = ruleta.size()-1; j < probabilidadAux; j++){
					ruleta.add(i);
				}
			} else {
				Integer tamannoAux = ruleta.size() +probabilidadAux;
				for ( int j = ruleta.size()-1; j < tamannoAux; j++){
					ruleta.add(i);
				}
			}
		}
		
		
		// Lanzamos la ruleta para cada individuo de la población
		for ( int i = 0; i < this.tamanoPoblacion; i++){
			ruleta.add(i,ThreadLocalRandom.current().nextInt(0,this.tamanoPoblacion));
		}
		
		for ( int i = 0; i < this.tamanoPoblacion; i++){
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
		for ( int i = 0; i < this.tamanoPoblacion; i++){
			aux = ThreadLocalRandom.current().nextDouble(0,1 + 1);
			if ( aux <= prob) {												// Vi cruza
				poblacionParejas.add(this.poblacionPostRuleta.get(i));
				//System.out.println("El individuo " + i + " cruza " );
			} else if ( aux > prob) {										// Vi no cruza
				poblacionSolteros.add(this.poblacionPostRuleta.get(i));
			}
		}
		
		// Nos preparamos para hacer el crossover con una pareja
		int nInvididuosCrossover = poblacionParejas.size();
		if ( nInvididuosCrossover%2 != 0) {												// 3 individuos o más
			//System.out.println("Crossover número impar de individuos no implementado");
			this.poblacionPostCrossOver = new ArrayList<>(this.poblacionInicial);
			return;
		} else if ( nInvididuosCrossover == 1 || nInvididuosCrossover == 0 ){			// 1 individuo
			//System.out.println("Crossover de un único individuo no se realiza");
			this.poblacionPostCrossOver = new ArrayList<>(this.poblacionInicial);
			return;
		} else {																		// par individuos
			// Calculamos y realizamos el corte 
			//System.out.println("Crossover de número par de elementos");
			int corte = ThreadLocalRandom.current().nextInt(0,1 + this.tamanoIndividuo);
			//System.out.println("El corte se realiza en " + corte);
			
			ArrayList<Integer> individuoAux = new ArrayList<>(this.tamanoIndividuo);
			
			for ( int j = 0; j < nInvididuosCrossover ; j++){
				for ( int i = 0; i < corte ; i++){
					individuoAux.add(poblacionParejas.get(j).get(i));
				}
				for ( int i = corte ; i < this.tamanoIndividuo; i++){
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
	
	public boolean criterioParada(){
		if ( iteracionActual == nMaximoIteraciones){
			return true;
		} else {
			Double contador = 0.0;
			if ( this.getBuenaForma()==null){
				return false;
			}
			for ( int i = 0; i < this.getBuenaForma().size(); i++){
				contador = contador +  (this.getBuenaForma().get(i)/ (double) this.tamanoIndividuo);
			}
			Double media = contador / (double) this.buenaForma.size();
			if ( media >= 0.75){
				System.out.println("La media vale:" + media);
				return true;
			}
			System.out.println(media);
			return false;
		}
	}
	
	
	public void mutar(Double prob){
		for ( int i = 0; i < this.tamanoPoblacion; i++){
			for ( int j = 0; j < this.tamanoIndividuo ; j++){
				Double probGen = ThreadLocalRandom.current().nextDouble(0,1 + 1);
				if ( probGen >= prob){
					// entonces mutamos el gen
					Integer nuevoGen = (Integer) ThreadLocalRandom.current().nextInt(this.min,this.max + 1);
					ArrayList<Integer> nuevoIndividuo = new ArrayList<>();
					nuevoIndividuo = this.poblacionInicial.get(i);
					nuevoIndividuo.set(j, nuevoGen);
				}
			}
		}
	}
	
	
	
	
	
}
