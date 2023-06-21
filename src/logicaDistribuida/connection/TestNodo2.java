package logicaDistribuida.connection;

import java.io.IOException;
import logicaDistribuida.nodo.Nodo;

public class TestNodo2 {
    
    public static void main(String[] args) throws IOException {
        //A donde se va a enviar
        int puertoRecepcion = 12342;

        // Mi nodo 
        Nodo nodo = new Nodo(2, "26.92.40.65");

        int cantidadEnviada = 300;

        //Hilo para escuchar
        Entrada serverThread = new Entrada(nodo, puertoRecepcion);
        serverThread.start();
        
    }
}
