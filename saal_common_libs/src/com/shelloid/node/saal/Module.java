package com.shelloid.node.saal;

import java.util.ArrayList;

public interface Module
{
    public String getName();
    public ArrayList<ShelloidSensor> listSensors();
    public ArrayList<Actuator> listActuator();
}
