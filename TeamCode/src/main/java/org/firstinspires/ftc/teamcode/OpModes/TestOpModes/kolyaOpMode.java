package org.firstinspires.ftc.teamcode.OpModes.TestOpModes;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class kolyaOpMode extends LinearOpMode {
    String[] sounds = new String[1];
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
