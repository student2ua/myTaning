package com.tor.pattens.mvc.simple1.model;

import org.apache.log4j.Logger;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 24.03.2009
 * Time: 14:19:18
 * ����� � �������� ��������� ��������-���������� ��������� ��������� ������ EventListenerList.
 * ��� ������� ���������� �� �����, ������� ������������� � ���������� ����� ������, ��������� �����,
 * ����������� ��������� ChangeListener (��. ����� StartUp.java). ����� ��������� ����� ������
 * ����������� � ��������� EventListenerList ��� ������ ������ addChangeListener (ChangeListener l).
 * ��� ������� ������������� ������ Add, ���������� ���������� ������ � ������ - ��������� ������
 * DataModel, ��� ������ ������ addData(String p_str), � ����� ����� ������ fireChange(), ������ ��������
 * � ����� ���������� ����� ���� ����������-���������� � ����������� ����������� ������ �����
 * ��� ������� ����������.
 */
public class DataModel {
    private static final Logger log = Logger.getLogger(DataModel.class);
    private String date;
    private EventListenerList eventListenerList = new EventListenerList();
    private ChangeEvent changeEvent = null;

    public void addData(String txt) {
        this.date = txt;
        fireChange();
    }

    private void fireChange() {
        Object[] listeners = eventListenerList.getListenerList();
        //���������� � �����
        for (int it = listeners.length - 2; it >= 0; it -= 2) {
            if (listeners[it] == ChangeListener.class) {
                if (changeEvent == null) {
                    changeEvent = new ChangeEvent(this);
                }
                ((ChangeListener) listeners[it + 1]).stateChanged(changeEvent);
            }
        }
    }

    public String getDate() {
        return date;
    }

    public void addChangeListener(ChangeListener listener) {
        eventListenerList.add(ChangeListener.class, listener);
    }

    public void removeChangeListener(ChangeListener listener) {
        eventListenerList.remove(ChangeListener.class, listener);
    }

}
