package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.follower.Follower;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Настройки Pedro Pathing для этого робота: имена и направления моторов меканума
 * и параметры одометрии Pinpoint (смещения подов в дюймах).
 */
public class Constants {
    /** Должна собирать Follower из привода, локализатора и Foresight. */
    // БАГ: всегда возвращает null — любой код, который вызовет create() и обратится к Follower, упадёт с NullPointerException.
    public static Follower create(HardwareMap h) {
        // return new Follower(Drivetrain, Localizer, Foresight);
        return null;
    }
    /** Моторы шасси: имена в конфиге Driver Station и направления (правая сторона развёрнута). */
    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("lf");
        c.frontRightName.set("rf");
        c.backLeftName.set("lb");
        c.backRightName.set("rb");
        c.frontLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.frontRightDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backRightDirection.set(DcMotorSimple.Direction.REVERSE);
    });
    /** Pinpoint "pc": тип подов, смещения от центра и направления энкодеров. */
    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pc");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_SWINGARM_POD);
        c.xPodOffset.set(5.610532985897515);
        c.yPodOffset.set(5.164762481929749);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });
}