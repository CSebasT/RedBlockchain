package logicaDistribuida.connection;

import java.io.*;
import java.net.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import logicaDistribuida.messageTypes.Message;

public class Entrada extends Thread {
    private ServerSocket serverSocket;
    private final Lock lock = new ReentrantLock();

    public Entrada(int puerto) throws IOException {
        serverSocket = new ServerSocket(puerto);
    }

    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Conexión aceptada");
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Message message = (Message)in.readObject();
                in.close();
                // billetera.wallet1 += cantidadRecibida;
                // System.out.println("Recibido" + billetera.wallet1);
                // BufferedWriter archivo = new BufferedWriter(new FileWriter("mensajes.txt",
                // true));
                // archivo.write(objeto.toString());
                // archivo.newLine();
                // archivo.close();
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