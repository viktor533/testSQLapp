package Sql;

/**
 * Created by Viktor on 12.01.2017.
 */

/**
 * Исключение устновки соединения с SQL базой данных
 */
public class SQLConnectException extends Exception {
    /** Сообщение об ощибке */
    private String massage;

    /**
     * Конструктор, создающий исключение
     * @param massage Сообщение об ошибке
     */
    SQLConnectException (String massage) {
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
