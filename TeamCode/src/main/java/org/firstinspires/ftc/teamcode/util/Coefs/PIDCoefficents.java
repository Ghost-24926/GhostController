package org.firstinspires.ftc.teamcode.util.Coefs;

public class PIDCoefficents {
    private float kP, kI, kD;

    public float getkP() {
        return kP;
    }

    public void setkP(float kP) {
        this.kP = kP;
    }

    public float getkI() {
        return kI;
    }

    public void setkI(float kI) {
        this.kI = kI;
    }

    public float getkD() {
        return kD;
    }

    public void setkD(float kD) {
        this.kD = kD;
    }

    public PIDCoefficents(float kP, float kI, float kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }
}
