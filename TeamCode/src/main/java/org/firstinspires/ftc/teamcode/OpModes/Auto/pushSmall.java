package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "5cm")
public class pushSmall extends LinearOpMode {
    DcMotor lf, rf, lb, rb;

    int rfpos;
    int rbpos;
    int lfpos;
    int lbpos;

    @Override
    public void runOpMode() throws InterruptedException {
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");

        rf.setDirection(DcMotorSimple.Direction.REVERSE);
        lf.setDirection(DcMotorSimple.Direction.REVERSE);

        lf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rb.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lfpos = 0;
        rfpos = 0;
        lbpos = 0;
        rbpos = 0;

        waitForStart();

        drive(300, 300, -300, -300, 0.5);

    }
    public void drive(int lftarg, int rftarg, int lbtarg, int rbtarg, double speed){
        lfpos += lftarg;
        rfpos += rftarg;
        lbpos += lbtarg;
        rbpos += rbtarg;

        lf.setTargetPosition(lftarg);
        rf.setTargetPosition(rftarg);
        lb.setTargetPosition(lbtarg);
        rb.setTargetPosition(rbtarg);

        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lb.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rb.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        lf.setPower(speed);
        rf.setPower(speed);
        lb.setPower(speed);
        rb.setPower(speed);

        while (opModeIsActive() && lf.isBusy() && rf.isBusy() && lb.isBusy() && rb.isBusy()){
            idle();
        }
    }

}
