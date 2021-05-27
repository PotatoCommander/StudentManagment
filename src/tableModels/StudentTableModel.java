package tableModels;

import model.Student;

import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
/**
 *<strong>StudentTableModel</strong>
 * - extends {@link AbstractTableModel}.
 * Class for mapping user class {@link Student} on table rows.
 * @author Nikita Bodry
 * @version 1.0
 */
public class StudentTableModel extends AbstractTableModel
{
    private ArrayList<Student> students;
    private String[] columns;
    /**
     * <b>Constructor</b> for creating new TableModel.
     * Calls constructor of parent class.
     * Sets column names to hard coded in constructor
     */
    public StudentTableModel(ArrayList<Student> studentsList)
    {
        super();
        students = studentsList;
        columns = new String[]{"Имя", "Фамилия", "Отчество","Дата рождения", "Матем.", "Русский", "Физика", "Итог"};
    }
    /**
     * <b>Overrided</b> method getColumnCount.
     * @return count of columns.
     */
    @Override
    public int getColumnCount()
    {
        return columns.length;
    }
    /**
     * <b>Overrided</b> method getRowCount.
     * @return count of rows.
     */
    @Override
    public int getRowCount()
    {
        return students.size();
    }
    /**
     * <b>Overrided</b> method getValueAt.
     * @return object at [row, col] index
     * @param row
     *          index of row
     * @param col
     *          index of col
     */
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
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
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
    /**
     * <b>Overrided</b> method getColumnName.
     * @return columnt name at index.
     * @param column
     *          index of column.
     */
    @Override
    public String getColumnName(int column)
    {
        return columns[column];
    }

}
