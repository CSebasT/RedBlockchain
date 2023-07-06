package logicaDistribuida2.messageTypes;

import java.io.Serializable;
import java.security.PublicKey;

public class InfoNodo implements Serializable{
    private String direccion;
    private PublicKey publicKey;
    private double stakeAmount1;
    private double stakeAmount2;
    private long stakeTime;
    
    
    public InfoNodo(String direccion, PublicKey publicKey, double stakeAmount1, double stakeAmount2, long stakeTime) {
        this.direccion = direccion;
        this.publicKey = publicKey;
        this.stakeAmount1 = stakeAmount1;
        this.stakeAmount2 = stakeAmount2;
        this.stakeTime = stakeTime;
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

    public long getStakeTime() {
        return stakeTime;
    }

    public void setStakeTime(long stakeTime) {
        this.stakeTime = stakeTime;
    }

}
