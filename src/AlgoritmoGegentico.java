import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class AlgoritmoGegentico {
	//=======================================Variables=================================================
	
	// La poblacion inicial 
	private ArrayList<ArrayList<Integer>> poblacionInicial;
	private int tamanoPoblacion = 0;
	private int tamanoIndividuo = 0;
	private ArrayList<Integer> buenaForma;
	
	//====================================GettersSetters===============================================
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

	//========================================Metodos==================================================
	/**
	 * Crear una poblacion con un tamaño num, formado por vectores len con valores entre el min y el max
	 * @param min
	 * @param max
	 * @param len
	 */
	public void crearPoblacionInicial(int min,int max, int len, int num){
		this.poblacionInicial = new ArrayList<>(num);
		ArrayList<Integer> aux = new ArrayList<>(len) ;
		this.tamanoIndividuo = len;
		this.tamanoPoblacion = num;
		for ( int i = 0; i < num; i++ ){
			aux = new ArrayList<>(len) ;
			for ( int j = 0; j < len; j++){
				aux.add(j, ThreadLocalRandom.current().nextInt(min,max + 1));
			}
			poblacionInicial.add(i, aux);
		}
	}

	/**
	 * Evaluamos la buena forma de cada individuo ( numero de 1's )
	 */
	public void evaluar() {
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
	private int contarUnos(ArrayList<Integer> individuo){
		int contador= 0;
		for ( int i = 0; i < individuo.size(); i++){
			if ( individuo.get(i) == 1 ){
				contador++;
			}
		}
		return contador;
	}

	public void seleccionarParaReproduccion() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
}
