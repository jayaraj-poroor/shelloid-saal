package com.shelloid.node.saal.common.actions;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import com.shelloid.query.vo.Literal;

public class LogEntry implements Serializable, Comparable<LogEntry>
{
    private static final long serialVersionUID = 1L;
    public long timestamp;
    public HashMap<String, Literal> msg;
   
    public LogEntry()
    {
        msg = null;
        timestamp = 0;
    }
    
    public LogEntry(HashMap<String, Literal> msg)
    {
        this.timestamp = new Date().getTime();
        this.msg = msg;
    }

    @Override
    public int compareTo(LogEntry o)
    {
        return (int)(timestamp - o.timestamp);
    }
}
