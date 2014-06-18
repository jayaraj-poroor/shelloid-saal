package com.shelloid.node.saal.common;

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

    public Module findModuleWithAllSensorActuators(ArrayList<String> sensorActuators)
    {
        for (Module m : getAllModules())
        {
            boolean res = false;
            ArrayList<SensorActuator> all = new ArrayList<SensorActuator>();
            all.addAll(m.listSensors());
            all.addAll(m.listActuator());
            for (SensorActuator s : all)
            {
                int found = 0;
                for (String name : sensorActuators)
                {
                    if (s.getName().equals(name))
                    {
                        found++;
                    }
                }
                if (found == sensorActuators.size())
                {
                    res = true;
                    break;
                }
            }
            if (res == true)
            {
                return m;
            }
        }
        return null;
    }

    public SensorActuator findSensorActuatorWithName(String module, String subModule)
    {
        SensorActuator res = null;
        for (Module m : modules)
        {
            if (m.getName().equals(module))
            {
                ArrayList<SensorActuator> all = new ArrayList<SensorActuator>();
                all.addAll(m.listSensors());
                all.addAll(m.listActuator());
                for (SensorActuator s : all)
                {
                    boolean isEqual = s.getName().equals(subModule);
                    if (isEqual)
                    {
                        res = s;
                        break;
                    }
                }
            }
            if (res != null)
            {
                break;
            }
        }
        return res;
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
                    for (ShelloidActuator a : m.listActuator())
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
