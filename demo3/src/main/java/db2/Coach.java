package db2;


public class Coach
{
    private int id;
    private String name;
    private int salary;
    private int work_hours;

    public Coach(int id, String name, int salary, int work_hours)
    {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.work_hours = work_hours;
    }

    // Getters and Setters
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getSalary()
    {
        return salary;
    }

    public void setSalary(int salary)
    {
        this.salary = salary;
    }

    public int getWork_hours()
    {
        return work_hours;
    }

    public void setWork_hours(int work_hours)
    {
        this.work_hours = work_hours;
    }
}