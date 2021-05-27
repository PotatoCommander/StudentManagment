package model;

import model.enums.Actions;

import java.util.Calendar;
import java.util.GregorianCalendar;
/**
 *<strong>Student</strong>
 * - domain model of student.
 * @author Nikita Bodry
 * @version 1.0
 */
public class Student
{
    public String FirstName;
    public String LastName;
    public String Patronymic;
    public Calendar DateOfBirth;
    public int MathScore;
    public int RussianScore;
    public int PhysicsScore;
    /**
     * <b>Constructor</b> for creating new student.
     */
    public Student()
    {
        DateOfBirth = new GregorianCalendar();
    }
    /**
     * Get sum of scores by exams.
     * @return sum of math score, physics score and russian score
     */
    public int getTotalScore()
    {
        return MathScore + RussianScore + PhysicsScore;
    }
}

