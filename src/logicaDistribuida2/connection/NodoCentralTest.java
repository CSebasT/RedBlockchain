package logicaDistribuida2.connection;
import logica.network.Network;
import logica.network.NuevoNodoCentral;

import java.io.IOException;

public class NodoCentralTest {
    public static void main(String[] args) throws IOException {
        NuevoNodoCentral nodoCentral = new NuevoNodoCentral();
        Network network = nodoCentral.getNetwork();

        // Agregar nodos a la red, realizar transacciones, etc.

        nodoCentral.printNetworkStats();
        nodoCentral.printWallets();


    }
}
