package Util;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class FileHandler
{
    private JFileChooser fileChooser;
    private Component parent;
    public FileHandler(Component parent)
    {
        this.parent = parent;
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileFilter filter = new FileNameExtensionFilter("CSV-File","csv");
        fileChooser.setFileFilter(filter);
    }
    public String openFileDialog()
    {
        fileChooser.setDialogTitle("Выберите файл для открытия");
        int result = fileChooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        return null;
    }
    public String saveAsFileDialog()
    {
        fileChooser.setDialogTitle("Сохранить как...");
        int result = fileChooser.showSaveDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION)
        {
            File fileToSave = fileChooser.getSelectedFile();
            return fileToSave.getAbsolutePath();
        }
        return null;
    }
}
