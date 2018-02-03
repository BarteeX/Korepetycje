package com.example.monika.korepetycje.database;

/**
 * Created by Monika on 2018-01-27.
 */

public class DBHelper {
    public static final String COMMA = " , ";
    public static final String LEFT_BRACKET = " ( ";
    public static final String RIGHT_BRACKET = " ) ";
    public static final String STUDENT_TABLE_NAME = "STUDENT";
    public static final String STUDENT_ID_PK = "student_id";
    public static final String STUDENT_ID_FK = "student_id_fk";
    public static final String ADDRESS_TABLE_NAME = "ADDRESS";
    public static final String ADDRESS_ID_PK = "address_id";
    public static final String ADDRESS_ID_FK = "address_id_fk";
    public static final String TERM_TABLE_NAME = "TERM";
    public static final String TERM_ID_PK = "term_id";
    public static final String TERM_DAY = "day";
    public static final String TERM_HOUR = "hour";
    public static final String TERM_LENGTH = "length";
    public static final String ADDRESS_CITY = "city";
    public static final String ADDRESS_HOUSE_NR = "house_nr";
    public static final String ADDRESS_FLAT = "flat";
    public static final String ADDRESS_STREET = "street";
    public static final String STUDENT_NAME = "name";
    public static final String STUDENT_SURNAME = "surname";
    public static final String STUDENT_TELEPHONE_NR = "telephone_nr";

    public static final String DATABASE_NAME = "LESSONS";

    public static String DROP_TABLE(String table) {
        return "DROP TABLE IF EXIST " + table;
    }

    private static String VARCHAR(Integer value){
      return  " VARCHAR" + LEFT_BRACKET + String.valueOf(value) + RIGHT_BRACKET;
    }

    private static String FOREIGN_KEY(String keyFK, String table, String keyPK) {
        return " FOREIGN KEY " + LEFT_BRACKET + keyFK + RIGHT_BRACKET +
                "REFERENCES " + table + LEFT_BRACKET + keyPK + RIGHT_BRACKET;
    }

    public static String CREATE_STUDENT_TABLE() {
        StringBuilder query = new StringBuilder();

        query.append("CREATE TABLE IF NOT EXISTS ")
                .append(STUDENT_TABLE_NAME)
                .append(LEFT_BRACKET)
                .append(STUDENT_ID_PK).append(" INTEGER PRIMARY KEY AUTOINCREMENT ")
                .append(COMMA)
                .append(STUDENT_NAME).append(VARCHAR(50))
                .append(COMMA)
                .append(STUDENT_SURNAME).append(VARCHAR(50))
                .append(COMMA)
                .append(STUDENT_TELEPHONE_NR).append(VARCHAR(9))
                .append(RIGHT_BRACKET);

        return query.toString();
    }

    public static String CREATE_ADDRESS_TABLE() {
        StringBuilder query = new StringBuilder();

        query.append("CREATE TABLE IF NOT EXISTS ")
                .append(ADDRESS_TABLE_NAME)
                .append(LEFT_BRACKET)
                .append(ADDRESS_ID_PK).append(" INTEGER PRIMARY KEY AUTOINCREMENT ")
                .append(COMMA)
                .append(ADDRESS_CITY).append(VARCHAR(50))
                .append(COMMA)
                .append(ADDRESS_HOUSE_NR).append(VARCHAR(50))
                .append(COMMA)
                .append(ADDRESS_FLAT).append(VARCHAR(50))
                .append(COMMA)
                .append(ADDRESS_STREET).append(VARCHAR(50))
                .append(COMMA)
                .append(STUDENT_ID_FK).append(" INTEGER")
                .append(COMMA)
                .append(FOREIGN_KEY(STUDENT_ID_FK, STUDENT_TABLE_NAME, STUDENT_ID_PK))
                .append(RIGHT_BRACKET);


        return query.toString();
    }

    public static String CREATE_TERM_TABLE() {
        StringBuilder query = new StringBuilder();

        query.append("CREATE TABLE IF NOT EXISTS ")
                .append(TERM_TABLE_NAME)
                .append(LEFT_BRACKET)
                .append(TERM_ID_PK).append(" INTEGER PRIMARY KEY AUTOINCREMENT ")
                .append(COMMA)
                .append(TERM_DAY).append(VARCHAR(20))
                .append(COMMA)
                .append(TERM_HOUR).append(VARCHAR(5))
                .append(COMMA)
                .append(TERM_LENGTH).append(VARCHAR(5))
                .append(COMMA)
                .append(STUDENT_ID_FK).append(" INTEGER")
                .append(COMMA)
                .append(ADDRESS_ID_FK).append(" INTEGER")
                .append(COMMA)
                .append(FOREIGN_KEY(STUDENT_ID_FK, STUDENT_TABLE_NAME, STUDENT_ID_PK))
                .append(COMMA)
                .append(FOREIGN_KEY(ADDRESS_ID_FK, ADDRESS_TABLE_NAME, ADDRESS_ID_PK))
                .append(RIGHT_BRACKET);


        return query.toString();
    }
}
