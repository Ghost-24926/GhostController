package org.firstinspires.ftc.teamcode.util.Coefs;

/** Пропорциональный, интегральный и дифференциальный коэффициенты ПИД-регулятора. */
public class PIDCoefficents {
    private float kP, kI, kD;

    /** Текущий kP. */
    public float getkP() {
        return kP;
    }

    /** Новый kP. */
    public void setkP(float kP) {
        this.kP = kP;
    }

    /** Текущий kI. */
    public float getkI() {
        return kI;
    }

    /** Новый kI. */
    public void setkI(float kI) {
        this.kI = kI;
    }

    /** Текущий kD. */
    public float getkD() {
        return kD;
    }

    /** Новый kD. */
    public void setkD(float kD) {
        this.kD = kD;
    }

    /** Задаёт все три коэффициента. */
    public PIDCoefficents(float kP, float kI, float kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }
}
