package com.shelloid.node.saal.android.modules;

import java.util.ArrayList;

import android.content.Context;
import android.content.pm.PackageManager;

import com.shelloid.node.saal.Actuator;
import com.shelloid.node.saal.Module;
import com.shelloid.node.saal.ShelloidSensor;
import com.shelloid.node.saal.android.phone.Accelerometer;
import com.shelloid.node.saal.android.phone.GPS;

public class PhoneModule implements Module
{
    ArrayList<ShelloidSensor> sensors;
    ArrayList<Actuator> actuators;
    
    public PhoneModule(Context ctx)
    {
        sensors = new ArrayList<ShelloidSensor>();
        actuators = new ArrayList<Actuator>();
        PackageManager packageManager = ctx.getPackageManager();
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS))
        {
            sensors.add(new GPS(this, ctx));
        }
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER))
        {
            sensors.add(new Accelerometer(this, ctx));
        }
        /* TODO: an so on... */
    }
    
    @Override
    public String getName()
    {
        return "phone";
    }

    @Override
    public ArrayList<ShelloidSensor> listSensors()
    {
        return sensors;
    }

    @Override
    public ArrayList<Actuator> listActuator()
    {
        return actuators;
    }

}
