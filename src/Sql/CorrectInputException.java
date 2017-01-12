package Sql;

/**
 * Created by Viktor on 13.12.2016.
 */

/**
 * Исключение корректных входных данных к запросу SQL таблицы
 */
public class CorrectInputException extends Exception {
    /** Сообщение об ощибке */
    private String massage;

    /**
     * Конструктор, создающий исключение
     * @param massage Сообщение об ошибке
     */
    CorrectInputException (String massage) {
        this.massage = massage;
    }

    /**
     * Возврашает собщение об ошибке
     * @return сообщение с ошибкой
     */
    public String getMsg() {
        return massage;
    }
}
