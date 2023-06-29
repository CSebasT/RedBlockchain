package logica.network;

import java.util.*;

/**
 * Clase NodoCentral.
 * Representa el nodo central que guarda los valores de la red.
 */
public class NuevoNodoCentral {
    private Network network;

    /**
     * Constructor de NodoCentral.
     * Crea un nuevo nodo central con una red vacía.
     */
    public NuevoNodoCentral() {
        network = new Network("Type1", "Type2");
    }

    /**
     * Método para obtener la red almacenada en el nodo central.
     *
     * @return La red almacenada.
     */
    public  Network getNetwork() {
        return network;
    }

    /**
     * Método para establecer la red en el nodo central.
     *
     * @param network La red que se va a almacenar.
     */
    public void setNetwork(Network network) {
        this.network = network;
    }

    /**
     * Método para imprimir las estadísticas de la red almacenada en el nodo central.
     */
    public  void printNetworkStats() {
        network.printStats();
    }

    /**
     * Método para imprimir el estado de las billeteras de la red almacenada en el nodo central.
     */
    public void printWallets() {
        network.printWallets();
    }
}

