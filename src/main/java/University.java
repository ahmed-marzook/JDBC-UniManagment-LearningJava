import java.sql.*;
import java.util.Scanner;

//        As a user I want to be able to add a new student to the database DONE
//
//        As a user I want to be able to add a new teacher to the database DONE
//
//        As a user I want to be able to view all Student DONE
//
//        As a user I want to be able to view all teachers DONE
//
//        As a user I want to see what courses are availiable DONE
//
//        As a user I want to be able to see what students are with which class DONE
//
//        As a user I want to be able to see what teachers are with each student DONE
//
//        As a user I want to be able to see how many students are enrolled DONE
//
//        As a user I want to be able to see how many teachers I have DONE
public class University {

    private static Connection con;
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        boolean quit = false;

        createConnection();
        printInstructions();
        while(!quit){
            System.out.println("\nPlease enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice){
                case 1: viewAllStudents(); break;
                case 2: viewAllTeachers(); break;
                case 3: viewAllCourses(); break;
                case 4: viewAllStudentsAndCoursesTheyAreEnrolledIn(); break;
                case 5: viewAllStudentsAndWhoTheyAreTaughtBy();break;
                case 6: viewAllTotalStudentsAndTeachers();break;
                case 7: addNewTeacher();break;
                case 8: addNewStudent(); break;
                case 9: printInstructions();
                case 10: quit = true; break;
            }
            scanner.nextLine();
        }
    }

    public static void printInstructions() {
        System.out.println("\t1) View All Students");
        System.out.println("\t2) View All Teachers");
        System.out.println("\t3) View All Courses and Who their are taught by");
        System.out.println("\t4) View All Students and what Course their are in");
        System.out.println("\t5) View All Students and who teaches them");
        System.out.println("\t6) View Total number of Students and Teachers");
        System.out.println("\t7) Add New teacher to database");
        System.out.println("\t8) Add New Student to database");
        System.out.println("\t9) Print Instructions");
        System.out.println("\t10) Quit");
    }

    public static void addNewStudent() {
        scanner.nextLine();
        System.out.println("Please enter the students first name: ");
        String teacherFirstName = scanner.nextLine();
        System.out.println("Please enter the students last name: ");
        String teacherLastName = scanner.nextLine();
        System.out.println("PLease enter the student course ID or leave it blank: ");
        int courseId = scanner.nextInt();


        try {
            //Preparing Statement to insert values into teachers table
            PreparedStatement stmt = con.prepareStatement("INSERT INTO student (student_first_name , student_last_name, student_course_id)" +
                    "VALUES (?,?, ?);");
            //prepared statements always start with 1
            stmt.setString(1, teacherFirstName);
            stmt.setString(2, teacherLastName);
            stmt.setInt(3, courseId);
            //Executing the statement
            stmt.execute();
            //make sure to always close statements after using them to save memory
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addNewTeacher() {
        scanner.nextLine();
        //Asking user for Teachers names
        System.out.println("Please enter the teachers first name: ");
        String teacherFirstName = scanner.nextLine();
        System.out.println("Please enter the teachers last name: ");
        String teacherLastName = scanner.nextLine();


        try {
            //Preparing Statement to insert values into teachers table
            PreparedStatement stmt = con.prepareStatement("INSERT INTO teacher (teacher_first_name , teacher_last_name)" +
                    "VALUES (?,?);");
            //prepared statements always start with 1
            stmt.setString(1, teacherFirstName);
            stmt.setString(2, teacherLastName);
            //Executing the statement
            stmt.execute();
            //make sure to always close statements after using them to save memory
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public static void viewAllCourses() {
        try {
            //Setting up Callable statement make sure it has NO SPACES IN THE NAME
            CallableStatement stmt = con.prepareCall("CALL CoursesAndTeachers()");
            //Checking if it is executable
            Boolean hasResults = stmt.execute();

            if(hasResults) {
                //storing results in a result set
                ResultSet rs = stmt.executeQuery();
                System.out.println("\t ----------COURSES----------");
                while (rs.next()) {
                    String courseName = rs.getString("course");
                    int courseId = rs.getInt("course_id");
                    String name = rs.getString("teacher_first_name");
                    String lastName = rs.getString("teacher_last_name");
                    System.out.println( courseId + ") " + courseName + " Taught by: " + name + " " + lastName);
                }
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void viewAllStudentsAndCoursesTheyAreEnrolledIn() {
        try {
            CallableStatement stmt = con.prepareCall("CALL StudentCourse()");
            Boolean hasResults = stmt.execute();

            if(hasResults) {
                ResultSet rs = stmt.executeQuery();
                System.out.println("\t ----------STUDENTS AND CLASS THEY ARE ENROLLED IN----------");
                while (rs.next()) {
                    String courseName = rs.getString("course");
                    int studentId = rs.getInt("student_id");
                    String name = rs.getString("student_first_name");
                    String lastName = rs.getString("student_last_name");
                    System.out.println(studentId + ") " + name + " " + lastName + " studies: " + courseName);
                }
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void viewAllStudentsAndWhoTheyAreTaughtBy() {
        try {
            CallableStatement stmt = con.prepareCall("CALL StudentTeacher()");
            Boolean hasResults = stmt.execute();

            if(hasResults) {
                ResultSet rs = stmt.executeQuery();
                System.out.println("\t ----------STUDENTS AND WHO TEACHES THEM----------");
                while (rs.next()) {
                    String name = rs.getString("student_first_name");
                    String lastName = rs.getString("student_last_name");
                    String teacherName = rs.getString("teacher_first_name");
                    String teacherLastName = rs.getString("teacher_last_name");
                    System.out.println(name + " " + lastName + " Taught by: " + teacherName + " " + teacherLastName);
                }
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void viewAllTotalStudentsAndTeachers() {
        try {
            CallableStatement stmt = con.prepareCall("CALL TotalStudentsAndTeachers()");
            Boolean hasResults = stmt.execute();

            if(hasResults) {
                ResultSet rs = stmt.executeQuery();
                System.out.println("\t ----------TOTAL NUMBER OF STUDENTS AND TEACHERS----------");
                while (rs.next()) {
                    int numberOfStudents = rs.getInt("Total_Students");
                    int numberOfTeachers = rs.getInt("Total_Teachers");
                    System.out.println("Number of Students: " + numberOfStudents);
                    System.out.println("Number of Teachers: " + numberOfTeachers);
                }
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void viewAllStudents() {
        try {
            //writing a statement
            Statement stmt = con.createStatement();
            //storing results in a result set
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
