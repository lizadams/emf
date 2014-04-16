package gov.epa.emissions.commons.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostgreSQLKeyWords {
    
    private static String[] reservedKeyWords = {"ALL",
            "ANALYSE",
            "AND",
            "ANY",
            "ARRAY",
            "AS",
            "ASC",
            "ASYMMETRIC",
            "AUTHORIZATION",
            "BETWEEN",
            "BINARY",
            "BOTH",
            "CASE",
            "CAST",
            "CHECK",
            "COLLATE",
            "COLUMN",
            "CONSTRAINT",
            "CREATE",
            "CROSS",
            "CURRENT_DATE",
            "CURRENT_ROLE",
            "CURRENT_TIME",
            "CURRENT_TIMESTAMP",
            "CURRENT_USER",
            "DEFAULT",
            "DEFERRABLE",
            "DESC",
            "DISTINCT",
            "DO",
            "ELSE",
            "END",
            "EXCEPT",
            "FALSE",
            "FOR",
            "FOREIGN",
            "FREEZE",
            "FROM",
            "FULL",
            "GRANT",
            "GROUP",
            "HAVING",
            "ILIKE",
            "IN",
            "INITIALLY",
            "INNER",
            "INTERSECT",
            "INTO",
            "IS",
            "ISNULL",
            "JOIN",
            "LEADING",
            "LEFT",
            "LIKE",
            "LIMIT",
            "LOCALTIME",
            "LOCALTIMESTAMP",
            "NATURAL",
            "NEW",
            "NOT",
            "NOTNULL",
            "NULL",
            "OFF",
            "OFFSET",
            "OLD",
            "ON",
            "ONLY",
            "OR",
            "ORDER",
            "OUTER",
            "PLACING",
            "PRIMARY",
            "REFERENCES",
            "RETURNING",
            "RIGHT",
            "SELECT",
            "SESSION_USER",
            "SIMILAR",
            "SOME",
            "SYMMETRIC",
            "TABLE",
            "THEN",
            "TO",
            "TRAILING",
            "TRUE",
            "UNION",
            "UNIQUE",
            "USER",
            "USING",
            "VERBOSE",
            "WHEN",
            "WHERE"
    };
    
    public static boolean reserved(String name) {
        List<String> keyList = new ArrayList<String>();
        keyList.addAll(Arrays.asList(reservedKeyWords));
        
        return keyList.contains(name);
    }

}
