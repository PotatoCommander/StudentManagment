package model.abstractions;

import model.Message;

import java.io.IOException;

public interface Observable
{

    /**Method for adding a observer to observable object
     * @param   observer
     *          Observer to add.
     */
    void AddObserver(Observer observer);
    /**Method for removing a observer from observable object
     * @param   observer
     *          Observer to remove.
     */
    void RemoveObserver(Observer observer);
    /**Method for notifying observers of observable object
     * @param   message
     *          Message to send for observers
     */
    void NotifyObservers(Message message);
}
