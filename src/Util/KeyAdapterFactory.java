package Util;

import Model.Enums.Adapters;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyAdapterFactory
{
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
    public static KeyAdapter getDigitsRestrictedAdapter(int numberOfDigits)
    {
        return new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                char c = e.getKeyChar();
                var length = ((JTextField)e.getSource()).getText().length();
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
            var text = ((JTextField)e.getSource()).getText();
            var length = text.length();
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
            var text = ((JTextField)e.getSource()).getText();
            var length = text.length();
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
