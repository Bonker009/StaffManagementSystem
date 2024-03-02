import org.nocrala.tools.texttablefmt.*;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    static enum TEXT_COLOR {
        RESET("\u001B\0m"),
        BLACK("\u001B[30m"),
        RED("\u001B[31m"),
        GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"),
        BLUE("\u001B[34m"),
        PURPLE("\u001B[35m"),
        CYAN("\u001B[36m"),
        WHITE("\u001B[37m");
        private String colorCode;
        TEXT_COLOR(String colorCode) {
            this.colorCode = colorCode;
        }
        public String getColorCode() {
            return colorCode;
        }
    }

    public static void main(String[] args) {
        CellStyle textAlign = new CellStyle(CellStyle.HorizontalAlign.center);
        int choice = 0;
        List<StaffMember> staffMembers = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        staffMembers.add(new Volunteer("Nha", "PP", 0.0));
        staffMembers.add(new HourlySalaryEmployee("Vuth", "BTB", 60, 10.0));
        staffMembers.add(new Volunteer("Hort", "SR", 0.0));
        staffMembers.add(new HourlySalaryEmployee("Penh Seyha", "TBK", 90, 10));
        staffMembers.add(new SalariedEmployee("Meyling", "PP", 5000, 30));
        staffMembers.add(new SalariedEmployee("Long", "TBK", 2343, 20));
        staffMembers.add(new SalariedEmployee("Chhunyeang", "SR", 123456, 34));
        staffMembers.add(new Volunteer("Chea Panha", "PP", 12345));
        staffMembers.add(new Volunteer("Nha", "PP", 0.0));
        staffMembers.add(new HourlySalaryEmployee("Chan Seyha", "NY", 80, 12));
        staffMembers.add(new HourlySalaryEmployee("John Dave", "CH", 34, 5));
        outerLoop:
        do {
            Table table2 = new Table(2, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE, ShownBorders.ALL);
            table2.setColumnWidth(0, 10, 30);
            table2.setColumnWidth(1, 25, 30);
            table2.addCell(TEXT_COLOR.BLUE.getColorCode() + "Staff Management System".toUpperCase(), textAlign, 2);
            table2.addCell(TEXT_COLOR.CYAN.getColorCode() + "1", textAlign);
            table2.addCell(TEXT_COLOR.PURPLE.getColorCode() + "Insert Employee", textAlign);
            table2.addCell(TEXT_COLOR.CYAN.getColorCode() + "2", textAlign);
            table2.addCell(TEXT_COLOR.PURPLE.getColorCode() + "Display Employee", textAlign);
            table2.addCell(TEXT_COLOR.CYAN.getColorCode() + "3", textAlign);
            table2.addCell(TEXT_COLOR.PURPLE.getColorCode() + "Update Employee", textAlign);
            table2.addCell(TEXT_COLOR.CYAN.getColorCode() + "4", textAlign);
            table2.addCell(TEXT_COLOR.PURPLE.getColorCode() + "Remove Employee", textAlign);
            table2.addCell(TEXT_COLOR.CYAN.getColorCode() + "5", textAlign);
            table2.addCell(TEXT_COLOR.PURPLE.getColorCode() + "Exit", textAlign);
            System.out.println(table2.render());


            choice = Integer.parseInt(validateInput(scanner, "Enter your choice : ", "[1-5]$", "Wrong Choice"));

            switch (choice) {
                case 1 -> {
                    insertEmployee(staffMembers);
                }
                case 2 -> {
                    Table table7 = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE, ShownBorders.ALL);
                    int pageSize = 10;
                    int pageNumber = 1;
                    table7.addCell(TEXT_COLOR.YELLOW.getColorCode() + "1). Next");
                    table7.addCell(TEXT_COLOR.YELLOW.getColorCode() + "2). Back");
                    table7.addCell(TEXT_COLOR.YELLOW.getColorCode() + "3). First");
                    table7.addCell(TEXT_COLOR.YELLOW.getColorCode() + "4). Last");
                    table7.addCell(TEXT_COLOR.YELLOW.getColorCode() + "5). Exit");
                    while (true) {
                        displayEmployee(staffMembers, null, 5, pageNumber);
                        System.out.println(table7.render());

                        int option = Integer.parseInt(validateInput(scanner, "Enter your option: ", "[1-5]$", "Option in range between(1-5)"));
                        switch (option) {
                            case 1 -> pageNumber++;
                            case 2 -> pageNumber = Math.max(1, pageNumber - 1);
                            case 3 -> pageNumber = 1;
                            case 4 -> pageNumber = (int) Math.ceil((double) staffMembers.size() / pageSize) + 1;
                            case 5 -> {
                                continue outerLoop;
                            }
                            default -> {
                                System.out.println(TEXT_COLOR.RED.getColorCode() + "Invalid option. Please try again." + TEXT_COLOR.RESET.getColorCode());
                                return;
                            }
                        }
                    }
                }
                case 3 -> {
                    System.out.println("============================================================= Update Employee =============================================================");
                    int minimumId = staffMembers.getFirst().getId();
                    int maximumId = staffMembers.getLast().getId();
                    String idPattern = "[" + minimumId + "-" + maximumId + "]+$";
                    int searchID = Integer.parseInt(validateInput(scanner, "Enter ID To Search : ", idPattern, "invalid ID between(" + minimumId + "-" + maximumId + ")"));
                    displayEmployee(staffMembers, searchID, 1, 1);
                    System.out.println();
                    updateEmployee(staffMembers, searchID);
                }
                case 4 -> {
                    System.out.println("============================================================= Delete Employee =============================================================" + TEXT_COLOR.RESET.getColorCode());
                    deleteEmployee(staffMembers);
                }
                case 5 -> System.exit(0);
            }
        } while (true);
    }

    public static void insertEmployee(List<StaffMember> staffMembers) {
        Scanner scanner = new Scanner(System.in);
        CellStyle textAlign = new CellStyle(CellStyle.HorizontalAlign.center);
        Table table1 = new Table(4, BorderStyle.UNICODE_BOX_DOUBLE_BORDER,
                ShownBorders.ALL);
        table1.addCell(TEXT_COLOR.YELLOW.getColorCode() + "1. Volunteer", textAlign);
        table1.addCell(TEXT_COLOR.YELLOW.getColorCode() + "2. Salaries Employee", textAlign);
        table1.addCell(TEXT_COLOR.YELLOW.getColorCode() + "3. Hourly Employee", textAlign);
        table1.addCell(TEXT_COLOR.YELLOW.getColorCode() + "4. Back", textAlign);
        table1.setColumnWidth(0, 25, 30);
        table1.setColumnWidth(1, 25, 30);
        table1.setColumnWidth(2, 25, 30);
        table1.setColumnWidth(3, 25, 30);
        boolean insertMore = true;
        while (insertMore) {
            System.out.println("=========================================================================== Insert Employee ===========================================================================");
            System.out.println("Choose Type : ");
            System.out.println(table1.render());
            int typeNumber = Integer.parseInt(validateInput(scanner, "Enter Type Number : ", "[1-4]$", "Number only from 1-4"));
            switch (typeNumber) {
                case 1 -> {
                    String volName = validateInput(scanner, "Enter the name: ", "^[a-zA-Z\\s]+$", "Text Only!!");
                    String volAddress = validateInput(scanner, "Enter the address: ", "^[a-zA-Z0-9\\s,-]+$", "Wrong Format");
                    double volSalary = Double.parseDouble(validateInput(scanner, "Enter the salary : ", "^\\d{1,9}(,\\d{3})*(\\.\\d+)?$", "Integer Only"));
                    Volunteer volunteer = new Volunteer(volName, volAddress, volSalary);
                    staffMembers.add(volunteer);
                    System.out.println("Volunteer added successfully!!");
                }
                case 2 -> {
                    String empName = validateInput(scanner, "Enter the name: ", "^[a-zA-Z\\s]+$", "Text Only!!");
                    String empAddress = validateInput(scanner, "Enter the address: ", "^[a-zA-Z0-9\\s,-]+$", "Wrong Format");
                    double empSalary = Double.parseDouble(validateInput(scanner, "Enter the Salary: ", "^\\d{1,9}(,\\d{3})*(\\.\\d+)?$", "Integer Only"));
                    double empBonus = Double.parseDouble(validateInput(scanner, "Enter the bonus: ", "^\\d{1,9}(,\\d{3})*(\\.\\d+)?$", "Integer Only"));
                    SalariedEmployee salariedEmployee = new SalariedEmployee(empName, empAddress, empSalary, empBonus);
                    staffMembers.add(salariedEmployee);
                }
                case 3 -> {
                    String empName = validateInput(scanner, "Enter the name: ", "^[a-zA-Z\\s]+$", "Text Only");
                    String empAddress = validateInput(scanner, "Enter the address: ", "^[a-zA-Z0-9\\s,-]+$", "Wrong Format");
                    int empHourWorked = Integer.parseInt(validateInput(scanner, "Enter the hour worked: ", "^\\d+$", "Integer Only!!"));
                    double empRate = Double.parseDouble(validateInput(scanner, "Enter the rate: ", "^\\d{1,9}(,\\d{3})*(\\.\\d+)?$", "Integer Only"));
                    HourlySalaryEmployee hourlySalaryEmployee = new HourlySalaryEmployee(empName, empAddress, empHourWorked, empRate);
                    staffMembers.add(hourlySalaryEmployee);
                }
                case 4 -> {
                    insertMore = false;
                }
                default ->
                        System.out.println(TEXT_COLOR.RED.getColorCode() + "Invalid option. Please select a valid option!!!" + TEXT_COLOR.RESET.getColorCode());
            }
        }
    }

    public static void displayEmployee(List<StaffMember> staffMembers, Integer searchID, int pageSize, int pageNumber) {
        CellStyle textAlign = new CellStyle(CellStyle.HorizontalAlign.center);
        Table table3 = new Table(9, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);
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
        table3.addCell(TEXT_COLOR.BLUE.getColorCode() + "Staff Information", textAlign, 9);
        table3.addCell(TEXT_COLOR.CYAN.getColorCode() + "Type", textAlign);
        table3.addCell(TEXT_COLOR.CYAN.getColorCode() + "ID", textAlign);
        table3.addCell(TEXT_COLOR.CYAN.getColorCode() + "Name", textAlign);
        table3.addCell(TEXT_COLOR.CYAN.getColorCode() + "Address", textAlign);
        table3.addCell(TEXT_COLOR.CYAN.getColorCode() + "Salary", textAlign);
        table3.addCell(TEXT_COLOR.CYAN.getColorCode() + "Bonus", textAlign);
        table3.addCell(TEXT_COLOR.CYAN.getColorCode() + "Hour", textAlign);
        table3.addCell(TEXT_COLOR.CYAN.getColorCode() + "Rate", textAlign);
        table3.addCell(TEXT_COLOR.CYAN.getColorCode() + "Pay", textAlign);

        boolean found = false;


        List<StaffMember> filteredStaffMembers = staffMembers.stream()
                .filter(member -> searchID == null || member.getId() == searchID)
                .skip((long) (pageNumber - 1) * pageSize)
                .limit(pageSize)
                .toList();
        for (StaffMember staffMember : filteredStaffMembers) {
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

        if (!found) {
            table3.addCell("No Staff Found!!!!", textAlign, 9);
        }
        System.out.println(table3.render());
    }

    public static void updateEmployee(List<StaffMember> staffMembers, Integer searchID) {
        Table table4 = new Table(4, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE,
                ShownBorders.ALL);
        CellStyle textAlign = new CellStyle(CellStyle.HorizontalAlign.center);
        Scanner scanner = new Scanner(System.in);
        staffMembers.stream()
                .filter(member -> searchID == null || member.getId() == searchID)
                .forEach(member -> {
                    if (member instanceof Volunteer) {
                        table4.addCell("Option", textAlign, 4);
                        table4.addCell("1). Name", textAlign);
                        table4.addCell("2). Address", textAlign);
                        table4.addCell("3). Salary", textAlign);
                        table4.addCell("0). Back", textAlign);
                        System.out.println(table4.render());
                        int column = Integer.parseInt(validateInput(scanner, "=> Select Column Number : ", "[0-3]$", "In Range between (0 - 3 )"));
                        switch (column) {
                            case 1:
                                String newName = validateInput(scanner, "Enter the name: ", "^[a-zA-Z\\s]+$", "Text Only");
                                member.setName(newName);
                                break;
                            case 2:
                                String newAddress = validateInput(scanner, "Enter the address: ", "^[a-zA-Z0-9\\s,-]+$", "Wrong Format");
                                member.setAddress(newAddress);
                                break;
                            case 3:
                                double newSalary = Double.parseDouble(validateInput(scanner, "Enter the salary : ", "^\\d{1,3}(,\\d{3})*(\\.\\d+)?$", "Integer Only"));
                                ((Volunteer) member).setSalary(newSalary);
                                break;
                            case 0:
                                break;
                            default:
                                System.out.println("Invalid column number.");
                        }
                    }
                    if (member instanceof SalariedEmployee) {
                        Table table5 = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE,
                                ShownBorders.ALL);
                        table5.addCell(TEXT_COLOR.YELLOW.getColorCode() + "Option", textAlign, 5);
                        table5.addCell(TEXT_COLOR.YELLOW.getColorCode() + "1). Name", textAlign);
                        table5.addCell(TEXT_COLOR.YELLOW.getColorCode() + "2). Address", textAlign);
                        table5.addCell(TEXT_COLOR.YELLOW.getColorCode() + "3). Salary", textAlign);
                        table5.addCell(TEXT_COLOR.YELLOW.getColorCode() + "4). Bonus: ", textAlign);
                        table5.addCell(TEXT_COLOR.YELLOW.getColorCode() + "5). Cancel", textAlign);
                        System.out.println(table5.render());

                        int column = Integer.parseInt(validateInput(scanner, "=> Select Column Number : ", "[1-5]$", "In Range between ( 1 - 5 )"));
                        switch (column) {
                            case 1 -> {
                                String newName = validateInput(scanner, "Enter the name: ", "^[a-zA-Z\\s]+$", "Text Only");
                                member.setName(newName);
                            }
                            case 2 -> {

                                String newAddress = validateInput(scanner, "Enter the address: ", "^[a-zA-Z0-9\\s,-]+$", "Wrong Format");
                                member.setAddress(newAddress);
                            }
                            case 3 -> {

                                double newSalary = Double.parseDouble(validateInput(scanner, "Enter the salary : ", "^\\d{1,3}(,\\d{3})*(\\.\\d+)?$", "Integer Only"));
                                ((SalariedEmployee) member).setSalary(newSalary);
                            }
                            case 4 -> {

                                double newBonus = Double.parseDouble(validateInput(scanner, "Enter the bonus: ", "^\\d+(\\.\\d+)?$", "Integer Only"));
                                ((SalariedEmployee) member).setBonus(newBonus);
                            }
                            case 5 -> {
                                break;
                            }
                            default -> System.out.println("invalid Column Number!!!!");
                        }
                    }
                    if (member instanceof HourlySalaryEmployee) {
                        Table table6 = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE,
                                ShownBorders.ALL);
                        table6.addCell(TEXT_COLOR.YELLOW.getColorCode() + "Option", textAlign, 5);
                        table6.addCell(TEXT_COLOR.YELLOW.getColorCode() + "1). Name", textAlign);
                        table6.addCell(TEXT_COLOR.YELLOW.getColorCode() + "2). Address", textAlign);
                        table6.addCell(TEXT_COLOR.YELLOW.getColorCode() + "3). Hour", textAlign);
                        table6.addCell(TEXT_COLOR.YELLOW.getColorCode() + "4). Rate: ", textAlign);
                        table6.addCell(TEXT_COLOR.YELLOW.getColorCode() + "5). Cancel", textAlign);
                        System.out.println(table6.render());
                        int column = Integer.parseInt(validateInput(scanner, "=> Select Column Number : ", "[1-5]$", "In Range between( 1 - 5)"));
                        switch (column) {
                            case 1 -> {
                                String newName = validateInput(scanner, "Enter the name: ", "^[a-zA-Z\\s]+$", "Text Only");
                                member.setName(newName);
                            }
                            case 2 -> {

                                String newAddress = validateInput(scanner, "Enter the address: ", "^[a-zA-Z0-9\\s,-]+$", "Wrong Format");
                                member.setAddress(newAddress);
                            }
                            case 3 -> {

                                int newHour = Integer.parseInt(validateInput(scanner, "Enter the hour worked: ", "^\\d+$", "Integer Only"));
                                ((HourlySalaryEmployee) member).setHourWorked(newHour);
                            }
                            case 4 -> {

                                double newBonus = Double.parseDouble(validateInput(scanner, "Enter the bonus: ", "^\\d+(\\.\\d+)?$", "In Number Only"));
                                ((HourlySalaryEmployee) member).setRate(newBonus);
                            }
                            case 5 -> {
                                break;
                            }
                            default -> System.out.println("invalid Column Number!!!!");
                        }
                    }
                });
    }

    public static void deleteEmployee(List<StaffMember> staffMembers) {
        Scanner scanner = new Scanner(System.in);
        int minimumId = staffMembers.getFirst().getId();
        int maximumId = staffMembers.getLast().getId();
        String idPattern = "\\d+";
        int staffId = Integer.parseInt(validateInput(scanner, "Enter ID to delete : ", idPattern, "Wrong ID between (" + minimumId + "-" + maximumId + ")"));
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
        table.addCell(TEXT_COLOR.GREEN.getColorCode() + optionalValue.orElse("..."), textAlign);
    }

    public static String validateInput(Scanner scanner, String input, String pattern, String invalidMessage) {
        String userInput;
        Pattern regex = Pattern.compile(pattern);
        do {
            System.out.print(input);
            userInput = scanner.nextLine().trim();
            if (!regex.matcher(userInput).matches()) {
                System.out.println(invalidMessage);
            }
        } while (!regex.matcher(userInput).matches());
        return userInput;
    }
}