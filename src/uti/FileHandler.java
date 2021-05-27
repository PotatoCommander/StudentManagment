package uti;

import model.Student;
import model.customLists.ObservableList;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
/**
 *<strong>FileHandler</strong>
 * Class for calling file choosers and getting filepath.
 * @author Nikita Bodry
 * @version 1.0
 */
public class FileHandler
{
    private JFileChooser fileChooser;
    private Component parent;
    /**
     *<i>Constructor to create FileHandler - class that show file dialog</i>
     * @param parent
     *          Component from file handler created.
     */
    public FileHandler(Component parent)
    {
        this.parent = parent;
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileFilter filter = new FileNameExtensionFilter("CSV-File","csv");
        fileChooser.setFileFilter(filter);
    }
    /**Returns filepath string from dialog menu.
     *
     * @return  filepath of chosen file if approve button clicked. Else returns {@code false}
     */
    public String openFileDialog()
    {
        fileChooser.setDialogTitle("Выберите файл для открытия");
        int result = fileChooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = fileChooser.getSelectedFile();
            String path = selectedFile.getAbsolutePath();
            if (!path.toLowerCase().endsWith( ".csv" )) path += ".csv";
            return path;
        }
        return null;
    }
    /**Returns filepath string from dialog menu.
     *
     * @return  filepath of chosen file if approve button clicked. Else returns {@code false}
     */
    public String saveAsFileDialog()
    {
        fileChooser.setDialogTitle("Сохранить как...");
        return getString();
    }
    /**Returns filepath string from dialog menu.
     *
     * @return  filepath of chosen file if approve button clicked. Else returns {@code false}
     */
    public String newFileDialog()
    {
        fileChooser.setDialogTitle("Создать новый файл...");
        return getString();
    }

    private String getString()
    {
        int result = fileChooser.showSaveDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION)
        {
            File fileToSave = fileChooser.getSelectedFile();
            String  path = fileToSave.getAbsolutePath();
            if (!path.toLowerCase().endsWith( ".csv" )) path += ".csv";
            return path;
        }
        return null;
    }
}
