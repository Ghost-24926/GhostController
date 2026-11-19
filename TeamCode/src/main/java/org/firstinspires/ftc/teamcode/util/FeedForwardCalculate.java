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

    public void setTarget(double target) {
        this.target = target;
    }

    public FeedForwardCalculate(FeedForwardCoefs feedForwardCoefs) {
        this.feedForwardCoefs = feedForwardCoefs;
    }

    public void updateFeedCoefs(FeedForwardCoefs feedForwardCoefs){
        this.feedForwardCoefs = feedForwardCoefs;
    }

    public double calculate(){
        output = (feedForwardCoefs.getkV() * target + feedForwardCoefs.getkA()  );
        return output;
    }
}
