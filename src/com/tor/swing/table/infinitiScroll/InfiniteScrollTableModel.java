package com.tor.swing.table.infinitiScroll;

/**
 * User: tor
 * Date: 04.06.2026
 * Time: 20:17
 */

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class InfiniteScrollTableModel<T> extends AbstractTableModel {
    private List<T> allData = new ArrayList<T>();    // Полный список (20 000 записей)
    private List<T> visibleData = new ArrayList<T>(); // То, что сейчас в таблице
    private CBP[] columns;
    private int batchSize = 100; // Размер порции догрузки
    private boolean loading = false;

    public InfiniteScrollTableModel(CBP[] columns) {
        this.columns = columns;
    }

    public void setFullData(List<T> data) {
        this.allData = (data != null) ? data : new ArrayList<T>();
        this.visibleData.clear();
        loadNextBatch(); // Загружаем первые 100 строк
    }

    public void loadNextBatch() {
        if (visibleData.size() >= allData.size() || loading) return;

        loading = true;
        int start = visibleData.size();
        int end = Math.min(start + batchSize, allData.size());

        // Добавляем следующую порцию в видимый список
        for (int i = start; i < end; i++) {
            visibleData.add(allData.get(i));
        }

        // Уведомляем таблицу о вставке строк (эффективнее, чем fireTableDataChanged)
//        System.out.println("fireTableDataChanged "+ (end - 1));
        fireTableRowsInserted(start, end - 1);
        loading = false;
    }

    public int getRowCount() {
        return visibleData.size();
    }

    public int getColumnCount() {
        return columns.length;
    }

    public String getColumnName(int col) {
        return columns[col].getTitle();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        T item = visibleData.get(rowIndex);
        return getFieldValue(item, columns[columnIndex].getPropertyPath());
    }

    public boolean isLoading() {
        return loading;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createExample();
            }
        });
    }

    public static void createExample() {
        final String filePath = "big.csv";
        final InfiniteScrollTableModel<MyData> model = new InfiniteScrollTableModel<MyData>(new CBP[]{
                new CBP("fio", String.class, "ПІБ", false),
                new CBP("dop", String.class, "Data", false),
                new CBP("phone", String.class, "Телефон", false),
                new CBP("email", String.class, "Email", false)
        });
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                model.setFullData(getData(filePath));
            }
        });
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                Adjustable adjustable = e.getAdjustable();

                // Проверка: докрутил ли пользователь до самого низа?
                int extent = adjustable.getVisibleAmount();
                int value = adjustable.getValue();
                int maximum = adjustable.getMaximum();

                if (value + extent >= maximum * 0.9) {
                    // Вызываем догрузку в потоке обработки событий Swing
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            model.loadNextBatch();
                        }
                    });
                }
            }
        });
        createAndShowGUI(scrollPane);
    }

    private static void createAndShowGUI(JScrollPane pane) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Table Scrolling Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(pane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private Object getFieldValue(Object obj, String path) {
        try {
            if (obj == null || path == null) return null;

            String[] parts = path.split("\\.", 2);
            String currentField = parts[0];

            // Пытаемся найти геттер getXxx или isXxx (для Boolean)
            Method method = findGetter(obj.getClass(), currentField);
            Object value = method.invoke(obj);

            if (parts.length > 1 && value != null) {
                return getFieldValue(value, parts[1]); // Рекурсия для вложенных полей
            }
            return value;
        } catch (Exception e) {
            return null; // Или логирование ошибки рефлексии
        }
    }

    private Method findGetter(Class<?> clazz, String fieldName) throws NoSuchMethodException {
        String capitalized = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        try {
            return clazz.getMethod("get" + capitalized);
        } catch (NoSuchMethodException e) {
            // Если get не найден, пробуем is (стандарт для boolean)
            return clazz.getMethod("is" + capitalized);
        }
    }

    private static List<MyData> getData(String filePath) {
        BufferedReader reader = null;
        int linesToRead = 10000;
        List<MyData> rez = new ArrayList<MyData>(linesToRead);
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            int lineCount = 0;
            System.out.println("Reading first " + linesToRead + " lines from " + filePath + ":");

            while ((line = reader.readLine()) != null && lineCount < linesToRead) {
                if (lineCount == 0) {
                    lineCount++;
                    continue;
                }
//                System.out.println(line);
                String[] fields = line.split("\\|"); //124
                final MyData myData = new MyData();
                myData.setFio(fields[0]);
                myData.setDob(fields[1]);
                myData.setPhone(fields[2]);
                myData.setEmail(fields[3]);

                rez.add(myData);
                lineCount++;
            }

            if (lineCount < linesToRead) {
                System.out.println("Reached end of file before reading " + linesToRead + " lines.");
            }

        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Always close the reader to release resources
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Error closing the file reader: " + e.getMessage());
                }
            }
        }
        System.out.println(linesToRead);
        return rez;
    }

    private static class MyData {
        String fio, dob, phone, email;

        public String getFio() {
            return fio;
        }

        public void setFio(String fio) {
            this.fio = fio;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}