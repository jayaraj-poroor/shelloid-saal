package com.shelloid.node.saal;

public interface SensorActuator
{
    public Module getModule();
    public String getName();
    public void setIoctl (String ctl, Object val);
    public Object getIoctl(String ctl);
}
