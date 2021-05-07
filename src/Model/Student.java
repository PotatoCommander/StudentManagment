package Model;

import java.util.UUID;

public class Student
{
    public String FirstName;
    public String LastName;
    public String Patronymic;
    public int MathScore;
    public int RussianScore;
    public int PhysicsScore;
    public int getTotalScore()
    {
        return MathScore + RussianScore + PhysicsScore;
    }
}

