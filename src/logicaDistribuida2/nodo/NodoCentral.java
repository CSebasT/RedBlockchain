package logicaDistribuida2.nodo;
import java.security.PublicKey;
import java.util.*;
import logica.network.Network;
import logica.network.Node;
import logicaDistribuida2.nodo.Nodo;


/**
 * Clase Nodo Central.
 * Nodo central para almacenar los valores de la red.
 */
public class NodoCentral {
    private Map<String, Integer> nbTransParType;
    private List<Integer> nbOfBlockOfType1Created;
    private List<Integer> nbOfBlockOfType2Created;
    private List<Double> st;
    private List<Double> pt1;
    private List<Double> pt2;
    private List<Integer> t1;
    private List<Integer> t2;
    private List<Integer> elected;
    private List<Double> exchangeMoney1;
    private List<Double> exchangeMoney2;
    private int difficulty;
    private String type1;
    private String type2;
    private List<Node> network = new ArrayList<>();
    private Map<String, PublicKey> keyTable;
    private String mode;
    private final static int INIT_DIFFICULTY = 4;



    /**
     * Constructor de CentralNode.
     */
    public NodoCentral() {
        nbTransParType = new HashMap<>();
        nbOfBlockOfType1Created = new ArrayList<>();
        nbOfBlockOfType2Created = new ArrayList<>();
        st = new ArrayList<>();
        pt1 = new ArrayList<>();
        pt2 = new ArrayList<>();
        t1 = new ArrayList<>();
        t2 = new ArrayList<>();
        elected = new ArrayList<>();
        exchangeMoney1 = new ArrayList<>();
        exchangeMoney2 = new ArrayList<>();
        difficulty = INIT_DIFFICULTY ;
        type1 = "";
        type2 = "";
        network = new ArrayList<>();
        keyTable = new HashMap<>();
        mode = "POS";
    }

    /**
     * Método para obtener nbTransParType.
     *
     * @return nbTransParType.
     */
    public Map<String, Integer> getNbTransParType() {
        return nbTransParType;
    }

    /**
     * Método para establecer nbTransParType.
     *
     * @param nbTransParType Mapa de nbTransParType.
     */
    public void setNbTransParType(Map<String, Integer> nbTransParType) {
        this.nbTransParType = nbTransParType;
    }

    /**
     * Método para obtener nbOfBlockOfType1Created.
     *
     * @return nbOfBlockOfType1Created.
     */
    public List<Integer> getNbOfBlockOfType1Created() {
        return nbOfBlockOfType1Created;
    }

    /**
     * Método para establecer nbOfBlockOfType1Created.
     *
     * @param nbOfBlockOfType1Created Lista de nbOfBlockOfType1Created.
     */
    public void setNbOfBlockOfType1Created(List<Integer> nbOfBlockOfType1Created) {
        this.nbOfBlockOfType1Created = nbOfBlockOfType1Created;
    }

    /**
     * Método para obtener nbOfBlockOfType2Created.
     *
     * @return nbOfBlockOfType2Created.
     */
    public List<Integer> getNbOfBlockOfType2Created() {
        return nbOfBlockOfType2Created;
    }

    /**
     * Método para establecer nbOfBlockOfType2Created.
     *
     * @param nbOfBlockOfType2Created Lista de nbOfBlockOfType2Created.
     */
    public void setNbOfBlockOfType2Created(List<Integer> nbOfBlockOfType2Created) {
        this.nbOfBlockOfType2Created = nbOfBlockOfType2Created;
    }

    /**
     * Método para obtener st.
     *
     * @return st.
     */
    public List<Double> getSt() {
        return st;
    }
}
