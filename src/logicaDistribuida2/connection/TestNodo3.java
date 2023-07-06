package logicaDistribuida2.connection;

import java.io.IOException;
import java.security.KeyPair;

import logicaDistribuida2.nodo.Nodo;
import logicaDistribuida2.utils.RsaUtil;

public class TestNodo3 {
    
    public static void main(String[] args) throws IOException {
        //A donde se va a enviar
        int puertoRecepcion = 12343;

        KeyPair keys = null;
        try {
            keys = RsaUtil.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Mi nodo 
        Nodo nodo = new Nodo(3, "26.37.38.157", keys);
        // Poner el stake
        nodo.stake(5, "Type1");
        nodo.stake(10, "Type2");
        nodo.addInvestorType(nodo.getNodeAddress(), nodo.getStakeAmount1(), "Type1"); //ln1.getNodeAddress(), ln1.getStakeAmount1(), TYPE1
        nodo.addInvestorType(nodo.getNodeAddress(), nodo.getStakeAmount1(), "Type2"); 

        int cantidadEnviada = 300;

        //Hilo para escuchar
        Entrada serverThread = new Entrada(nodo,puertoRecepcion);
        serverThread.start();

        nodo.buscarInfoRed();
    }
}
