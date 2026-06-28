package tests.db;

import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBTest {

    @Test
    public void checkSelect() throws SQLException {
        DBConnection connection = new DBConnection();
        connection.connect();
        ResultSet result = connection.select("SELECT * FROM public.person WHERE (id = 13132)");
        while (result.next()) {
            System.out.print(result.getInt("id") + " ");
            System.out.print(result.getInt("age") + " ");
            System.out.print(result.getString("first_name") + " ");
            System.out.print(result.getInt("money") + " ");
            System.out.print(result.getString("second_name") + " ");
            System.out.print(result.getBoolean("sex") + " ");
            System.out.print(result.getInt("house_id") + " ");
            System.out.println();
        }
        connection.close();
    }
}
