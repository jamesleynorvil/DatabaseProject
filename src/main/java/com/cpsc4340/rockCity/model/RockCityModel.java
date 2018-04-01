package com.cpsc4340.rockCity.model;

import com.cpsc4340.rockCity.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;


public class RockCityModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(RockCityModel.class);
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;

    private static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(Constants.DB_DRIVER);
            connection = DriverManager.getConnection(Constants.DB_URL, Constants
                    .DB_USER, Constants.DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.error("Failed to make database connection. {}", e);
        }
        return connection;
    }

    public boolean insert(String tableName, String... values) {
        boolean result = false;
        String query = "";
        query = ("Organizer".equals(tableName)) ? "INSERT INTO " + tableName + " " +
                "(OrgName, Address, Email, `Web site`, phone, fax, `contact-name`, " +
                "`Contact-Num`) VALUES (?,?,?,?,?,?,?,?)" : query;
        query = ("Sponsor".equals(tableName)) ? "INSERT INTO " + tableName + " (SpName," +
                " Address, Email, `Web site`, phone, fax, `contact-name`, " +
                "`Contact-Num`) VALUES (?,?,?,?,?,?,?,?)" : query;
        query = ("Host".equals(tableName)) ? "INSERT INTO " + tableName + " (HName, " +
                "Address, Email, `Web site`, phone, fax, `contact-name`, `Contact-Num`)" +
                " " +
                "VALUES (?,?,?,?,?,?,?,?)" : query;
        query = ("Show".equals(tableName)) ? "INSERT INTO `Show` (SName, " +
                "`Attendees-num`, `Target-audiences`, `Start_date`, `End_date`, " +
                "`Preferred_area`, `S-type`, OrgName1) VALUES (?,?,?,?,?,?,?,?)" : query;
        query = ("Venue".equals(tableName)) ? "INSERT INTO " + tableName + " (VName, " +
                "Address, Email, `manager-name`, phone, HName1) " +
                "VALUES (?,?,?,?,?,?)" : query;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < values.length; i++) {
                if ((i == 3 || i == 4) && isValidDate(values[i])) {
                    preparedStatement.setDate(i + 1, Date.valueOf(values[i]));
                } else if (i == 1 && values[i].matches("^[0-9]+$")) {
                    preparedStatement.setInt(i + 1, Integer.valueOf(values[i]));
                } else {
                    preparedStatement.setString(i + 1, values[i]);
                }
            }
            result = preparedStatement.executeUpdate() == 1;

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.info(e.getMessage());
                }
            }
        }

        return result;
    }

    public boolean delete(String table, String name) {
        Boolean result = false;
        String column = getColumnName(table);
        String query = "DELETE FROM " + table + " WHERE " + column + " = ?";
        query = ("Show".equals(table)) ? "DELETE FROM `Show` WHERE " + column + " = ?" :
                query;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            result = preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.info(e.getMessage());
                }
            }
        }

        return result;
    }

    public boolean update(String tableName, String... values) {
        boolean result = false;
        String query = "";
        query = ("Organizer".equals(tableName)) ? "UPDATE " + tableName + " SET" +
                " Address=?, Email=?, `Web site`=?, phone=?, fax=?, `contact-name`=?, " +
                "`Contact-Num`=? WHERE OrgName=?" : query;
        query = ("Sponsor".equals(tableName)) ? "UPDATE " + tableName + " SET" +
                " Address=?, Email=?, `Web site`=?, phone=?, fax=?, `contact-name`=?, " +
                "`Contact-Num`=? WHERE SpName=?" : query;
        query = ("Host".equals(tableName)) ? "UPDATE " + tableName + " SET" +
                " Address=?, Email=?, `Web site`=?, phone=?, fax=?, " +
                "`contact-name`=?, `Contact-Num`=? WHERE HName=?" : query;
        query = ("Show".equals(tableName)) ? "UPDATE `Show` SET " +
                "`Attendees-num`=?, `Target-audiences`=?, `Start_date`=?, `End_date`=?," +
                " `Preferred_area`=?,`S-type`=?, OrgName1=? WHERE SName=? " : query;
        query = ("Venue".equals(tableName)) ? "UPDATE " + tableName + " SET Address=?, " +
                "Email=?, `manager-name`=?, phone=?, HName1=? WHERE VName=?" : query;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < values.length; i++) {
                if ("Show".equals(tableName) && (i == 3 || i == 4) && isValidDate
                        (values[i])) {
                    preparedStatement.setDate(i + 1, Date.valueOf(values[i]));
                } else if ("Show".equals(tableName) && i == 1 && values[i].matches
                        ("^[0-9]+$")) {
                    preparedStatement.setInt(i + 1, Integer.valueOf(values[i]));
                } else {
                    preparedStatement.setString(i + 1, values[i]);
                }
            }
            result = preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.info(e.getMessage());
                }
            }
        }

        return result;
    }

    public HashMap find(String table, String name) {
        HashMap<String, String> hashMap = new HashMap<>();
        String column = getColumnName(table);
        String query = "SELECT * FROM " + table + " WHERE " + column + " = ?";
        query = ("Show".equals(table)) ? "SELECT * FROM `Show` WHERE " + column + " = ?" :
                query;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ResultSetMetaData meta = resultSet.getMetaData();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    hashMap.put(meta.getColumnLabel(i), String.valueOf(resultSet
                            .getObject(i)));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage());
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.info(e.getMessage());
                }
            }
        }

        return hashMap;
    }

    public ConcurrentHashMap getReport(String table, String name) {
        ConcurrentHashMap<String, String> hashMap = new ConcurrentHashMap<>();
        String query = "";
        query = ("ShowReport".equals(table)) ? "" : query;
        query = ("ManagementReport".equals(table)) ? "SELECT * FROM Sponsorship WHERE " +
                "SpName2=? ORDER BY Cash" : query;
        query = ("EventSchedule".equals(table)) ? "SELECT * FROM Event WHERE SName7=? " +
                "ORDER BY EName AND `Date` AND time" : query;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ResultSetMetaData meta = resultSet.getMetaData();
                synchronized (hashMap) {
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    if (hashMap.containsKey(meta.getColumnLabel(i))) {
                        hashMap.put(meta.getColumnLabel(i) + i, String.valueOf(resultSet
                                .getObject(i)));
                    } else {
                        hashMap.put(meta.getColumnLabel(i), String.valueOf(resultSet
                                .getObject(i)));
                    }
                }
                }
            }

            if (!hashMap.isEmpty() && "EventSchedule".equals(table)) {

                hashMap.forEach((key, value) -> {
                    String str = "";
                    if (key.startsWith("VName")) {
                        try {
                            preparedStatement = connection.prepareStatement("SELECT * " +
                                    "FROM " +
                                    "Indoor WHERE VName3 =?");
                            preparedStatement.setString(1, value);
                            final ResultSet res = preparedStatement.executeQuery();
                            str = (res.next()) ? "Indoor"
                                    : "Outdoor";
                            synchronized (hashMap) {
                                hashMap.put("InOut" + key, str);
                            }
                        } catch (SQLException e) {
                            LOGGER.error(e.getMessage(), e);
                        }
                    }
                });
            }
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
        return hashMap;
    }

    private String getColumnName(String table) {
        String column = "";
        if ("Organizer".equals(table)) {
            column = "OrgName";
        } else if ("Sponsor".equals(table)) {
            column = "SpName";
        } else if ("Host".equals(table)) {
            column = "HName";
        } else if ("Venue".equals(table)) {
            column = "VName";
        } else if ("Show".equals(table)) {
            column = "SName";
        }
        return column;
    }

    private boolean isValidDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

}
