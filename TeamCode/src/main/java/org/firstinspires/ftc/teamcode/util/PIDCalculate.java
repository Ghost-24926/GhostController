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
    /**
     * Накопленная ошибка и ошибка предыдущего такта — для I и D.
     */
    private float integral;
    private float previousError;
    float target;
    float current;
    float derivative = 0;
    float error;
    ElapsedTime elapsedTime;

    /**
     * Создаёт регулятор и запускает таймер для dt.
     */
    public PIDCalculate(PIDCoefficents pidCoefficents) {
        this.pidCoefficents = pidCoefficents;
        elapsedTime = new ElapsedTime();
    }

    /**
     * Считает управляющее воздействие по текущим target и current.
     */
    public float calculate() {

        error = target - current;
        integral += (float) (error * elapsedTime.seconds());
        derivative = (float) ((error - previousError) / elapsedTime.seconds());
        elapsedTime.reset();
        float u = pidCoefficents.getkP() * error + pidCoefficents.getkI() * integral + pidCoefficents.getkD() * derivative;

        previousError = error;

        return u;
    }

    /**
     * Подменяет коэффициенты на лету, например из FTC Dashboard.
     */
    public void updateCoefs(PIDCoefficents pidCoefficents) {
        this.pidCoefficents = pidCoefficents;
    }

    /**
     * Задаёт цель регулятора.
     */
    public void updateTarget(float target) {
        this.target = target;
    }

    /**
     * Ошибка, посчитанная в последнем calculate().
     */
    public float getError() {
        return error;
    }

    /**
     * Задаёт текущее измеренное значение.
     */
    public void updateCurrent(float current) {
        this.current = current;
    }
}
