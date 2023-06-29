package logicaDistribuida.connection;

import java.io.IOException;
import logicaDistribuida.nodo.Nodo;

public class Test {
    
    public static void main(String[] args) throws IOException {
        //A donde se va a enviar
        String host = "26.37.38.157";
        int puertoRecepcion = 12341;
        /*
         * Enviar a Jorge
         * String host = "26.92.40.65";
         * int puertoEnvio = 12346;
         * int puertoRecepcion = 12345;
         */

        // Mi nodo 
        // La dirección en "logica" se obtiene de un hash a la clave publica
        Nodo nodo = new Nodo(1, "26.20.111.124");
        // Poner el stake
        nodo.stake(20, "Type1");
        nodo.stake(10, "Type2");
        nodo.addInvestorType(nodo.getNodeAddress(), nodo.getStakeAmount1(), "Type1"); //ln1.getNodeAddress(), ln1.getStakeAmount1(), TYPE1
        nodo.addInvestorType(nodo.getNodeAddress(), nodo.getStakeAmount1(), "Type2"); 
       
        int cantidadEnviada = 300;
        //Hilo para escuchar
        Entrada serverThread = new Entrada(nodo, puertoRecepcion);
        serverThread.start();
        
        //Genera transaccion
        nodo.sendMoneyTo(cantidadEnviada, host, "Type1");

        //Run de validador ParaL
        nodo.validate();
        nodo.sendMoneyTo(cantidadEnviada, "26.143.218.218", "Type1");
        nodo.validate();
    }
}
