package com.shelloid.node.saal.android.phone;

import java.security.InvalidParameterException;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.shelloid.node.saal.Module;
import com.shelloid.node.saal.NoDataAvailableException;
import com.shelloid.node.saal.ShelloidSensor;

public class GPS implements ShelloidSensor, LocationListener
{
    public static long DEFAULT_SAMPLING_PERIOD = 1000;
    public static long DELTA = 10;
    
    private static final String TAG = "GPS Sensor";
    private static final String provider = LocationManager.GPS_PROVIDER;
    private final Module module;
    private final LocationManager locationManager;
    private Location location;
    private boolean isStarted;
    private long samplingPeriod;
    private long samplingTs;
    
    public GPS(Module module, Context ctx)
    {
        samplingPeriod = DEFAULT_SAMPLING_PERIOD;
        isStarted = false;
        this.module = module;
        locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        location = locationManager.getLastKnownLocation(provider);
    }
    
    @Override
    public Module getModule()
    {
        return module;
    }

    @Override
    public String getName()
    {
        return "gps";
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
    public Object read(String componentName) throws NoDataAvailableException
    {
        if (componentName == null)
        {
            throw new InvalidParameterException("Requires a component (lat, long) to be specified");
        }else
        {
            long currentTs = System.currentTimeMillis();
            if( (currentTs - samplingTs) > (samplingPeriod+DELTA))
            {
                throw new NoDataAvailableException("No new GPS data available.");
            }
            if (componentName.equals("lat"))
            {
                return location.getLatitude();
            }
            else if (componentName.endsWith("long"))
            {
                return location.getLongitude();
            }
            else
            {
                throw new InvalidParameterException("A component name must be either lat or long");
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        samplingTs = System.currentTimeMillis();
        this.location = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) 
    {
        Log.w(TAG, "Status Changed for " + provider);
    }

    @Override
    public void onProviderEnabled(String provider) 
    {
        Log.w(TAG, "Provide " + provider + " Enabled.");
    }

    @Override
    public void onProviderDisabled(String provider) 
    {
        Log.w(TAG, "Provide " + provider + " Disabled.");
    }

    public void reset(long samplingPeriod)
    {
        this.samplingPeriod = samplingPeriod;
        stop();
        start();
    }
    
    @Override
    public void start()
    {   
        if (!isStarted)
        {
            isStarted = true;
            locationManager.requestLocationUpdates(provider, samplingPeriod, 1, this);
        }
    }

    @Override
    public void stop()
    {
        if (isStarted)
        {
            isStarted = false;
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public boolean isStarted()
    {
        return isStarted;
    }
}
