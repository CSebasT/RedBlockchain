package logicaDistribuida2.connection;

import java.io.*;
import java.net.*;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import logica.network.ValidatorNode;
import logicaDistribuida2.nodo.InfoRed;
import logicaDistribuida2.nodo.Nodo;
import logicaDistribuida2.messageTypes.Transaction;
import logicaDistribuida2.messageTypes.InfoNodo;
import logicaDistribuida2.messageTypes.Message;
import logicaDistribuida2.blockchain.Block;

public class Salida {
    private Nodo miNodo;
    private String host;
    private int puertoEnvio;
    private HashMap<String, Integer> direcciones = new HashMap<>();
    public String mode = "POS";

    public Salida(Nodo miNodo) {
        this.miNodo = miNodo;
        /* Nodo 1 */
        direcciones.put("26.20.111.124", 12341);
        /* Nodo 2 */
        // direcciones.put("26.92.40.65", 12342);
        /* Nodo 3 */
        direcciones.put("26.37.38.157", 12343);
        /* Nodo 4 */
        direcciones.put("26.143.218.218", 12344);
    }

    public void broadcastMessage(Message m) {
        System.out.println("Broadcasting Message");
        direcciones.forEach((d, p) -> enviarMensaje(d, p, m));
        if (m.getType() == 1) {
            Block block;
            try {
                block = (Block) m.getMessageContent().get(0);
                if (!block.getNodeAddress().equals("Master"))
                    updateAllWallet(block);
            } catch (NullPointerException ignored) {
            }
        }
    }

    private void enviarMensaje(String d, Integer p, Message m) {
        this.host = d;
        this.puertoEnvio = p;
        Socket socket;
        System.out.println("Envio de Mensaje a " + host);
        if (!miNodo.getNodeAddress().equals(host)) {
            try {
                socket = new Socket("localhost", puertoEnvio);
                System.out.println("Conexion iniciada");
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(m);
            } catch (IOException e) {
                System.out.println("-------------------");
                System.out.println("No se pudo establecer conexión con " + host);
                System.out.println("-------------------");
                // e.printStackTrace();
            }
        } else {
            System.out.println("Nodo local");
            miNodo.receiptMessage(m);
        }
    }

    /**
     * Método que actualiza las billeteras de todos los nodos que participarón.
     *
     * @param b Bloque.
     */
    private void updateAllWallet(Block b) {
        double totalFee = 0;
        List<Transaction> t = b.getTransaction();
        Nodo vn = null;
        if (mode.equals("POS")) {
            String vnDireccion = b.getNodeAddress();
        }
        double amount = 0;
        for (Transaction transaction : t) {
            transaction.confirmed();
            double takenFromTrans = (transaction.getTransactionFee()) * transaction.getAmount();
            totalFee += takenFromTrans; // Cálculo de la tarifa, va aumentando por cada iteración.
            amount += transaction.getAmount(); // Cálculo del monto, va aumentando por cada iteración.
            String toAddress = transaction.getToAddress();
            /*
             * Actualización de la billetera del destinatario de la transacción.
             */
            updateWalletWithAddress(amount, toAddress, transaction.getTransactionID());
            /*
             * Actualización de la billetera del emisor de la transacción.
             */
            updateWalletWithAddress(-(amount + takenFromTrans), transaction.getFromAddress(), transaction.getTransactionID());

            Set<String> investorList;

            // TODO: Recuperar la lista de inversores (dada la logica es la misma direccion del nodo vnDireccion)
            if (vn != null) {
                if (b.getBlockID().equals("Type1")) { // Se obtiene la lista de inversores del blockchain lógico.
                    investorList = vn.getInvestorList1();
                } else {
                    investorList = vn.getInvestorList2();
                }

                double otherNodeReward = takenFromTrans * ValidatorNode.INVEST_RATE;
                double thisNodeReward = takenFromTrans - otherNodeReward;
                vn.receiptCoin(thisNodeReward, transaction.getTransactionID()); //updateWalletWithAddress?
                for (String s : investorList) {
                    updateWalletWithAddress(otherNodeReward, s, transaction.getTransactionID()); //Actualización de la billetera de los inversores.
                }
            }

        }

        if (b.getBlockID().equals("Type1")) { //Los intercambios de dinero se agregan a la lista de la red.
            // TODO: Actualizar InfoRed Brodcast
            //this.EXCHANGE_MONEY1.add(EXCHANGE_MONEY1.get(EXCHANGE_MONEY1.size() - 1) + amount);
            //this.EXCHANGE_MONEY2.add(EXCHANGE_MONEY2.get(EXCHANGE_MONEY2.size()-1));
        }
        if (b.getBlockID().equals("Type1")){
            // TODO: Actualizar InfoRed Brodcast
            //this.EXCHANGE_MONEY2.add(EXCHANGE_MONEY2.get(EXCHANGE_MONEY2.size() - 1) + amount);
            //this.EXCHANGE_MONEY1.add(EXCHANGE_MONEY1.get(EXCHANGE_MONEY1.size()-1));
        }

        // NOTA: Actualización de la billetera del minero
        // El minero es el mismo vn
        updateWalletWithAddress(totalFee, b.getNodeAddress(), b.getBlockID());
    }

    /**
     * Método para actualizar la billetera del cliente con su dirección.
     *
     * @param amount        Monto de la transacción.
     * @param clientAddress Dirección del beneficiario.
     * @param type          Identificador de la transacción.
     */
    public void updateWalletWithAddress(double amount, String clientAddress, String type) {
        this.host = clientAddress;
        this.puertoEnvio = direcciones.get(clientAddress);
        Socket socket;
        System.out.println("Envio de peticion (actualización de billetera) a " + host);
        if (!miNodo.getNodeAddress().equals(host)) {
            try {
                socket = new Socket("localhost", puertoEnvio);
                System.out.println("Conexion iniciada");
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                if (type.equals("Type1")) {
                    out.writeObject("ActBilleteraType1" + amount);
                } else {
                    out.writeObject("ActBilleteraType2" + amount);
                }
            } catch (IOException e) {
                System.out.println("-------------------");
                System.out.println("No se pudo establecer conexión con " + host);
                System.out.println("-------------------");
            }
        } else {
            System.out.println("Nodo local");
            miNodo.receiptCoin(amount, type);
        }
    }

    public void enviarAForjar(String direccionValidador1, String type) {
        this.host = direccionValidador1;
        this.puertoEnvio = direcciones.get(direccionValidador1);
        String peticion = "Forja" + type;
        Socket socket;
        System.out.println("Envio de petición (Forja " + type + ") a " + host);
        if (!miNodo.getNodeAddress().equals(host)) {
            try {
                socket = new Socket("localhost", puertoEnvio);
                System.out.println("Conexion iniciada");
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(peticion);
            } catch (IOException e) {
                System.out.println("-------------------");
                System.out.println("No se pudo establecer conexión con " + host);
                System.out.println("-------------------");
            }
        } else {
            System.out.println("Nodo local");
            miNodo.forgeBlock(type);
        }

    }

    public void actualizarStakeAmount(String direccion, String type) {
        /* Mandar a consultar */
        /* En Entrada hacer que se actualice el mapStakeAmount1 del nodo */

    }

    public void actualizarStakeTime(String direccion) {
        /* Mandar a consultar */
        /* En Entrada hacer que se actualice el mapStakeTime del nodo */

    }

    public void buscarInfoRed() {
        pedirInfoRed();
        if (miNodo.getInfoRed() == null) {
            System.out.println("No se pudo copiar un InfoRed");
            miNodo.setInfoRed(new InfoRed());
        } else {
            System.out.println("Copia de InfoRed creada");
        }
        broadcastInfoNodo(miNodo.getNodeAddress(), miNodo.getPublicKey(), miNodo.getStakeAmount1(),
                miNodo.getStakeAmount2(), miNodo.getStakeTime());
    }

    private void broadcastInfoNodo(String nodeAddress, PublicKey publicKey, double stakeAmount1, double stakeAmount2,
            long stakeTime) {
        direcciones
                .forEach((d, p) -> enviarInfoNodo(d, p, nodeAddress, publicKey, stakeAmount1, stakeAmount2, stakeTime));
    }

    private void enviarInfoNodo(String d, Integer p, String nodeAddress, PublicKey publicKey, double stakeAmount1,
            double stakeAmount2, long stakeTime) {
        this.host = d;
        this.puertoEnvio = p;
        Socket socket;
        InfoNodo clavePublica = new InfoNodo(nodeAddress, publicKey, stakeAmount1, stakeAmount2, stakeTime);
        System.out.println("Envio de información del nodo a " + host);
        if (!miNodo.getNodeAddress().equals(host)) {
            try {
                socket = new Socket("localhost", puertoEnvio);
                System.out.println("Conexion iniciada");
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(clavePublica);
            } catch (IOException e) {
                System.out.println("-------------------");
                System.out.println("No se pudo establecer conexión con " + host);
                System.out.println("-------------------");
            }
        } else {
            System.out.println("Nodo local");
            InfoRed infoRed = miNodo.getInfoRed();
            infoRed.addNode(nodeAddress, publicKey, stakeAmount1, stakeAmount2, stakeTime);
        }
    }

    private void pedirInfoRed() {
        Iterator iterator = direcciones.entrySet().iterator();
        while (miNodo.getInfoRed() == null && iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            this.host = (String) entry.getKey();
            this.puertoEnvio = (int) entry.getValue();
            Socket socket;
            System.out.println("Envío de peticion (Pedir copia de InfoRed) a " + host);
            if (!miNodo.getNodeAddress().equals(host)) {
                try {
                    socket = new Socket("localhost", puertoEnvio);
                    System.out.println("Conexion iniciada");
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    out.writeObject("InfoRed" + miNodo.getNodeAddress());
                } catch (IOException e) {
                    System.out.println("-------------------");
                    System.out.println("No se pudo establecer conexión con " + host);
                    System.out.println("-------------------");
                }
            } else {
                System.out.println("Nodo local");
                continue;
            }
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void enviarInfoRed(InfoRed infoRed, String direccion) {
        this.host = direccion;
        this.puertoEnvio = direcciones.get(direccion);
        Socket socket;
        System.out.println("Envio de copia InfoRed a " + host);
        if (!miNodo.getNodeAddress().equals(host)) {
            try {
                socket = new Socket("localhost", puertoEnvio);
                System.out.println("Conexion iniciada");
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(infoRed);
            } catch (IOException e) {
                System.out.println("-------------------");
                System.out.println("No se pudo establecer conexión con " + host);
                System.out.println("-------------------");
            }
        } else {
            System.out.println("Nodo local");
        }
    }

    public void broadcastNbTransParType(String transactionType, int cantidad) {
        System.out.println("Broadcast actualizacion NbTransParType");
        direcciones.forEach((d, p) -> enviarActualizacionNbTransParType(d, p, transactionType, cantidad));
    }

    private void enviarActualizacionNbTransParType(String d, Integer p, String transactionType, int cantidad) {
        this.host = d;
        this.puertoEnvio = p;
        Socket socket;
        System.out.println("Envío de petición (Actualización NbTransParType) a " + host);
        if (!miNodo.getNodeAddress().equals(host)) {
            try {
                socket = new Socket("localhost", puertoEnvio);
                System.out.println("Conexion iniciada");
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                if (transactionType.equals("Type1")) {
                    if (cantidad == 1) {
                        out.writeObject("NbTransParType1+");
                    } else {
                        out.writeObject("NbTransParType1-");
                    }
                } else {
                    if (cantidad == -1) {
                        out.writeObject("NbTransParType2+");
                    } else {
                        out.writeObject("NbTransParType2-");
                    }
                }
            } catch (IOException e) {
                System.out.println("-------------------");
                System.out.println("No se pudo establecer conexión con " + host);
                System.out.println("-------------------");
            }
        } else {
            System.out.println("Nodo local");
            miNodo.actualizarNbTransParType(transactionType, cantidad);
        }
    }

}
