package model.abstractions;

import model.Message;

public interface Observable
{
    void AddObserver(Observer observer);
    void RemoveObserver(Observer observer);
    void NotifyObservers(Message message);
}
