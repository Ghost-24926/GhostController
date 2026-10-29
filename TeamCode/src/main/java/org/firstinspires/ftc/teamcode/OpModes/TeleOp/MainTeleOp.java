package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import static com.sun.tools.doclint.Entity.or;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.CalculateVelocity;
import org.firstinspires.ftc.teamcode.util.Matrix;
import org.firstinspires.ftc.teamcode.util.WheelVelocity;

@TeleOp
public class MainTeleOp extends LinearOpMode {
    DcMotor lf, rf, lb, rb;
    GoBildaPinpointDriver pc;
    Matrix matrix = new Matrix(4, 1);

    @Override
    public void runOpMode() throws InterruptedException {
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        pc = hardwareMap.get(GoBildaPinpointDriver.class, "pc");
        lf.setDirection(DcMotor.Direction.FORWARD);
        rf.setDirection(DcMotor.Direction.REVERSE);
        rb.setDirection(DcMotor.Direction.REVERSE);
        lb.setDirection(DcMotor.Direction.FORWARD);
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        telemetry.addLine("ya gotov sosat'");
        telemetry.update();
        CalculateVelocity velocity = new CalculateVelocity(pc, 0.024, 0.1, 0.1, 1, 1);
        WheelVelocity wheelVelocity = new WheelVelocity(20, 20, 0.053);


        waitForStart();

        while (opModeIsActive()) {
            float x = gamepad1.left_stick_x;
            float y = -gamepad1.left_stick_y;
            float r = -gamepad1.left_trigger + gamepad1.right_trigger;
            move(x, y, r);
            double vx = velocity.getVx();
            double vy = velocity.getVy();
            double vo = velocity.getOmega();
            matrix = wheelVelocity.omegasMat(vx, vy, vo);


            for (int i = 0; i < matrix.getMatrix().length; i++) {
                for (int j = 0; j < matrix.getMatrix()[i].length; j++) {
                    telemetry.addData(i + "" + j, matrix.getMatrix()[i][j]);
                }
            }

            telemetry.addData("vx", vx);
            telemetry.addData("vy", vy);
            telemetry.addData("vo", vo);
            telemetry.addData("r", 1 / wheelVelocity.r);
            telemetry.update();

        }

    }

    public void move(float x, float y, float r) {
        float lfPower = x + y + r;
        float rfPower = -x + y - r;
        float lbPower = -x + y + r;
        float rbPower = x + y - r;

        lf.setPower(lfPower);
        rf.setPower(rfPower);
        lb.setPower(lbPower);
        rb.setPower(rbPower);
    }
}
