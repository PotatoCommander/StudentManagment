package Util;

import Model.Student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Sorter
{
    public static class ScoreSorter implements Comparator<Student>
    {
        @Override
        public int compare(Student o1, Student o2) {
            return Integer.compare(o2.getTotalScore(),o1.getTotalScore());
        }
    }
    public static ArrayList<Student> SortByScore(ArrayList<Student> students)
    {
        students.sort(new ScoreSorter());
        return students;
    }
}
