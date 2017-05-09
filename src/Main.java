
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AlgoritmoGegentico algoritmoUno = new AlgoritmoGegentico();
		
		algoritmoUno.crearPoblacionInicial(0, 1, 5, 2);
		System.out.println(algoritmoUno.getPoblacionInicial());
		
		algoritmoUno.evaluar();
		System.out.println(algoritmoUno.getBuenaForma());
		
		algoritmoUno.seleccionarParaReproduccion();
	}

}
