package uti;

import model.abstractions.Observable;
import model.abstractions.Observer;
import model.enums.Actions;
import model.enums.Sort;
import model.Message;
import model.Student;

import java.util.ArrayList;
import java.util.Comparator;

public class Sorter implements Observable
{
    ArrayList<Observer> observers;
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

    public static class ScoreSorter implements Comparator<Student>
    {
        @Override
        public int compare(Student o1, Student o2) {
            return Integer.compare(o2.getTotalScore(),o1.getTotalScore());
        }
    }
    public static class FirstNameSorter implements Comparator<Student>
    {
        @Override
        public int compare(Student o1, Student o2)
        {
            return o1.FirstName.compareTo(o2.FirstName);
        }
    }
    public static class LastNameSorter implements Comparator<Student>
    {
        @Override
        public int compare(Student o1, Student o2)
        {
            return o1.LastName.compareTo(o2.LastName);
        }
    }
    public static class PatronymicSorter implements Comparator<Student>
    {
        @Override
        public int compare(Student o1, Student o2) {
            return o1.Patronymic.compareTo(o2.Patronymic);
        }
    }
    public static class DateBirthSorter implements Comparator<Student>
    {
        @Override
        public int compare(Student o1, Student o2)
        {
            return o2.DateOfBirth.compareTo(o1.DateOfBirth);
        }
    }
    public  ArrayList<Student> SortBy(ArrayList<Student> students, Sort sortType)
    {
        Comparator<Student> comparator = switch (sortType)
                {
                    case SCORE -> new ScoreSorter();
                    case DATE_OF_BIRTH -> new DateBirthSorter();
                    case FIRST_NAME -> new FirstNameSorter();
                    case LAST_NAME -> new LastNameSorter();
                    case PATRONYMIC -> new PatronymicSorter();
                };
        students.sort(comparator);
        var action = Actions.LIST_SORTED;
        NotifyObservers(new Message(this, action, action.toString() + sortType.toString()));
        return students;
    }
}
