package db2;

public class Machine_Training
{
    private int trainingID;
    private int machineID;

    public Machine_Training (int trainingID, int machineID) {
        this.trainingID = trainingID;
        this.machineID = machineID;
    }

    // Getters and Setters
    public int getMachineID() {
        return machineID;
    }

    public void setMachineID(int machineID) {
        this. machineID =  machineID;
    }
    public int getTrainingID() {
        return trainingID;
    }

    public void setTrainingID(int trainingID) {
        this.trainingID = trainingID;
    }
}
