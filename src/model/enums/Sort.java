package model.enums;

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
