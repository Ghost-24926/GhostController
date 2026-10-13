package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class MainTeleOp extends LinearOpMode{
    DcMotor lf, rf, lb, rb;
    GoBildaPinpointDriver pc;

    @Override
    public void runOpMode() throws InterruptedException {
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        pc = hardwareMap.get(GoBildaPinpointDriver.class, "pc");
        lf.setDirection(DcMotor.Direction.REVERSE);
        rf.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addLine("ya gotov sosat'");
        telemetry.update();

        waitForStart();

        while(opModeIsActive()){
            float x = -gamepad1.left_stick_x;
            float y = -gamepad1.left_stick_y;
            float r = -gamepad1.left_trigger + gamepad1.right_trigger;
            move(x, y, r);
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
