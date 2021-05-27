package frames;

import DAL.StudentCSVRepository;
import model.enums.Adapters;
import model.Message;
import model.enums.Sort;
import model.customLists.ObservableList;
import model.abstractions.Observer;
import model.Student;
import tableModels.StudentTableModel;
import uti.FileHandler;
import uti.KeyAdapterFactory;
import uti.Sorter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
/**
 *<strong>MainFrame</strong>
 *
 * <i> extends JFrame and implements Observer.</i>
 * Contains main logic of application (event handlers).
 * Contains UI and implemented Update method with logic.
 * @author Nikita Bodry
 * @version 1.0
 */
public class MainFrame extends JFrame implements Observer
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

    private JTextField dayTextBox;
    private JTextField monthTextBox;
    private JTextField yearTextBox;
    private JTextArea logArea;

    private JButton saveFileButton;
    private JButton openFileButton;
    private JButton newFileButton;
    private Sorter sorter;
    private JMenuItem aboutProgramButton;
    private JMenuItem aboutAuthorButton;
    private JMenuItem exitMenuItem;

    /**Constructor of MainFrame contains calling of UI creation methods and
     * connecting element actions to listeners.
     * @param title
     *          Title of a Frame
     */
    public MainFrame(String title)
    {
        sorter = new Sorter();
        sorter.AddObserver(this);
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

        JScrollPane dataTable = createDataTable();
        add(dataTable);
        JPanel inputInitials = createInputInitialsPanel();
        add(inputInitials);
        JPanel inputScorePanel = createInputScorePanel();
        add(inputScorePanel);
        JPanel buttonsPanel = createButtonsPanel();
        add(buttonsPanel);
        JPanel sortPanel = createSortPanel();
        add(sortPanel);
        JPanel logPanel = createLogPanel();
        add(logPanel);
        JPanel fileButtonsPanel = createFileButtonsPanel();
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

        newFileButton.addActionListener(e -> handleNewClickEvent(e));
        openFileButton.addActionListener(e ->  handleOpenClickEvent(e));
        saveFileButton.addActionListener(e -> handleSaveClickEvent(e));

        aboutAuthorButton.addActionListener(e -> new AboutFrame());
        aboutProgramButton.addActionListener(e -> new AboutProgramFrame());
        exitMenuItem.addActionListener(e -> System.exit(EXIT_ON_CLOSE));

        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(e -> handleSelectionEvent(e));




        dayTextBox.addKeyListener(KeyAdapterFactory.getDateRestrictedAdapter(Adapters.DAY_OF_MONTH_ADAPTER));
        monthTextBox.addKeyListener(KeyAdapterFactory.getDateRestrictedAdapter(Adapters.MONTH_ADAPTER));
        yearTextBox.addKeyListener(KeyAdapterFactory.getDateRestrictedAdapter(Adapters.YEAR_ADAPTER));

        KeyAdapter scoreAdapter = KeyAdapterFactory.getDigitsRestrictedAdapter(3);
        mathScoreTextBox.addKeyListener(scoreAdapter);
        russianScoreTextBox.addKeyListener(scoreAdapter);
        physicsScoreTextBox.addKeyListener(scoreAdapter);


    }
    private Student createStudentByTextBoxes()
    {
        Student student = new Student();
        student.FirstName = firstNameTextBox.getText();
        student.LastName = lastNameTextBox.getText();
        student.Patronymic = patronymicTextBox.getText();
        student.MathScore = Integer.parseInt(mathScoreTextBox.getText());
        student.PhysicsScore = Integer.parseInt(physicsScoreTextBox.getText());
        student.RussianScore = Integer.parseInt(russianScoreTextBox.getText());
        int day = Integer.parseInt(dayTextBox.getText());
        int month = Integer.parseInt(monthTextBox.getText());
        int year = Integer.parseInt(yearTextBox.getText());
        student.DateOfBirth = new GregorianCalendar(year,month - 1,day);

        return student;
    }
    //region EVENT_HANDLERS
    private void handleNewClickEvent(ActionEvent e)
    {
        String filepath = fileHandler.newFileDialog();
        if (filepath == null) return;
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
        String filepath = fileHandler.openFileDialog();
        if (filepath == null) return;
        repository.setFilePath(filepath);
        students.clear();
        students.addAll(repository.GetAll());
    }
    private void handleSaveAsClickEvent(ActionEvent e)
    {
        String filepath = fileHandler.saveAsFileDialog();
        if (filepath == null) return;
        repository.setFilePath(filepath);
        repository.SaveAs(students);
    }
    private void handleButtonSortClickEvent(ActionEvent e)
    {
        Sort selected = (Sort) sortComboBox.getSelectedItem();
        sorter.SortBy(students, Sort.valueOf(selected.name()));
        table.updateUI();
    }
    private void handleSelectionEvent(ListSelectionEvent e)
    {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0)
        {
            firstNameTextBox.setText(table.getValueAt(selectedRow, 0).toString());
            lastNameTextBox.setText(table.getValueAt(selectedRow, 1).toString());
            patronymicTextBox.setText(table.getValueAt(selectedRow, 2).toString());
            {
                Student s = students.get(selectedRow);
                dayTextBox.setText(String.valueOf(s.DateOfBirth.get(Calendar.DAY_OF_MONTH)));
                monthTextBox.setText(String.valueOf(s.DateOfBirth.get(Calendar.MONTH) + 1));
                yearTextBox.setText(String.valueOf(s.DateOfBirth.get(Calendar.YEAR)));
            }
            mathScoreTextBox.setText(table.getValueAt(selectedRow, 4).toString());
            russianScoreTextBox.setText(table.getValueAt(selectedRow, 5).toString());
            physicsScoreTextBox.setText(table.getValueAt(selectedRow, 6).toString());
        }
    }
    private void handleButtonCreateClickEvent(ActionEvent e)
    {
        try
        {
            students.add(createStudentByTextBoxes());
            table.setRowSelectionInterval(students.size() - 1, students.size() - 1);
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Произошла ошибка.",
                    "Предупреждение", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void handleButtonEditClickEvent(ActionEvent e)
    {
        try
        {
            int index = table.getSelectedRow();
            students.set(index, createStudentByTextBoxes());
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "Произошла ошибка.",
                    "Предупреждение", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void handleButtonDeleteClickEvent(ActionEvent e)
    {
        int index = table.getSelectedRow();
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
    //region UI_CREATION
    private JPanel createFileButtonsPanel()
    {
        JPanel panel = new JPanel(null);

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
        JPanel panel = new JPanel(null);
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
        JPanel panel = new JPanel(null);

        Icon sortIcon = new ImageIcon("D:\\CourseProjectJAVA\\src\\Images\\funnel-simple.png");

        sortButton = new JButton("Сортировать");
        sortComboBox = new JComboBox(Sort.values());
        JLabel sortLabel = new JLabel("Сортировать \n по:");

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
        JPanel panel = new JPanel(null);

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
        JPanel panel = new JPanel(null);

        mathScoreTextBox = new JTextField(3);
        russianScoreTextBox = new JTextField(3);
        physicsScoreTextBox = new JTextField(3);
        dropSelectionButton = new JButton("Сбросить выделение");

        JLabel mathScoreLabel = new JLabel("Математика");
        JLabel russianScoreLabel = new JLabel("Русский");
        JLabel physicsScoreLabel = new JLabel("Физика");

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
        JPanel panel = new JPanel(null);

        firstNameTextBox = new JTextField(25);
        lastNameTextBox = new JTextField(25);
        patronymicTextBox = new JTextField(25);

        dayTextBox = new JTextField(4);
        monthTextBox = new JTextField(4);
        yearTextBox = new JTextField(4);

        JLabel firstNameLabel = new JLabel("Имя");
        JLabel lastNameLabel = new JLabel("Фамилия");
        JLabel patronymicLabel = new JLabel("Отчество");

        JLabel dayLabel = new JLabel("День");
        JLabel monthLabel = new JLabel("Месяц");
        JLabel yearLabel = new JLabel("Год");

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
        JMenu fileMenu = new JMenu("Файл");

        newMenuItem = new JMenuItem("Новый");
        openMenuItem = new JMenuItem("Открыть");
        saveMenuItem = new JMenuItem("Сохранить");
        saveAsMenuItem = new JMenuItem("Сохранить как...");
        exitMenuItem = new JMenuItem("Выход");


        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.add(exitMenuItem);

        return fileMenu;
    }
    private JMenu createHelpMenuBar()
    {
        JMenu helpMenu = new JMenu("Помощь");

        aboutAuthorButton = new JMenuItem("Об авторе");
        aboutProgramButton = new JMenuItem("О программе");

        helpMenu.add(aboutAuthorButton);
        helpMenu.add(aboutProgramButton);

        return helpMenu;
    }
    //endregion
    /**<b>Override of interface method</b>
     * If Update called and initiator of event is a {@link ObservableList} then calls updating of table.
     * Record a action to a log panel.
     * @param message
     *        A {@link Message} class object contains initiator object, info and time of action fired
    */
    @Override
    public void Update(Message message)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm ");
        if (message.getInitiator() instanceof ObservableList)
        {
            table.updateUI();
        }
        String msg = message.getInfo();
        if(message.getAction() != null)
        {
            logArea.append(sdf.format(message.getActionTime()) + msg +"\n");
        }
    }
}
