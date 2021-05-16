package jdbctests;

import org.junit.Test;

import java.sql.*;

public class jdbc_example {

    String dbUrl = "jdbc:oracle:thin:@35.153.78.124:1521:xe";
    String dbUsername = "hr";
    String dbPassword = "hr";

    @Test
    public void test1() throws SQLException {

        //create the connection
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        //create statement object
        Statement statement = connection.createStatement();
        //run query and get the result in resultset object
        ResultSet resultSet = statement.executeQuery("select * from regions");

        while(resultSet.next()){
            System.out.println(resultSet.getString(2));
        }

        //=========================

        resultSet = statement.executeQuery("select * from departments");

        while(resultSet.next()){
            System.out.println(resultSet.getString(2));
        }

        //closing all connections
        resultSet.close();
        statement.close();
        connection.close();

    }

    @Test
    public void CountAndNavigate() throws SQLException {


        //create the connection
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        //create statement object
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        //run query and get the result in resultset object
        ResultSet resultSet = statement.executeQuery("select * from departments");

        //how to find how many rows we have for the query
        //go to last row
        resultSet.last();
        //get the row count
        int rowCount = resultSet.getRow();

        System.out.println(rowCount);

        resultSet.beforeFirst();

        while(resultSet.next()){
            System.out.println(resultSet.getString(2));
        }
        //closing all connections
        resultSet.close();
        statement.close();
        connection.close();

    }

    @Test
    public void metadata() throws SQLException {
        //create the connection
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        //create statement object
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        //run query and get the result in resultset object
        ResultSet resultSet = statement.executeQuery("select * from departments");

        //get the database related data inside the dbMetadata object
        DatabaseMetaData dbMetadata = connection.getMetaData();

        System.out.println("UserName() = " + dbMetadata.getUserName());
        System.out.println("ProductName() = " + dbMetadata.getDatabaseProductName());
        System.out.println("DatabaseProductVersion() = " + dbMetadata.getDatabaseProductVersion());
        System.out.println("DriverName() = " + dbMetadata.getDriverName());
        System.out.println("DriverVersion() = " + dbMetadata.getDriverVersion());

        ResultSetMetaData rsMetaData = resultSet.getMetaData();

        System.out.println("ColumnCount() = " + rsMetaData.getColumnCount());

        System.out.println("ColumnName(4) = " + rsMetaData.getColumnName(4));
        for(int i =1 ; i<=rsMetaData.getColumnCount();i++){
            System.out.print(rsMetaData.getColumnName(i));
            if(i!=rsMetaData.getColumnCount()){
                System.out.print(", ");
            }
        }
        //closing all connections
        resultSet.close();
        statement.close();
        connection.close();

    }

}