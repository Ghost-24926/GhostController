package org.firstinspires.ftc.teamcode.mechanism;


import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;
import java.util.List;

public class Base implements Mechanism {

    private DcMotor lf, rf, lb, rb, zov;
    GoBildaPinpointDriver pc;

    List<HardwareDevice> devices = new ArrayList<>();

    @Override
    public void init(HardwareMap hardwareMap) {
        lf = hardwareMap.get(DcMotor.class, "lf");
        rf = hardwareMap.get(DcMotor.class, "rf");
        lb = hardwareMap.get(DcMotor.class, "lb");
        rb = hardwareMap.get(DcMotor.class, "rb");
        zov = hardwareMap.get(DcMotor.class, "zov");
        pc = hardwareMap.get(GoBildaPinpointDriver.class, "pc");
        lf.setDirection(DcMotor.Direction.FORWARD);
        rf.setDirection(DcMotor.Direction.REVERSE);
        rb.setDirection(DcMotor.Direction.REVERSE);
        lb.setDirection(DcMotor.Direction.FORWARD);

        devices.clear();

        devices.add(lf);
        devices.add(rf);
        devices.add(lb);
        devices.add(rb);
        devices.add(zov);
        devices.add(pc);
    }

    @Override
    public void initLoop() {
        pc.update();
    }

    public void move(double x, double y, double r) {
        double lfPower = x + y + r;
        double rfPower = -x + y - r;
        double lbPower = -x + y + r;
        double rbPower = x + y - r;

        lf.setPower(lfPower);
        rf.setPower(rfPower);
        lb.setPower(lbPower);
        rb.setPower(rbPower);
    }
    @Override
    public List<HardwareDevice> getDevices() {
        return devices;
    }
}