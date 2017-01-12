package Windows;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Viktor on 13.12.2016.
 */

/**
 * Класс для создания окна с таблицей элементов SQL таблицы
 */
public class TableWindow {
    /** массив строк заголовков таблицы */
    public static final String[] columnNames = {"surname", "name", "patronymic", "birthday", "gender"};

    /**
     * Вычисляет размер полученной таблицы. Обрабатывает исключение ошибки SQL запроса SQLException
     * @param resultSet Множество элементов SQL таблицы
     * @return количество элементов SQL таблицы
     */
    public static int getResultSetSize(ResultSet resultSet) {
        int size = 0;
        try {
            resultSet.last();
            size = resultSet.getRow();
            resultSet.beforeFirst();
        }
        catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return size;
    }


    /**
     * Выводит окно с талицей элементов SQL таблицы
     * @param time время в милисеккундах, потраченное на выполнение SQL запроса
     * @param result Полученное множество элементов SQL таблицы
     */
    public static void outTable(long time, ResultSet result) {
        try {
            String[][] tableData = new String[getResultSetSize(result)][5];
            int i = 0;
            while (result.next()) {
                tableData[i][0] = result.getString(1);
                tableData[i][1] = result.getString(2);
                tableData[i][2] = result.getString(3);
                tableData[i][3] = result.getDate(4).toString();
                tableData[i][4] = Integer.toString(result.getInt(5));
                i++;
            }

            JTable table = new JTable(tableData, columnNames);
            JFrame tableFrame = new JFrame("sql table");
            tableFrame.setBounds(100, 100, 500, 400);
            JPanel tablePanel = new JPanel();
            tablePanel.add(table);
            JScrollPane scrollPane = new JScrollPane(table);
            tablePanel.add(scrollPane);
            tablePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            tableFrame.setLayout(new FlowLayout());
            tableFrame.add(new JLabel("Request executed in " + time + "ms"));
            tableFrame.add(tablePanel);
            tableFrame.setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
