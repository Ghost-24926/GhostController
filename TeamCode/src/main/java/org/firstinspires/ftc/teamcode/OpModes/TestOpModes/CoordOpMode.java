package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.Matrix;

@TeleOp
public class CoordOpMode extends LinearOpMode {
    DcMotor rb, lb, rf, lf;
    Matrix matrix, matrix2;


    @Override
    public void runOpMode() throws InterruptedException {
        matrix = new Matrix(2,2);
        matrix2 = new Matrix(2,2);

        matrix.setMatrixElem(1,1,1);
        matrix.setMatrixElem(1,2,2);
        matrix.setMatrixElem(2,1,3);
        matrix.setMatrixElem(2,2,4);

        matrix2.setMatrixElem(1,1,5);
        matrix2.setMatrixElem(1,2,6);
        matrix2.setMatrixElem(2,1,7);
        matrix2.setMatrixElem(2,2,8);

        rb = hardwareMap.get(DcMotor.class, "rb");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lf = hardwareMap.get(DcMotor.class, "lf");

        matrix.multMatrixPerMatrix(matrix2);

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
            telemetry.addData("1,1", matrix.getMatrix()[0][0]);
            telemetry.addData("1,2", matrix.getMatrix()[0][1]);
            telemetry.addData("2,1", matrix.getMatrix()[1][0]);
            telemetry.addData("2,2", matrix.getMatrix()[1][1]);
            telemetry.update();
        }
    }
}
