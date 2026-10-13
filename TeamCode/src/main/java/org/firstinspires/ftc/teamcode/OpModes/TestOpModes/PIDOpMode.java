package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.bylazar.configurables.annotations.Configurable;

import org.firstinspires.ftc.teamcode.util.*;
import org.firstinspires.ftc.teamcode.util.Coefs.FeedForwardCoefs;
import org.firstinspires.ftc.teamcode.util.Coefs.PIDCoefficents;

@TeleOp
@Configurable
public class PIDOpMode extends LinearOpMode {
    DcMotorEx l;
    byte a = 0;
    byte b = 0;
    PIDCoefficents pidCoefficents;
    FeedForwardCoefs feedForwardCoefs;
    FeedForwardCalculate feedForwardCalculate;
    PIDCalculate pidCalculate;
    float target = 0;
    public static float kI, kP, kD, kV = 1 /95f, kA = 0.03f;

    @Override
    public void runOpMode() throws InterruptedException {
        l = hardwareMap.get(DcMotorEx.class, "l");
        l.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        l.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        pidCoefficents = new PIDCoefficents(kP,kI,kD);
        feedForwardCoefs = new FeedForwardCoefs(kV, kA);
        pidCalculate = new PIDCalculate(pidCoefficents);
        feedForwardCalculate = new FeedForwardCalculate(feedForwardCoefs);

        waitForStart();


        while(opModeIsActive()){
            pidCoefficents.setkD(kD);
            pidCoefficents.setkI(kI);
            pidCoefficents.setkP(kP);
            feedForwardCoefs.setkA(kA);
            feedForwardCalculate.updateFeedCoefs(feedForwardCoefs);
            pidCalculate.updateCoefs(pidCoefficents);


            if (a == 0){
                if(gamepad1.a) {
                    target = abs(3500);
                    a = 1;
                    sleep(300);
                }
            } else {
                if(gamepad1.a) {
                    target = 0;
                    a = 0;
                    sleep(300);
                }
            }
            if(b == 0){
                if(gamepad1.b){
                    l.setPower(0.8);
                    b = 1;
                    sleep(300);
                }
            }else{
                if(gamepad1.b){
                    l.setPower(0);
                    b = 0;
                    sleep(300);
                }
            }
            pidCalculate.updateCurrent((float) (l.getVelocity()/28*60));
            pidCalculate.updateTarget(target);
            feedForwardCalculate.setTarget(target);
            l.setPower(pidCalculate.calculate() + feedForwardCalculate.calculate());
            telemetry.addData("velocity", l.getVelocity()/28*60);
            telemetry.addData("error", pidCalculate.getError());
            telemetry.update();
        }
    }
}
