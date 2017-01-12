package Windows;

import Sql.CorrectInputException;
import Sql.SQLConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

/**
 * Created by Viktor on 13.12.2016.
 */

/**
 * Класс окна автоматической геенрации таблицы
 */
public class GenerateWindow extends JFrame {
    /** Такстовое поле, для ввода имени таблицы */
    private JTextField tableName;
    /** Текстовое поле для вводы буквы, на которую будут начинается целивые данные*/
    private JTextField charField;

    /** Класс соединения с SQL таблицей*/
    private SQLConnection connection;
    /** Буква, на которую будут начинаться целевые данные. По умолчанию s */
    private char findingChar = 's';
    /** Количество генерируемых элементов таблицы */
    private int genSize = 1000;
    /** Количество целевых элементов, начинающихся с указанной буквы */
    private int findingSize = 100;

    /**
     * Конструктор, выполняющий вывод и заполнение окна генерации таблицы
     * @param connection Класс соединения с SQL таблицей
     */
    public GenerateWindow(SQLConnection connection) {
        super("Generate table");
        this.setBounds(100, 100, 300, 150);
        this.connection = connection;

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new GridLayout(3, 2, 10, 10));

        tableName = new JTextField(connection.getTableName(), 20);
        JButton generateButton = new JButton("Generate");
        generateButton.addActionListener(new GenButtonListener());
        JButton showButton = new JButton("Show");
        showButton .addActionListener(new ShowButtonListener());

        mainPanel.add(new JLabel("Table name: "));
        mainPanel.add(tableName);
        mainPanel.add(new JLabel(""));
        mainPanel.add(generateButton);

        JPanel charPanel = new JPanel();
        charPanel.setLayout(new FlowLayout());
        charField = new JTextField(""+findingChar, 1);
        charPanel.add(new JLabel("get 100 male on:"));
        charPanel.add(charField);
        mainPanel.add(charPanel);

        mainPanel.add(showButton);
        this.add(mainPanel);
    }

    /**
     * Класс слушатель события нажатия кнопки Generate
     * При нажатии выполняет заполнение таблицы в указанном размере данными,
     * состоящими из рандомно сгенерированных строк
     */
    class GenButtonListener implements ActionListener {
        /** Класс генерации рандомных данны */
        private Random rand = new Random();

        /**
         *  Генерирует произвольную строчку, заданной длины
         * @param onF флаг того, что строчка должна начинаться на заданную букву
         * @return Сгенирированная строка
         */
        private String randomString(boolean onF) {
            rand = new Random();
            int size = 1 + rand.nextInt(20);
            char[] randStr = new char[size];
            if (onF) {
                randStr[0] = findingChar;
            } else {
                randStr[0] = (char)('a' + rand.nextInt(27));
                if (!onF && randStr[0] == findingChar) {
                    randStr[0]++;
                }
            }
            for (int i = 1; i < size; i++) {
                randStr[i] = (char)('a' + rand.nextInt(27));
            }
            return new String(randStr);
        }

        /**
         * Генерирует произвольную дату
         * @return Строка с произвольной датой
         */
        private String randData() {
            int y = 1900 + rand.nextInt(100);
            int m = 1 + rand.nextInt(12);
            int d;
            if (m == 2) {
                d = 1 + rand.nextInt(27);
            } else {
                d = 1 + rand.nextInt(30);
            }
            return "" + y + "." + m + "." + d;
        }

        /**
         * Метод, выполняющий обработку события нажатия кнопки Generate
         * @param event Обрабатываемое событие
         */
        public void actionPerformed(ActionEvent event) {
            try {
                connection.setTableName(tableName.getText());
                connection.createTable();
            } catch (SQLException e) {
                if (e.getMessage().indexOf("already") != -1 && e.getMessage().indexOf("exists") != -1) {
                } else {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
            try {
                for (int i = 0; i < genSize - findingSize; i++) {
                    connection.add(randomString(false), randomString(false), randomString(false), randData(), rand.nextInt(2));
                    //connection.add("a", "b", "c", "1991.1.1", 0);
                }
                for (int i = 0; i < findingSize; i++) {
                    connection.add(randomString(true), randomString(false), randomString(false), randData(), 1);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            } catch (CorrectInputException e) {
                JOptionPane.showMessageDialog(null, "Something strange occurred during the generation\n" + e.getMsg());
            }
        }
    }

    /**
     * Класс слушатель обработки события нажатия кнопки Show
     * При нажатии создает и выводит окно с таблицей элементов
     */
    class ShowButtonListener implements ActionListener {
        /**
         * Метод, выполняющий обработку события нажатия кнопки Show
         * @param event Обрабатываемое событие
         */
        public void actionPerformed(ActionEvent event) {
            try {
                if (charField.getText().length() > 0) {
                    findingChar = charField.getText().charAt(0);
                }
                long start = System.currentTimeMillis();
                connection.setTableName(tableName.getText());
                ResultSet result = connection.getMaleOnF(findingChar);
                TableWindow.outTable(System.currentTimeMillis() - start, result);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }
}
