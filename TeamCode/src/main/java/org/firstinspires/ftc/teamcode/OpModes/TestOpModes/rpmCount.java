package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class rpmCount extends LinearOpMode {
    DcMotorEx siska;


    @Override
    public void runOpMode() throws InterruptedException {
        siska = hardwareMap.get(DcMotorEx.class, "siska");
        siska.setDirection(DcMotorEx.Direction.FORWARD);
        siska.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        siska.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        while(opModeIsActive()){
            if (gamepad1.a){
                siska.setVelocity((double) (4000 * 28) /60);
            }
            else{
                siska.setVelocity(0);
            }

            telemetry.addData("velocity", siska.getVelocity());
            telemetry.addData("rpm", siska.getVelocity()/28*60);
            telemetry.update();
        }
    }
}
