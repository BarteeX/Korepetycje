package com.example.monika.korepetycje.database


object DBHelper {
    val COMMA = " , "
    val LEFT_BRACKET = " ( "
    val RIGHT_BRACKET = " ) "
    val STUDENT_TABLE_NAME = "STUDENT"
    val STUDENT_ID_PK = "student_id"
    val STUDENT_ID_FK = "student_id_fk"
    val ADDRESS_TABLE_NAME = "ADDRESS"
    val ADDRESS_ID_PK = "address_id"
    val ADDRESS_ID_FK = "address_id_fk"
    val TERM_TABLE_NAME = "TERM"
    val TERM_ID_PK = "term_id"
    val TERM_DAY = "day"
    val TERM_HOUR = "hour"
    val TERM_LENGTH = "length"
    val ADDRESS_CITY = "city"
    val ADDRESS_HOUSE_NR = "house_nr"
    val ADDRESS_FLAT = "flat"
    val ADDRESS_STREET = "street"
    val STUDENT_NAME = "name"
    val STUDENT_SURNAME = "surname"
    val STUDENT_TELEPHONE_NR = "telephone_nr"

    val DATABASE_NAME = "LESSONS"

    fun LEFT_JOIN (table : String, field1 : String, field2 : String) : String {
        return " LEFT JOIN $table ON ( $field1 = $field2 ) "
    }

    fun DROP_TABLE(table: String): String {
        return "DROP TABLE IF EXISTS " + table
    }

    private fun VARCHAR(value: Int?): String {
        return " VARCHAR" + LEFT_BRACKET + value.toString() + RIGHT_BRACKET
    }

    private fun FOREIGN_KEY(keyFK: String, table: String, keyPK: String): String {
        return " FOREIGN KEY " + LEFT_BRACKET + keyFK + RIGHT_BRACKET +
                "REFERENCES " + table + LEFT_BRACKET + keyPK + RIGHT_BRACKET
    }

    fun CREATE_STUDENT_TABLE(): String {
        val query = StringBuilder()

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
                .append(RIGHT_BRACKET)

        return query.toString()
    }

    fun CREATE_ADDRESS_TABLE(): String {
        val query = StringBuilder()

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
                .append(RIGHT_BRACKET)


        return query.toString()
    }

    fun CREATE_TERM_TABLE(): String {
        val query = StringBuilder()

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
                .append(RIGHT_BRACKET)


        return query.toString()
    }

    fun getQuery(queryParams: Map<String, Map<*,*>>) : String {
        return """
                |${getSelectClosure()}
                |${getJoinClosure()}
                |${getWhereFromCfg(queryParams)}
            |""".trimMargin()
    }

    private fun getSelectClosure() : String {
        return "SELECT $STUDENT_TABLE_NAME.$STUDENT_ID_PK FROM $STUDENT_TABLE_NAME "
    }

    private fun getJoinClosure() : String {
        return LEFT_JOIN(
                ADDRESS_TABLE_NAME,
                "$ADDRESS_TABLE_NAME.$STUDENT_ID_FK",
                "$STUDENT_TABLE_NAME.$STUDENT_ID_PK"
        ) + LEFT_JOIN(
                TERM_TABLE_NAME,
                "$TERM_TABLE_NAME.$STUDENT_ID_FK",
                "$STUDENT_TABLE_NAME.$STUDENT_ID_PK"
        )
    }

    private fun getWhereFromCfg(queryParams : Map<String, Map<*,*>>) : String {
        if (queryParams.isEmpty())
            return ""

        val sb = StringBuilder()
        sb.append(" WHERE ")
        for (entry in queryParams.entries) {
            sb.append(entry.key)
                    .append(" ")
                    .append(entry.value["operator"])
                    .append(" ")
                    .append(entry.value["value"])
                    .append(" ")
        }

        return sb.toString()
    }
}
