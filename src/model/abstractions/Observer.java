package model.abstractions;

import model.Message;

public interface Observer
{
    /**Method contains logic of observer object when observable fire event
     * @param   message
     *          Message from observable object.
     */
    void Update(Message message);
}
