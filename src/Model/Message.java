package Model;

import Model.Abstraction.Observable;
import Model.Enums.Actions;

import java.util.Date;

public class Message
{
    private Observable initiator;
    public Observable getInitiator()
    {
        return initiator;
    }

    private Date actionTime;
    public Date getActionTime()
    {
        return actionTime;
    }

    private Actions action;
    public Actions getAction()
    {
        return action;
    }
    private String info;
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
