package logicaDistribuida2.connection;

import java.io.IOException;
import java.security.KeyPair;

import logicaDistribuida2.nodo.Nodo;
import logicaDistribuida2.utils.RsaUtil;

public class TestNodo1 {
    
    public static void main(String[] args) throws IOException {
        int puertoRecepcion = 12341;
        
        KeyPair keys = null;
        try {
            keys = RsaUtil.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Mi nodo 
        // La dirección en "logica" se obtiene de un hash a la clave publica
        Nodo nodo = new Nodo(1, "26.20.111.124", keys);
        // Poner el stake
        nodo.stake(20, "Type1");
        nodo.stake(10, "Type2");
        nodo.addInvestorType(nodo.getNodeAddress(), nodo.getStakeAmount1(), "Type1"); //ln1.getNodeAddress(), ln1.getStakeAmount1(), TYPE1
        nodo.addInvestorType(nodo.getNodeAddress(), nodo.getStakeAmount1(), "Type2"); 
       
        int cantidadEnviada = 300;
        //Hilo para escuchar
        Entrada serverThread = new Entrada(nodo, puertoRecepcion);
        serverThread.start();

        nodo.buscarInfoRed();
        
        //Genera transaccion
        nodo.sendMoneyTo(cantidadEnviada, "26.37.38.157", "Type1");
        nodo.sendMoneyTo(cantidadEnviada, "26.143.218.218", "Type2");
        nodo.validate();//Run de validador ParaL
    }
}
