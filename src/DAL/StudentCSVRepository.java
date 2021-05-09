package DAL;

import Model.Abstraction.Observable;
import Model.Abstraction.Observer;
import Model.Enums.Actions;
import Model.CustomLists.ObservableList;
import Model.Message;
import Model.Student;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class StudentCSVRepository implements Observable
{
    private ArrayList<Observer> observers;
    private String filePath;
    SimpleDateFormat sdf;
    public void setFilePath(String path)
    {
        this.filePath = path;
    }
    private String[] header = {"FirstName", "LastName", "Patronymic","DateOfBirth", "MathScore", "RussianScore", "PhysicsScore"};

    public StudentCSVRepository()
    {
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        observers = new ArrayList<>();
        filePath = null;
    }

    public ObservableList<Student> GetAll()
    {
        Actions action = null;
        var students = new ObservableList<Student>();
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
                var firstName = csvRecord.get("FirstName");
                var lastName = csvRecord.get("LastName");
                var patronymic = csvRecord.get("Patronymic");
                var dateOfBirth = csvRecord.get("DateOfBirth");
                var mathScore = Integer.parseInt(csvRecord.get("MathScore"));
                var russianScore = Integer.parseInt(csvRecord.get("RussianScore"));
                var physicsScore = Integer.parseInt(csvRecord.get("PhysicsScore"));

                var student = new Student();

                student.FirstName = firstName;
                student.LastName = lastName;
                student.Patronymic = patronymic;
                student.MathScore = mathScore;
                student.RussianScore = russianScore;
                student.PhysicsScore = physicsScore;
                student.DateOfBirth.setTime(sdf.parse(dateOfBirth));

                students.add(student);
            }
            action = Actions.OPENED_FILE;
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
            action = Actions.SAVED_FILE;
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
    public boolean CreateFile()
    {
        Actions action = null;
        try
        {
            File file = new File(filePath);
            var result = file.createNewFile();
            if (result)
            {
                action = Actions.CREATED_FILE;
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
        return file.exists() || !file.isDirectory();
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
