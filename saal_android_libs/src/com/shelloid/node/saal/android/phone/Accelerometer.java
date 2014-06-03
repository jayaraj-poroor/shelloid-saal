package com.shelloid.node.saal.android.phone;

import java.security.InvalidParameterException;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.shelloid.node.saal.Module;
import com.shelloid.node.saal.ShelloidSensor;

public class Accelerometer implements ShelloidSensor, SensorEventListener
{
    private static final String TAG = "Accelerometer";
    private final Module module;
    private final SensorManager mSensorManager;
    private final Sensor mAccelerometer;
    private float x;
    private float y;
    private float z;
    private boolean isStarted;

    public Accelerometer(Module module, Context ctx)
    {
        this.module = module;
        mSensorManager = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public Module getModule()
    {
        return module;
    }

    @Override
    public String getName()
    {
        return "accelerometer";
    }

    @Override
    public void setIoctl(String ctl, Object val)
    {
        throw new UnsupportedOperationException("setIoctl is not suppoerted by GPS Module.");
    }

    @Override
    public Object getIoctl(String ctl)
    {
        throw new UnsupportedOperationException("getIoctl is not suppoerted by GPS Module.");
    }

    @Override
    public Object read(String componentName)
    {
        if (componentName == null)
        {
            throw new InvalidParameterException("Requires a component (x, y or z) to be specified");
        }
        else if (componentName.equals("x"))
        {
            return x;
        }
        else if (componentName.endsWith("y"))
        {
            return y;
        }
        else if (componentName.endsWith("z"))
        {
            return z;
        }
        else
        {
            throw new InvalidParameterException("A component name must be either x, y or z");
        }
    }

    @Override
    public void start() 
    {
        if (!isStarted)
        {
            isStarted = true;
            mSensorManager.registerListener(this, mAccelerometer, 1000);
        }
    }

    @Override
    public void stop() 
    {
        if (isStarted)
        {
            isStarted = false;
            mSensorManager.unregisterListener(this);
        }
    }
    
    @Override
    public void onSensorChanged(SensorEvent event)
    {
        this.x = event.values[0];
        this.y = event.values[1];
        this.z = event.values[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        Log.w(TAG, "Accuracy changed for " + getName() + " by " + accuracy);
    }

    @Override
    public boolean isStarted()
    {
        return isStarted;
    }
}
