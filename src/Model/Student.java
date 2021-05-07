package Model;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

public class Student
{
    public String FirstName;
    public String LastName;
    public String Patronymic;
    public Calendar DateOfBirth;
    public int MathScore;
    public int RussianScore;
    public int PhysicsScore;
    public Student()
    {
        DateOfBirth = new GregorianCalendar();
    }
    public int getTotalScore()
    {
        return MathScore + RussianScore + PhysicsScore;
    }
}

