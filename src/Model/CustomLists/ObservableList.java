package Model.CustomLists;
import Model.Abstraction.Observable;
import Model.Abstraction.Observer;

import java.util.ArrayList;
import java.util.Collection;

public class ObservableList<E> extends ArrayList<E> implements Observable
{
    private ArrayList<Observer> observers = new ArrayList<>();
    private String lastOperation;

    @Override
    public String getLastOperation()
    {
        return lastOperation;
    }

    @Override
    public boolean add(E e)
    {
        lastOperation = "Добавлен объект";
        NotifyObservers();
        return super.add(e);
    }

    @Override
    public E remove(int index)
    {
        lastOperation = String.format("Удален {0} объект", index);
        NotifyObservers();
        return super.remove(index);
    }

    @Override
    public E set(int index, E element)
    {
        lastOperation = String.format("Изменен {0} объект", index);
        NotifyObservers();
        return super.set(index, element);
    }

    @Override
    public void clear()
    {
        lastOperation = null;
        NotifyObservers();
        super.clear();
    }

    @Override
    public boolean addAll(Collection<? extends E> c)
    {
        lastOperation = null;
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
