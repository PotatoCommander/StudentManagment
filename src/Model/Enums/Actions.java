package Model.Enums;

public enum Actions
{
    ITEM_ADDED("Добавлен объект"),
    ITEM_REMOVED("Удален объект"),
    ITEM_REPLACED("Объект отредактирован"),
    ITEMS_ADDED("Объекты загружены"),
    LIST_CLEARED("Список очищен"),

    OPENED_FILE("Файл прочтен: "),
    SAVED_FILE("Файл сохранен: "),
    CREATED_FILE("Создан файл: "),

    FAIL_NEW_FILE("Не удалось создать файл."),
    FAIL_OPEN_FILE("Не удалось открыть файл: "),
    FAIL_SAVE_FILE("Не удалось сохранить файл: "),

    NONE("");


    private final String text;

    Actions(final String text)
    {
        this.text = text;
    }

    @Override
    public String toString()
    {
        return text;
    }
}
