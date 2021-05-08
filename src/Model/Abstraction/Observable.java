package Model.Abstraction;

public interface Observable
{
    void AddObserver(Observer observer);
    void RemoveObserver(Observer observer);
    void NotifyObservers();
    String getLastOperation();
}
