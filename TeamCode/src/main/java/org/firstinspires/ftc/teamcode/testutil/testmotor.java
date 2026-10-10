package org.firstinspires.ftc.teamcode.testutil;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp

public class testmotor extends LinearOpMode {
    DcMotor siska;

    @Override
    public void runOpMode() throws InterruptedException {
        siska = hardwareMap.get(DcMotor.class, "gun");

        waitForStart();

        while(opModeIsActive()){
            if(gamepad1.a){
                siska.setPower(1);
            }
            if(gamepad1.b){
                siska.setPower(0.8);
            }
            if(gamepad1.y){
                siska.setPower(0.6);
            }
            if(gamepad1.x){
                siska.setPower(0);
            }
            telemetry.addData("Power", siska.getPower());
        }
    }
}
