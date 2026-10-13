package org.firstinspires.ftc.teamcode.util.Coefs;

public class FeedForwardCoefs {
    float kV, kA;

    public FeedForwardCoefs(float kV, float kA) {
        this.kV = kV;
        this.kA = kA;
    }

    public void setkV(float kV) {
        this.kV = kV;
    }

    public void setkA(float kA) {
        this.kA = kA;
    }

    public float getkV() {
        return kV;
    }

    public float getkA() {
        return kA;
    }
}
