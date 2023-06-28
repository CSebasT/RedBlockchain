package logicaDistribuida2.connection;

import java.io.IOException;
import logicaDistribuida.nodo.Nodo;

public class TestNodo3 {
    
    public static void main(String[] args) throws IOException {
        //A donde se va a enviar
        int puertoRecepcion = 12343;

        // Mi nodo 
        Nodo nodo = new Nodo(1, "");

        int cantidadEnviada = 300;

        //Hilo para escuchar
        Entrada serverThread = new Entrada(nodo,puertoRecepcion);
        serverThread.start();
    }
}
