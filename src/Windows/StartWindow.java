package Windows;

import Sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Viktor on 13.12.2016.
 */

/**
 * Класс для создания стартового стартового окна. Предлагает выбор для дальнейщих действий
 */
public class StartWindow extends JFrame {
    /** Кнопка для перехода в окно автоматической генерации таблицы */
    private JButton generateButton;
    /** Кнопка для перехода в окно редактирования и просмотра таблицы */
    private JButton editButton;
    /** Кнопка для перехода в окно настроек */
    private JButton settingsButton;
    /** Класс коннектор для связи с SQL таблицей */
    private SQLConnection connection;

    /**
     * Конструктор, выполняющий вывод и заполнение стартового окна
     */
    public StartWindow() {
        super("Table application");
        this.setBounds(100, 100, 250, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        connection = new SQLConnection(this);

        editButton = new JButton("Edit Table");
        editButton.addActionListener(new EditButtonListener ());
        generateButton = new JButton("Generate Table");
        generateButton.addActionListener(new GenerateButtonListener());
        settingsButton = new JButton("Settings");
        settingsButton.addActionListener(new SettingsButtonListener());

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new GridLayout(3, 1, 10, 10));
        mainPanel.add(generateButton);
        mainPanel.add(editButton);
        mainPanel.add(settingsButton);
        this.add(mainPanel);
    }

    /**
     * Класс слушатель нажатия кнопки перехода в окно редактирования таблицы.
     * При нажатии создает и запускает окно редактирования.
     */
    class EditButtonListener implements ActionListener {
        /**
         * Метод, выполняющий обработку события нажатия кнопки Edit Table
         * @param event Обрабаываемое событие
         */
        public void actionPerformed(ActionEvent event) {
            EditWindow editWindow = new EditWindow(connection);
            editWindow.setVisible(true);
        }
    }

    /**
     * Класс слушатель нажатия кнопки перехода в окно автоматической генерации таблицы.
     * При нажатии создает и запускает окно редактирования.
     */
    class GenerateButtonListener implements ActionListener {
        /**
         * Метод, выполняющий обработку события нажатия кнопки Generate Table
         * @param e Обрабаываемое событие
         */
        public void actionPerformed(ActionEvent e) {
            GenerateWindow generateWindow = new GenerateWindow(connection);
            generateWindow.setVisible(true);
        }
    }

    /**
     * Класс слушатель нажатия кнопки перехода в окно настроек
     * При нажатии создает и запускает окно настроек
     */
    class SettingsButtonListener implements ActionListener {
        /**
         * Метод, выполняющий обработку события нажатия кнопки Settings
         * @param e Обрабатываемое событие
         */
        public void actionPerformed(ActionEvent e) {
            SettingsWindow settingsWindow = new SettingsWindow(connection);
            settingsWindow.setVisible(true);
        }
    }
}
