package org.firstinspires.ftc.teamcode.util.Coefs;

/** Коэффициенты прямой связи: kV — на единицу целевой скорости, kA — постоянное смещение. */
// Todo: см Контроллер
public class FeedForwardCoefs {
    float kV, kS;

    /** Задаёт оба коэффициента. */
    public FeedForwardCoefs(float kV, float kA) {
        this.kV = kV;
        this.kS = kA;
    }

    /** Новый kV. */
    public void setkV(float kV) {
        this.kV = kV;
    }

    /** Новый kA. */
    public void setkS(float kS) {
        this.kS = kS;
    }

    /** Текущий kV. */
    public float getkV() {
        return kV;
    }

    /** Текущий kA. */
    public float getkS() {
        return kS;
    }
}
