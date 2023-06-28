package logicaDistribuida.connection;

import java.io.*;
import java.net.*;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import logicaDistribuida.blockchain.Blockchain;
import logicaDistribuida.nodo.Nodo;
import logicaDistribuida.messageTypes.Transaction;
import logica.network.ValidatorNode;
import logicaDistribuida.messageTypes.Message;
import logicaDistribuida.blockchain.Block;

public class Salida {
    private Nodo miNodo;
    private String host;
    private int puertoEnvio;
    private HashMap<String, Integer> direcciones = new HashMap<>();
    public String mode = "POS";

<<<<<<< HEAD
    public Salida(Nodo miNodo) {
        this.miNodo = miNodo;
        /* Nodo 1 */
        direcciones.put("26.20.111.124", 12341);
        /* Nodo 2 */
        direcciones.put("26.92.40.65", 12342);
        /* Nodo 3 */
        // direcciones.put("", 12343));
=======
    public Salida() {
    }

    public void sendMoneyTo(int cantidadEnviada, String host, int puertoEnvio) {
        this.host = host;
        this.puertoEnvio = puertoEnvio;
        Socket socket;
        try {
            socket = new Socket(host, puertoEnvio);
            System.out.println("Coneccion iniciada");
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeInt(cantidadEnviada);
            //El dinero se envia inmediatamente al nodo receptor
            out.flush();
            System.out.println("Cantidad enviada" + cantidadEnviada);
        } catch (IOException e) {
            e.printStackTrace();
        }
>>>>>>> 073aa7223530f5953fcd17ff01a7ac68ad683810
    }

    public void broadcastMessage(Message m) {
        System.out.println("Broadcasting");
        direcciones.forEach((d, p) -> enviarMensaje(d, p, m));
        if (m.getType() == 1) {
            // this.copyBlockchainFromFN().printBlk();
            Block block;
            try {
                block = (Block) m.getMessageContent().get(0); // copyBlockchainFromFN().getLatestBlock(); TODO: Obtiene
                                                              // el bloque de un full nodo.
                if (!block.getNodeAddress().equals("Master"))
                    updateAllWallet(block)
                    ;
            } catch (NullPointerException ignored) {
            }
        }
    }

    private void enviarMensaje(String d, Integer p, Message m) {
        this.host = d;
        this.puertoEnvio = p;
        Socket socket;
        if (!miNodo.getNodeAddress().equals(host)) {
            try {
                socket = new Socket("localhost", puertoEnvio);
                System.out.println("Conexion iniciada (mensaje)");
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(m);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            miNodo.receiptMessage(m);
        }

    }

    public void copyBlockchainFromFN() {

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
            // Busqueda del nodo que minó el bloque.
            // for (Nodo n : network) {
            // if (n.nodeAddress.equals(b.getNodeAddress())) {
            // vn = ((ValidatorNode) n);
            // }
            // }
        }
        double amount = 0;
        for (Transaction transaction : t) {
            transaction.confirmed();
            double takenFromTrans = (transaction.getTransactionFee()) * transaction.getAmount();
            totalFee += takenFromTrans; // Cálculo de la tarifa, va aumentando por cada iteración.
            amount += transaction.getAmount(); // Cálculo del monto, va aumentando por cada iteración.
            String toAddress = transaction.getToAddress();
            updateWalletWithAddress(amount, toAddress, transaction.getTransactionID()); // Actualización de la billetera
                                                                                        // del destinarario de la
                                                                                        // transacción.
            updateWalletWithAddress(-(amount + takenFromTrans), transaction.getFromAddress(),
                    transaction.getTransactionID()); // Actualización de la billetera del emisor de la transacción.

            Set<String> investorList;
            if (vn != null) {
                if (b.getBlockID().equals("Type1")) { // Se obtiene la lista de inversores del blockchain lógico.
                    investorList = vn.getInvestorList1();
                } else {
                    investorList = vn.getInvestorList2();
                }

                double otherNodeReward = takenFromTrans * ValidatorNode.INVEST_RATE;
                double thisNodeReward = takenFromTrans - otherNodeReward;
                vn.receiptCoin(thisNodeReward, transaction.getTransactionID()); // vn.fullNodeAccount.receiptCoin******
                for (String s : investorList) {
                    updateWalletWithAddress(otherNodeReward, s, transaction.getTransactionID()); // Actualización de la
                                                                                                 // billetera de los
                                                                                                 // inversores.
                }
            }

        }
        // Los intercambios de dinero se agregan a la lista de la red. Pero ya on hay
        // clase red*************
        updateWalletWithAddress(totalFee, b.getNodeAddress(), b.getBlockID()); // Actualización de la billetera del
                                                                               // minero
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
        if (!miNodo.getNodeAddress().equals(host)) {
            try {
                socket = new Socket("localhost", puertoEnvio);
                System.out.println("Conexion iniciada (mensaje)");
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                if (type.equals("Type1")) {
                    out.writeObject("ActBilleteraType1"+amount);
                } else {
                    out.writeObject("ActBilleteraType2"+amount);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(amount);
            miNodo.receiptCoin(amount, type);
        }

    }

    /* Este metodo tambien es un envio con retorno entre salida y entrada */
    public PublicKey getPkWithAddress(String fromAddress) {
        this.host = fromAddress;
        this.puertoEnvio = direcciones.get(fromAddress);
        Socket socket;
        try {
            socket = new Socket(host, puertoEnvio);
            System.out.println("Conexion-s iniciada");
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            String peticion = "DameTuClavePublica";
            out.writeObject(peticion);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // TODO

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void enviarAForjar(String direccionValidador1, String type) {
        this.host = direccionValidador1;
        this.puertoEnvio = direcciones.get(direccionValidador1);
        String peticion = "Forja" + type;
        Socket socket;
        if (!miNodo.getNodeAddress().equals(host)) {
            try {
                socket = new Socket("localhost", puertoEnvio);
                System.out.println("Conexion iniciada (forja)");
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(peticion);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
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
}
