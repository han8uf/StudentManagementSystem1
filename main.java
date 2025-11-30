import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        StudentManager manager = new StudentManager();

        // تشغيل الحفظ التلقائي
        AutoSaveThread autoSave = new AutoSaveThread(manager);
        autoSave.start();   // يبدأ بالخلفية

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n--- Student System ---");
            System.out.println("1) Add student");
            System.out.println("2) Show all");
            System.out.println("3) Save to file");
            System.out.println("4) Load from file");
            System.out.println("5) Generate Report");
            System.out.println("0) Exit");
            System.out.print("Choose: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume

            if (choice == 1) {

                try {
                    System.out.print("ID: ");
                    String id = sc.nextLine();

                    System.out.print("First name: ");
                    String fn = sc.nextLine();

                    System.out.print("Last name: ");
                    String ln = sc.nextLine();

                    System.out.print("GPA: ");
                    double gpa = sc.nextDouble();
                    sc.nextLine();

                    System.out.print("Department: ");
                    String dept = sc.nextLine();

                    Address ad = new Address("N/A", "N/A", "000", "N/A");

                    Student s = new UndergraduateStudent(id, fn, ln, gpa, dept, ad, 1);

                    manager.addStudent(s);
                    System.out.println("Added!");

                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }

            } else if (choice == 2) {

                List<Student> list = manager.getAllStudents();
                for (Student s : list) {
                    System.out.println(s);
                }

            } else if (choice == 3) {

                FileHandler.saveToCSV("students.csv", manager.getAllStudents());
                System.out.println("Saved.");

            } else if (choice == 4) {

                List<Student> list = FileHandler.loadFromCSV("students.csv");
                System.out.println("Loaded " + list.size() + " students.");

                // نستبدل القائمة
                for (Student s : list) manager.addStudent(s);

            } else if (choice == 5) {

                // تشغيل التقرير بالخلفية
                ReportThread rpt = new ReportThread(manager);
                rpt.start();
                System.out.println("Report requested...");

            } else if (choice == 0) {
                System.out.println("Exiting...");

                // إيقاف الحفظ التلقائي
                autoSave.stopService();

                break;
            }
        }

        sc.close();
    }
}
