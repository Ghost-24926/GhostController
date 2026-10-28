package org.firstinspires.ftc.teamcode.util;


import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class CalculateVelocity {
    GoBildaPinpointDriver pinpoint;
    double radius, oldHeading;
    double xCoord;
    double yCoord;
    double angleCoord;
    double deltaTheta1;
    double deltaTetha2;
    double deltaHeading;
    double y1;
    double x2;
    double metersPerTick;
    double dt;
    long oldTime;
    int xSgn, ySgn;



    public CalculateVelocity(GoBildaPinpointDriver pinpoint, double radius, double y1, double x2, int xSgn, int ySgn) {
        this.pinpoint = pinpoint;
        this.y1 = y1;
        this.x2 = x2;
        this.xSgn = xSgn;
        this.ySgn = ySgn;
        this.metersPerTick = 2.0 * Math.PI*radius/28;

        pinpoint.update();
        xCoord = pinpoint.getEncoderX();
        yCoord = pinpoint.getEncoderY();

        double newHeading = pinpoint.getHeading(AngleUnit.RADIANS);
        oldHeading = newHeading;
        long newTime = System.nanoTime();
        oldTime = newTime;
    }
    public double getVx(){
        pinpoint.update();

        int newX = pinpoint.getEncoderX();
        double heading = pinpoint.getHeading(AngleUnit.RADIANS);
        long newTime = System.nanoTime();
        dt = (newTime - oldTime);

        double wheelOffField = xSgn * (newX - xCoord) * dt;
        double deltaHeading = heading - oldHeading;
        double deltaX = wheelOffField + y1 * deltaHeading;

        xCoord = newX;
        oldHeading = heading;
        oldTime = newTime;
        return deltaX/dt;
    }
    public double getVy(){
        pinpoint.update();

        int newY = pinpoint.getEncoderY();
        double heading = pinpoint.getHeading(AngleUnit.RADIANS);
        long newTime = System.nanoTime();
        dt = (newTime - oldTime);

        double wheelOffField = ySgn * (newY - yCoord) * dt;
        double deltaHeading = heading - oldHeading;
        double deltaY = wheelOffField + x2 * deltaHeading;

        yCoord = newY;
        oldHeading = heading;
        oldTime = newTime;
        return deltaY/dt;
    }
    public double getOmega(){
        pinpoint.update();

        double heading = pinpoint.getHeading(AngleUnit.RADIANS);
        long newTime = System.nanoTime();

        dt = (newTime - oldTime);

        double deltaHeading = heading - oldHeading;
        oldHeading = heading;
        oldTime = newTime;
        return deltaHeading/dt;
    }
}
