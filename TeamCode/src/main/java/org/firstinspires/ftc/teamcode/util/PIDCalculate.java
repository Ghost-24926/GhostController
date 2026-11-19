package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.util.Coefs.PIDCoefficents;

/**
 * Дискретный ПИД: u = kP·e + kI·∫e dt + kD·de/dt.
 * Цель и текущее значение задаются снаружи перед каждым {@link #calculate()}.
 * Интеграл и прошлая ошибка живут между вызовами, чтобы регулятор помнил историю.
 */
public class PIDCalculate {


    private PIDCoefficents pidCoefficents;
    /** Накопленная ошибка и ошибка предыдущего такта — для I и D. */
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

    /** Считает управляющее воздействие по текущим target и current. */
    public float calculate() {

        error = target - current;
        elapsedTime.reset();
        integral += (float) (error * elapsedTime.milliseconds());
        derivative = (float) ((error - previousError) / elapsedTime.milliseconds());

        float u = pidCoefficents.getkP() * error + pidCoefficents.getkI() * integral + pidCoefficents.getkD() * derivative;

        previousError = error;

        return u;
    }

    /** Подменяет коэффициенты на лету, например из FTC Dashboard. */
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
