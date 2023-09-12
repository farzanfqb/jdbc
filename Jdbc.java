package jdbc;
import java.sql.*;
import java.util.Scanner;

public class Jdbc{
    public static void main(String[] args) {
        StudentDao dao = new StudentDao();
        Scanner scanner = new Scanner(System.in);
        Scan scan = new Scan(scanner);
        Student student = new Student();

        student.full_name = scan.full_name;
        student.roll_no = scan.roll_no;

        scanner.close();

        dao.connect();

        try {
            dao.addStudent(student);
            Student retrievedStudent = dao.getStudent(student.roll_no);
            System.out.println("Student details: " + retrievedStudent.full_name + " " + retrievedStudent.roll_no);
            dao.display();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class Scan {
    String full_name = "";
    int roll_no;

    public Scan(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Enter name");
                full_name = scanner.nextLine();
                System.out.println("Enter Roll_Number");
                roll_no = checkInt(scanner);
                break;
            } catch (Exception e) {
                scanner.next();
                e.printStackTrace();
            }
        }
    }

    private int checkInt(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next();
            }
        }
    }
}

class StudentDao {
    Connection con = null;

    public void connect() {
        String url = "jdbc:mysql://localhost:3306/navin";
        String userName = "root";
        String password = "12345";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Student getStudent(int roll_no) throws SQLException {
        String query = "SELECT full_name FROM students WHERE roll_no = ?";
        Student student = new Student();
        student.roll_no = roll_no;

        try (PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, roll_no);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                student.full_name = rs.getString("full_name");
            }
        }

        return student;
    }

    public void addStudent(Student student) throws SQLException {
        String query;
        if (student.roll_no != 0) {
            query = "INSERT INTO students (roll_no, full_name) VALUES (?, ?)";
        } else {
            query = "INSERT INTO students (full_name) VALUES (?)";
        }

        try (PreparedStatement st = con.prepareStatement(query)) {
            if (student.roll_no != 0) {
                st.setInt(1, student.roll_no);
                st.setString(2, student.full_name);
            } else {
                st.setString(1, student.full_name);
            }

            int count = st.executeUpdate();
            System.out.println(count + " rows affected");
        }
    }

    public void display() throws SQLException {
        String query = "SELECT * FROM students";
        int roll_no;
        String full_name = "";

        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                roll_no = rs.getInt("roll_no");
                full_name = rs.getString("full_name");
                System.out.println("Roll Number = " + roll_no + " Full Name = " + full_name);
            }
        }
    }
}

class Student {
    int roll_no;
    String full_name;}
