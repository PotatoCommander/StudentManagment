package DAL;

import Model.Student;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class StudentCSVRepository
{
    private String filePath;
    public void setFilePath(String path)
    {
        this.filePath = path;
    }
    private String[] header = {"FirstName", "LastName", "Patronymic", "MathScore", "RussianScore", "PhysicsScore"};

    public StudentCSVRepository()
    {
        filePath = null;
    }

    public ArrayList<Student> GetAll()
    {
        var students = new ArrayList<Student>();
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

                students.add(student);
            }
        }
        catch (IOException e)
        {
        }
        return students;
    }

    public void Save(ArrayList<Student> items)
    {
        try
        {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));

            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(header));
            for (Student item: items)
            {
                csvPrinter.printRecord(item.FirstName,item.LastName, item.Patronymic,
                        item.MathScore, item.PhysicsScore, item.RussianScore);
            }
            csvPrinter.flush();
        }
        catch (IOException e)
        {

        }
    }


}
