package Frames;

import Model.Student;
import TableModels.StudentTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainScreen extends JFrame
{
    private JMenuBar menuBar;
    private ArrayList<Student> students;
    MainScreen()
    {
        menuBar = new JMenuBar();
        students = new ArrayList<Student>();
        {
            var s1 = new Student();
            s1.EnglishScore = 100;
            s1.FirstName = "Nikita";
            s1.LastName = "Bodry";
            s1.Patronymic = "Sergeevich";
            s1.MathScore = 110;
            s1.PhysicsScore = 120;
            s1.RussianScore = 0;
            students.add(s1);

            var s2 = new Student();
            s2.EnglishScore = 100;
            s2.FirstName = "Dima";
            s2.LastName = "Urbanov";
            s2.Patronymic = "Albertovich";
            s2.MathScore = 0;
            s2.PhysicsScore = 0;
            s2.RussianScore = 0;
            students.add(s2);
        }


        setResizable(false);
        setSize(800, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        var dataTable = createDataTable();
        add(dataTable);

        var panel1 = createInputInitialsPanel();
        add(panel1);
        panel1.setBounds(20,10,400,100);
        panel1.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        var panel2 = createInputScorePanel();
        add(panel2);
        panel2.setBounds(20,115,200,110);
        panel2.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        var panel3 = createButtonsPanel();
        add(panel3);
        panel3.setBounds(230,115,190,110);
        panel3.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        menuBar.add(createFileMenuBar());
        menuBar.add(createHelpMenuBar());
        setJMenuBar(menuBar);
        setVisible(true);
    }
    private JPanel createButtonsPanel()
    {
        var panel = new JPanel(null);

        var createButton = new JButton("Создать");
        var editButton = new JButton("Изменить");
        var deleteButton = new JButton("Удалить");

        createButton.setBounds(10,5,170,30);
        editButton.setBounds(10,40,170,30);
        deleteButton.setBounds(10,75,170,30);

        panel.add(createButton);
        panel.add(editButton);
        panel.add(deleteButton);

        return panel;
    }
    private JPanel createInputScorePanel()
    {
        var panel = new JPanel(null);

        var mathScore = new JTextField(3);
        var russianScore = new JTextField(3);
        var physicsScore = new JTextField(3);
        var englishScore = new JTextField(3);

        var mathScoreLabel = new JLabel("Математика");
        var russianScoreLabel = new JLabel("Русский");
        var physicsScoreLabel = new JLabel("Физика");
        var englishScoreLabel = new JLabel("Английский");

        mathScore.setBounds(5,5,50,20);
        russianScore.setBounds(5,30,50,20);
        physicsScore.setBounds(5,55,50,20);
        englishScore.setBounds(5,80,50,20);

        mathScoreLabel.setBounds(60,5,80,20);
        russianScoreLabel.setBounds(60,30,80,20);
        physicsScoreLabel.setBounds(60,55,80,20);
        englishScoreLabel.setBounds(60,80,80,20);

        panel.add(mathScore);
        panel.add(russianScore);
        panel.add(physicsScore);
        panel.add(englishScore);

        panel.add(mathScoreLabel);
        panel.add(russianScoreLabel);
        panel.add(physicsScoreLabel);
        panel.add(englishScoreLabel);

        return panel;
    }
    private JPanel createInputInitialsPanel()
    {
        var panel = new JPanel(null);

        var firstName = new JTextField(25);
        var lastName = new JTextField(25);
        var patronymic = new JTextField(25);

        var firstNameLabel = new JLabel("Имя");
        var lastNameLabel = new JLabel("Фамилия");
        var patronymicLabel = new JLabel("Отчество");

        lastName.setBounds(5,5,200,25);
        firstName.setBounds(5,35,200,25);
        patronymic.setBounds(5,65,200,25);

        lastNameLabel.setBounds(210,5,150,25);
        firstNameLabel.setBounds(210,35,150,25);
        patronymicLabel.setBounds(210,65,150,25);

        panel.add(firstName);
        panel.add(lastName);
        panel.add(patronymic);

        panel.add(firstNameLabel);
        panel.add(lastNameLabel);
        panel.add(patronymicLabel);

        return panel;
    }
    private JScrollPane createDataTable()
    {
        StudentTableModel model = new StudentTableModel(students);
        JTable table = new JTable(model);

        table.getTableHeader().setResizingAllowed(false);
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(50);
        table.getColumnModel().getColumn(4).setPreferredWidth(70);
        table.getColumnModel().getColumn(5).setPreferredWidth(70);
        table.getColumnModel().getColumn(6).setPreferredWidth(70);
        table.getColumnModel().getColumn(7).setPreferredWidth(50);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setBackground(new Color(155, 177, 205));

        JScrollPane scrollPane = new  JScrollPane(table);
        scrollPane.setBounds(15,235,760,400);
        return scrollPane;
    }
    private JMenu createFileMenuBar()
    {
        var fileMenu = new JMenu("Файл");

        var newMenuItem = new JMenuItem("Новый");
        var openMenuItem = new JMenuItem("Открыть");
        var saveMenuItem = new JMenuItem("Сохранить");
        var saveAsMenuItem = new JMenuItem("Сохранить как...");


        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);

        return fileMenu;
    }
    private JMenu createHelpMenuBar()
    {
        var helpMenu = new JMenu("Помощь");

        var helpButton = new JMenuItem("Помощь");
        var aboutAuthorButton = new JMenuItem("Об авторе");
        var aboutProgramButton = new JMenuItem("О программе");

        helpMenu.add(helpButton);
        helpMenu.add(aboutAuthorButton);
        helpMenu.add(aboutProgramButton);

        return helpMenu;
    }
}
