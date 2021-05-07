package Model;

public  enum ColumnForSort
{
    MATH("Математика"),
    PHYSICS("Физика"),
    RUSSIAN("Русский"),
    FIRST_NAME("Имя"),
    LAST_NAME("Фамилия"),
    PATRONYMIC("Отчество"),
    DATE_OF_BIRTH("Дата рождения");

    private final String text;

    ColumnForSort(final String text)
    {
        this.text = text;
    }

    @Override
    public String toString()
    {
        return text;
    }
}
