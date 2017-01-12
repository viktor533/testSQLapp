package Windows;
/**
 * Created by Viktor on 13.12.2016.
 */

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;
import Sql.*;

/**
 * Класс для создания окна редактирования таблицы.
 * Предоставляет возможность просмотра таблицы и добавления в нее элементов.
 */
public class EditWindow extends JFrame {
    /** Класс соединения с SQL таблицей */
    private SQLConnection connection;
    /** Текстовое поле, для ввода имени таблицы */
    private JTextField inputTableName;
    /** Текстовое поле для ввода имени добавляемого элемента */
    private JTextField inputName;
    /** Текствое поле для ввода фамилии добавляемого элемента */
    private JTextField inputSurname;
    /** Текстовое поле для ввода отчества добавляемого элемена */
    private JTextField inputPatronymic;
    /** Текствое поле для ввода даты рождения добавляемого элемента*/
    private JTextField inputBirthday;

    /** Фалжок мужского пола */
    private JRadioButton radioMale;

    /**
     * Конструктор,
     * @param connection класс
     */
    public EditWindow(SQLConnection connection) {
        super("Edit table");
        this.connection = connection;
        this.setBounds(100, 100, 260, 300);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new GridLayout(8, 2, 10, 10));

        inputTableName = new JTextField(connection.getTableName(), connection.stringSize);
        inputName = new JTextField("", connection.stringSize);
        inputSurname = new JTextField("", connection.stringSize);
        inputPatronymic = new JTextField("", connection.stringSize);
        inputBirthday = new JTextField("", connection.stringSize);
        JButton addButton = new JButton("add");
        JButton showButton = new JButton("show");
        JButton showUniqueButton = new JButton("Show unique");
        JRadioButton radioFemale = new JRadioButton("female");
        radioMale = new JRadioButton("male");

        mainPanel.add(new JLabel("Table name: "));
        mainPanel.add(inputTableName);
        mainPanel.add(new JLabel("Name: "));
        mainPanel.add(inputName);
        mainPanel.add(new JLabel("Surname: "));
        mainPanel.add(inputSurname);
        mainPanel.add(new JLabel("Patronymic: "));
        mainPanel.add(inputPatronymic);
        mainPanel.add(new JLabel("Birthday: "));
        mainPanel.add(inputBirthday);

        ButtonGroup group = new ButtonGroup();
        group.add(radioMale);
        group.add(radioFemale);
        mainPanel.add(radioMale);
        radioMale.setSelected(true);
        mainPanel.add(radioFemale);

        mainPanel.add(new JLabel(""));
        addButton.addActionListener(new AddButtonListener());
        mainPanel.add(addButton);

        showButton.addActionListener(new ShowButtonListener());
        mainPanel.add(showButton);

        showUniqueButton.addActionListener(new ShowUniqueButtonListener());
        mainPanel.add(showUniqueButton);

        this.add(mainPanel);
    }

    /**
     * Класс слушатель события нажатия кнопки Show
     * Выводит окно с таблицей элементов SQL таблицы
     */
    class ShowButtonListener implements ActionListener {
        /**
         * Метод, выполняющий обработку собьытия нажатия кнопки Show
         * @param event Обрабатываемое событие
         */
        public void actionPerformed(ActionEvent event) {
            try {
                connection.setTableName(inputTableName.getText());
                long start = System.currentTimeMillis();
                TableWindow.outTable(System.currentTimeMillis() - start, connection.getAll());
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    /**
     * Класс слушатель события нажатия кнопки Show Unique
     * Выводит окно с таблицей уникальных элементов SQL таблицы
     */
    class ShowUniqueButtonListener implements ActionListener {
        /**
         * Метод, выполняющий обработку события нажатия кнопки Show Unique
         * @param event обрабатываемое событие
         */
        public void actionPerformed(ActionEvent event) {
            try {
                connection.setTableName(inputTableName.getText());
                long start = System.currentTimeMillis();
                TableWindow.outTable(System.currentTimeMillis() - start, connection.getUnique());
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    /**
     * Класс слушатель события нажатия кнопеи Add
     * При нажатии генерирует запрос к SQL таблице добавления нового элемента с введенными данными
     */
    class AddButtonListener implements ActionListener {
        /**
         * Метож, выполняющий обработку события нажатия кнопки Add
         * @param event Обрабатываемое событие
         */
        public void actionPerformed(ActionEvent event) {
            try {
                connection.setTableName(inputTableName.getText());
                if (radioMale.isSelected()) {
                    connection.add(inputName.getText(), inputSurname.getText(), inputPatronymic.getText(), inputBirthday.getText(), 1);
                } else {
                    connection.add(inputName.getText(), inputSurname.getText(), inputPatronymic.getText(), inputBirthday.getText(), 0);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());

            } catch (CorrectInputException e) {
                JOptionPane.showMessageDialog(null, e.getMsg());
            }
        }
    }

}