package com.tor.pattens.mvc.model;

import org.apache.log4j.Logger;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

/**
 * Created by IntelliJ IDEA.
 * User: tor
 * Date: 24.03.2009
 * Time: 14:19:18
 * Здесь в качестве хранилища объектов-слушателей выступает экземпляр класса EventListenerList.
 * Для каждого компонента на форме, который заинтересован в обновление своих данных, создается класс,
 * наследующий интерфейс ChangeListener (см. класс StartUp.java). Далее экземпляр этого класса
 * добавляется в хранилище EventListenerList при помощи метода addChangeListener (ChangeListener l).
 * При нажатии пользователем кнопки Add, происходит добавление данных в модель - экземпляр класса
 * DataModel, при помощи метода addData(String p_str), а затем вызов метода fireChange(), внутри которого
 * в цикле происходит опрос всех участников-слушателей с последующим обновлением модели через
 * них каждого компонента.
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
        //переберает с конца
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
