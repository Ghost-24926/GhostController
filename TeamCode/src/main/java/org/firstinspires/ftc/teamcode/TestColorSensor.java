package org.firstinspires.ftc.teamcode;
import static android.service.autofill.Validators.and;
import static com.sun.tools.doclint.Entity.and;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
@Disabled
public class TestColorSensor extends LinearOpMode {
    // Define a variable for our color sensor
    ColorSensor color;
    Servo S0;

    @Override
    public void runOpMode() {
// Get the color sensor from hardwareMap
        color = hardwareMap.get(ColorSensor.class, "Color");
        S0 = hardwareMap.get(Servo.class, "s0");

// Wait for the Play button to be pressed
        waitForStart();

// While the Op Mode is running, update the telemetry values
        while (opModeIsActive()) {
            if (color.red() > color.green()) {
                if (color.red() > color.blue()) {
                    S0.setPosition(0.25);
                }
            }

             if (color.blue() > color.red()){
                    if (color.blue() > color.green()){
                        S0.setPosition(0.5);
                    }
             }


            telemetry.addData("Red", color.red());
            telemetry.addData("Green", color.green());
            telemetry.addData("Blue", color.blue());
            telemetry.addData("servoPos", S0.getPosition());
            telemetry.update();
        }
    }
}