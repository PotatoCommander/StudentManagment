package Util;

import Model.Enums.Sort;
import Model.Student;

import java.util.ArrayList;
import java.util.Comparator;

public class Sorter
{
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
            return o2.FirstName.compareTo(o1.FirstName);
        }
    }
    public static class LastNameSorter implements Comparator<Student>
    {
        @Override
        public int compare(Student o1, Student o2)
        {
            return o2.LastName.compareTo(o1.LastName);
        }
    }
    public static class PatronymicSorter implements Comparator<Student>
    {
        @Override
        public int compare(Student o1, Student o2) {
            return o2.Patronymic.compareTo(o1.Patronymic);
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
    public static ArrayList<Student> SortBy(ArrayList<Student> students, Sort sortType)
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
        return students;
    }
}
