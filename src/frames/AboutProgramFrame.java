package frames;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 *<strong>AboutProgramFrame extends JFrame</strong>
 *
 * <i>Implementation of parent class JFrame contains info about program</i>
 *
 * @author Nikita Bodry
 * @version 1.0
 */
public class AboutProgramFrame extends JFrame
{
    public AboutProgramFrame()
    {
        setResizable(false);
        setSize(700,200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("О Программе");
        setLocationRelativeTo(null);
        JLabel iconLabel = new JLabel();
        iconLabel.setBounds(5,5,180,180);
        try
        {
            BufferedImage image = ImageIO.read(new File("D:\\CourseProjectJAVA\\src\\Images\\abiturienty.jpg"));
            ImageIcon icon = new ImageIcon(image.getScaledInstance(180, 180, Image.SCALE_SMOOTH));
            iconLabel.setIcon(icon);
        } catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
        add(iconLabel);
        add(new myComponent());
        setVisible(true);
    }

    /**
     *<strong>Child class myComponent, witch is inherited from the class JComponent</strong>
     *
     * <i>In this class implemented component for {@link AboutProgramFrame}</i>
     */
    static class myComponent extends JComponent{

        protected void paintComponent(Graphics g){
            Font font = new Font("TimesRoman", Font.BOLD, 14);
            Graphics2D graphics = (Graphics2D) g;
            graphics.setFont(font);
            graphics.drawString(" Программа позволяет:", 220, 20);
            graphics.drawString(" 1. Создавать " +
                    "список абитуриентов ", 220, 40);
            graphics.drawString(" 2. Добавлять, изменять, удалять, записи ", 220, 80);
            graphics.drawString(" 3. Сортировать " +
                    "список по инициалам, дате рождения, баллам", 220, 300);
            graphics.drawString(" 4. Загружать, создавать и сохранять список в CSV файл", 220, 120);
            graphics.drawString(" Версия программы: 1.4", 220,140);
        }
    }
}
