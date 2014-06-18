package com.shelloid.node.saal.android.phone;

import android.content.Context;

import com.shelloid.node.saal.common.Module;
import com.shelloid.node.saal.common.ShelloidActuator;

public class Log implements ShelloidActuator
{
    private Module module;

    public Log(Module module, Context ctx)
    {
        this.module = module;
    }

    @Override
    public Module getModule()
    {
        return module;
    }

    @Override
    public String getName()
    {
        return "log";
    }

    @Override
    public void setIoctl(String ctl, Object val)
    {
        throw new UnsupportedOperationException("You cant set any options for Log");
    }

    @Override
    public Object getIoctl(String ctl)
    {
        throw new UnsupportedOperationException("You cant get any options for Log");
    }

    @Override
    public boolean write(String channel, Object val)
    {
        System.out.println("Logging to " + channel + ": " + val.toString());
        return true;
    }

}
