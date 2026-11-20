package org.firstinspires.ftc.teamcode.util;

import org.firstinspires.ftc.teamcode.util.Coefs.FeedForwardCoefs;

/**
 * Прямая связь по скорости: выход = kV · цель + kA.
 * Дополняет ПИД постоянной составляющей, чтобы мотор держал нужные обороты
 * без того, чтобы всю работу делала интегральная часть.
 */
public class FeedForwardCalculate {
    private double output, target;

    FeedForwardCoefs feedForwardCoefs;

    /** Целевая скорость, под которую считается прямая связь. */
    public void setTarget(double target) {
        this.target = target;
    }

    /** Создаёт регулятор с заданными kV и kA. */
    public FeedForwardCalculate(FeedForwardCoefs feedForwardCoefs) {
        this.feedForwardCoefs = feedForwardCoefs;
    }

    /** Подменяет коэффициенты на лету. */
    public void updateFeedCoefs(FeedForwardCoefs feedForwardCoefs){
        this.feedForwardCoefs = feedForwardCoefs;
    }

    /** Возвращает kV · target + kA. */
    public double calculate(){
        output = (feedForwardCoefs.getkV() * target + feedForwardCoefs.getkS()  * Math.signum(target) );
        return output;
    }
}
