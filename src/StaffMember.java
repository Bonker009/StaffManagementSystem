import java.util.concurrent.atomic.AtomicInteger;

public abstract class StaffMember {
    protected int id;
    protected String name;
    protected String address;
    protected static int staffId = 1;

    public StaffMember(String name, String address) {
        this.id = StaffMember.staffId++;

        this.name = name;
        this.address = address;

    }
    @Override
    public String toString() {
        return "StaffMember{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
    public abstract double pay();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
