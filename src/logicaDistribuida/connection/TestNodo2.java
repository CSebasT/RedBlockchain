package logicaDistribuida.connection;

import java.io.IOException;
import logicaDistribuida.nodo.Nodo;

public class TestNodo2 {
    
    public static void main(String[] args) throws IOException {
        //A donde se va a enviar
        int puertoRecepcion = 12344;

        // Mi nodo 
        Nodo nodo = new Nodo(4, "26.143.218.218");

        int cantidadEnviada = 300;

        //Hilo para escuchar
        Entrada serverThread = new Entrada(nodo, puertoRecepcion);
        serverThread.start();
        
    }
}
