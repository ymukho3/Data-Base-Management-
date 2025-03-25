package db2;

public class Customer_Training
{
    private int trainingID;
    private int customerID;

    public Customer_Training ( int customerID, int trainingID) {
        this.trainingID = trainingID;
        this.customerID = customerID;
    }

    // Getters and Setters
    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this. customerID =  customerID;
    }
    public int getTrainingID() {
        return trainingID;
    }

    public void setTrainingID(int trainingID) {
        this.trainingID = trainingID;
    }
}
