package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import static com.sun.tools.doclint.Entity.or;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.util.CalculateVelocity;
import org.firstinspires.ftc.teamcode.util.Matrix;

@TeleOp
public class MainTeleOp extends LinearOpMode{
    DcMotor lf, rf, lb, rb;
    GoBildaPinpointDriver pc;
    Matrix matrix1 = new Matrix(4,3), matrix2 = new Matrix(3,1);

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
        matrix1.setMatrixElem(1,1,1);
        matrix1.setMatrixElem(1,2,2);
        matrix1.setMatrixElem(1,3,3);
        matrix1.setMatrixElem(2,1,4);
        matrix1.setMatrixElem(2,2,5);
        matrix1.setMatrixElem(2,3,6);
        matrix1.setMatrixElem(3,1,7);
        matrix1.setMatrixElem(3,2,8);
        matrix1.setMatrixElem(3,3,9);
        matrix1.setMatrixElem(4,1,10);
        matrix1.setMatrixElem(4,2,11);
        matrix1.setMatrixElem(4,3,12);
        matrix2.setMatrixElem(1,1,2);
        matrix2.setMatrixElem(2,1,6);
        matrix2.setMatrixElem(3,1,7);
        matrix1.multMatrixPerMatrix(matrix2);

        telemetry.addLine("ya gotov sosat'");
        telemetry.update();

        waitForStart();

        while(opModeIsActive()){
            float x = gamepad1.left_stick_x;
            float y = -gamepad1.left_stick_y;
            float r = -gamepad1.left_trigger + gamepad1.right_trigger;
            move(x, y, r);

            for (int i = 0; i < matrix1.getMatrix().length; i++){
                for (int j = 0; j < matrix1.getMatrix()[i].length; j++){
                    telemetry.addData(i + "" + j, matrix1.getMatrix()[i][j]);
                }
            }

            CalculateVelocity velocity = new CalculateVelocity(pc, 0.024, 0.1, 0.1, 1, 1);
            double vx = velocity.getVx();
            double vy = velocity.getVy();
            double vo = velocity.getOmega();
            telemetry.addData("vx", vx);
            telemetry.addData("vy", vy);
            telemetry.addData("vo", vo);
            telemetry.update();

        }

    }
    public void move(float x, float y, float r){
        float lfPower = x+y+r;
        float rfPower = -x+y-r;
        float lbPower = -x+y+r;
        float rbPower = x+y-r;

        lf.setPower(lfPower);
        rf.setPower(rfPower);
        lb.setPower(lbPower);
        rb.setPower(rbPower);
    }
}
