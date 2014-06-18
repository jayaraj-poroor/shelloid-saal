package com.shelloid.node.saal.common;

public interface ShelloidActuator extends SensorActuator
{
    public boolean write (String ioname, Object val);
}
