package com.shelloid.node.saal.common;

import com.shelloid.node.saal.common.exceptions.NoDataAvailableException;

public interface ShelloidSensor extends SensorActuator
{
    public Object read(String componentName) throws NoDataAvailableException;
    public void start();
    public void stop();
    public boolean isStarted();
}
