package com.shelloid.node.saal.common.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Queue;

import com.shelloid.common.messages.MessageFields;
import com.shelloid.common.messages.MessageTypes;
import com.shelloid.common.messages.ShelloidMessage;
import com.shelloid.query.vo.Literal;
import com.shelloid.querylib.TimedBuffer;
import com.shelloid.querylib.TimedBufferFactory;

public class LogAction
{
    private static final String TAG = "LogAction";
    private static LogAction logAction;
    private final TimedBufferFactory<LogEntry> fact;

    private LogAction(File filesDir)
    {
        TimedBufferFactory.configure(filesDir);
        fact = TimedBufferFactory.getInstance(LogEntry.class);
    }

    @Override
    public void finalize() throws Throwable
    {
        super.finalize();
    }

    public boolean log(long currentTs, Object[] params, String streamId, HashMap<String, Literal> vars)
    {
        boolean res = false;
        try
        {
            TimedBuffer<LogEntry> buf = fact.getBuffer(streamId + ".stream");
            long expiryTime = 0;
            HashMap<String, Literal> loggableVars;
            if (params.length > 0)
            {
                int startIdx = 0;
                if (params[0] instanceof Long)
                {
                    expiryTime = (Long) params[0];
                    startIdx = 1;
                }
                if (params.length > startIdx)
                {
                    loggableVars = new HashMap<String, Literal>();
                    while (startIdx < params.length)
                    {
                        String key = params[startIdx].toString();
                        if (vars.containsKey(key))
                        {
                            loggableVars.put(key, vars.get(key));
                        }
                        startIdx++;
                    }
                }
                else
                {
                    loggableVars = vars;
                }
            }
            else
            {
                loggableVars = vars;
            }
            res = buf.addItem(currentTs, expiryTime, new LogEntry(loggableVars));
            buf.cleanup();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return res;
    }

    public ShelloidMessage getMessages(String streamId)
    {
        TimedBuffer<LogEntry> buf = fact.getBuffer(streamId + ".stream");
        Queue<LogEntry> msgs = buf.getItems();
        ShelloidMessage msg = new ShelloidMessage();
        msg.put(MessageFields.type, MessageTypes.STREAM_MSG);
        msg.put(MessageFields.streamId, streamId);
        msg.put(MessageFields.subType, MessageTypes.STREAM_LOG);
        ArrayList<HashMap<String, Object>> msgsArray = new ArrayList<HashMap<String, Object>>();
        while (msgs.peek() != null)
        {
            LogEntry entry = msgs.remove();
            HashMap<String, Literal> emsg = entry.msg;
            HashMap<String, String> logs = new HashMap<String, String>();
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put(MessageFields.timestamp, entry.timestamp + "");
            for (Entry<String, Literal> e : emsg.entrySet())
            {
                String val = "";
                Literal l = e.getValue();
                switch (l.kind)
                {
                    case Literal.DOUBLE:
                    {
                        val = l.dVal + "";
                        break;
                    }
                    case Literal.STRING:
                    {
                        val = l.sVal;
                        break;
                    }
                    case Literal.LONG:
                    {
                        val = l.lVal + "";
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
                logs.put(e.getKey(), val);
            }
            map.put(MessageFields.msg, logs);

            msgsArray.add(map);
        }
        msg.put(MessageFields.msg, msgsArray.toArray());
        return msg;
    }

    public static LogAction getInstance(File baseDir)
    {
        if (logAction == null)
        {
            logAction = new LogAction(baseDir);
        }
        return logAction;
    }
}
