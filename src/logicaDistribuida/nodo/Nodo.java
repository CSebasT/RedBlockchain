package logicaDistribuida.nodo;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import logica.network.Network;
import logica.utils.HashUtil;
import logicaDistribuida.blockchain.Blockchain;
import logicaDistribuida.blockchain.Block;
import logicaDistribuida.messageTypes.Message;
import logicaDistribuida.messageTypes.Transaction;
import logicaDistribuida.connection.Salida;
import logicaDistribuida.utils.RsaUtil;

public class Nodo {

    private Salida salida;

    /* Nodo */
    /**
     * Clave publica del nodo. (Identificador del segundo nodo, utilizado para
     * verificar las firmas)
     */
    public PublicKey publicKey;
    /**
     * Clave privada del nodo. (Actúa como su contraseña, necesaria para firmar la
     * transacción)
     */
    public PrivateKey privateKey;
    /**
     * Par de claves.
     */
    public KeyPair keys;
    /**
     * Dirección del nodo.
     */
    protected String nodeAddress;
    /**
     * Identificador del nodo.
     */
    protected final int nodeID;
    /**
     * Nombre del nodo.
     */
    public String name;
    /**
     * Blockchain del nodo. (Puede ser LightBlockChain si el nodo es un LightNode)
     */
    protected Blockchain blockchain;



    /* Light Node */
    /**
     * Tarifa de transacción que se aplica cuando un LightNode envía dinero a otro
     * LightNode
     */
    public final static double TRANSACTION_FEE = 0.1;
    /**
     * Valor inicial en la wallet.
     */
    public final static int INIT_WALLET = 100000000;
    /**
     * Wallet para el primer blockchain lógico.
     */
    public double wallet1;
    /**
     * Wallet para el segundo blockchain lógico.
     */
    public double wallet2;
    /**
     * Monto de apuesta para el primer blockchain lógico.
     */
    public double stakeAmount1;
    /**
     * Monto de apuesta para el segundo blockchain lógico.
     */
    public double stakeAmount2;
    /**
     * Fecha de la última apuesta.
     */
    public double stakeTime;

    /* Validator Node */
    /**
     * Cantidad máxima de las transacciones en un bloque.
     */
    public static int MAX_TRANSACTION = 1; //10
    /**
     * Tasa de inversión.
     */
    public static double INVEST_RATE = 0.80;
    /**
     * Lista de trasacciones pendientes.
     */
    private final ArrayList<Transaction> pendingTransaction = new ArrayList<>();
    /**
     * Lista de trasacciones fraudulentas.
     */
    private final ArrayList<Transaction> fraudulentTransaction = new ArrayList<>();
    /**
     * Lista de inversores del primer blockchain lógico.
     */
    private final Map<String, Double> investorList1 = new HashMap<>();
    /**
     * Lista de inversores del segundo blockchain lógico.
     */
    private final Map<String, Double> investorList2 = new HashMap<>();



    /* ValidatorParaL */
    /**
     * Estructura con ip y puerto
     */
    private String validator1 = null;
    /**
     * Estructura con ip y puerto
     */
    private String validator2 = null;



    public Nodo(int id, String nodeAddress) {
        this.nodeID = id;
        try {
            this.keys = RsaUtil.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.publicKey = keys.getPublic();
        this.privateKey = keys.getPrivate();
        // this.nodeAddress = HashUtil.SHA256(String.valueOf(publicKey));
        this.nodeAddress = nodeAddress;
        this.wallet1 = INIT_WALLET;
        this.wallet2 = INIT_WALLET;
        this.stakeAmount1 = 0;
        this.stakeAmount2 = 0;
        this.stakeTime = System.currentTimeMillis();
        blockchain = new Blockchain();
        this.salida = new Salida();
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public KeyPair getKeys() {
        return keys;
    }

    public void setKeys(KeyPair keys) {
        this.keys = keys;
    }

    public static double getTransactionFee() {
        return TRANSACTION_FEE;
    }

    public static int getInitWallet() {
        return INIT_WALLET;
    }

    public double getWallet1() {
        return wallet1;
    }

    public void setWallet1(double wallet1) {
        this.wallet1 = wallet1;
    }

    public double getWallet2() {
        return wallet2;
    }

    public void setWallet2(double wallet2) {
        this.wallet2 = wallet2;
    }

    public double getStakeAmount1() {
        return stakeAmount1;
    }

    public void setStakeAmount1(double stakeAmount1) {
        this.stakeAmount1 = stakeAmount1;
    }

    public double getStakeAmount2() {
        return stakeAmount2;
    }

    public void setStakeAmount2(double stakeAmount2) {
        this.stakeAmount2 = stakeAmount2;
    }

    public double getStakeTime() {
        return stakeTime;
    }

    public void setStakeTime(double stakeTime) {
        this.stakeTime = stakeTime;
    }

    public String getNodeAddress() {
        return nodeAddress;
    }

    public void setNodeAddress(String nodeAddress) {
        this.nodeAddress = nodeAddress;
    }

    public int getNodeID() {
        return nodeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Salida getSalida() {
        return salida;
    }

    public void setSalida(Salida salida) {
        this.salida = salida;
    }

    public Blockchain getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    public static int getMAX_TRANSACTION() {
        return MAX_TRANSACTION;
    }

    public static void setMAX_TRANSACTION(int mAX_TRANSACTION) {
        MAX_TRANSACTION = mAX_TRANSACTION;
    }

    public static double getINVEST_RATE() {
        return INVEST_RATE;
    }

    public static void setINVEST_RATE(double iNVEST_RATE) {
        INVEST_RATE = iNVEST_RATE;
    }

    public ArrayList<Transaction> getPendingTransaction() {
        return pendingTransaction;
    }

    public ArrayList<Transaction> getFraudulentTransaction() {
        return fraudulentTransaction;
    }

    public Set<String> getInvestorList1() {
        return investorList1.keySet();
    }

    public Set<String> getInvestorList2() {
        return this.investorList2.keySet();
    }

    public String getValidator1() {
        return validator1;
    }

    public void setValidator1(String validator1) {
        this.validator1 = validator1;
    }

    public String getValidator2() {
        return validator2;
    }

    public void setValidator2(String validator2) {
        this.validator2 = validator2;
    }

    public void sendMoneyTo(double amount, String nodeAddress, String transactionType) {
        if (wallet1 - amount * (1 + TRANSACTION_FEE) < 0) {
            System.out.println(" Not enough currency of type " + transactionType + " to send"); // Whatever the currency
            System.out.println("Rejected transaction");
            return;
        }
        Transaction toSend = new Transaction(transactionType, this.getNodeAddress(), nodeAddress, amount,
                System.currentTimeMillis(), TRANSACTION_FEE, privateKey);
        Message m = null;
        try {
            m = new Message(this.nodeAddress, nodeAddress, RsaUtil.sign(toSend.toString(), privateKey),
                    System.currentTimeMillis(), 0, toSend);
        } catch (Exception e) {
            e.printStackTrace();
        }
        salida.broadcastMessage(m);
    }

    public void receiptCoin(double amount, String type) {
        wallet1 += amount;
    }

    public void receiptMessage(Message m) {
        // If message is a transaction
        int messageType = m.getType();
        List<Object> listOfContent = m.getMessageContent();
        if (messageType == 0) { // Si es transacción se ejecuta el método para recibir transacciones.
            // System.out.println(listOfContent.get(0));
            Transaction tr = (Transaction) (listOfContent.get(0));
            receiptTransaction(tr);
        }
        // If message is a block
        if (messageType == 1) { // Si es bloque se ejecuta el método para recibir bloques.
            /*
             * Block bPrev = (Block) listOfContent.get(0);
             * Blockchain blk = (Blockchain) listOfContent.get(1);
             * String nodeAddress = m.getFromAddress();
             * String signature = m.getSignature();
             * receiptBlock(bPrev, signature, nodeAddress, blk);
             */
        }
    }

    public void receiptTransaction(Transaction t) {
        boolean transactionStatus = false;
        try {
            transactionStatus = verifyTransaction(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (transactionStatus) {
            pendingTransaction.add(t);

        } else {
            fraudulentTransaction.add(t);
        }
    }

    // TODO
    public boolean verifyTransaction(Transaction t) throws Exception {
        return true;
    }

    /*
     * public void receiptBlock(Block b, String signature, String nodeAddress,
     * Blockchain blk) {
     * updateTransactionList(b);
     * try {
     * if (true) { //TODO verificación
     * //this.blockchain.addBlock(b); //TODO añadir bloque
     * }
     * } catch (Exception e) {
     * e.printStackTrace();
     * }
     * }
     */

    public void updateTransactionList(Block b) {
        List<Transaction> lt = b.getTransaction();
        for (Transaction t : lt) {
            pendingTransaction.remove(t);
        }
    }

    public void validate() {
        // Asignación momentanea:
        validator1 = "26.92.40.65";
        validator2 = "26.20.111.124";
        

        if (validator1 != null && validator2 != null) {
            forgeBlock(validator1);
            forgeBlock(validator2); //validator2.forgeBlock(network.TYPE2);
            validator1 = null;
            validator2 = null;
        }
        /*
        long start = System.currentTimeMillis();
        while (true) {
            long end = System.currentTimeMillis();
            if (end - start > 10000) {
                break;
            }
        }*/
        //chooseValidator(network.TYPE1);
        //chooseValidator(network.TYPE2);
    }


    public void chooseValidator(String ID) {
        //TODO metodo de la clase ValidatorParaL
    }

    public void forgeBlock(String type) {

        List<Transaction> inBlockTransaction = new ArrayList<>();
        for (int i = 0; (i < MAX_TRANSACTION) && (i < pendingTransaction.size()); i++) {
            if (pendingTransaction.get(i).getTransactionID().equals(type)) {
                inBlockTransaction.add(pendingTransaction.get(i));
                //network.setNbTransParType(type, network.getNbTransParType().get(type) - 1); //Estrectura inservible
            }
        }
        long start = System.nanoTime();
        Block prevBlockID = this.blockchain.searchPrevBlockByID(type, this.blockchain.getSize() - 1); //
        long end = System.nanoTime();
        //network.ST.add((double) end - start); // IMPORTANT time, innecesario?
        Block forgedBlock = new Block(this.blockchain.getLatestBlock(), prevBlockID, inBlockTransaction, type);
        forgedBlock.setNodeID(this.nodeID);
        forgedBlock.setNodeAddress(this.nodeAddress);
        // System.out.println("Block has been forged by " + this.name);
        // System.out.println("---------------------------------------------------");
        // System.out.println("Broadcasting");
        try {
            List<Object> messageContent = new ArrayList<>();
            messageContent.add(forgedBlock);
            messageContent.add(this.blockchain);
            Message m = new Message(this.nodeAddress, "ALL",
                    RsaUtil.sign(HashUtil.SHA256(forgedBlock.toString()), this.privateKey), System.currentTimeMillis(),
                    1, messageContent);
            salida.broadcastMessage(m);
            if (type.equals("Type1")) {
                Network.NB_OF_BLOCK_OF_TYPE1_CREATED.add(
                        Network.NB_OF_BLOCK_OF_TYPE1_CREATED.get(Network.NB_OF_BLOCK_OF_TYPE1_CREATED.size() - 1) + 1);
            } else {
                Network.NB_OF_BLOCK_OF_TYPE2_CREATED.add(
                        Network.NB_OF_BLOCK_OF_TYPE2_CREATED.get(Network.NB_OF_BLOCK_OF_TYPE2_CREATED.size() - 1) + 1);
            }
            // System.out.println("Block forged and broadcast successfully by " +
            // this.name);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error signing");
        }
    }


}
