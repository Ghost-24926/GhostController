package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.util.CalculateVelocity;

@TeleOp
public class CoordOpMode extends LinearOpMode {
    DcMotorEx rb, lb, rf, lf;

    GoBildaPinpointDriver pc;


    @Override
    public void runOpMode() throws InterruptedException {

        rb = hardwareMap.get(DcMotorEx.class, "rb");
        lb = hardwareMap.get(DcMotorEx.class, "lb");
        rf = hardwareMap.get(DcMotorEx.class, "rf");
        lf = hardwareMap.get(DcMotorEx.class, "lf");
        pc = hardwareMap.get(GoBildaPinpointDriver.class, "pc");

        pc.resetPosAndIMU();

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

            CalculateVelocity velocity = new CalculateVelocity(pc, 0.024, 0.1, 0.1, 1, 1);
            double vx = velocity.getVx();
            double vy = velocity.getVy();
            double vo = velocity.getOmega();

            telemetry.addData("rb", rb.getCurrentPosition());
            telemetry.addData("lb", lb.getCurrentPosition());
            telemetry.addData("rf", rf.getCurrentPosition());
            telemetry.addData("lf", lf.getCurrentPosition());
            telemetry.addData("vx", vx);
            telemetry.addData("vy", vy);
            telemetry.addData("vo", vo);
            telemetry.update();
        }
    }
}
