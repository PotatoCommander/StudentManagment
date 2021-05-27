package frames;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 *<strong>AboutFrame extends JFrame</strong>
 *
 * <i>Implementation of parent class JFrame contains info about author</i>
 *
 * @author Nikita Bodry
 * @version 1.0
 */
public class AboutFrame extends JFrame
{
    public AboutFrame()
    {
        setResizable(false);
        setSize(350,400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Об авторе");
        setLocationRelativeTo(null);
        JLabel iconLabel = new JLabel();
        iconLabel.setBounds(20,20,200,200);
        try
        {
            BufferedImage image = ImageIO.read(new File("D:\\CourseProjectJAVA\\src\\Images\\me.jpg"));
            ImageIcon icon = new ImageIcon(image.getScaledInstance(200, 200, Image.SCALE_SMOOTH));
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
     *<strong>Child class myComponent, which is inherited from the class JComponent</strong>
     *
     * <i>In this class implemented component for {@link AboutFrame}</i>
     */
    static class myComponent extends JComponent
    {
        protected void paintComponent(Graphics g)
        {
            Font font = new Font("TimesRoman", Font.BOLD, 12);
            Graphics2D graphics = (Graphics2D) g;
            graphics.setFont(font);
            graphics.drawString(" Автор:" +
                    " Никита Бодрый", 20, 240);
            graphics.drawString(" E-Mail: " +
                    "bodrik66aa@gmail.com", 20, 260);
            graphics.drawString(" Об Авторе: " +
                    "Студент белорусского национального", 20, 280);
            graphics.drawString(" национального " +
                    "технического университета", 20, 300);
            graphics.drawString(" обучается на " +
                    "Факультете информационных", 20, 320);
            graphics.drawString(" технологий и робототехники", 20,340);

        }
    }
}
