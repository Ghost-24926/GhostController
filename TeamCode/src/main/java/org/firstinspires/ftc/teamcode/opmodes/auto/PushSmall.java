package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Короткий выезд вперёд на 300 мс мощностью 0.5, затем ожидание.
 * И в init, и после старта dpad вправо проигрывает raw-звук "kolya".
 */
@Autonomous
public class PushSmall extends LinearOpMode {
    DcMotor lf, rf, lb, rb, zov;
    /** Имя ресурса в res/raw, который ищем через getIdentifier. */
    String[] sounds = new String[1];
    /** Ставится в true колбэком, когда плеер закончил звук. */
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

        // До старта матча можно проверить звук, не трогая привод.
        while (opModeInInit()) {
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
                // Пауза, чтобы удержание кнопки не запускало звук каждый цикл.
                sleep(300);
            }
        }

        waitForStart();


        // Короткий толчок всеми колёсами вперёд.
        rf.setPower(0.5);
        lb.setPower(0.5);
        rb.setPower(0.5);
        lf.setPower(0.5);
        sleep(300);
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
