package com.shelloid.node.saal;

import java.util.ArrayList;

public abstract class ModuleFactory
{
    public enum By
    {
        SENSOR, ACTUATOR
    }
    protected final ArrayList<Module> modules;

    protected ModuleFactory()
    {
        modules = new ArrayList<Module>();
    }

    public ArrayList<Module> getAllModules()
    {
        return modules;
    }

    public Module getModule(String moduleName)
    {
        for (Module m : modules)
        {
            if (m.getName().equals(moduleName))
            {
                return m;
            }
        }
        return null;
    }
    
    public abstract void updateCurrentDeviceInfo(Object params);
    public abstract void onDeviceChanged(Object device) throws Exception;

    public Module getModule(By by, String name)
    {
        /* node.sensors.get("accelerometer"), here by = sensor, name = "accelerometer" */
        for (Module m : modules)
        {
            switch (by)
            {
                case ACTUATOR:
                {
                    for (Actuator a : m.listActuator())
                    {
                        if (a.getName().equals(name))
                        {
                            return m;
                        }
                    }
                    break;
                }
                case SENSOR:
                {
                    for (ShelloidSensor s : m.listSensors())
                    {
                        if (s.getName().equals(name))
                        {
                            return m;
                        }
                    }
                    break;
                }
                default:
                {
                    break;
                }
            }
        }
        return null;
    }
}
