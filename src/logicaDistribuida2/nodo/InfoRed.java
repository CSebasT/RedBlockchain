package logicaDistribuida2.nodo;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InfoRed implements Serializable {

    /**
     * Dificultad inicial.
     **/
    private final static int INIT_DIFFICULTY = 4;
    /**
     * Cambio de dificultad.
     **/
    private final static int CHANGE_DIFFICULTY = 50;
    /**
     * Número de bloques del primer blockchain lógico.
     **/
    public static List<Integer> NB_OF_BLOCK_OF_TYPE1_CREATED = new ArrayList<>();
    /**
     * Número de bloques del segundo blockchain lógico.
     */
    public static List<Integer> NB_OF_BLOCK_OF_TYPE2_CREATED = new ArrayList<>();
    /**
     * Lista de tiempos (lapso que se demora en encontrar el bloque previo de un
     * blockchain lógico.)
     */
    public List<Double> ST = new ArrayList<>();
    /**
     * Lista de las probabilidades de las trasacciones del primer blockchain lógico.
     **/
    public List<Double> PT1 = new ArrayList<>();
    /**
     * Lista de las probabilidades de las trasacciones del segundo blockchain
     * lógico.
     */
    public List<Double> PT2 = new ArrayList<>();
    /**
     * Lista de número de trasacciones del primer blockchain lógico.
     */
    public List<Integer> T1 = new ArrayList<>();
    /**
     * Lista de número de trasacciones del segundo blockchain lógico.
     */
    public List<Integer> T2 = new ArrayList<>();
    /**
     * Lista de los nodos elegidos para la creación de bloques.
     */
    public List<Integer> ELECTED = new ArrayList<>();
    /**
     * Intercambios de dinero del primer blockchain lógico.
     */
    public List<Double> EXCHANGE_MONEY1 = new ArrayList<>();
    /**
     * Intercambios de dinero del segundo blockchain lógico.
     */
    public List<Double> EXCHANGE_MONEY2 = new ArrayList<>();
    /**
     * Identificador del primer blockchain lógico.
     */
    public final String TYPE1;
    /**
     * Identificador del segundo blockchain lógico.
     */
    public final String TYPE2;
    /**
     * Tabla de mapeo de NodeAddress y PublicKey para verificar firmas.
     */
    private final Map<String, PublicKey> keyTable = new HashMap<>();
    /**
     * Algoritmo de consenso.
     */
    public String mode = "POS";
    /**
     * Dificultad actual.
     */
    private int difficulty = INIT_DIFFICULTY;
    /**
     * Número de transacciones de cada blockchain lógico.
     */
    private final Map<String, Integer> nbTransParType = new HashMap<>();

    private Map<String, Double> mapStakeAmount1 = new HashMap<>();
    private Map<String, Double> mapStakeAmount2 = new HashMap<>();

    private Map<String, Long> mapStakeTime = new HashMap<>();

    /**
     * Constructor de Network.
     * Red con dos blockchains.
     * 
     */
    public InfoRed() {
        TYPE1 = "Type1";
        TYPE2 = "Type2";
        nbTransParType.put(TYPE1, 0);
        nbTransParType.put(TYPE2, 0);
        NB_OF_BLOCK_OF_TYPE1_CREATED.add(1);
        NB_OF_BLOCK_OF_TYPE2_CREATED.add(1);
        EXCHANGE_MONEY1.add(0.);
        EXCHANGE_MONEY2.add(0.);
        // keyTable.put("26.20.111.124", );
        // keyTable.put("26.92.40.65", );
        // keyTable.put("26.37.38.157", );
        // keyTable.put("26.143.218.218", );
        // keyTable.put("26.194.104.185", new PublicKey());
    }

    public void addNode(String direccion, PublicKey publicKey, double stakeAmount1, double stakeAmount2, long stakeTime) {
        mapStakeAmount1.put(direccion, stakeAmount1);
        mapStakeAmount2.put(direccion, stakeAmount2);
        mapStakeTime.put(direccion, stakeTime);
        try {
            keyTable.put(direccion, publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printStats() {
        try {
            System.out.println("ST=" + ST);
            System.out.println("NBT1=" + NB_OF_BLOCK_OF_TYPE1_CREATED);
            System.out.println("NBT2=" + NB_OF_BLOCK_OF_TYPE2_CREATED);
            // System.out.println("WTT1=" + copyBlockchainFromFN().WTT1);
            // System.out.println("WTT2=" + copyBlockchainFromFN().WTT2);
            System.out.println("PT1=" + PT1);
            System.out.println("PT2=" + PT2);
            System.out.println("T1=" + T1);
            System.out.println("T2=" + T2);
            System.out.println("ELECTED=" + ELECTED);
            System.out.println("Type_1_currency_exchanged=" + EXCHANGE_MONEY1);
            System.out.println("Type_2_currency_exchanged=" + EXCHANGE_MONEY2);
        } catch (NullPointerException e) {
            System.out.println("Objeto vacio");
        }
    }

    public PublicKey getPkWithAddress(String address) {
        return keyTable.get(address);
    }

    public Map<String, Integer> getNbTransParType() {
        return nbTransParType;
    }

    public void setNbTransParType(String type, int nb) {
        this.nbTransParType.put(type, nb);
    }

    public Set<String> getNetwork() {
        return keyTable.keySet();
    }

    public double getStakeAmount1WhitAdress(String direccion) {
        return mapStakeAmount1.get(direccion);
    }

    public double getStakeAmount2WhitAdress(String direccion) {
        return mapStakeAmount2.get(direccion);
    }

    public double getStakeTimeWhitAdress(String direccion) {
        return mapStakeTime.get(direccion);
    }

}