package logicaDistribuida2.connection;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.sound.midi.MidiDevice.Info;

import logicaDistribuida2.blockchain.Block;
import logicaDistribuida2.blockchain.Blockchain;
import logicaDistribuida2.messageTypes.ClavePublica;
import logicaDistribuida2.messageTypes.Message;
import logicaDistribuida2.messageTypes.Transaction;
import logicaDistribuida2.nodo.InfoRed;
import logicaDistribuida2.nodo.Nodo;

public class Entrada extends Thread {
    private ServerSocket serverSocket;
    private final Lock lock = new ReentrantLock();
    private Nodo nodo;

    public Entrada(Nodo nodo, int puerto) throws IOException {
        serverSocket = new ServerSocket(puerto);
        this.nodo = nodo;
    }

    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Conexion aceptada");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                // Detectar tipo de entrada
                Object obj = ois.readObject();
                System.out.println("Se recibio un objeto");
                // Mensajes
                if (obj instanceof Message) {
                    Message message = (Message) obj;
                    System.out.println("El objeto es un mensaje");
                    nodo.receiptMessage(message);
                }
                // Claves
                if (obj instanceof ClavePublica) {
                    ClavePublica clavePublica = (ClavePublica) obj;
                    System.out.println("El objeto es una ClavePublica");
                    InfoRed infoRed = nodo.getInfoRed();
                    infoRed.addNode(clavePublica.getDireccion(), clavePublica.getPublicKey());
                }
                // InfoRed
                if (obj instanceof InfoRed) {
                    InfoRed infoRed = (InfoRed) obj;
                    System.out.println("El objeto es un InfoRed");
                    nodo.setInfoRed(infoRed);
                }
                // Strings
                if (obj instanceof String) {
                    String peticion = (String) obj;
                    System.out.println("Se recibio un string");
                    System.out.println("La petición es " + peticion);
                    switch (peticion) {
                        case "ForjaType1":
                            nodo.forgeBlock("Type1");
                            break;
                        case "ForjaType2":
                            nodo.forgeBlock("Type2");
                            break;
                        case "NbTransParType1":
                            nodo.actualizarNbTransParType("Type1");
                            break;
                        case "NbTransParType2":
                            nodo.actualizarNbTransParType("Type2");
                            break;

                    }
                    double amount;
                    if (peticion.length() >= 7) {
                        switch (peticion.substring(0, 7)) {
                            case "InfoRed":
                                nodo.enviarInfoRed(peticion.substring(7));
                        }
                    }
                    if (peticion.length() >= 17) {
                        switch (peticion.substring(0, 17)) {
                            case "ActBilleteraType1":
                                amount = Double.parseDouble(peticion.substring(17));
                                nodo.receiptCoin(amount, "Type1");
                                break;
                            case "ActBilleteraType2":
                                amount = Double.parseDouble(peticion.substring(17));
                                nodo.receiptCoin(amount, "Type2");
                                break;
                        }

                    }

                }

                ois.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}

/*
 * 
 * boolean interrupt = false;
 * while (!interrupt) {
 * lock.lock();
 * try {
 * 
 * 
 * 
 * 
 * } catch (Exception e) {
 * e.printStackTrace();
 * interrupt = true;
 * } finally {
 * lock.unlock();
 * }
 * }
 */

// BufferedWriter archivo = new BufferedWriter(new FileWriter("mensajes.txt",
// true));
// archivo.write(objeto.toString());
// archivo.newLine();
// archivo.close();