package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
@TeleOp
public class CoordOpMode extends LinearOpMode {
    DcMotor rb, lb, rf, lf;

    @Override
    public void runOpMode() throws InterruptedException {
        rb = hardwareMap.get(DcMotor.class, "rb");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lf = hardwareMap.get(DcMotor.class, "lf");

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.a) {
                rb.setPower(1);
            } else {
                rb.setPower(0);
            }
            if (gamepad1.b) {
                lb.setPower(1);
            } else {
                lb.setPower(0);
            }
            if (gamepad1.y) {
                rf.setPower(1);
            } else {
                rf.setPower(0);
            }
            if (gamepad1.x) {
                lf.setPower(1);
            } else {
                lf.setPower(0);
            }

            telemetry.addData("rb", rb.getCurrentPosition());
            telemetry.addData("lb", lb.getCurrentPosition());
            telemetry.addData("rf", rf.getCurrentPosition());
            telemetry.addData("lf", lf.getCurrentPosition());
            telemetry.update();
        }
    }
}
