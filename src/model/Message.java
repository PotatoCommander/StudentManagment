package model;

import model.abstractions.Observable;
import model.enums.Actions;

import java.util.Date;

public class Message
{
    private final Observable initiator;
    public Observable getInitiator()
    {
        return initiator;
    }

    private final Date actionTime;
    public Date getActionTime()
    {
        return actionTime;
    }

    private final Actions action;
    public Actions getAction()
    {
        return action;
    }

    private final String info;
    public String getInfo()
    {
        return info;
    }

    public Message(Observable initiator, Actions action, String info)
    {
        this.action = action;
        this.info = info;
        this.initiator = initiator;
        this.actionTime = new Date(System.currentTimeMillis());
    }
}
