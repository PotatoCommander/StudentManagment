package model.customLists;
import model.abstractions.Observable;
import model.abstractions.Observer;
import model.enums.Actions;
import model.Message;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
/**
 *<strong>ObservableList</strong>
 *
 * <i> extends {@link ArrayList} and implements {@link Observable}</i>.
 * Contains overrided methods of built-in list with notifying observers if any action performed.
 * @author Nikita Bodry
 * @version 1.0
 */
public class ObservableList<E> extends ArrayList<E> implements Observable
{
    private ArrayList<Observer> observers = new ArrayList<>();

    @Override
    public boolean add(E e)
    {
        Actions action = Actions.ITEM_ADDED;
        NotifyObservers(new Message(this,action,action.toString()));
        return super.add(e);
    }

    @Override
    public E remove(int index)
    {
        Actions action = Actions.ITEM_REMOVED;
        NotifyObservers(new Message(this,action,action.toString()));
        return super.remove(index);
    }

    @Override
    public E set(int index, E element)
    {
        Actions action = Actions.ITEM_REPLACED;
        NotifyObservers(new Message(this,action,action.toString()));
        return super.set(index, element);
    }

    @Override
    public void clear()
    {
        Actions action = Actions.LIST_CLEARED;
        NotifyObservers(new Message(this,Actions.LIST_CLEARED,action.toString()));
        super.clear();
    }

    @Override
    public boolean addAll(Collection<? extends E> c)
    {

        Actions action = Actions.ITEMS_ADDED;
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
