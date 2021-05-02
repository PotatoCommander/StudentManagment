package Frames;

import javax.swing.*;

public class MainScreen extends JFrame
{
    private JMenuBar menuBar;
    MainScreen()
    {
        menuBar = new JMenuBar();
        setResizable(false);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuBar.add(createFileMenuBar());
        menuBar.add(createHelpMenuBar());
        setJMenuBar(menuBar);
        setVisible(true);
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
