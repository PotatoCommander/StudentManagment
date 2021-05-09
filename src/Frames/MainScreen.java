package Frames;

import DAL.StudentCSVRepository;
import Model.Enums.Adapters;
import Model.Message;
import Model.Enums.Sort;
import Model.CustomLists.ObservableList;
import Model.Abstraction.Observer;
import Model.Student;
import TableModels.StudentTableModel;
import Util.FileHandler;
import Util.KeyAdapterFactory;
import Util.Sorter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
//TODO: Refactoring
//TODO: Separate listeners as classes
//TODO: Add HelpFrame and AboutFrame
//TODO: Add JavaDoc to public methods
//TODO: Approbation of functionality
//TODO: Change asc to desc in sorter
//TODO: Change month display format to +1
public class MainScreen extends JFrame implements Observer
{
    private JMenuBar menuBar;
    private ObservableList<Student> students;

    private JTextField mathScoreTextBox;
    private JTextField physicsScoreTextBox;
    private JTextField russianScoreTextBox;

    private JTextField lastNameTextBox;
    private JTextField firstNameTextBox;
    private JTextField patronymicTextBox;

    private JButton deleteButton;
    private JButton editButton;
    private JButton createButton;

    private StudentCSVRepository repository;
    private JTable table;
    private JButton dropSelectionButton;
    private JButton sortButton;
    private JComboBox sortComboBox;

    FileHandler fileHandler;

    private JMenuItem newMenuItem;
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem saveAsMenuItem;

    private boolean isSaved;
    private JTextField dayTextBox;
    private JTextField monthTextBox;
    private JTextField yearTextBox;
    private JTextArea logArea;

    private JButton saveFileButton;
    private JButton openFileButton;
    private JButton newFileButton;


    MainScreen(String title)
    {
        isSaved = false;
        repository = new StudentCSVRepository();
        repository.AddObserver(this);
        menuBar = new JMenuBar();
        students = new ObservableList<Student>();
        students.AddObserver(this);


        setResizable(false);
        setTitle(title);
        setSize(800, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        var dataTable = createDataTable();
        add(dataTable);
        var inputInitials = createInputInitialsPanel();
        add(inputInitials);
        var inputScorePanel = createInputScorePanel();
        add(inputScorePanel);
        var buttonsPanel = createButtonsPanel();
        add(buttonsPanel);
        var sortPanel = createSortPanel();
        add(sortPanel);
        var logPanel = createLogPanel();
        add(logPanel);
        var fileButtonsPanel = createFileButtonsPanel();
        add(fileButtonsPanel);

        menuBar.add(createFileMenuBar());
        menuBar.add(createHelpMenuBar());
        setJMenuBar(menuBar);
        setVisible(true);

        initActionListeners();
        fileHandler = new FileHandler(this);
    }
    private void initActionListeners()
    {
        createButton.addActionListener(e -> handleButtonCreateClickEvent(e));
        deleteButton.addActionListener(e -> handleButtonDeleteClickEvent(e));
        editButton.addActionListener(e -> handleButtonEditClickEvent(e));
        dropSelectionButton.addActionListener(e -> handleButtonDropSelectionClickEvent(e));
        sortButton.addActionListener(e -> handleButtonSortClickEvent(e));

        saveAsMenuItem.addActionListener(e -> handleSaveAsClickEvent(e));
        openMenuItem.addActionListener(e -> handleOpenClickEvent(e));
        saveMenuItem.addActionListener(e -> handleSaveClickEvent(e));
        newMenuItem.addActionListener(e -> handleNewClickEvent(e));

        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(e -> handleSelectionEvent(e));

        table.getModel().addTableModelListener(
                new TableModelListener()
                {
                    public void tableChanged(TableModelEvent e)
                    {
                        isSaved = false;
                    }
                });



        dayTextBox.addKeyListener(KeyAdapterFactory.getDateRestrictedAdapter(Adapters.DAY_OF_MONTH_ADAPTER));
        monthTextBox.addKeyListener(KeyAdapterFactory.getDateRestrictedAdapter(Adapters.MONTH_ADAPTER));
        yearTextBox.addKeyListener(KeyAdapterFactory.getDateRestrictedAdapter(Adapters.YEAR_ADAPTER));

        var scoreAdapter = KeyAdapterFactory.getDigitsRestrictedAdapter(3);
        mathScoreTextBox.addKeyListener(scoreAdapter);
        russianScoreTextBox.addKeyListener(scoreAdapter);
        physicsScoreTextBox.addKeyListener(scoreAdapter);


    }
    //region EVENT_HANDLERS
    private void handleNewClickEvent(ActionEvent e)
    {
        var filepath = fileHandler.newFileDialog();
        repository.setFilePath(filepath);
        repository.CreateFile();
        students.clear();
    }

    private void handleSaveClickEvent(ActionEvent e)
    {
        repository.Save(students);
    }

    private void handleOpenClickEvent(ActionEvent e)
    {
        var filepath = fileHandler.openFileDialog();
        repository.setFilePath(filepath);
        students.clear();
        students.addAll(repository.GetAll());
    }

    private void handleSaveAsClickEvent(ActionEvent e)
    {
        var filepath = fileHandler.saveAsFileDialog();
        repository.setFilePath(filepath);
        repository.SaveAs(students);
    }
    private void handleButtonSortClickEvent(ActionEvent e)
    {
        var selected = (Sort) sortComboBox.getSelectedItem();
        Sorter.SortBy(students, Sort.valueOf(selected.name()));
        table.updateUI();
    }
    private void handleSelectionEvent(ListSelectionEvent e)
    {
        var selectedRow = table.getSelectedRow();
        if (selectedRow >= 0)
        {
            firstNameTextBox.setText(table.getValueAt(selectedRow, 0).toString());
            lastNameTextBox.setText(table.getValueAt(selectedRow, 1).toString());
            patronymicTextBox.setText(table.getValueAt(selectedRow, 2).toString());
            {
                var s = students.get(selectedRow);
                dayTextBox.setText(String.valueOf(s.DateOfBirth.get(Calendar.DAY_OF_MONTH)));
                monthTextBox.setText(String.valueOf(s.DateOfBirth.get(Calendar.MONTH)));
                yearTextBox.setText(String.valueOf(s.DateOfBirth.get(Calendar.YEAR)));
            }
            mathScoreTextBox.setText(table.getValueAt(selectedRow, 4).toString());
            russianScoreTextBox.setText(table.getValueAt(selectedRow, 5).toString());
            physicsScoreTextBox.setText(table.getValueAt(selectedRow, 6).toString());
        }
    }
    private void handleButtonCreateClickEvent(ActionEvent e)
    {
        students.add(createStudentByTextBoxes());
        handleButtonDropSelectionClickEvent(e);
        //table.setRowSelectionInterval(students.size() - 1, students.size() - 1);
    }

    private void handleButtonEditClickEvent(ActionEvent e)
    {
        var index = table.getSelectedRow();
        students.set(index,createStudentByTextBoxes());
    }
    private void handleButtonDeleteClickEvent(ActionEvent e)
    {
        var index = table.getSelectedRow();
        students.remove(index);
        table.updateUI();
    }
    private void handleButtonDropSelectionClickEvent(ActionEvent e)
    {
        table.clearSelection();
        firstNameTextBox.setText("");
        lastNameTextBox.setText("");
        patronymicTextBox.setText("");
        mathScoreTextBox.setText("");
        physicsScoreTextBox.setText("");
        russianScoreTextBox.setText("");
        dayTextBox.setText("");
        monthTextBox.setText("");
        yearTextBox.setText("");
    }
    //endregion
    private Student createStudentByTextBoxes()
    {
        var student = new Student();
        student.FirstName = firstNameTextBox.getText();
        student.LastName = lastNameTextBox.getText();
        student.Patronymic = patronymicTextBox.getText();
        student.MathScore = Integer.parseInt(mathScoreTextBox.getText());
        student.PhysicsScore = Integer.parseInt(physicsScoreTextBox.getText());
        student.RussianScore = Integer.parseInt(russianScoreTextBox.getText());
        var day = Integer.parseInt(dayTextBox.getText());
        var month = Integer.parseInt(monthTextBox.getText());
        var year = Integer.parseInt(yearTextBox.getText());
        student.DateOfBirth = new GregorianCalendar(year,month - 1,day);

        return student;
    }
    //region UI_CREATION
    private JPanel createFileButtonsPanel()
    {
        var panel = new JPanel(null);

        Icon saveIcon = new ImageIcon("D:\\CourseProjectJAVA\\src\\Images\\floppy-disk.png");
        Icon openIcon = new ImageIcon("D:\\CourseProjectJAVA\\src\\Images\\folders.png");
        Icon newIcon = new ImageIcon("D:\\CourseProjectJAVA\\src\\Images\\file-plus.png");


        saveFileButton = new JButton(saveIcon);
        openFileButton = new JButton(openIcon);
        newFileButton = new JButton(newIcon);

        saveFileButton.setHorizontalAlignment(SwingConstants.CENTER);
        saveFileButton.setIconTextGap(20);

        openFileButton.setHorizontalAlignment(SwingConstants.CENTER);
        openFileButton.setIconTextGap(20);

        newFileButton.setHorizontalAlignment(SwingConstants.CENTER);
        newFileButton.setIconTextGap(20);

        newFileButton.setBounds(22,6,70,25);
        saveFileButton.setBackground(new Color(160, 212, 156));
        openFileButton.setBounds(22,37,70,25);
        saveFileButton.setBounds(22,69,70,25);

        panel.add(saveFileButton);
        panel.add(openFileButton);
        panel.add(newFileButton);

        panel.setBounds(660,10,115,100);
        panel.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        return panel;
    }
    private JPanel createLogPanel()
    {
        var panel = new JPanel(null);
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setBackground(new Color(0, 9, 114));
        logArea.setForeground(new Color(215, 190, 90));
        logArea.setFont(new Font("Consolas",Font.PLAIN, 12));

        JScrollPane scrollPane = new  JScrollPane(logArea);
        scrollPane.setBounds(5,5,335,95);

        panel.add(scrollPane);
        panel.setBounds(430,120,345,105);
        panel.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        return panel;
    }
    private JPanel createSortPanel()
    {
        var panel = new JPanel(null);

        Icon sortIcon = new ImageIcon("D:\\CourseProjectJAVA\\src\\Images\\funnel-simple.png");

        sortButton = new JButton("Сортировать");
        sortComboBox = new JComboBox(Sort.values());
        var sortLabel = new JLabel("Сортировать \n по:");

        sortLabel.setBounds(10,5,200,25);
        sortComboBox.setBounds(10,35,200,25);
        sortButton.setBounds(10,65,200,30);

        sortButton.setHorizontalAlignment(SwingConstants.LEFT);
        sortButton.setIcon(sortIcon);
        sortButton.setIconTextGap(20);

        panel.add(sortButton);
        panel.add(sortComboBox);
        panel.add(sortLabel);

        panel.setBounds(430,10,220,100);
        panel.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        return panel;
    }
    private JPanel createButtonsPanel()
    {
        var panel = new JPanel(null);

        Icon createIcon = new ImageIcon("D:\\CourseProjectJAVA\\src\\Images\\plus-square.png");
        Icon editIcon = new ImageIcon("D:\\CourseProjectJAVA\\src\\Images\\edit (1).png");
        Icon deleteIcon = new ImageIcon("D:\\CourseProjectJAVA\\src\\Images\\delete.png");


        createButton = new JButton("Создать");
        editButton = new JButton("Изменить");
        deleteButton = new JButton("Удалить");

        createButton.setHorizontalAlignment(SwingConstants.LEFT);
        createButton.setIcon(createIcon);
        createButton.setIconTextGap(20);

        deleteButton.setHorizontalAlignment(SwingConstants.LEFT);
        deleteButton.setIcon(deleteIcon);
        deleteButton.setIconTextGap(20);

        editButton.setHorizontalAlignment(SwingConstants.LEFT);
        editButton.setIcon(editIcon);
        editButton.setIconTextGap(20);

        createButton.setBounds(10,5,170,30);
        editButton.setBounds(10,40,170,30);
        deleteButton.setBounds(10,75,170,30);

        panel.add(createButton);
        panel.add(editButton);
        panel.add(deleteButton);

        panel.setBounds(230,115,190,110);
        panel.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        return panel;
    }
    private JPanel createInputScorePanel()
    {
        var panel = new JPanel(null);

        mathScoreTextBox = new JTextField(3);
        russianScoreTextBox = new JTextField(3);
        physicsScoreTextBox = new JTextField(3);
        dropSelectionButton = new JButton("Сбросить выделение");

        var mathScoreLabel = new JLabel("Математика");
        var russianScoreLabel = new JLabel("Русский");
        var physicsScoreLabel = new JLabel("Физика");

        mathScoreTextBox.setBounds(5,5,50,20);
        russianScoreTextBox.setBounds(5,30,50,20);
        physicsScoreTextBox.setBounds(5,55,50,20);


        mathScoreLabel.setBounds(60,5,80,20);
        russianScoreLabel.setBounds(60,30,80,20);
        physicsScoreLabel.setBounds(60,55,80,20);
        dropSelectionButton.setBounds(5,80,180,20);

        panel.add(mathScoreTextBox);
        panel.add(russianScoreTextBox);
        panel.add(physicsScoreTextBox);

        panel.add(mathScoreLabel);
        panel.add(russianScoreLabel);
        panel.add(physicsScoreLabel);
        panel.add(dropSelectionButton);

        panel.setBounds(20,115,200,110);//225
        panel.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        return panel;
    }
    private JPanel createInputInitialsPanel()
    {
        var panel = new JPanel(null);

        firstNameTextBox = new JTextField(25);
        lastNameTextBox = new JTextField(25);
        patronymicTextBox = new JTextField(25);

        dayTextBox = new JTextField(4);
        monthTextBox = new JTextField(4);
        yearTextBox = new JTextField(4);

        var firstNameLabel = new JLabel("Имя");
        var lastNameLabel = new JLabel("Фамилия");
        var patronymicLabel = new JLabel("Отчество");

        var dayLabel = new JLabel("День");
        var monthLabel = new JLabel("Месяц");
        var yearLabel = new JLabel("Год");

        lastNameTextBox.setBounds(5,5,200,25);
        firstNameTextBox.setBounds(5,35,200,25);
        patronymicTextBox.setBounds(5,65,200,25);

        dayTextBox.setBounds(285,5,50,25);
        monthTextBox.setBounds(285,35,50,25);
        yearTextBox.setBounds(285,65,50,25);

        lastNameLabel.setBounds(210,5,70,25);
        firstNameLabel.setBounds(210,35,70,25);
        patronymicLabel.setBounds(210,65,70,25);

        dayLabel.setBounds(345,5,50,25);
        monthLabel.setBounds(345,35,50,25);
        yearLabel.setBounds(345,65,70,25);

        panel.add(firstNameTextBox);
        panel.add(lastNameTextBox);
        panel.add(patronymicTextBox);

        panel.add(yearTextBox);
        panel.add(monthTextBox);
        panel.add(dayTextBox);

        panel.add(firstNameLabel);
        panel.add(lastNameLabel);
        panel.add(patronymicLabel);

        panel.add(yearLabel);
        panel.add(monthLabel);
        panel.add(dayLabel);

        panel.setBounds(20,10,400,100);
        panel.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        return panel;
    }
    private JScrollPane createDataTable()
    {
        StudentTableModel model = new StudentTableModel(students);
        table = new JTable(model);

        table.getTableHeader().setResizingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setPreferredWidth(120);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        table.getColumnModel().getColumn(4).setPreferredWidth(50);
        table.getColumnModel().getColumn(5).setPreferredWidth(50);
        table.getColumnModel().getColumn(6).setPreferredWidth(50);
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

        newMenuItem = new JMenuItem("Новый");
        openMenuItem = new JMenuItem("Открыть");
        saveMenuItem = new JMenuItem("Сохранить");
        saveAsMenuItem = new JMenuItem("Сохранить как...");


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

    @Override
    public void Update(Message message)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm ");
        if (message.getInitiator() instanceof ObservableList)
        {
            table.updateUI();
        }
        var msg = message.getInfo();
        if(message.getAction() != null)
        {
            logArea.append(sdf.format(message.getActionTime()) + msg +"\n");
        }
    }
    //endregion
}
