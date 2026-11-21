package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanism.Base;
import org.firstinspires.ftc.teamcode.util.CalculateVelocity;
import org.firstinspires.ftc.teamcode.util.Matrix;
import org.firstinspires.ftc.teamcode.util.WheelVelocity;

@TeleOp
public class MainTeleOp extends LinearOpMode {

    private Base base = new Base();
    Matrix matrix = new Matrix(4, 1);
    GoBildaPinpointDriver pc;

    @Override
    public void runOpMode() throws InterruptedException {
        base.init(hardwareMap);

        pc = (GoBildaPinpointDriver) base.getDevice("pc");

        telemetry.addLine("ya gotov sosat'");
        telemetry.update();

        CalculateVelocity velocity = new CalculateVelocity(pc, 0.024, 0.025, 0.012, 1, -1);
        WheelVelocity wheelVelocity = new WheelVelocity(0.1675, 0.1225, 0.053);

        waitForStart();

        while (opModeIsActive()) {
            float x = gamepad1.left_stick_x;
            float y = -gamepad1.left_stick_y;
            float r = -gamepad1.left_trigger + gamepad1.right_trigger;

            base.move(x, y, r);

            double vx = velocity.getVx();
            double vy = velocity.getVy();
            double vo = velocity.getOmega();

            matrix = wheelVelocity.omegasMat(vx, vy, vo);

            telemetry.addData("vx", vx);
            telemetry.addData("vy", vy);
            telemetry.addData("vo", vo);
            telemetry.addData("r", 1 / wheelVelocity.r);
            telemetry.update();
        }
    }
}