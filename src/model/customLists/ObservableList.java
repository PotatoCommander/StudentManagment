package model.customLists;
import model.abstractions.Observable;
import model.abstractions.Observer;
import model.enums.Actions;
import model.Message;

import java.util.ArrayList;
import java.util.Collection;

public class ObservableList<E> extends ArrayList<E> implements Observable
{
    private ArrayList<Observer> observers = new ArrayList<>();

    @Override
    public boolean add(E e)
    {
        var action = Actions.ITEM_ADDED;
        NotifyObservers(new Message(this,action,action.toString()));
        return super.add(e);
    }

    @Override
    public E remove(int index)
    {
        var action = Actions.ITEM_REMOVED;
        NotifyObservers(new Message(this,action,action.toString()));
        return super.remove(index);
    }

    @Override
    public E set(int index, E element)
    {
        var action = Actions.ITEM_REPLACED;
        NotifyObservers(new Message(this,action,action.toString()));
        return super.set(index, element);
    }

    @Override
    public void clear()
    {
        var action = Actions.LIST_CLEARED;
        NotifyObservers(new Message(this,Actions.LIST_CLEARED,action.toString()));
        super.clear();
    }

    @Override
    public boolean addAll(Collection<? extends E> c)
    {

        var action = Actions.ITEMS_ADDED;
        NotifyObservers(new Message(this,action,action.toString()));
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
    public void NotifyObservers(Message message)
    {
        if (observers != null)
        {
            for (Observer observer : observers)
            {
                observer.Update(message);
            }
        }
    }
}
