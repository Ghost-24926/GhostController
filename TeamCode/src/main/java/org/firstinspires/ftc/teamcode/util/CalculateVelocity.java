package org.firstinspires.ftc.teamcode.util;


import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.logging.Logger;

public class CalculateVelocity {
    GoBildaPinpointDriver pinpoint;
    private static final Logger LOGGER = Logger.getLogger(CalculateVelocity.class.getName());
    double radius, oldHeadingY = 0, oldHeadingX = 0, oldHeadingO = 0;
    double xCoord;
    double yCoord;
    double y1;
    double x2;
    double metersPerTick;
    double dt;
    long oldTimeY = 0, oldTimeX = 0, oldTimeO = 0;
    int xSgn, ySgn;



    public CalculateVelocity(GoBildaPinpointDriver pinpoint, double radius, double y1, double x2, int xSgn, int ySgn) {
        this.pinpoint = pinpoint;
        this.y1 = y1;
        this.x2 = x2;
        this.xSgn = xSgn;
        this.ySgn = ySgn;
        this.metersPerTick = 2.0 * Math.PI*radius/28;
    }
    public double getVx(){
//        pinpoint.update();

        int newX = pinpoint.getEncoderX();
        double heading = pinpoint.getHeading(AngleUnit.RADIANS);
        long newTime = System.nanoTime();
        dt = (newTime - oldTimeX) * Math.pow(10, -9);

        double wheelOffField = xSgn * (newX - xCoord) * dt;
        double deltaHeading = heading - oldHeadingX;
        double deltaX = wheelOffField + y1 * deltaHeading;

        xCoord = newX;
        oldHeadingX = heading;
        oldTimeX = newTime;
        return deltaX/dt;
    }
    public double getVy(){
//        pinpoint.update();

        int newY = pinpoint.getEncoderY();
        double heading = pinpoint.getHeading(AngleUnit.RADIANS);
        long newTime = System.nanoTime();
        dt = (newTime - oldTimeY) * Math.pow(10, -9);

        double wheelOffField = ySgn * (newY - yCoord) * dt;
        double deltaHeading = heading - oldHeadingY;
        double deltaY = wheelOffField + x2 * deltaHeading;

        yCoord = newY;
        oldHeadingY = heading;
        oldTimeY = newTime;
        return deltaY/dt;

    }
    public double getOmega() throws InterruptedException {
//        pinpoint.update();

        double heading = pinpoint.getHeading(AngleUnit.RADIANS);
        long newTime = System.nanoTime();



        dt = (newTime - oldTimeO) * Math.pow(10, -9);
        for (int i = 0; i < 10; i++){
            LOGGER.warning("rev pidori");
        }


        double deltaHeading = heading - oldHeadingO;
        oldHeadingO = heading;
        oldTimeO = System.nanoTime();
        LOGGER.info(Double.toString(dt) + " dt");
        LOGGER.info(Double.toString(deltaHeading)+ " dH");
        LOGGER.info(Double.toString(heading)+ " H");
        LOGGER.info(Double.toString(oldHeadingO)+ " oH");
        return deltaHeading/dt;
    }
}
