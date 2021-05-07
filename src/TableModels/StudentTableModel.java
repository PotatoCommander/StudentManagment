package TableModels;

import Model.Student;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class StudentTableModel extends AbstractTableModel
{
    private ArrayList<Student> students;
    private String[] columns;

    public StudentTableModel(ArrayList<Student> studentsList)
    {
        super();
        students = studentsList;
        columns = new String[]{"Имя", "Фамилия", "Отчество","Дата рождения", "Матем.", "Русский", "Физика", "Итог"};
    }

    @Override
    public int getColumnCount()
    {
        return columns.length;
    }

    @Override
    public int getRowCount()
    {
        return students.size();
    }
    @Override
    public Object getValueAt(int row, int col)
    {
        Student student = students.get(row);
        switch (col)
        {
            case 0:
                return student.FirstName;
            case 1:
                return student.LastName;
            case 2:
                return student.Patronymic;
            case 3:
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                return sdf.format(student.DateOfBirth.getTime());
            case 4:
                return student.MathScore;
            case 5:
                return student.RussianScore;
            case 6:
                return student.PhysicsScore;
            case 7:
                return student.MathScore+ student.PhysicsScore+ student.RussianScore;
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column)
    {
        return columns[column];
    }

}
