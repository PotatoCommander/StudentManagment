package Model.CustomLists;
import Model.Abstraction.Observable;
import Model.Abstraction.Observer;

import java.util.ArrayList;
import java.util.Collection;

public class ObservableList<E> extends ArrayList<E> implements Observable
{
    private ArrayList<Observer> observers = new ArrayList<>();
    @Override
    public boolean add(E e)
    {
        NotifyObservers();
        return super.add(e);
    }

    @Override
    public E remove(int index)
    {
        NotifyObservers();
        return super.remove(index);
    }

    @Override
    public E set(int index, E element)
    {
        NotifyObservers();
        return super.set(index, element);
    }

    @Override
    public void clear()
    {
        NotifyObservers();
        super.clear();
    }

    @Override
    public boolean addAll(Collection<? extends E> c)
    {
        NotifyObservers();
        return super.addAll(c);
    }

    @Override
    public void AddObserver(Observer observer)
    {
        observers.add(observer);
    }

    @Override
    public void RemoveObserver(Observer observer)
    {
        observers.remove(observer);
    }

    @Override
    public void NotifyObservers()
    {
        if (observers != null)
        {
            for (Observer observer : observers)
            {
                observer.Update(this);
            }
        }
    }
}
