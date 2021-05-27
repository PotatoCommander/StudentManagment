package model.enums;

import model.abstractions.Observable;

import java.util.ArrayList;

/**
 *<strong>Actions</strong>
 * - enum with actions and text information about them.
 * @author Nikita Bodry
 * @version 1.0
 */
public enum Actions
{
    ITEM_ADDED("Добавлен объект"),
    ITEM_REMOVED("Удален объект"),
    ITEM_REPLACED("Объект отредактирован"),
    ITEMS_ADDED("Объекты загружены"),
    LIST_CLEARED("Список очищен"),
    LIST_SORTED("Список отсортирован по:"),
    FILE_OPENED("Файл прочтен: "),
    FILE_SAVED("Файл сохранен: "),
    FILE_CREATED("Создан файл: "),
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
