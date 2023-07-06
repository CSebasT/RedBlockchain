package logicaDistribuida2.messageTypes;

import java.io.Serializable;
import java.security.PublicKey;

public class ClavePublica implements Serializable{
    private String direccion;
    private PublicKey publicKey;
    
    
    public ClavePublica(String direccion, PublicKey publicKey) {
        this.direccion = direccion;
        this.publicKey = publicKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
