package db2;

public class Machine
{
    private int machineID;
    private String machineName;

    // Constructors
    public Machine(int machineID, String machineName) {
        this.machineID = machineID;
        this.machineName = machineName;
    }

    // Getters and setters
    public int getMachineID() {
        return machineID;
    }

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    // toString method
    @Override
    public String toString() {
        return "Machine{" +
                "machineID=" + machineID +
                ", machineName='" + machineName + '\'' +
                '}';
    }
}
