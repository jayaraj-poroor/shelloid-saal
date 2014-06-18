package com.shelloid.node.saal.android.modules;

import java.util.ArrayList;

import android.content.Context;
import android.content.pm.PackageManager;

import com.shelloid.node.saal.android.phone.Accelerometer;
import com.shelloid.node.saal.android.phone.GPS;
import com.shelloid.node.saal.android.phone.Log;
import com.shelloid.node.saal.common.SensorActuator;
import com.shelloid.node.saal.common.ShelloidActuator;
import com.shelloid.node.saal.common.Module;
import com.shelloid.node.saal.common.ShelloidSensor;

public class PhoneModule implements Module
{
    ArrayList<ShelloidSensor> sensors;
    ArrayList<ShelloidActuator> actuators;
    
    public PhoneModule(Context ctx)
    {
        sensors = new ArrayList<ShelloidSensor>();
        actuators = new ArrayList<ShelloidActuator>();
        PackageManager packageManager = ctx.getPackageManager();
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS))
        {
            sensors.add(new GPS(this, ctx));
        }
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER))
        {
            sensors.add(new Accelerometer(this, ctx));
        }
        actuators.add(new Log(this, ctx));
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
    public ArrayList<ShelloidActuator> listActuator()
    {
        return actuators;
    }

    @Override
    public SensorActuator getSensorActuator(String name)
    {
        for (ShelloidSensor s : sensors)
        {
            if (s.getName().equals(name))
            {
                return s;
            }
        }
        for (ShelloidActuator a : actuators)
        {
            if (a.getName().equals(name))
            {
                return a;
            }
        }
        return null;
    }

}
