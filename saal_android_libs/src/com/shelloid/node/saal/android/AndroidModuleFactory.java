package com.shelloid.node.saal.android;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;

import com.shelloid.node.saal.android.modules.PhoneModule;
import com.shelloid.node.saal.common.ModuleFactory;

public class AndroidModuleFactory extends ModuleFactory
{
    private static AndroidModuleFactory factory = new AndroidModuleFactory();
    private static final String TAG = "AndroidModuleFactory";
    
    private AndroidModuleFactory()
    {
        
    }

    public static ModuleFactory getInstance()
    {
        return factory;
    }

    @Override
    public void onDeviceChanged(Object device) throws Exception
    {
        ParcelUuid[] uuids = ((BluetoothDevice)device).getUuids();
        for (ParcelUuid uuid : uuids)
        {
            Log.d(TAG, "UUID: " + uuid.getUuid().toString());
            /* TODO: use this UUID's to load modules. */
        }
    }
    
    @Override
    public void updateCurrentDeviceInfo(Object ctx)
    {
        PhoneModule module = new PhoneModule((Context)ctx);
        modules.add(module);
    }
}
