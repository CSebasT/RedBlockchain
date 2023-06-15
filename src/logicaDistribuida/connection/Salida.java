package logicaDistribuida.connection;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Set;

import logicaDistribuida.blockchain.Blockchain;
import logicaDistribuida.nodo.Nodo;
import logicaDistribuida.messageTypes.Transaction;
import logica.network.ValidatorNode;
import logicaDistribuida.messageTypes.Message;
import logicaDistribuida.nodo.Nodo;
import logicaDistribuida.blockchain.Block;

public class Salida {
    private String host = "26.92.40.65";
    private int puertoEnvio = 12346;
    /**
     * Algoritmo de consenso.
     */
    public String mode = "POS";

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcastMessage(Message m) {
        // Para toda la red
        Nodo nodo = new Nodo(2, "26.92.40.65");
        nodo.receiptMessage(m);
        if (m.getType() == 1) {
            // this.copyBlockchainFromFN().printBlk();
            Block block;
            try {
                block = copyBlockchainFromFN().getLatestBlock(); // TODO: Obtiene el bloque de un full nodo.
                if (!block.getNodeAddress().equals("Master"))
                    updateAllWallet(block);
            } catch (NullPointerException ignored) {
            }
        }
    }

    public Blockchain copyBlockchainFromFN() {
        // Consultar a la red a al mismo nodo por una copia del blockchain
        // NO se instancia, se pide al nodo del Test o a la red.
        Nodo nodo = new Nodo(2, "26.92.40.65");
        return nodo.getBlockchain();
        // throw new NullPointerException();
    }

    /**
     * Método que actualiza las billeteras de todos los nodos que participarón.
     *
     * @param b Bloque.
     */
    private void updateAllWallet(Block b) {
        double totalFee = 0;
        List<Transaction> t = b.getTransaction();
        Nodo vn = null; // Validator node, Hay que separar el nodo y el nodo validador
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
     * @param currency      Identificador de la transacción.
     */
    public void updateWalletWithAddress(double amount, String clientAddress, String currency) {
        //TODO: Buscar cliente con la dirección clientAddress 
        //associatedLightNode.receiptCoin(amount, currency);
    }

    /*
     * try
     * 
     * {
     * Socket socket = new Socket(host, puertoEnvio);
     * System.out.println("Coneccion iniciada");
     * BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
     * ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
     * // out.writeObject();
     * // Envio de 300 dolares
     * if (billetera.wallet1 - cantidadEnviada * (1 + billetera.TRANSACTION_FEE) <
     * 0) {
     * System.out.println(" Not enough currency of type " + 1 + " to send");
     * System.out.println("Rejected transaction");
     * return;
     * }
     * billetera.wallet1 -= cantidadEnviada * (1 + billetera.TRANSACTION_FEE);
     * 
     * }catch(
     * IOException e)
     * {
     * e.printStackTrace();
     * }
     */

}
