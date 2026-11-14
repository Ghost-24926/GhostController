package org.firstinspires.ftc.teamcode.OpModes.Auto;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
@Autonomous
public class pushSmall extends LinearOpMode {
    DcMotor lf, rf, lb, rb, zov;
    String[] sounds = new String[1];
    boolean playing = false;

    @Override
    public void runOpMode() throws InterruptedException {
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        zov = hardwareMap.get(DcMotor.class, "zov");
        lf.setDirection(DcMotor.Direction.FORWARD);
        rf.setDirection(DcMotor.Direction.REVERSE);
        rb.setDirection(DcMotor.Direction.REVERSE);
        lb.setDirection(DcMotor.Direction.FORWARD);
        lf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rb.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        waitForStart();

        zov.setPower(-0.8);
        sleep(300);
        zov.setPower(0);

        rf.setPower(0.5);
        lb.setPower(0.5);
        rb.setPower(0.5);
        lf.setPower(0.5);
        sleep(500);
        rf.setPower(0);
        lb.setPower(0);
        rb.setPower(0);
        lf.setPower(0);
        while (opModeIsActive()) {
            sounds[0] = "kolya";
            SoundPlayer.PlaySoundParams par = new SoundPlayer.PlaySoundParams();
            par.loopControl = 0;
            int a = this.hardwareMap.appContext.getResources().getIdentifier(sounds[0], "raw", hardwareMap.appContext.getPackageName());
            playing = false;
            if (gamepad1.dpad_right) {
                SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, a, par, null, new Runnable() {
                    @Override
                    public void run() {
                        playing = true;
                    }
                });
                sleep(300);
            }
        }

        telemetry.update();
    }
}
