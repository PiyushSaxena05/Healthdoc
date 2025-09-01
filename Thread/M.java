import java.sql.*;
import java.util.Scanner;

class doctor extends Thread{
    private static final String url = "jdbc:mysql://localhost:3306/healthcheck";
    private static final String user = "root";
    private static final String password = "Password007";
    @Override
    public void run() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            String query = "INSERT INTO login(doctor,doctormob,patient,patientmob,symptoms,medicines,securitypin)VALUES(?,?,?,?,?,?,?)";

            Connection con = DriverManager.getConnection(url,user,password);
            try(PreparedStatement p = con.prepareStatement(query)){



            Scanner sc = new Scanner(System.in);
            String name;
            Long mobile_no;
            String symptoms;
            String medicines;
            long patientsmob;
            String patientsName;
            String securitypin;
            System.out.println("Please enter number of entries");
            int entries = sc.nextInt();
            sc.nextLine();
            for (int i = 1; i <= entries; i++) {
                System.out.println("Please enter your name: ");
                name = sc.nextLine();
                System.out.println("Please enter your mobile number ");
                mobile_no = sc.nextLong();
                sc.nextLine();
                System.out.println("Please enter patients name ");
                patientsName = sc.nextLine();
                System.out.println("Please enter patients mobile number");
                patientsmob = sc.nextLong();
                sc.nextLine();
                System.out.println("Please enter symptoms: ");
                symptoms = sc.nextLine();
                System.out.println("Please enter medicines ");
                medicines = sc.nextLine();
                System.out.println("Please allot a password to the patient");
                securitypin = sc.nextLine();
                System.out.println("Doctor name : " + name);
                System.out.println("Patient" + i + " name: " + patientsName);
                System.out.println("Symptoms: " + symptoms);
                System.out.println("medicines: " + medicines);
                p.setString(1,name);
                if (mobile_no >= 1_000_000_000L && mobile_no <= 9_999_999_999L) {
                    p.setLong(2, mobile_no);
                }else{
                    System.out.println("Please enter valid number");
                }
                p.setString(3,patientsName);
                if (patientsmob >= 1_000_000_000L && patientsmob <= 9_999_999_999L) {
                    p.setLong(4, patientsmob);
                }else{
                    System.out.println("Please enter valid number");
                }
                p.setString(5,symptoms);
                p.setString(6,medicines);
                p.setString(7,securitypin);
                int rowsaffected = p.executeUpdate();
                if(rowsaffected>0){
                    System.out.println("data saved");
                }else{
                    System.out.println("something went wrong");
                }

            }
            }} catch (SQLException e) {
            throw new RuntimeException(e);
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
public class M {
    private static final String url = "jdbc:mysql://localhost:3306/healthcheck";
    private static final String user = "root";
    private static final String password = "Password007";
    public static void main(String[] args) throws SQLException, ClassNotFoundException, InterruptedException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        Connection con = DriverManager.getConnection(url,user,password);

        Scanner sc = new Scanner(System.in);
        doctor doctor = new doctor();


        while(true) {
            System.out.println();
            System.out.println("Please enter option 1 for doctor ");
            System.out.println("Please enter option 2 for patient ");
            System.out.println("Please enter option 3 for update records ");
            System.out.println("please enter option 4 for exit ");
            int option = sc.nextInt();
            sc.nextLine();
            switch (option) {
                case 1:
                    doctor.start();
                    break;
                case 2:
                    doctor.patient(con, sc);
                    break;
                case 3:
                    doctor.updateRecord(con,sc);
                    break;
                case 4:
                    doctor.exit();
                    return;

                default:
                    System.out.println("SOMETHING WENT WRONG");
            }
        }




    }
}
