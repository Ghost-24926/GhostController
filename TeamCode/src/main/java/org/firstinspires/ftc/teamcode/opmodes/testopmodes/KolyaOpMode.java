package org.firstinspires.ftc.teamcode.opmodes.testopmodes;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/** Тест звука: после старта dpad вправо проигрывает res/raw/kolya один раз за нажатие. */
@Autonomous
public class KolyaOpMode extends LinearOpMode {
    String[] sounds = new String[1];
    /** Колбэк плеера ставит true, когда воспроизведение завершилось. */
    boolean playing = false;

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

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
    }
}
