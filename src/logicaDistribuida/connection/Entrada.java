package logicaDistribuida.connection;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import logicaDistribuida.blockchain.Block;
import logicaDistribuida.blockchain.Blockchain;
import logicaDistribuida.messageTypes.Message;
import logicaDistribuida.messageTypes.Transaction;
import logicaDistribuida.nodo.Nodo;

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
<<<<<<< HEAD
                System.out.println("Conexion aceptada");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                // Detectar tipo de entrada
                Object obj = ois.readObject();
                System.out.println("Se recibio un objeto");
                // Mensajes
                if (obj instanceof Message) {
                    Message message = (Message) obj;
                    System.out.println("Se recibio un mensaje");
                    nodo.receiptMessage(message);
                }
                // Strings
                if (obj instanceof String) {
                    String peticion = (String) obj;
                    System.out.println("Se recibio un string");
                    switch (peticion) {
                        case "ForjaType1":
                            nodo.forgeBlock("Type1");
                            break;
                        case "ForjaType2":
                            nodo.forgeBlock("Type2");
                            break;
                    }
                    double amount;
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
=======
                System.out.println("Conexión aceptada");
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Message message = (Message)in.readObject();
                in.close();
>>>>>>> 073aa7223530f5953fcd17ff01a7ac68ad683810
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