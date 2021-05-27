package uti;

import model.abstractions.Observable;
import model.abstractions.Observer;
import model.enums.Actions;
import model.enums.Adapters;
import model.enums.Sort;
import model.Message;
import model.Student;

import java.awt.event.KeyAdapter;
import java.util.ArrayList;
import java.util.Comparator;
/**
 *<strong>Sorter</strong>
 * class implements {@link Observable} interface.
 * Contains method to sort list of students.
 * @author Nikita Bodry
 * @version 1.0
 */
public class Sorter implements Observable
{
    ArrayList<Observer> observers;
    /**
     *<i>Constructor to create Sorter</i>
     */
    public Sorter()
    {
        observers = new ArrayList<>();
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

    private static class ScoreSorter implements Comparator<Student>
    {
        @Override
        public int compare(Student o1, Student o2) {
            return Integer.compare(o2.getTotalScore(),o1.getTotalScore());
        }
    }
    private static class FirstNameSorter implements Comparator<Student>
    {
        @Override
        public int compare(Student o1, Student o2)
        {
            return o1.FirstName.compareTo(o2.FirstName);
        }
    }
    private static class LastNameSorter implements Comparator<Student>
    {
        @Override
        public int compare(Student o1, Student o2)
        {
            return o1.LastName.compareTo(o2.LastName);
        }
    }
    private static class PatronymicSorter implements Comparator<Student>
    {
        @Override
        public int compare(Student o1, Student o2) {
            return o1.Patronymic.compareTo(o2.Patronymic);
        }
    }
    private static class DateBirthSorter implements Comparator<Student>
    {
        @Override
        public int compare(Student o1, Student o2)
        {
            return o2.DateOfBirth.compareTo(o1.DateOfBirth);
        }
    }
    /**Returns adapter with restriction by number of digits.
     *
     * @return  {@link ArrayList} sorted by {@link Sort} enum value.
     * @param students
     *          Input list.
     * @param sortType
     *          Enum value - that show by what field list will be sorted.
     */
    public   ArrayList<Student> SortBy(ArrayList<Student> students, Sort sortType)
    {
        Comparator<Student> comparator;
        switch (sortType)
        {
            case SCORE:
                comparator = new ScoreSorter();
                break;
            case DATE_OF_BIRTH:
                comparator = new DateBirthSorter();
                break;
            case FIRST_NAME:
                comparator = new FirstNameSorter();
                break;
            case LAST_NAME:
                comparator = new LastNameSorter();
                break;
            case PATRONYMIC:
                comparator = new PatronymicSorter();
                break;
            default:
                throw new IllegalArgumentException();
        }
        students.sort(comparator);
        Actions action = Actions.LIST_SORTED;
        NotifyObservers(new Message(this, action, action.toString() + sortType.toString()));
        return students;
    }
}
