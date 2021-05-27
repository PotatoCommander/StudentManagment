package DAL;

import model.abstractions.Observable;
import model.abstractions.Observer;
import model.enums.Actions;
import model.customLists.ObservableList;
import model.Message;
import model.Student;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
/**
 *<strong>CSVRepository</strong>
 *
 * <i>contains CRUD methods for operating CSV data from file</i>
 *
 * @author Nikita Bodry
 * @version 1.0
 */
public class StudentCSVRepository implements Observable
{
    private ArrayList<Observer> observers;
    private String filePath;
    SimpleDateFormat sdf;
    /**
     * Set the file path.
     * <b>Use this method before all CRUD methods if filepath changed</b>
     *
     */
    public void setFilePath(String path)
    {
        this.filePath = path;
    }
    private String[] header = {"FirstName", "LastName", "Patronymic","DateOfBirth", "MathScore", "RussianScore", "PhysicsScore"};
    /**
     *<i>Constructor to create CSVRepository - data access layer with CRUD methods</i>
     */
    public StudentCSVRepository()
    {
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        observers = new ArrayList<>();
        filePath = null;
    }
    /**Returns a List of objects extracted from CSV file by filepath
     *
     * @return  An array of objects.Type of list is {@link ObservableList}.
     *
     */
    public ObservableList<Student> GetAll()
    {
        Actions action = null;
        ObservableList students = new ObservableList<Student>();
        try
        {
            Reader reader = Files.newBufferedReader(Paths.get(filePath));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withHeader(header)
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());

            for (CSVRecord csvRecord : csvParser)
            {
                String firstName = csvRecord.get("FirstName");
                String lastName = csvRecord.get("LastName");
                String patronymic = csvRecord.get("Patronymic");
                String dateOfBirth = csvRecord.get("DateOfBirth");
                int mathScore = Integer.parseInt(csvRecord.get("MathScore"));
                int russianScore = Integer.parseInt(csvRecord.get("RussianScore"));
                int physicsScore = Integer.parseInt(csvRecord.get("PhysicsScore"));

                Student student = new Student();

                student.FirstName = firstName;
                student.LastName = lastName;
                student.Patronymic = patronymic;
                student.MathScore = mathScore;
                student.RussianScore = russianScore;
                student.PhysicsScore = physicsScore;
                student.DateOfBirth.setTime(sdf.parse(dateOfBirth));

                students.add(student);
            }
            action = Actions.FILE_OPENED;
        }
        catch (IOException | ParseException e)
        {
            action = Actions.FAIL_OPEN_FILE;
            e.printStackTrace();
        }
        finally
        {
            NotifyObservers(new Message(this, action, action.toString() + filePath));
        }
        return students;
    }
    /**Saving a list of items in file
     * @param   items
     *          A list of items to save.
     * @return  Boolean = True if file was successfully saved.
     *          Boolean = False - in case of fail.
     */
    public boolean Save(ObservableList<Student> items)
    {
        Actions action = null;
        try
        {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(header));
            for (Student item : items)
            {
                csvPrinter.printRecord(item.FirstName, item.LastName, item.Patronymic,
                        sdf.format(item.DateOfBirth.getTime()),
                        item.MathScore, item.PhysicsScore, item.RussianScore);
            }
            csvPrinter.flush();
            action = Actions.FILE_SAVED;
            return true;
        }
        catch (IOException e)
        {
            action = Actions.FAIL_SAVE_FILE;
            System.out.println(e.getMessage());
            return false;
        }
        finally
        {
            NotifyObservers(new Message(this, action, action.toString() + filePath));
        }
    }
    /**Saving a list of items in file by path.
     * If file not exist create new.
     * @param   items
     *          A list of items to save.
     * @return Boolean = {@code true} if file was successfully saved.
     *         Boolean = {@code false} - in case of fail.
     */
    public boolean SaveAs(ObservableList<Student> items)
    {
        File file = new File(filePath);
        if (!isFileExist(file))
        {
            if(!CreateFile())
            {
                return false;
            }
        }
        return Save(items);
    }
    /**Creating file by a path.
     * @return  Boolean = {@code true} if file was successfully created.
     *          Boolean = {@code false} - in case of fail.
     *
     */
    public boolean CreateFile()
    {
        Actions action = null;
        try
        {
            File file = new File(filePath);
            boolean result = file.createNewFile();
            if (result)
            {
                action = Actions.FILE_CREATED;
            }
            else action = Actions.FAIL_NEW_FILE;
            return result;
        }
        catch (IOException e)
        {
            action = Actions.FAIL_NEW_FILE;
            System.out.println(e.getMessage());
            return false;
        }
        finally
        {
            NotifyObservers(new Message(this, action, action.toString() + filePath));
        }
    }
    private boolean isFileExist(File file)
    {
        return file.exists() && !file.isDirectory();
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
