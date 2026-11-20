package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.pedropathing.tuning.autotune.Procedure;
import com.pedropathing.tuning.autotune.Tuner;

import org.firstinspires.ftc.teamcode.pedro.procedures.ForesightTuner;
import org.firstinspires.ftc.teamcode.pedro.procedures.MecanumTuner;
import org.firstinspires.ftc.teamcode.pedro.procedures.PinpointTuner;
import org.firstinspires.ftc.teamcode.pedro.procedures.Tests;


/** Список процедур автотюнинга Pedro: каждый метод с @Tuner появляется в меню тюнера. */
public class Tuning {
    /** Проверка направлений моторов меканума. */
    @Tuner
    public static Procedure mecanumTuner() {
        return new MecanumTuner();
    }
    /** Набор тестовых заездов (hold, line, curve, локализация и т. д.). */
    // БАГ: localizerFunction и algorithmSupplier переданы как null. Тесты, которым нужен локализатор
    // или Follower (localization, odometry, pose, hold, line, curve...), упадут с NullPointerException.
    // Нужно передать hardwareMap -> new PinpointLocalizer(hardwareMap, Constants.localizerConfig) и алгоритм.
    @Tuner
    public static Procedure tests() {
        return new Tests(hardwareMap -> new Mecanum(hardwareMap, Constants.drivetrainConfig), null, null);
    }
    /** Калибровка Pinpoint: масштаб подов, направления и смещения. */
    @Tuner
    public static Procedure pinpointTuner() {
        return new PinpointTuner();
    }
    /** Подбор скоростей, торможения и коэффициентов Foresight на Pinpoint + меканум. */
    @Tuner
    public static Procedure foresightTuner() {
        return new ForesightTuner((hardwareMap) -> new PinpointLocalizer(hardwareMap, Constants.localizerConfig), (hardwareMap) -> new Mecanum(hardwareMap, Constants.drivetrainConfig));
    }
}