package com.shelloid.node.saal;

public interface Actuator extends SensorActuator
{
    public boolean write (String ioname, Object val);
}
