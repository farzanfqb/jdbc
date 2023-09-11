package jdbcdao;
import java.sql.*;
public class jdbcDaoDemo{
    public static void main(String[]arg) throws Exception {;
        StudentDao dao = new StudentDao();
        Student s2 = new Student();
        s2.full_name = "kala";
        dao.addStudent(s2);
        Student s1 = dao.getStudent(12);
        System.out.println(s1.full_name + s1.roll_no);
        dao.display();
    }
}
class StudentDao {
    public Student getStudent (int roll_no){
        try {
        String query = "Select full_name From students where roll_no = " + roll_no;
        Student s = new Student();
        String url = "jdbc:mysql://localhost:3306/navin"; // enter in driver manager to create connection object
        String userName = "root";                          
        String password = "12345";
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, userName, password);
        s.roll_no = roll_no;
        Statement st = con.createStatement();
        ResultSet rs= st.executeQuery(query);
        rs.next();
        String full_name = rs.getString("full_name");
        s.full_name = full_name;
        st.close();
        con.close();
        return s;

    }
    catch(Exception e){
        System.out.println(e);
    }
    return null;
}
public void addStudent (Student s){
        String query = "Insert into students values (default , ?)";
        String url = "jdbc:mysql://localhost:3306/navin"; // enter in driver manager to create connection object
        String userName = "root";                          
        String password = "12345";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
        Connection con = DriverManager.getConnection(url, userName, password);
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1, s.full_name);
        int count = st.executeUpdate();
        System.out.println(count + "rows affected");
        st.close();
        con.close();
            }
        catch (Exception e){ e.printStackTrace();}
}
public void display (){
     String query = "Select * From students";
        String url = "jdbc:mysql://localhost:3306/navin"; // enter in driver manager to create connection object
        String userName = "root";                          
        String password = "12345";
        int roll_no;
        String full_name = "";
        
        try {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, userName, password);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()){
            roll_no = rs.getInt("roll_no");
            full_name = rs.getString("full_name");
            System.out.println("roll Number = "+roll_no +" full Name = "+ full_name);
        }
        st.close();
        con.close(); 

}catch(Exception e){ e.printStackTrace();}
        
}
}
class Student {
    int roll_no;
    String full_name ;
}