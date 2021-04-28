import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SplashScreen extends JFrame {

    public SplashScreen(){
        setResizable(false);
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setTitle("Начальный экран");
        setLocationRelativeTo(null);

        JLabel label = new JLabel();
        JButton jbtStart = new JButton("Далее");
        JButton jbtExit = new JButton("Выход");
        add(label);
        add(jbtStart);
        add(jbtExit);

        jbtStart.setBounds(80,500,285,30);
        jbtExit.setBounds(410,500,285,30);
        label.setSize(800,600);
        try
        {
            BufferedImage image = ImageIO.read(new File("Student.jpg"));
            ImageIcon icon = new ImageIcon(image.getScaledInstance(180,180, Image.SCALE_SMOOTH));
            label.setIcon(icon);
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }

        jbtExit.addActionListener(e -> System.exit(0));
        jbtStart.addActionListener(e -> {
            dispose();

        });
        add(new myComponent());
        setVisible(true);
    }
    static class myComponent extends JComponent{
        protected void paintComponent(Graphics g){
            Font font1 = new Font("TimesRoman", Font.BOLD, 14);
            Graphics2D g1 = (Graphics2D) g;
            g1.setFont(font1);
            g1.drawString(" Белорусский национальный технический университет", 200, 30);
            g1.drawString(" Выполнил: студент гр.10702318", 530, 300);
            g1.drawString(" Бодрый Никита Сергеевич", 530, 320);
            g1.drawString(" Проверил: к.ф.-м.н.,доц.", 530, 370);
            g1.drawString(" Сидорик Валерий Владимирович", 530, 390);

            Font font2 = new Font("TimesRoman", Font.BOLD, 12);
            Graphics2D g2 = (Graphics2D) g;
            g2.setFont(font2);
            g2.drawString(" Факультет информационных технологий и робототехники", 230, 60);
            g2.drawString(" Кафедра программного обеспечения информационных систем и технологий", 172, 80);
            g2.drawString(" Минск, 2021", 350, 480);

            Font font3 = new Font("TimesRoman", Font.BOLD, 25);
            Graphics2D g3 = (Graphics2D) g;
            g3.setFont(font3);
            g3.drawString(" Курсовая работа", 280, 140);
            g3.drawString(" формирование списка абитуриентов", 75, 200);


            Font font4 = new Font("TimesRoman", Font.BOLD, 16);
            Graphics2D g4 = (Graphics2D) g;
            g4.setFont(font4);
            g4.drawString(" По дисциплине 'Программирование на Java' ", 210, 165);

        }
    }
}