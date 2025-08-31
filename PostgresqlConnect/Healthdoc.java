package JDBC.PostgreSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

class Login{

    public static void doctor(Connection con , Scanner sc){
        String doctorName;
        String patientName;
        String medicines;
        Long mob_no;
        Long phone_no;
        String symptoms;
        String password;
        int patient_Count;
        String query = "INSERT INTO login(doctor,doctormob,patient,patientmob,symptoms,medicines,securitypin)VALUES(?,?,?,?,?,?,?)";
        try {
            try(PreparedStatement p = con.prepareStatement(query)) {

                System.out.println("Please enter your name: ");
                doctorName = sc.nextLine();
                System.out.println("Please enter your mobile no: ");
                phone_no = sc.nextLong();
                sc.nextLine();
                System.out.println("how many patients ?");
                patient_Count = sc.nextInt();
                sc.nextLine();
                for (int i = 1; i <= patient_Count; i++) {
                    p.setString(1,doctorName);
                    System.out.println("Please enter patient name: " + " " + i);
                    patientName = sc.nextLine();
                    sc.nextLine();
                    System.out.println("Please enter patients mob_no " + " " + i);
                    mob_no = sc.nextLong();
                    sc.nextLine();
                    System.out.println("Please enter the symptoms of patient " + " " + i);
                    symptoms = sc.nextLine();
                    p.setString(5,symptoms);
                    sc.nextLine();
                    System.out.println("Please write medicines " + " " + i);
                    medicines = sc.nextLine();
                    p.setString(6,medicines);
                    System.out.println("Please allot a secure password " + " " + i);
                    password = sc.next();
                    sc.nextLine();
                    p.setString(7,password);
                    if (phone_no >= 1_000_000_000L && phone_no <= 9_999_999_999L) {
                        p.setLong(2, phone_no);
                    }else{
                        System.out.println("Please enter valid number");
                    }
                    p.setString(3,patientName);
                    if (mob_no >= 1_000_000_000L && mob_no <= 9_999_999_999L) {
                        p.setLong(4, mob_no);
                    }else{
                        System.out.println("Please enter valid number");
                    }
                    int rowsaffect = p.executeUpdate();
                    if(rowsaffect>0){
                        System.out.println("data saved");
                    }else{
                        System.out.println("something is wrong");
                    }

                }
            }

        }catch (Exception e){
            System.out.println( e.getMessage());
        }

    }
    public static void patient(Connection con, Scanner sc) {
        String query = "SELECT doctor, doctormob,patient,patientmob,symptoms,medicines from login where securitypin = ?";
        try {
            try (PreparedStatement p = con.prepareStatement(query)) {
                System.out.println("Please enter your password given by your doctor ");
                String password = sc.next();
                p.setString(1, password);
                try (ResultSet rs = p.executeQuery()) {
                    boolean flag = false;
                    while (rs.next()) {
                        flag = true;
                        System.out.println("Doctor: " + rs.getString("doctor") + " (" + rs.getLong("doctormob") + ")");
                        System.out.println("Patient: " + rs.getString("patient") + " (" + rs.getLong("patientmob") + ")");
                        System.out.println("Symptoms: " + rs.getString("symptoms"));
                        System.out.println("Medicines: " + rs.getString("medicines"));
                        System.out.println("--------------------------------");
                    }
                    if (!flag) {
                        System.out.println("No records found or wrong password.");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }



    }
    public static void updateRecord(Connection con, Scanner sc) {
        System.out.println("Enter what to update");
        String update = sc.next();
        if (update.equalsIgnoreCase("doctormob")) {
            String query = "update login set doctormob =? where doctor = ?";
            try {
                try (PreparedStatement p = con.prepareStatement(query)) {
                    long mob = sc.nextLong();
                    sc.nextLine();

                    System.out.println("your name: ");
                    String name = sc.nextLine();

                    p.setLong(1, mob);
                    p.setString(2, name);
                    p.executeUpdate();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (update.equalsIgnoreCase("medicines")) {
            String query = "update login set medicines = ? where securitypin = ?";
            try {
                try (PreparedStatement p = con.prepareStatement(query)) {
                    sc.nextLine();

                    System.out.println("update medicines");
                    String medicines = sc.nextLine();

                    System.out.println("securitypin: ");
                    String security = sc.nextLine();

                    p.setString(1, medicines);
                    p.setString(2, security);
                    p.executeUpdate();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (update.equalsIgnoreCase("symptoms")) {
            String query = "update login set symptoms = ? where securitypin = ?";
            try {
                try (PreparedStatement p = con.prepareStatement(query)) {
                    sc.nextLine();
                    System.out.println("update symptoms");
                    String symptoms = sc.nextLine();
                    System.out.println("securitypin: ");
                    String security = sc.nextLine();
                    p.setString(1, symptoms);
                    p.setString(2, security);
                    p.executeUpdate();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void exit() throws InterruptedException {
        System.out.print("Exiting");
        int i = 5;
        while(i!=0){
            System.out.print(".");
            Thread.sleep(1000);
            i--;
        }
        System.out.println();
        System.out.println("ThankYou for login ");
    }
}



public class Healthdoc {
    private static final String url = "jdbc:postgresql://localhost:5432/healthcheck";
    private static final String user = "postgres";
    private static final String password = "PIYUSH@111WORD016";
    public static void main(String[] args) throws Exception{
        try{
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        System.out.println("welcome to Healthdoc ");

        Connection con = DriverManager.getConnection(url,user,password);
        while(true) {
            Scanner sc = new Scanner(System.in);
            System.out.println();
            System.out.println("Please enter option 1 for doctor ");
            System.out.println("Please enter option 2 for patient ");
            System.out.println("Please enter option 3 for update records ");
            System.out.println("please enter option 4 for exit ");
            int option = sc.nextInt();
            sc.nextLine();
            switch (option) {
                case 1:
                   Login.doctor(con, sc);
                    break;
                case 2:
                    Login.patient(con, sc);
                    break;
                case 3:
                   Login.updateRecord(con,sc);
                    break;
                case 4:
                    Login.exit();
                    return;
                default:
                    System.out.println("SOMETHING WENT WRONG");
            }
        }
    }


}


