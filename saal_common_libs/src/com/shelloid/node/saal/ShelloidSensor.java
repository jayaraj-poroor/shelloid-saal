package com.shelloid.node.saal;

public interface ShelloidSensor extends SensorActuator
{
    public Object read(String componentName) throws NoDataAvailableException;
    public void start();
    public void stop();
    public boolean isStarted();
}
