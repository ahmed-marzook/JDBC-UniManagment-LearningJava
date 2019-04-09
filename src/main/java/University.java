import java.sql.*;

public class University {

    private static Connection con;

    public static void main(String[] args) {
        createConnection();
        viewAllStudents();
        viewAllTeachers();
    }

    public static void viewAllStudents() {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM student");

            System.out.println("\t ----------STUDENTS----------");
            while(rs.next()){
                String name = rs.getString("student_first_name");
                String lastName = rs.getString("student_last_name");
                int studentId = rs.getInt("student_id");
                System.out.println( studentId + ") " + name + " " + lastName);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void viewAllTeachers() {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM teacher");

            System.out.println("\t ----------TEACHERS----------");
            while(rs.next()){
                String name = rs.getString("teacher_first_name");
                String lastName = rs.getString("teacher_last_name");
                int teacherId = rs.getInt("teacher_id");
                System.out.println(teacherId + ") "+ name + " " + lastName);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/School", "root", "Orange123");
            System.out.println("Database Successfully Connected");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
