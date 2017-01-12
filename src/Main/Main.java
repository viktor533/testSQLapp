package Main;

import Windows.StartWindow;

/**
 * Created by Viktor on 13.12.2016.
 */

/**
 *  Главный класс. Запускает стартовое окно.
 */
public class Main {
    /**
     * Главная функция. Запускает стартовое окно
     * @param args параметры кмандной строки. Не обрабатываются
     */
    public static void main(String[] args) {
        StartWindow app = new StartWindow();
        app.setVisible(true);
    }
}
