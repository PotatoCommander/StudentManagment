package uti;

import model.enums.Adapters;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
/**
 *<strong>KeyAdapterFactory</strong>
 * Static class factory for getting adapters by {@link Adapters} enum
 * @author Nikita Bodry
 * @version 1.0
 */
public class KeyAdapterFactory
{
    /**Returns adapter with date input restriction.
     *
     * @return  {@link KeyAdapter} inheritor with overrided keyTyped method.
     * @param adapterType
     *          Type of adapter ({@link Adapters} enum).
     */
    public static KeyAdapter getDateRestrictedAdapter(Adapters adapterType)
    {
        KeyAdapter adapter;
        switch (adapterType)
        {
            case YEAR_ADAPTER:
                return  getDigitsRestrictedAdapter(4);
            case MONTH_ADAPTER:
                return new MonthKeyAdapter();
            case DAY_OF_MONTH_ADAPTER:
                return new DayOfMonthKeyAdapter();
            default:
                throw new IllegalStateException("Unexpected value: " + adapterType);
        }
    }
    /**Returns adapter with restriction by number of digits.
     *
     * @return  {@link KeyAdapter} inheritor with overrided keyTyped method.
     * @param numberOfDigits
     *          Max number of digits in text field.
     */
    public static KeyAdapter getDigitsRestrictedAdapter(int numberOfDigits)
    {
        return new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                char c = e.getKeyChar();
                int length = ((JTextField)e.getSource()).getText().length();
                if (!Character.isDigit(c) || length >= numberOfDigits) { e.consume();}
            }
        };
    }
    private static class MonthKeyAdapter extends KeyAdapter
    {
        @Override
        public void keyTyped(KeyEvent e)
        {
            char c = e.getKeyChar();
            String  text = ((JTextField)e.getSource()).getText();
            int length = text.length();
            switch (length)
            {
                case 0:
                    if (!(c>='1' && c<='9')) e.consume();
                    break;
                case 1:
                    if(text.charAt(0)!='1') e.consume();
                    else if (!(c>='0' && c<='2')) e.consume();
                    break;
                default:
                    e.consume();
                    break;
            }
        }
    }

    private static class DayOfMonthKeyAdapter extends KeyAdapter
    {
        @Override
        public void keyTyped(KeyEvent e)
        {
            char c = e.getKeyChar();
            if (!Character.isDigit(c))
            {
                e.consume();
                return;
            }
            String text = ((JTextField)e.getSource()).getText();
            int length = text.length();
            switch (length)
            {
                case 0:
                    break;
                case 1:
                    if(text.charAt(0) =='3')
                    {
                        if (c!='1' && c!='0') e.consume();
                    }
                    if(text.charAt(0) >= '4' && text.charAt(0) <= '9')
                    {
                        e.consume();
                    }
                    break;
                default:
                    e.consume();
                    break;
            }
        }
    }
}
