package Windows;

import Sql.SQLConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;

/**
 * Created by Viktor on 13.12.2016.
 */

/**
 * Класс окна настроек
 */
public class SettingsWindow extends JFrame {
    /** Класс соединения с SQL таблицей*/
    private SQLConnection connection;
    /** Текстовое поле для ввода адреса сервера */
    private JTextField hostName;
    /** Текстовое поле для ввода имения базы данных */
    private JTextField databaseName;
    /** Текстовое поля для ввода имени пользователя */
    private JTextField username;
    /** Текстовое поле для ввода пароль */
    private JTextField password;

     /**
     * Конструктор, выполняющий вывод и заполнение окна настроек.
     * @param connection Класс соединения с SQL таблицей
     */
    public SettingsWindow(SQLConnection connection) {
        super("Settings");
        this.connection = connection;
        this.setBounds(100, 100, 350, 200);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new GridLayout(5, 2, 10, 10));

        JButton saveButton = new JButton("save");
        hostName = new JTextField(connection.getUrl());
        databaseName = new JTextField(connection.getDataBaseName());
        username = new JTextField(connection.getUser());
        password = new JTextField(connection.getPassword());

        mainPanel.add(new JLabel("Host name: "));
        mainPanel.add(hostName);
        mainPanel.add(new JLabel("Database name:: "));
        mainPanel.add(databaseName);
        mainPanel.add(new JLabel("Username: "));
        mainPanel.add(username);
        mainPanel.add(new JLabel("Password: "));
        mainPanel.add(password);

        mainPanel.add(new JLabel(""));
        saveButton.addActionListener(new SaveButtonListener());
        mainPanel.add(saveButton);
        this.add(mainPanel);
    }

    /**
     * Класс слушатель события нажатия кнопки Save
     * При нажатии обновляет параметры класса connection
     */
    class SaveButtonListener implements ActionListener {
        /**
         * Метод, выполняющий обработку события нажатия кнопки Save
         * @param event обрабатываемое событие
         */
        public void actionPerformed(ActionEvent event) {
            connection.setUrl(hostName.getText());
            connection.setDataBaseName(databaseName.getText());
            connection.setUser(username.getText());
            connection.setPassword(password.getText());
        }
    }

}
