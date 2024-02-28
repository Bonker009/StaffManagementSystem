public class Volunteer extends StaffMember {
    private double salary;

    public Volunteer(String name, String address, double salary) {
        super(name, address);
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "salary=" + salary +
                "Class name " + getClass().getSimpleName()+
                "} " + super.toString();
    }

    @Override
    public double pay() {
        return this.salary;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
