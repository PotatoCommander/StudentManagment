package model;

import model.abstractions.Observable;
import model.enums.Actions;

import java.util.Date;
/**
 *<strong>Message</strong>
 * - class contains info about performed action.
 * @author Nikita Bodry
 * @version 1.0
 */
public class Message
{
    private final Observable initiator;
    /**
     * Gets an initiator of action
     */
    public Observable getInitiator()
    {
        return initiator;
    }

    /**
     * Gets a time of action
     */
    private final Date actionTime;
    public Date getActionTime()
    {
        return actionTime;
    }

    private final Actions action;
    /**
     * Gets a type of action
     */
    public Actions getAction()
    {
        return action;
    }

    private final String info;
    /**
     * Gets a text info about action
     */
    public String getInfo()
    {
        return info;
    }
    /**
     * <b>Constructor</b> for creating new message
     * @param initiator
     *          initiator of action
     * @param action
     *          type of action (enum {@link Actions})
     * @param info
     *          text info about action
     */
    public Message(Observable initiator, Actions action, String info)
    {
        this.action = action;
        this.info = info;
        this.initiator = initiator;
        this.actionTime = new Date(System.currentTimeMillis());
    }
}
