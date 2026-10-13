package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.Coefs.PIDCoefficents;

public class PIDCalculate {


    private PIDCoefficents pidCoefficents;
    private float integral;
    private float previousError;
    float target;
    float current;
    float derivative = 0;
    float error;
    ElapsedTime elapsedTime;

    public PIDCalculate(PIDCoefficents pidCoefficents) {
        this.pidCoefficents = pidCoefficents;
        elapsedTime = new ElapsedTime();
    }

    public float calculate() {

        error = target - current;
        elapsedTime.reset();
        integral += (float) (error * elapsedTime.milliseconds());
        derivative = (float) ((error - previousError) / elapsedTime.milliseconds());

        float u = pidCoefficents.getkP() * error + pidCoefficents.getkI() * integral + pidCoefficents.getkD() * derivative;

        previousError = error;

        return u;
    }
    public void updateCoefs(PIDCoefficents pidCoefficents){
        this.pidCoefficents = pidCoefficents;
    }
    public void updateTarget(float target){
        this.target = target;
    }

    public float getError() {
        return error;
    }

    public void updateCurrent(float current){
        this.current = current;
    }
}
