package org.firstinspires.ftc.teamcode.mechanism;

import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.List;
/*
Todo: выбрать название я препдпочитаю механизмы в фтц любят больше сабсистемы
Todo: сделать метод инициализации всего и лист в котором хранятся "Hardware Device"
Todo: сделать метод который периодически обновляется во время инита
Todo: переписать инициализацию таким образом
 */
public interface Mechanism {

    void init(HardwareMap hardwareMap);
    void initLoop();
    List<HardwareDevice> getDevices();

    HardwareDevice getDevice(String name);
}
