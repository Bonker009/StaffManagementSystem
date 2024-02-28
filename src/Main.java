import org.nocrala.tools.texttablefmt.*;

import java.util.*;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        CellStyle textAlign = new CellStyle(CellStyle.HorizontalAlign.center);
        int choice = 0;
        List<StaffMember> staffMembers = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        staffMembers.add(new Volunteer("Tina","PP",0.0));
        staffMembers.add(new HourlySalaryEmployee("Sokha","BTB",60,10.0));
        staffMembers.add(new Volunteer("Lee","SR",0.0));
        staffMembers.add(new HourlySalaryEmployee("Penh Seyha","TBK",90,10));
        staffMembers.add(new SalariedEmployee("Meyling","PP",5000,30));
        staffMembers.add(new SalariedEmployee("Long","TBK",2343,20));
        do {
            Table table2 = new Table(2, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE,
                    ShownBorders.ALL);
            table2.setColumnWidth(0, 10, 30);
            table2.setColumnWidth(1, 25, 30);
            table2.addCell("Staff Management System",textAlign,2);
            table2.addCell("1",textAlign);
            table2.addCell("Insert Employee",textAlign);
            table2.addCell("2",textAlign);
            table2.addCell("Display Employee",textAlign);
            table2.addCell("3",textAlign);
            table2.addCell("Update Employee",textAlign);
            table2.addCell("4",textAlign);
            table2.addCell("Remove Employee",textAlign);
            table2.addCell("5",textAlign);
            table2.addCell("Exit",textAlign);
            System.out.println(table2.render());
            System.out.print("Enter type Number : ");
            boolean isValidInput = false;
            while (!isValidInput) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    if (choice >= 1 && choice <= 5) {
                        isValidInput = true;
                    } else {
                        System.out.println("Invalid input. Please enter a number between 1 and 5.");
                        System.out.print("Enter type Number : ");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid integer.");
                    scanner.next();
                    System.out.print("Enter type Number : ");
                }
            }
            switch (choice){
                case 1 ->{
                insertEmployee(staffMembers);
                }
                case 2 ->{
                displayEmployee(staffMembers,null);
                scanner.nextLine();
                scanner.nextLine();
                }
                case 3 ->{
                    scanner.nextLine();
                    System.out.println("============================================================= Update Employee =============================================================");
                    int searchID = Integer.parseInt(validateInput(scanner, "Enter ID To Search : ", "^\\d+$"));
                    displayEmployee(staffMembers,searchID);
                    System.out.println();
                    updateEmployee(staffMembers,searchID);
                }
                case 4 ->{
                    System.out.println("============================================================= Delete Employee =============================================================");
                    deleteEmployee(staffMembers);
                }
                case 5 -> System.exit(0);
            }
        }while(choice != 5);
    }
    public static void insertEmployee(List<StaffMember> staffMembers){
        Scanner scanner = new Scanner(System.in);
        CellStyle textAlign = new CellStyle(CellStyle.HorizontalAlign.center);
        Table table1 = new Table(4, BorderStyle.UNICODE_BOX_DOUBLE_BORDER,
                ShownBorders.ALL);
        table1.setColumnWidth(0, 25, 30);
        table1.setColumnWidth(1, 25, 30);
        table1.setColumnWidth(2, 25, 30);
        table1.setColumnWidth(3, 25, 30);
        System.out.println("========================= Insert Employee =========================");
        System.out.println("Choose Type : ");

        table1.addCell("1. Volunteer",textAlign);
        table1.addCell("2. Salaries Employee",textAlign);
        table1.addCell("3. Hourly Employee",textAlign);
        table1.addCell("4. Back",textAlign);
        System.out.println(table1.render());
        System.out.print("Enter type Number : ");
        int typeNumber = 0;
        boolean isValidInput = false;
        while (!isValidInput) {
            if (scanner.hasNextInt()) {
                typeNumber = scanner.nextInt();
                if (typeNumber >= 1 && typeNumber <= 4) {
                    isValidInput = true;
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 4.");
                    System.out.print("Enter type Number : ");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid integer.");
                System.out.print("Enter type Number : ");
            }
        }

        switch(typeNumber){
            case 1 -> {
                String volName = validateInput(scanner, "Enter the name: ", "^[a-zA-Z\\s]+$");
                String volAddress = validateInput(scanner, "Enter the address: ", "^[a-zA-Z0-9\\s,-]+$");
                double volSalary = Double.parseDouble(validateInput(scanner,"Enter the salary : ","^\\d{1,3}(,\\d{3})*(\\.\\d+)?$"));
                Volunteer volunteer = new Volunteer(volName, volAddress,  volSalary);
                staffMembers.add(volunteer);
                System.out.println("Volunteer added successfully!!");
            }
            case 2 -> {
                String empName = validateInput(scanner, "Enter the name: ", "^[a-zA-Z\\s]+$");
                String empAddress = validateInput(scanner, "Enter the address: ", "^[a-zA-Z0-9\\s,-]+$");
                double empSalary = Double.parseDouble(validateInput(scanner, "Enter the bonus: ", "^\\d+(\\.\\d+)?$"));
                double empBonus = Double.parseDouble(validateInput(scanner, "Enter the bonus: ", "^\\d+(\\.\\d+)?$"));
                SalariedEmployee salariedEmployee = new SalariedEmployee(empName,empAddress,empSalary,empBonus);
                staffMembers.add(salariedEmployee);
            }
            case 3 ->{
                String empName = validateInput(scanner, "Enter the name: ", "^[a-zA-Z\\s]+$");
                String empAddress = validateInput(scanner, "Enter the address: ", "^[a-zA-Z0-9\\s,-]+$");
                int empHourWorked = Integer.parseInt(validateInput(scanner, "Enter the hour worked: ", "^\\d+$"));
                double empRate = Double.parseDouble(validateInput(scanner, "Enter the rate: ", "^\\d+(\\.\\d+)?$"));
                HourlySalaryEmployee hourlySalaryEmployee = new HourlySalaryEmployee(empName,empAddress,empHourWorked,empRate);
                staffMembers.add(hourlySalaryEmployee);
            }
            case 4 -> {
                return;
            }
            default -> System.out.println("Invalid option. Please select a valid option!!!");
        }
    }
    public static void displayEmployee(List<StaffMember> staffMembers, Integer searchID) {
        CellStyle textAlign = new CellStyle(CellStyle.HorizontalAlign.center);
        Table table3 = new Table(9, BorderStyle.UNICODE_BOX_DOUBLE_BORDER,
                ShownBorders.ALL);
        table3.setColumnWidth(0, 28, 30);
        table3.setColumnWidth(1, 10, 30);
        table3.setColumnWidth(2, 15, 30);
        table3.setColumnWidth(3, 25, 30);
        table3.setColumnWidth(4, 25, 30);
        table3.setColumnWidth(5, 25, 30);
        table3.setColumnWidth(6, 25, 30);
        table3.setColumnWidth(7, 25, 30);
        table3.setColumnWidth(8, 25, 30);
        // Table Header
        table3.addCell("Staff Information", textAlign, 9);
        table3.addCell("Type", textAlign);
        table3.addCell("ID", textAlign);
        table3.addCell("Name", textAlign);
        table3.addCell("Address", textAlign);
        table3.addCell("Salary", textAlign);
        table3.addCell("Bonus", textAlign);
        table3.addCell("Hour", textAlign);
        table3.addCell("Rate", textAlign);
        table3.addCell("Pay", textAlign);
        // Table Data
        boolean found = false;
        for (StaffMember staffMember : staffMembers) {
            if (searchID != null && staffMember.getId() == searchID) {

                addCellWithOptional(table3, Optional.of(String.join(" ", staffMember.getClass().getSimpleName().split("(?=[A-Z])"))));
                addCellWithOptional(table3, Optional.of(String.valueOf(staffMember.getId())));
                addCellWithOptional(table3, Optional.ofNullable(staffMember.getName()));
                addCellWithOptional(table3, Optional.ofNullable(staffMember.getAddress()));
                if (staffMember instanceof Volunteer) {
                    addCellWithOptional(table3, Optional.of(String.valueOf(((Volunteer) staffMember).getSalary())));
                    addCellWithOptional(table3, Optional.empty());
                    addCellWithOptional(table3, Optional.empty());
                    addCellWithOptional(table3, Optional.empty());
                    addCellWithOptional(table3, Optional.of(String.valueOf(staffMember.pay())));
                } else if (staffMember instanceof SalariedEmployee) {
                    addCellWithOptional(table3, Optional.of(String.valueOf(((SalariedEmployee) staffMember).getSalary())));
                    addCellWithOptional(table3, Optional.of(String.valueOf(((SalariedEmployee) staffMember).getBonus())));
                    addCellWithOptional(table3, Optional.empty());
                    addCellWithOptional(table3, Optional.empty());
                    addCellWithOptional(table3, Optional.of(String.valueOf(staffMember.pay())));
                } else {
                    addCellWithOptional(table3, Optional.empty());
                    addCellWithOptional(table3, Optional.empty());
                    addCellWithOptional(table3, Optional.of(String.valueOf(((HourlySalaryEmployee) staffMember).getHourWorked())));
                    addCellWithOptional(table3, Optional.of(String.valueOf(((HourlySalaryEmployee) staffMember).getRate())));
                    addCellWithOptional(table3, Optional.of(String.valueOf(staffMember.pay())));
                }
                found = true;
            }
        }
        if (!found) {
            staffMembers.forEach((member) -> {
                addCellWithOptional(table3, Optional.of(String.join(" ", member.getClass().getSimpleName().split("(?=[A-Z])"))));
                addCellWithOptional(table3, Optional.of(String.valueOf(member.getId())));
                addCellWithOptional(table3, Optional.ofNullable(member.getName()));
                addCellWithOptional(table3, Optional.ofNullable(member.getAddress()));
                if (member instanceof Volunteer) {
                    addCellWithOptional(table3, Optional.of(String.valueOf(((Volunteer) member).getSalary())));
                    addCellWithOptional(table3, Optional.empty());
                    addCellWithOptional(table3, Optional.empty());
                    addCellWithOptional(table3, Optional.empty());
                    addCellWithOptional(table3, Optional.of(String.valueOf(member.pay())));
                } else if (member instanceof SalariedEmployee) {
                    addCellWithOptional(table3, Optional.of(String.valueOf(((SalariedEmployee) member).getSalary())));
                    addCellWithOptional(table3, Optional.of(String.valueOf(((SalariedEmployee) member).getBonus())));
                    addCellWithOptional(table3, Optional.empty());
                    addCellWithOptional(table3, Optional.empty());
                    addCellWithOptional(table3, Optional.of(String.valueOf(member.pay())));
                } else {
                    addCellWithOptional(table3, Optional.empty());
                    addCellWithOptional(table3, Optional.empty());
                    addCellWithOptional(table3, Optional.of(String.valueOf(((HourlySalaryEmployee) member).getHourWorked())));
                    addCellWithOptional(table3, Optional.of(String.valueOf(((HourlySalaryEmployee) member).getRate())));
                    addCellWithOptional(table3, Optional.of(String.valueOf(member.pay())));
                }
            });
        }
        System.out.println(table3.render());
    }
    public static void updateEmployee(List<StaffMember> staffMembers, Integer searchID) {
        Table table4= new Table(4, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE,
                ShownBorders.ALL);
        CellStyle textAlign = new CellStyle(CellStyle.HorizontalAlign.center);
        Scanner scanner = new Scanner(System.in);
        staffMembers.stream()
                .filter(member -> searchID == null || member.getId() == searchID)
                .forEach(member -> {
                    if (member instanceof Volunteer){
                        table4.addCell("Option",textAlign,4);
                        table4.addCell("1). Name",textAlign);
                        table4.addCell("2). Address",textAlign);
                        table4.addCell("3). Salary",textAlign);
                        table4.addCell("0). Back",textAlign);
                        System.out.println(table4.render());
                        int column =Integer.parseInt(validateInput(scanner, "=> Select Column Number : ", "^\\d+$"));
                        switch (column) {
                            case 1:
                                String newName =validateInput(scanner, "Enter the name: ", "^[a-zA-Z\\s]+$");
                                member.setName(newName);
                                break;
                            case 2:
                                String newAddress = validateInput(scanner, "Enter the address: ", "^[a-zA-Z0-9\\s,-]+$");
                                member.setAddress(newAddress);
                                break;
                            case 3:
                                double newSalary = Double.parseDouble(validateInput(scanner,"Enter the salary : ","^\\d{1,3}(,\\d{3})*(\\.\\d+)?$"));
                                 ((Volunteer) member).setSalary(newSalary);
                                break;
                            case 0:
                                break;
                            default:
                                System.out.println("Invalid column number.");
                        }
                    }
                    if(member instanceof SalariedEmployee){
                        Table table5= new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE,
                                ShownBorders.ALL);
                        table5.addCell("Option",textAlign,5);
                        table5.addCell("1). Name",textAlign);
                        table5.addCell("2). Address",textAlign);
                        table5.addCell("3). Salary",textAlign);
                        table5.addCell("4). Bonus: ",textAlign);
                        table5.addCell("5). Cancel",textAlign);
                        System.out.println(table5.render());

                        int column =Integer.parseInt(validateInput(scanner, "=> Select Column Number : ", "^\\d+$"));
                        switch (column){
                            case 1 ->{
                                String newName = validateInput(scanner, "Enter the name: ", "^[a-zA-Z\\s]+$");
                                member.setName(newName);
                            }
                            case 2 ->{

                                String newAddress = validateInput(scanner, "Enter the address: ", "^[a-zA-Z0-9\\s,-]+$");
                                member.setAddress(newAddress);
                            }
                            case 3 ->{

                                double newSalary =  Double.parseDouble(validateInput(scanner,"Enter the salary : ","^\\d{1,3}(,\\d{3})*(\\.\\d+)?$"));
                                ((SalariedEmployee) member).setSalary(newSalary);
                            }
                            case 4 ->{

                                double newBonus =  Double.parseDouble(validateInput(scanner, "Enter the bonus: ", "^\\d+(\\.\\d+)?$"));
                                ((SalariedEmployee) member).setBonus(newBonus);
                            }
                            case 5 ->{
                              break;
                            }
                            default -> System.out.println("invalid Column Number!!!!");
                        }
                    }
                    if(member instanceof HourlySalaryEmployee){
                        Table table6= new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE,
                                ShownBorders.ALL);
                        table6.addCell("Option",textAlign,5);
                        table6.addCell("1). Name",textAlign);
                        table6.addCell("2). Address",textAlign);
                        table6.addCell("3). Hour",textAlign);
                        table6.addCell("4). Rate: ",textAlign);
                        table6.addCell("5). Cancel",textAlign);
                        System.out.println(table6.render());
                        int column =Integer.parseInt(validateInput(scanner, "=> Select Column Number : ", "^\\d+$"));
                        switch (column){
                            case 1 ->{
                                String newName =validateInput(scanner, "Enter the name: ", "^[a-zA-Z\\s]+$");
                                member.setName(newName);
                            }
                            case 2 ->{

                                String newAddress = validateInput(scanner, "Enter the address: ", "^[a-zA-Z0-9\\s,-]+$");
                                member.setAddress(newAddress);
                            }
                            case 3 ->{

                                int newHour = Integer.parseInt(validateInput(scanner, "Enter the hour worked: ", "^\\d+$"));
                                ((HourlySalaryEmployee) member).setHourWorked(newHour);
                            }
                            case 4 ->{

                                double newBonus = Double.parseDouble(validateInput(scanner, "Enter the bonus: ", "^\\d+(\\.\\d+)?$"));
                                ((HourlySalaryEmployee) member).setRate(newBonus);
                            }
                            case 5 ->{
                                break;
                            }
                            default -> System.out.println("invalid Column Number!!!!");
                        }
                    }
                });
    }
    public static void deleteEmployee(List<StaffMember> staffMembers){
        Scanner scanner = new Scanner(System.in);
        displayEmployee(staffMembers,null);
        int staffId = Integer.parseInt(validateInput(scanner,"Enter ID to delete : ", "^\\d+$"));
        Optional<StaffMember> optionalStaffMember = staffMembers.stream()
                .filter(staffMember -> staffMember.getId() == staffId)
                .findFirst();
        if (optionalStaffMember.isPresent()) {
            staffMembers.removeIf(staffMember -> staffMember.getId() == staffId);
            System.out.println("Staff member with ID " + staffId + " has been deleted.");
        } else {
            System.out.println("No staff member with ID " + staffId + " exists.");
        }
    }
    private static void addCellWithOptional(Table table, Optional<String> optionalValue) {
        CellStyle textAlign = new CellStyle(CellStyle.HorizontalAlign.center);
        table.addCell(optionalValue.orElse("..."),textAlign);
    }
    public static String validateInput(Scanner scanner, String prompt, String pattern) {
        String userInput;
        Pattern regex = Pattern.compile(pattern);
        do {
            System.out.print(prompt);
            userInput = scanner.nextLine().trim();
        } while (!regex.matcher(userInput).matches());
        return userInput;
    }

}