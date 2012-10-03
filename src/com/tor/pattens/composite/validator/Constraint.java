package com.tor.pattens.composite.validator;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 21.03.12
 * Time: 14:24
 * $Rev::               $:  Revision of last commit
 * $Author::            $:  Author of last commit
 * $Date::              $:  Date of last commit
 */
public interface Constraint<T> {
    /**
     * ��������� �� ��������
     */
    public boolean isValid(T t);

    /**
     * ��������� �� ������ � ������ ������
     */
    public String getMessage();
}
