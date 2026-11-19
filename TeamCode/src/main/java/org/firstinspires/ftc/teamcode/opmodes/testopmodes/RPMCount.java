package org.firstinspires.ftc.teamcode.opmodes.testopmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

/**
 * Проверка перевода об/мин в тики/с.
 * Пока зажата A, мотор крутится на 4000 об/мин через setVelocity.
 * В телеметрии сырая скорость SDK и те же тики, переведённые обратно в об/мин (28 тиков на оборот).
 */
@TeleOp
public class RPMCount extends LinearOpMode {
    DcMotorEx siska;


    @Override
    public void runOpMode() throws InterruptedException {
        siska = hardwareMap.get(DcMotorEx.class, "siska");
        siska.setDirection(DcMotorEx.Direction.FORWARD);
        siska.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        siska.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        while(opModeIsActive()){
            if (gamepad1.a){
                // 4000 об/мин · 28 тиков/об / 60 с = тики в секунду для setVelocity.
                siska.setVelocity((double) (4000 * 28) /60);
            }
            else{
                siska.setVelocity(0);
            }

            telemetry.addData("velocity", siska.getVelocity());
            telemetry.addData("rpm", siska.getVelocity()/28*60);
            telemetry.update();
        }
    }
}
