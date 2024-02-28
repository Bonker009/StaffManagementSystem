public class SalariedEmployee extends StaffMember {
    private double salary;
    private double bonus;

    public SalariedEmployee( String name, String address, double salary, double bonus) {
        super(name, address);
        this.salary = salary;
        this.bonus = bonus;
    }

    @Override
    public String toString() {
        return "SalariedEmployee{" +
                "salary=" + salary +
                ", bonus=" + bonus +
                "} " + super.toString();
    }
    @Override
    public double pay() {
        return bonus + salary;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }
}
