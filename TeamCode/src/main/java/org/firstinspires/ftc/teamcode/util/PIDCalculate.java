package org.firstinspires.ftc.teamcode.util;

public class PIDCalculate{

    public class PIDCoefficents{
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

    PIDCoefficents pidCoefficents;
    private float integral;
    private float previousError;
    private boolean firstUpdate = true;

    public PIDCalculate(float kp, float ki, float kd) {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
    }
    public float calculate(float error, float dt) {

        if (dt <= 0) {
            throw new IllegalArgumentException("dt должен быть больше 0");
        }

        integral += error * dt;

        float derivative;

        if (firstUpdate) {
            derivative = 0.0F;
        } else {
            derivative = (error - previousError) / dt;
        }
        float u = kp * error + ki * integral + kd * derivative;

        previousError = error;
        firstUpdate = false;

        return u;
    }

    public void reset() {
        integral = 0.0F;
        previousError = 0.0F;
        firstUpdate = true;
    }
}
