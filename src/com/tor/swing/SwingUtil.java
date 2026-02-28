package com.tor.swing;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * User: tor
 * Date: 21.06.19
 * Time: 14:50
 * To change this template use File | Settings | File Templates.
 */
public class SwingUtil {
    private SwingUtil() {
    }

    /**
     * https://www.logicbig.com/tutorials/java-swing/generate-jtable-model.html
     */
    public static <T> TableModel createTableModel(Class<T> beanClass, final List<T> list) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
            final List<String> columns = new ArrayList<String>();
            final List<Method> getters = new ArrayList<Method>();

            for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
                String name = pd.getName();
                if (name.equals("class")) {
                    continue;
                }
                name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
                String[] s = name.split("(?=\\p{Upper})");
                String displayName = "";
                for (String s1 : s) {
                    displayName += s1 + " ";
                }

                columns.add(displayName);
                Method m = pd.getReadMethod();
                getters.add(m);
            }

            TableModel model = new AbstractTableModel() {
                @Override
                public String getColumnName(int column) {
                    return columns.get(column);
                }

                @Override
                public int getRowCount() {
                    return list.size();
                }

                @Override
                public int getColumnCount() {
                    return columns.size();
                }

                @Override
                public Object getValueAt(int rowIndex, int columnIndex) {
                    try {
                        return getters.get(columnIndex).invoke(list.get(rowIndex));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            return model;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> JPanel createParamsPanel(T updateParams) {
        JPanel rez = new JPanel(new GridLayout(0, 2));
        try {
            BeanInfo info = Introspector.getBeanInfo(updateParams.getClass());
            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                try {
                    String name = pd.getName();
                    if (name.equals("class")) {
                        continue;
                    }
                    Method readMethod = pd.getReadMethod();
                    Object val = readMethod.invoke(updateParams, new Object[0]);
                    // Or you may want to access the field directly... but it has to be not-private
                    // Field f = c.getDeclaredField(pd.getName());
                    // Object val = f.get(b);
                    java.beans.PropertyEditor editor = java.beans.PropertyEditorManager.findEditor(readMethod.getReturnType());
                    if (editor != null) {
                        editor.setValue(val);
                        editor.addPropertyChangeListener(new PropertyChangeListener() {
                            @Override
                            public void propertyChange(PropertyChangeEvent evt) {
                                System.out.println("evt = " + evt);
                            }
                        });
                        rez.add(new JLabel(pd.getDisplayName()));
                        if (editor.supportsCustomEditor()) {
                            rez.add(editor.getCustomEditor());
                        } else {
                            if (editor.getTags() != null) {
                                rez.add(new PropertySelector(editor));
                            } else {
                                if (editor.getAsText() != null) {
                                    rez.add(new PropertyText(editor));
                                } else {
                                    rez.add(new JPanel());
                                }
                            }
                        }
                    }

                } catch (SecurityException e1) {
                    e1.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        } catch (IntrospectionException ex) {
            ex.printStackTrace();
        }

        return rez;
    }

    //************************** JTree **************
    public static void expandAll(JTree tree) {
        for (int i = 0; i < tree.getRowCount(); i++) {
            tree.expandRow(i);
        }
    }

    public static void expandTree(JTree tree) {
        expandTree(0, tree);
    }

    public static void expandTree(int level, JTree tree) {
        boolean wasExpanded = false;
        int rowcount = tree.getRowCount();
        for (int i = 0; i < rowcount; i++) {
            TreePath nodePath = tree.getPathForRow(i);
            if (!tree.isExpanded(nodePath)) {
                tree.expandPath(nodePath);
                if (tree.isExpanded(nodePath)) {
                    wasExpanded = true;
                }
            }
        }
        if (wasExpanded) expandTree(++level, tree);
    }

    /**<pre>
     * // после какого-то действия, например после выбора файла
     JOptionPane.showMessageDialog(this, "Файл успешно загружен!\nТеперь нажмите «Далее»");

     // акцентируем кнопку
     blinkButtonBorder(btnNext, 3, 300);   // 3 раза, каждое мигание 300 мс
     </> */
    public static void blinkButtonBorder(final JButton button, int times, int delayMs) {
        if (button == null) return;

        final Border originalBorder = button.getBorder();
        final Border highlightBorder = BorderFactory.createLineBorder(new Color(255, 80, 0), 3); // яркий оранжевый

        final Timer timer = new Timer(delayMs, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // переключаем бордер
                if (button.getBorder() == originalBorder) {
                    button.setBorder(highlightBorder);
                } else {
                    button.setBorder(originalBorder);
                }//To change body of implemented methods use File | Settings | File Templates.
            }
        });

        timer.setRepeats(true);
        timer.setInitialDelay(0);

        // Сколько раз мигнём (каждое мигание = два переключения: on → off)
        int totalTicks = times * 2;

        Timer stopTimer = new Timer(delayMs * totalTicks + 100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                timer.stop();
                button.setBorder(originalBorder);           // обязательно возвращаем оригинал
                ((Timer) ev.getSource()).stop();
            }
        });
        stopTimer.setRepeats(false);

        timer.start();
        stopTimer.start();
    }
}
