package Sql;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Viktor on 13.12.2016.
 */

/**
 * Класс, отвечающий за связь с SQL таблицей
 */
public class SQLConnection {
    /** Адрес SQL сервера. Значение по умолчанию: jdbc:mysql://localhost:3306/ */
    private String url = "jdbc:mysql://localhost:3306/";
    /** Название базы данных. Значение по умолчанию: mydb1 */
    private String dataBaseName = "mydb1";
    /** Название таблицы в базе данных. Значение по умолчанию: table1*/
    private String tableName = "table1";
    /** Имя пользователя. Значение по умолчанию: root */
    private String user = "root";
    /** Пароль пользователя. По умолчанию пустой. */
    private String password = "";
    /** Константа размера строки. */
    public final int stringSize = 20;

    /** Класс коннектор с SQL таблицей */
    private Connection con;
    /** Класс отправитель запросов к SQL таблицы по установленному соединению. */
    public static Statement statmt;

    /**
     * Конструктор класса. Вызывает функцию инициализации соединения.
     * @param mainComponent Класс вызываемого окна, для вывода поверх него диалогового сообдения
     *                      в случае исключения инициализации соединения с SQL таблицей
     */
    public SQLConnection(JFrame mainComponent) {
        try {
            init();
        } catch (SQLConnectException e) {
            JOptionPane.showMessageDialog(mainComponent, e.getMsg());
        }
    }

    /**
     * Функция инициализации соединения.
     * В случае успешного соединения создает экземпляр класса Statement - statmt,
     * В случае неуспешного соединения выводит соответствующее соединение.
     */
    public void init() throws SQLConnectException {
        try {
            con = DriverManager.getConnection(url + dataBaseName, user, password);
            statmt  =  con.createStatement();
        }  catch (SQLException s) {
            throw new SQLConnectException(s.getMessage());
        }
    }

    /**
     * Запрос создания SQL таблицы.
     * @throws SQLException Исключение SQL запросв. В случае возникновения, оно передастся в выхыващий меотд.
     */
    public void createTable() throws SQLException {
        statmt.execute("CREATE TABLE " + tableName + " (surname VARCHAR(20), name VARCHAR(20), patronymic VARCHAR(20), birthday DATE, gender TINYINT(1));");
    }

    /**
     * Запрос добавление новой строчки в SQL таблицу.
     * @param name Значение столбца имя.
     * @param surName Значение столбца фамилия
     * @param patronymic Значение столбца отчество
     * @param birthday Значение столбца день рождения
     * @param gender Значение столбца пол
     * @throws SQLException Исключение SQL запросв. В случае возникновения, оно передастся в выхыващий меотд.
     * @throws CorrectInputException Возниувкт в случае некорректорых входных параметров
     */
    public void add(String name, String surName, String patronymic, String birthday, int gender) throws SQLException, CorrectInputException {
        correctInput(name, surName, patronymic, birthday, gender);
        statmt.execute("INSERT INTO " + tableName + " VALUES ('" + name + "', '" + surName + "', '" + patronymic + "', '" + birthday +  "', '" + gender + "');");
    }

    /**
     * Запрос на получение всех элементов таблицы
     * @return Множество элементов таблицы
     * @throws SQLException Исключение SQL запросв. В случае возникновения, оно передастся в выхыващий меотд.
     */
    public ResultSet getAll() throws SQLException {
        return statmt.executeQuery("SELECT * from " + tableName);
    }

    /**
     * Запрос на получение всех уникальных элементов таблицы
     * @return Можество уникальных элементов таблицы
     * @throws SQLException Исключение запроса к базе данных
     */
    public ResultSet getUnique() throws SQLException {
        return statmt.executeQuery("SELECT DISTINCT * from " + tableName);
    }

    /**
     * Запрос на получение всех элементов твблицы, в которых фамилия начинается с заданной буквы
     * @param f бука, с которой начинаются фамилии в искомых элементов
     * @return Множество элементов, в которых фамилия начинается на заданную букву
     * @throws SQLException Исключение запроса к базе данных
     */
    public ResultSet getMaleOnF(char f) throws SQLException {
        return statmt.executeQuery("SELECT DISTINCT * from " + tableName + " WHERE gender=1 AND surname LIKE '" + f + "%';");
    }

    /**
     * Проверка корректности данных в запросе:
     * Строчи с ФИО не больше заданной длины
     * Пол имеет значение 1 или 0
     * Дата в формате yyyy.MM.dd
     * @param name Имия
     * @param surName Фамилия
     * @param patronymic Отчество
     * @param birthday Дата рождения
     * @param gender Пол
     * @throws CorrectInputException Исключение, генерируемое в случае некорректных данных
     */
    private void correctInput(String name, String surName, String patronymic, String birthday, int gender) throws CorrectInputException {
        if (name.length() > stringSize) {
            throw new CorrectInputException("Name is too long! It must contain less than 20 characters.");
        }
        if (surName.length() > stringSize) {
            throw new CorrectInputException("Surname is too long! It must contain less than 20 characters.");
        }
        if (patronymic.length() > stringSize) {
            throw new CorrectInputException("Patronymic is too long! It must contain less than 20 characters.");
        }
        if (gender != 0 && gender != 1) {
            throw new CorrectInputException("Incorrect gender! It must be 0 or 1.");
        }
        DateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        try {
            format.parse(birthday);
        } catch (ParseException e) {
            throw new CorrectInputException("Incorrect date format! It must be yyyy.MM.dd");
        }
    }

    /**
     * Возвращает имя таблицы
     * @return Имя таблицы
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Задает имя таблицы
     * @param tableName Имя таблицы
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * Возвращает адерес сервера
     * @return Адрес сервера
     */
    public String getUrl() {
        return url;
    }

    /**
     * Задает адрес сервера
     * @param url Адрес сервера
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Возвращает название базы данных
     * @return Название базы данных
     */
    public String getDataBaseName() {
        return dataBaseName;
    }

    /**
     * Задает название базы данных
     * @param dataBaseName Название базы данных
     */
    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    /**
     * Возвращает имя пользователя
     * @return Имя пользователя
     */
    public String getUser() {
        return user;
    }

    /**
     * Задает имя пользователя
     * @param user Имя пользователя
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Возвращает пароль пользователя
     * @return Пароль пользователя
     */
    public String getPassword() {
        return password;
    }

    /**
     * Задает пароль пользователя
     * @param password Пароль пользователя
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
