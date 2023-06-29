package logicaDistribuida2.nodo;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public static int MAX_TRANSACTION = 1; // 10
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
    private String direccionValidadorType1 = null;
    /**
     * Estructura con ip y puerto
     */
    private String direccionValidadorType2 = null;

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
        this.salida = new Salida(this);
        this.name = Integer.toString(id);
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

    public String getDireccionValidadorType1() {
        return direccionValidadorType1;
    }

    public void setDireccionValidadorType1(String validator1) {
        this.direccionValidadorType1 = validator1;
    }

    public String getDireccionValidadorType2() {
        return direccionValidadorType2;
    }

    public void setDireccionValidadorType2(String validator2) {
        this.direccionValidadorType2 = validator2;
    }

    public void sendMoneyTo(double amount, String nodeAddress, String transactionType) {
        if (wallet1 - amount * (1 + TRANSACTION_FEE) < 0) {
            System.out.println(name + " Not enough currency of type " + transactionType + " to send");
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
        System.out.println("Mensaje creado");
        salida.broadcastMessage(m);
    }

    public void receiptCoin(double amount, String type) {
        System.out.println("Cantidad recibida: " + amount);
        wallet1 += amount;
        System.out.println("Nuevo valor: " + wallet1);
    }

    public void receiptMessage(Message message) {
        // If message is a transaction
        int messageType = message.getType();
        List<Object> listOfContent = message.getMessageContent();
        if (messageType == 0) {
            Transaction tr = (Transaction) (listOfContent.get(0));
            System.out.println("Transaccion recibida");
            receiptTransaction(tr);
        }
        if (messageType == 1) {
            Block bPrev = (Block) listOfContent.get(0);
            Blockchain blk = (Blockchain) listOfContent.get(1);
            String nodeAddress = message.getFromAddress();
            String signature = message.getSignature();
            System.out.println("Bloque recibido");
            receiptBlock(bPrev, signature, nodeAddress, blk);
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
            System.out.println(t);
        } else {
            fraudulentTransaction.add(t);
        }
    }

    public boolean verifyTransaction(Transaction t) throws Exception {
        return true;
        // TODO: envio multiple al nodo al nodo que generó la transacción
        // return RsaUtil.verify(t.toString(), t.getSignature(),
        // salida.getPkWithAddress(t.getFromAddress()));
    }

    public void receiptBlock(Block b, String signature, String nodeAddress, Blockchain blk) {
        updateTransactionList(b);
        try {
            if (true) { // RsaUtil.verify(HashUtil.SHA256(b.toString()), signature,
                        // network.getPkWithAddress(nodeAddress))
                this.blockchain.addBlock(b);
                System.out.println(b);
                System.out.println(b.getTransaction());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTransactionList(Block b) {
        List<Transaction> lt = b.getTransaction();
        for (Transaction t : lt) {
            pendingTransaction.remove(t);
        }
    }

    public void validate() {
        //chooseValidator("Type1");
        //chooseValidator("Type2");
        // Asignación momentanea:
        direccionValidadorType1 = "26.92.40.65";
        direccionValidadorType2 = "26.20.111.124";

        if (direccionValidadorType1 != null && direccionValidadorType2 != null) {
            salida.enviarAForjar(direccionValidadorType1, "Type1");
            // salida.enviarAForjar(direccionValidador2, "Type2"); //
            // validator2.forgeBlock(network.TYPE2);
            direccionValidadorType1 = null;
            direccionValidadorType2 = null;
        }
        // chooseValidator("Type1");
        // chooseValidator("Type2");
    }

    public void chooseValidator(String type) {
        System.out.println("Choosing a validator for a block of type " + type);
        // List of nodes in the network
        List<String> listNode = new ArrayList<>(Arrays.asList("26.92.40.65", "26.92.40.65"));
        Map<String, Double> mapProba = new HashMap<>();
        for (String direccion : listNode) {
            double stakeAmount;
            if (type.equals("Type1")) {
                salida.actualizarStakeAmount(direccion,"Type1");
                //stakeAmount =  // mapStakeAmount1.get(direccion);
            } else {
                //stakeAmount = salida.pedirStakeAmount(direccion,"Type2");
            }
            //Get LightNode's stakeTime (How long the node have been Staking)
            salida.actualizarStakeTime(direccion);
            //double stakeTime = System.currentTimeMillis() - mapStakeTime.get(direccion);;
            //mapProba.put(direccion, stakeAmount * (stakeTime));
        }
        double sum = mapProba.values().stream().mapToDouble(v -> v).sum();
        int number_of_slots = 0;
        for (String direccion : listNode) {
            number_of_slots += (mapProba.get(direccion) / sum) * 10;
        }
        System.out.println("Slots : " + number_of_slots);
        int node_slots;
        List<String> validatorNodesSlots = new ArrayList<>(number_of_slots);
        for (int j = 0; j < number_of_slots; j++)
            validatorNodesSlots.add(null);
        for (String direccion : listNode) {
                node_slots = (int) ((mapProba.get(direccion) / sum) * 10);
                int slot_index;
                for (int i = 0; i < node_slots; i++) {
                    do {
                        slot_index = (int) (Math.random() * number_of_slots);
                    } while (validatorNodesSlots.get(slot_index) != null);
                    validatorNodesSlots.set(slot_index, direccion);
                }
        }
        System.out.print("[");
        for (String ln : validatorNodesSlots) {
            System.out.print(ln + " ");
        }
        System.out.print("]\n");

        if (sum == 0) // if anyone didn't deposit bitcoin as stake
            return;

        int chosen_node_index = (int) (Math.random() * number_of_slots);
        if (type.equals("Type1")) {
            direccionValidadorType1 = validatorNodesSlots.get(chosen_node_index);
            System.out.println(direccionValidadorType1 + " is chosen");
        } else {
            direccionValidadorType2 = validatorNodesSlots.get(chosen_node_index);
            System.out.println(direccionValidadorType2 + " is chosen");
        }

    }

    public void forgeBlock(String type) {
        List<Transaction> inBlockTransaction = new ArrayList<>();
        for (int i = 0; (i < MAX_TRANSACTION) && (i < pendingTransaction.size()); i++) {
            if (pendingTransaction.get(i).getTransactionID().equals(type)) {
                inBlockTransaction.add(pendingTransaction.get(i));
                // network.setNbTransParType(type, network.getNbTransParType().get(type) -
                // 1);**********
            }
        }
        long start = System.nanoTime();
        Block prevBlockID = this.blockchain.searchPrevBlockByID(type, this.blockchain.getSize() - 1); //
        long end = System.nanoTime();
        // network.ST.add((double) end - start);**************
        Block forgedBlock = new Block(this.blockchain.getLatestBlock(), prevBlockID, inBlockTransaction, type);
        forgedBlock.setNodeID(this.nodeID);
        forgedBlock.setNodeAddress(this.nodeAddress);
        System.out.println("Block has been forged by " + this.name);
        System.out.println("---------------------------------------------------");
        try {
            List<Object> messageContent = new ArrayList<>();
            messageContent.add(forgedBlock);
            messageContent.add(this.blockchain);
            Message m = new Message(this.nodeAddress, "ALL",
                    RsaUtil.sign(HashUtil.SHA256(forgedBlock.toString()), this.privateKey), System.currentTimeMillis(),
                    1, messageContent);
            salida.broadcastMessage(m);
            if (type.equals("Type1")) {
                // Network.NB_OF_BLOCK_OF_TYPE1_CRE q
                // Network.NB_OF_BLOCK_OF_TYPE1_CREATED.get(Network.NB_OF_BLOCK_OF_TYPE1_CREATED.size()
                // - 1) + 1);
            } else {
                // Network.NB_OF_BLOCK_OF_TYPE2_CREATED.add(Network.NB_OF_BLOCK_OF_TYPE2_CREATED.get(Network.NB_OF_BLOCK_OF_TYPE2_CREATED.size()
                // - 1) + 1);
            }
            // System.out.println("Block forged and broadcast successfully by " +
            // this.name);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error signing");
        }
    }

    public void stake(int amount, String type) {
        if (type.equals("Type1")) {
            if (wallet1 < amount) {
                System.out.println(name + " don't have enough money for stake in wallet1");
            }
            stakeAmount1 = amount;
            this.wallet1 -= amount;
        } else {
            if (wallet1 < amount) {
                System.out.println(name + " don't have enough money for stake in wallet1");
            }
            stakeAmount2 = amount;
            this.wallet2 -= amount;
        }
        stakeTime = System.currentTimeMillis();
        System.out.println(name + " deposit " + amount + " as stake");
    }

    public void addInvestorType(String investorAddress, double stakeAmount, String type) {
        if (type.equals("Type1")) {
            this.investorList1.put(investorAddress, stakeAmount);
            this.stakeAmount1 += stakeAmount;
        } else {
            this.investorList2.put(investorAddress, stakeAmount);
            this.stakeAmount2 += stakeAmount;
        }
    }
    public void recuperarDatos(){

    }
}
