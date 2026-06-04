package com.tor.swing.table.infinitiScroll;

/**
 * User: tor
 * Date: 04.06.2026
 * Time: 20:19
 * ecwo ColumnBeanProperty
 */
public class CBP {
    private final String propertyPath; // например, "author.name"
    private final Class<?> type;       // класс данных
    private final String title;        // заголовок колонки
    private final boolean editable;    // можно ли редактировать

    public CBP(String propertyPath, Class<?> type, String title, boolean editable) {
        this.propertyPath = propertyPath;
        this.type = type;
        this.title = title;
        this.editable = editable;
    }

    // Геттеры
    public String getPropertyPath() { return propertyPath; }
    public Class<?> getType() { return type; }
    public String getTitle() { return title; }
    public boolean isEditable() { return editable; }
}
