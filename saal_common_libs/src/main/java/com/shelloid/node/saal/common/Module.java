package com.shelloid.node.saal.common;

import java.util.ArrayList;

public interface Module
{
    public String getName();
    public ArrayList<ShelloidSensor> listSensors();
    public ArrayList<ShelloidActuator> listActuator();
    
    public SensorActuator getSensorActuator(String name);
}
