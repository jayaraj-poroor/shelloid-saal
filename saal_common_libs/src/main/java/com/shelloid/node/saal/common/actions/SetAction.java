package com.shelloid.node.saal.common.actions;

import com.shelloid.node.saal.common.ShelloidActuator;

public class SetAction
{
    public void set(ShelloidActuator a, String setting, Object val)
    {
        a.setIoctl(setting, val);
    }
}
