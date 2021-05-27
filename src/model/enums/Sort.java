package model.enums;
/**
 *<strong>Sort</strong>
 * - enum with types of sort and text description in russian.
 *  Used for choosing sort method and displaying in comboboxes.
 * @author Nikita Bodry
 * @version 1.0
 */
public enum Sort
{
    FIRST_NAME("Имя"),
    LAST_NAME("Фамилия"),
    PATRONYMIC("Отчество"),
    SCORE("Сумма баллов"),
    DATE_OF_BIRTH("Дата рождения");

    private final String text;

    Sort(final String text)
    {
        this.text = text;
    }

    @Override
    public String toString()
    {
        return text;
    }
}
