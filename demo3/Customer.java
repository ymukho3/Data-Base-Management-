package db2;
import java.time.LocalDate;
import java.time.Period;

public class Customer {
    private int id;//s
    private String name;//s
    private String email;//s
    private LocalDate joinDate; //joinDate,subType,customerAddress, gender,age,coachID
    private String subType;
    private String customerAddress;
    private String gender;
    private int age;
    private int coachID;

    public Customer(int id, String name, String email, LocalDate joinDate,
                    String subType, String customerAddress, String gender, int age,int coachID) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.joinDate = joinDate;
        this.subType = subType;
        this.customerAddress = customerAddress;
        this.gender = gender;
        this.coachID = coachID;
        this.age=age;
        calculateAge();
    }

    private void calculateAge() {
        LocalDate currentDate = LocalDate.now();
        this.age = Period.between(joinDate, currentDate).getYears();
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
        calculateAge(); // Recalculate age when joinDate is updated
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCoachID() {
        return coachID;
    }

    public void setCoachID(int coachID) {
        this.coachID = coachID;
    }
}
