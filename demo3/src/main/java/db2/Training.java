package db2;

public class Training
{
    private int trainingID;
    private String trainingName;
    private int coachID;
    private String trainingTime;

    public Training(int trainingID, String trainingName, int coachID, String trainingTime) {
        this.trainingID = trainingID;
        this.trainingName = trainingName;
        this.coachID = coachID;
        this.trainingTime = trainingTime;
    }

    // Getters and Setters
    public int getTrainingID() {
        return trainingID;
    }

    public void setTrainingID(int trainingID) {
        this.trainingID = trainingID;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public int getCoachID() {
        return coachID;
    }

    public void setCoachID(int coachID) {
        this.coachID = coachID;
    }

    public String getTrainingTime() {
        return trainingTime;
    }

    public void setTrainingTime(String trainingTime) {
        this.trainingTime = trainingTime;
    }
}



