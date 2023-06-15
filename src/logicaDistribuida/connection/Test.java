package logicaDistribuida.connection;

import java.io.IOException;
import logicaDistribuida.nodo.Nodo;

public class Test {
    
    public static void main(String[] args) throws IOException {
        //A donde se va a enviar
        String host = "26.92.40.65";
        int puertoEnvio = 12346;
        int puertoRecepcion = 12345;
        /*
         * Enviar a Jorge
         * String host = "26.92.40.65";
         * int puertoEnvio = 12346;
         * int puertoRecepcion = 12345;
         */

        // Mi nodo 
        Nodo nodo = new Nodo(1, "26.20.111.124");

        int cantidadEnviada = 300;

        //Hilo para escuchar
        Entrada serverThread = new Entrada(puertoRecepcion);
        serverThread.start();
        
        //Genera transaccion
        nodo.sendMoneyTo(cantidadEnviada, host, "Bit1");

        //Run de validador ParaL
        nodo.validate();
    }
}
