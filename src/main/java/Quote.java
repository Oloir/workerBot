import java.sql.*;
import java.util.Scanner;


public class Quote {

    Scanner Scan;

    public String getQuote() throws SQLException {


        Connection conn;
        Statement statmt;
        ResultSet resSet;

        conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:quotes");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        Statement statement = conn.createStatement();{
            ResultSet resultSet = statement.executeQuery("SELECT text FROM quotes ORDER BY RANDOM() LIMIT 1;");
            System.out.println(resultSet.getString(1));
            return resultSet.getString(1);
        }








    }
}







//        {
//            try {
//                Scan = new Scanner(new File("Library"));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//        {
//            while (Scan.hasNextLine()) {
//                List1.add(Scan.nextLine());
//            }
//            String[] array = List1.toArray(new String[0]);
//
//            double z = Math.random() * List1.size();
//            int x = (int) Math.round(z);
//            System.out.println(z);
//
//            return (List1.get(x));
//        }
//  }
//}
