package db2;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class Main extends Application {
    private ObservableList<Customer> dataList;
    private ObservableList<Customer_Training> dataListCT;
    private ObservableList<Coach> dataListCoach;
    private ObservableList<Training> dataListTraining;
    private ObservableList<Machine> dataListMachine;
    private ObservableList<GymAdmin> dataListAdmin;

    Image coachImage = new Image("file:///C:\\Users\\hp\\Desktop\\Database\\admin.jpg");
    final Button Coach = new Button("Coach Information's",new ImageView(coachImage));
    final Button Training = new Button("Training Information's");
    final Button Machine = new Button("Machine Information's");

    final Button customerButton = new Button("Customer Information's");
    final Button showTableButton = new Button("Show Table");
    final Button addButton = new Button("Add Customer");
    final Button deleteButton = new Button("Delete Customer");
    final Button backButton = new Button("Back");

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) {
        dataList = FXCollections.observableArrayList();
        dataListCoach = FXCollections.observableArrayList();
        dataListTraining = FXCollections.observableArrayList();
        dataListMachine=FXCollections.observableArrayList();
        dataListAdmin=FXCollections.observableArrayList();
        dataListCT = FXCollections.observableArrayList();
        try {
            getData();
            stage.setTitle("Puregym Interface");
            stage.setMaximized(true);

            // Load the background image
            Image backgroundImage = new Image("file:///C:\\Users\\hp\\Desktop\\Database\\admin.jpg");
            ImageView backgroundImageView = new ImageView(backgroundImage);
            backgroundImageView.setPreserveRatio(false);
            backgroundImageView.setFitWidth(700);
            backgroundImageView.setFitHeight(300);

            // Create a VBox for the login form
            VBox loginForm = new VBox(10);
            loginForm.setAlignment(Pos.CENTER_LEFT);
            loginForm.setPadding(new Insets(50));
            loginForm.setStyle("-fx-background-color: white; -fx-padding: 100; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 10);");

            // Create form elements
            Label loginLabel = new Label("LOGIN");
            loginLabel.setStyle("-fx-font-size: 35px; -fx-text-fill: darkcyan; -fx-font-weight: bold;");

            TextField usernameField = new TextField();
            usernameField.setPromptText("Username");
            usernameField.setStyle("-fx-font-size: 20px;");

            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Password");
            passwordField.setStyle("-fx-font-size: 20px;");

            Button loginButton = new Button("Login");
            loginButton.setStyle("-fx-font-size: 20px; -fx-pref-width: 150px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;");

            // Handle login button action
            loginButton.setOnAction(event -> {
                String username = usernameField.getText();
                String password = passwordField.getText();
                if (isValidCredentials(username,password)) { // isValidCredentials(username, password)
                    showAdminInterface(stage);
                } else {
                    showAlert("Invalid Credentials", "Please enter valid username and password.");
                }
            });

            // Add form elements to the VBox
            loginForm.getChildren().addAll(loginLabel, usernameField, passwordField, loginButton);

            // Create a BorderPane to hold the background image and the login form
            BorderPane borderPane = new BorderPane();
            borderPane.setCenter(backgroundImageView);
            borderPane.setRight(loginForm);
            borderPane.setStyle("-fx-background-color: white;"); // Set the background color to white
            BorderPane.setMargin(loginForm, new Insets(50));

            // Create the mainScene with the BorderPane
            Scene mainScene = new Scene(borderPane, 1536, 870);

            // Set the mainScene to the stage
            stage.setScene(mainScene);
            stage.show();

            // Adjust the background image size when the window is resized
            mainScene.widthProperty().addListener((obs, oldVal, newVal) -> {
                backgroundImageView.setFitWidth(newVal.doubleValue() - loginForm.getWidth());
            });
            mainScene.heightProperty().addListener((obs, oldVal, newVal) -> {
                backgroundImageView.setFitHeight(newVal.doubleValue());
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Add this method to validate the login credentials
    private boolean isValidCredentials(String username, String password)
    {
        TableView<GymAdmin> tableView = new TableView<>();
        for (GymAdmin admin : dataListAdmin)
        {
            if (admin.getAdminName().equals(username) && admin.getAdminPassword().equals(password))
            {
                return true;
            }
        }
        return false;
    }

    private void showAdminInterface(Stage stage) {
        Image backgroundImage = new Image("file:///C:\\Users\\hp\\Desktop\\Database\\admin.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setPreserveRatio(false);
        backgroundImageView.setFitWidth(700);
        backgroundImageView.setFitHeight(300);

        // Create a VBox for the login form
        VBox loginForm = new VBox(10);
        loginForm.setAlignment(Pos.CENTER_LEFT);
        loginForm.setPadding(new Insets(10));
        loginForm.setStyle("-fx-background-color: white; -fx-padding: 150; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 10);");

        // Create form elements
        Label loginLabel = new Label("Pure Gym");
        loginLabel.setStyle("-fx-font-size: 35px; -fx-text-fill: darkcyan; -fx-font-weight: bold;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 10);");

        // Create buttons with custom styling
        Button coachButton = new Button("Coach");
        coachButton.setStyle("-fx-font-size: 20px; -fx-pref-width: 150px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;");
        Button trainingButton = new Button("Training");
        trainingButton.setStyle("-fx-font-size: 20px; -fx-pref-width: 150px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;");
        Button customerButton = new Button("Customer");
        customerButton.setStyle("-fx-font-size: 20px; -fx-pref-width: 150px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;");
        Button machineButton = new Button("Machine");
        machineButton.setStyle("-fx-font-size: 20px; -fx-pref-width: 150px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;");


        // Create a BorderPane to hold the background image and the login form
        BorderPane borderPane = new BorderPane();
        Scene adminScene = new Scene(borderPane, 1536, 870);

        // Add form elements to the VBox
        loginForm.getChildren().addAll(loginLabel, coachButton, trainingButton, customerButton, machineButton);

        borderPane.setCenter(backgroundImageView);
        borderPane.setRight(loginForm);
        borderPane.setStyle("-fx-background-color: white;"); // Set the background color to white
        BorderPane.setMargin(loginForm, new Insets(50));
        // Set button actions


        // Set the mainScene to the stage
        stage.setScene(adminScene);
        stage.show();
        trainingButton.setOnAction(e -> TrainingScreen(stage, adminScene));
        coachButton.setOnAction(e -> CoachScreen(stage, adminScene));
        customerButton.setOnAction(e -> CustomerScreen(stage, adminScene));
        machineButton.setOnAction(e -> MachineScreen(stage, adminScene));
    }


    private Button createButtonWithImage(String text, Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100); // Set the width of the image
        imageView.setFitHeight(100); // Set the height of the image
        imageView.setPreserveRatio(true);

        Label label = new Label(text);
        label.setStyle("-fx-font-size: 16px; -fx-text-alignment: center;");

        VBox vBox = new VBox(imageView, label);
        vBox.setAlignment(Pos.CENTER);

        Button button = new Button();
        button.setGraphic(vBox);
        button.setContentDisplay(ContentDisplay.TOP);

        return button;
    }

    //************************************************************
    private void TrainingScreen(Stage stage, Scene adminScene) {
        Image backgroundImage = new Image("file:///C:\\Users\\hp\\Desktop\\Database\\tra.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setPreserveRatio(false);
        backgroundImageView.setFitWidth(900);
        backgroundImageView.setFitHeight(600);

        // Create a VBox for the buttons
        VBox buttonBox = new VBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(15);
        buttonBox.setPadding(new Insets(40));

        // Create buttons with custom styling
        Button add = new Button("Add Training");
        add.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;");
        add.setOnMouseEntered(e -> add.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: Turquoise; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color on mouse enter
        add.setOnMouseExited(e -> add.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color back on mouse exit


        Button showT = new Button("Show Table");
        showT.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;");
        showT.setOnMouseEntered(e -> showT.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: Turquoise; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color on mouse enter
        showT.setOnMouseExited(e -> showT.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color back on mouse exit

        Button delete = new Button("Delete Training");
        delete.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;");
        delete.setOnMouseEntered(e -> delete.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: Turquoise; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color on mouse enter
        delete.setOnMouseExited(e -> delete.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color back on mouse exit

        Button T_coach = new Button("Show Coach Trainings");
        T_coach.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;");
        T_coach.setOnMouseEntered(e -> T_coach.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: Turquoise; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color on mouse enter
        T_coach.setOnMouseExited(e -> T_coach.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color back on mouse exit



        Button SpecificTraining = new Button("Info of a specific training");
        SpecificTraining.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;");
        SpecificTraining.setOnMouseEntered(e -> SpecificTraining.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: Turquoise; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color on mouse enter
        SpecificTraining.setOnMouseExited(e -> SpecificTraining.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color back on mouse exit


        Button T_customer = new Button("Show Count's Customer Trainings");
        T_customer.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;");
        T_customer.setOnMouseEntered(e -> T_customer.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: Turquoise; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color on mouse enter
        T_customer.setOnMouseExited(e -> T_customer.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color back on mouse exit

        Button update = new Button("Update Training");
        update.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;");
        update.setOnMouseEntered(e -> update.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: Turquoise; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color on mouse enter
        update.setOnMouseExited(e -> update.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color back on mouse exit


        Button back = new Button("Back");
        back.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;");
        back.setOnMouseEntered(e -> back.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: Turquoise; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color on mouse enter
        back.setOnMouseExited(e -> back.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color back on mouse exit


        Button trainingCustomer = new Button("Specific Training Customers");
        trainingCustomer.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;");
        trainingCustomer.setOnMouseEntered(e -> trainingCustomer.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: Turquoise; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color on mouse enter
        trainingCustomer.setOnMouseExited(e -> trainingCustomer.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color back on mouse exit


        // Add buttons to the VBox
        buttonBox.getChildren().addAll(showT, add, delete,update, T_coach, SpecificTraining,trainingCustomer,T_customer, back);

        // Create a BorderPane to hold the background image and the buttons
        BorderPane borderPane = new BorderPane();
        Scene trainingScene = new Scene(borderPane, 1370, 730);

        // Set the background image to the center of the BorderPane
        borderPane.setCenter(backgroundImageView);

        // Set the VBox containing the buttons to the right of the BorderPane
        borderPane.setRight(buttonBox);

        // Set button actions
        back.setOnAction(e -> stage.setScene(adminScene));
        showT.setOnAction(e -> showTrainingTable(stage, trainingScene));
        add.setOnAction(e -> addTraining(stage));
        delete.setOnAction(e -> deleteTraining());
        SpecificTraining.setOnAction(e -> SpecificTraining(stage, trainingScene));
        T_coach.setOnAction(e -> TrainingCoach(stage, trainingScene));
        T_customer.setOnAction(e -> TrainingCountCustomer(stage, trainingScene));
        update.setOnAction(e -> updateTraining(stage, trainingScene));
        trainingCustomer.setOnAction(e -> SpecificCustomerTraining(stage, trainingScene));

        // Set the mainScene to the stage
        stage.setScene(trainingScene);
    }
    //Count the number of customers associated with each training
    private void TrainingCountCustomer(Stage stage, Scene previousScene) {
        // Create a stage for user input
        Stage askStage = new Stage();
        VBox askBox = new VBox();
        askBox.setAlignment(Pos.CENTER);
        askBox.setSpacing(10);
        askBox.setPadding(new Insets(20));

        // Create input fields
        Label idLabel = new Label("Training ID:");
        TextField idField = new TextField();

        HBox idBox = new HBox();
        idBox.setAlignment(Pos.CENTER);
        idBox.setSpacing(10);
        idField.setPadding(new Insets(5));
        idBox.getChildren().addAll(idLabel, idField);

        Button nextButton = new Button("Next");

        // Add input fields and buttons to the VBox
        askBox.getChildren().addAll(idBox, nextButton);

        // Create the scene for user input
        Scene askScene = new Scene(askBox, 300, 200);
        askStage.setScene(askScene);
        askStage.setTitle("Training -> Customer");
        askStage.show();

        // Define action for the "Next" button
        nextButton.setOnAction(event -> {
            try {
                // Get the training ID from the input field
                int trainingID = Integer.parseInt(idField.getText());

                // Close the user input stage
                askStage.close();

                // Connect to the database
                try (Connection connection = connectDB()) {
                    // Prepare the SQL statement
                    String query = "SELECT t.trainingID, t.trainingName, COUNT(ct.customerID) AS num_customers " +
                            "FROM training t  JOIN customer_training ct ON t.trainingID = ct.trainingID " +
                            "WHERE t.trainingID = ? " +
                            "GROUP BY t.trainingID, t.trainingName";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, trainingID);

                    // Execute the query
                    ResultSet resultSet = statement.executeQuery();

                    // Process the results and display on the interface
                    if (resultSet.next()) {
                        int trainingId = resultSet.getInt("trainingID");
                        String trainingName = resultSet.getString("trainingName");
                        int numCustomers = resultSet.getInt("num_customers");

                        // Create labels to display the result
                        Label resultLabel = new Label("Training ID: " + trainingId + ", Training Name: " + trainingName + ", Number of Customers: " + numCustomers);
                        resultLabel.setStyle(
                                "-fx-font-size: 16px; " +
                                        "-fx-background-color: darkcyan; " +
                                        "-fx-text-fill: white; " +
                                        "-fx-padding: 10; " +
                                        "-fx-border-radius: 10; " +
                                        "-fx-background-radius: 10; " +
                                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 10);"
                        );

                        // Create a VBox to hold the result label and back button
                        VBox resultBox = new VBox();
                        resultBox.setAlignment(Pos.CENTER);
                        resultBox.setSpacing(10);
                        resultBox.getChildren().add(resultLabel);

                        // Create the back button
                        Button backButton = new Button("Back");
                        backButton.setPadding(new Insets(10));
                        backButton.setOnAction(e -> stage.setScene(previousScene));
                        resultBox.getChildren().add(backButton);

                        // Create the scene for displaying the result
                        Scene resultScene = new Scene(resultBox, 800, 600);

                        // Set the scene to the stage
                        stage.setScene(resultScene);
                    } else {
                        showAlert("Error", "No data found for the specified training ID.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert("Error", "Failed to execute the query.");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                showAlert("Error", "Please enter a valid integer for Training ID.");
            }
        });
    }

    private void updateTraining(Stage stage, Scene previousScene) {
        Stage askStage = new Stage();
        VBox askBox = new VBox();
        askBox.setAlignment(Pos.CENTER);
        askBox.setSpacing(10);
        askBox.setPadding(new Insets(20));

        Label idLabel = new Label("Training ID:");
        TextField idField = new TextField();

        HBox WHBox = new HBox();
        WHBox.setAlignment(Pos.CENTER);
        WHBox.setSpacing(10);
        idField.setPadding(new Insets(10));
        WHBox.getChildren().addAll(idLabel, idField);

        Button next = new Button("Next");

        askBox.getChildren().addAll(WHBox, next);
        Scene askScene = new Scene(askBox, 300, 200);
        askStage.setScene(askScene);
        askStage.setTitle("Update Training");
        askStage.show();

        next.setOnAction(event -> {
            try {
                Integer userID = Integer.parseInt(idField.getText());
                askStage.close();
                TableView<Training> tableView = new TableView<>();
                ObservableList<Training> ValidTraining = FXCollections.observableArrayList();

                for (Training training : dataListTraining) {
                    if (training.getTrainingID() == userID) {
                        ValidTraining.add(training);
                    }
                }

                TableColumn<Training, String> nameCol = new TableColumn<>("Name Training");
                nameCol.setCellValueFactory(new PropertyValueFactory<>("trainingName"));

                TableColumn<Training, String> ttCol = new TableColumn<>("Training Time");
                ttCol.setCellValueFactory(new PropertyValueFactory<>("trainingTime"));

                TableColumn<Training, Integer> cidCol = new TableColumn<>("Coach ID");
                cidCol.setCellValueFactory(new PropertyValueFactory<>("coachID"));

                tableView.getColumns().addAll(nameCol, ttCol, cidCol);

                tableView.setItems(ValidTraining);

                // Create VBox
                VBox EditForm = new VBox(10);
                EditForm.setAlignment(Pos.CENTER_LEFT);
                EditForm.setPadding(new Insets(10));
                EditForm.setStyle("-fx-background-color: white; -fx-padding: 50; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 10);");

                // Create form elements
                Label cidLabel = new Label("Coach ID");
                cidLabel.setStyle("-fx-font-size: 25px; -fx-text-fill: darkcyan; -fx-font-weight: bold;");

                TextField cidField = new TextField();
                cidField.setPromptText("Coach ID");
                cidField.setStyle("-fx-font-size: 15px;");

                Label ttLabel = new Label("Training Time");
                ttLabel.setStyle("-fx-font-size: 25px; -fx-text-fill: darkcyan; -fx-font-weight: bold;");

                TextField ttField = new TextField();
                ttField.setPromptText("Training Time");
                ttField.setStyle("-fx-font-size: 15px;");

                Button button1 = new Button("Update");
                button1.setStyle("-fx-font-size: 15px; -fx-pref-width: 100px; -fx-pref-height: 30px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 10px;");

                // Coach ID updating
                button1.setOnAction(e -> {
                    Training selectedTraining = null;
                    for (Training training : dataListTraining) {
                        if (training.getTrainingID() == userID) {
                            selectedTraining = training;
                            break;
                        }
                    }
                    if (selectedTraining != null) {
                        button1.setDisable(true);
                        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmationAlert.setTitle("Update Coach ID");
                        confirmationAlert.setHeaderText(null);
                        confirmationAlert.setContentText("Are you sure you want to update " + selectedTraining.getTrainingName() + " Coach ID to " + Integer.parseInt(cidField.getText()) + " ?");

                        Optional<ButtonType> deleteResult = confirmationAlert.showAndWait();
                        if (deleteResult.isPresent() && deleteResult.get() == ButtonType.OK) {
                            try {
                                updateTrainingCoachIDDB(selectedTraining, Integer.parseInt(cidField.getText()));
                                for (Training training : dataListTraining) {
                                    if (training.getTrainingID() == userID) {
                                        training.setCoachID(Integer.parseInt(cidField.getText()));
                                        break;
                                    }
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                showAlert("Error", "Unable to update coach ID, please try again.");
                            }
                        }
                    } else {
                        showAlert("Training Not Found", "The training with ID " + userID + " does not exist.");
                    }
                });

                Button button2 = new Button("Update");
                button2.setStyle("-fx-font-size: 15px; -fx-pref-width: 100px; -fx-pref-height: 30px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 10px;");

                // Training Time updating
                button2.setOnAction(e -> {
                    Training selectedTraining = null;
                    for (Training training : dataListTraining) {
                        if (training.getTrainingID() == userID) {
                            selectedTraining = training;
                            break;
                        }
                    }
                    if (selectedTraining != null) {
                        button2.setDisable(true);
                        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmationAlert.setTitle("Update Training Time");
                        confirmationAlert.setHeaderText(null);
                        confirmationAlert.setContentText("Are you sure you want to update " + selectedTraining.getTrainingName() + " Training Time to " + Integer.parseInt(ttField.getText()) + " ?");

                        Optional<ButtonType> deleteResult = confirmationAlert.showAndWait();
                        if (deleteResult.isPresent() && deleteResult.get() == ButtonType.OK) {
                            try {
                                updateTrainingTTDB(selectedTraining, Integer.parseInt(ttField.getText()));
                                for (Training training : dataListTraining) {
                                    if (training.getTrainingID() == userID) {
                                        training.setTrainingTime(ttField.getText());
                                        break;
                                    }
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                showAlert("Error", "Unable to update training time, please try again.");
                            }
                        }
                    } else {
                        showAlert("Training Not Found", "The training with ID " + userID + " does not exist.");
                    }
                });

                // Add form elements to the VBox
                EditForm.getChildren().addAll(cidLabel, cidField, button1, ttLabel, ttField, button2);

                // Create a BorderPane to hold the background image and the form
                BorderPane borderPane = new BorderPane();
                borderPane.setLeft(EditForm);
                borderPane.setStyle("-fx-background-color: white;"); // Set the background color to white
                BorderPane.setMargin(EditForm, new Insets(5));

                Button back = new Button("Back");
                back.setStyle("-fx-font-size: 15px; -fx-pref-width: 100px; -fx-pref-height: 30px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 10px;");
                back.setOnAction(e -> stage.setScene(previousScene));

                VBox vbox = new VBox();
                vbox.getChildren().addAll(tableView, borderPane, back);

                Scene scene = new Scene(vbox, 300, 200);
                stage.setScene(scene);

            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                showAlert("Error", "Enter an integer");
            }
        });
    }

    private void updateTrainingCoachIDDB(Training training, int newS) throws SQLException {
        try (Connection con = connectDB()) {
            String query = "UPDATE Training SET coachID = ? WHERE trainingID = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setInt(1, newS);
                pstmt.setInt(2, training.getTrainingID());
                pstmt.executeUpdate();
            }
        }
    }

    private void updateTrainingTTDB(Training training, int newTT) throws SQLException {
        try (Connection con = connectDB()) {
            String query = "UPDATE Training SET trainingTime = ? WHERE trainingID = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setInt(1, newTT);
                pstmt.setInt(2, training.getTrainingID());
                pstmt.executeUpdate();
            }
        }
    }

    private void TrainingCoach(Stage stage, Scene previousScene) {
        Stage askStage = new Stage();
        VBox askBox = new VBox();
        askBox.setAlignment(Pos.CENTER);
        askBox.setSpacing(10);
        askBox.setPadding(new Insets(20));

        Label idLabel = new Label("Training ID:");
        TextField idField = new TextField();

        HBox WHBox = new HBox();
        WHBox.setAlignment(Pos.CENTER);
        WHBox.setSpacing(10);
        idField.setPadding(new Insets(5));
        WHBox.getChildren().addAll(idLabel, idField);

        Button next = new Button("Next");

        askBox.getChildren().addAll(WHBox, next);
        Scene askScene = new Scene(askBox, 300, 200);
        askStage.setScene(askScene);
        askStage.setTitle("Training -> Coach");
        askStage.show();

        next.setOnAction(event -> {
            try {
                Integer trainingId = Integer.parseInt(idField.getText());
                askStage.close();
                TableView<Coach> tableView = new TableView<>();
                ObservableList<Coach> validCoaches = FXCollections.observableArrayList();

                // Find the training by training ID
                Training training = null;
                for (Training t : dataListTraining) {
                    if (t.getTrainingID() == trainingId) {
                        training = t;
                        break;
                    }
                }

                // If the training is found, find the corresponding coach
                if (training != null) {
                    for (Coach coach : dataListCoach) {
                        if (coach.getId() == training.getCoachID()) {
                            validCoaches.add(coach);
                        }
                    }
                }

                TableColumn<Coach, String> nameCol = new TableColumn<>("Name");
                nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

                TableColumn<Coach, Integer> salaryCol = new TableColumn<>("Salary");
                salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));

                TableColumn<Coach, Integer> whCol = new TableColumn<>("Work Hours");
                whCol.setCellValueFactory(new PropertyValueFactory<>("work_hours"));

                tableView.getColumns().addAll(nameCol, salaryCol, whCol);

                tableView.setItems(validCoaches);

                Button backButton = new Button("Back");
                backButton.setOnAction(e -> stage.setScene(previousScene));

                VBox vbox = new VBox();
                vbox.setSpacing(10);
                vbox.setPadding(new Insets(10));
                vbox.getChildren().addAll(tableView, backButton);

                Scene scene = new Scene(vbox, 800, 600);
                stage.setScene(scene);

            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                showAlert("Error", "Enter an integer");
            }
        });
    }
    private void SpecificTraining(Stage stage, Scene previousScene) {
        Stage askStage = new Stage();
        VBox askBox = new VBox();
        askBox.setAlignment(Pos.CENTER);
        askBox.setSpacing(10);
        askBox.setPadding(new Insets(20));

        Label idLabel = new Label("Training ID:");
        TextField idField = new TextField();

        HBox WHBox = new HBox();
        WHBox.setAlignment(Pos.CENTER);
        WHBox.setSpacing(10);
        idField.setPadding(new Insets(5));
        WHBox.getChildren().addAll(idLabel, idField);

        Button next = new Button("Next");

        askBox.getChildren().addAll(WHBox, next);
        Scene askScene = new Scene(askBox, 300, 200);
        askStage.setScene(askScene);
        askStage.setTitle("Specific Training");
        askStage.show();

        next.setOnAction(event -> {
            try {
                Integer trainingID = Integer.parseInt(idField.getText());
                askStage.close();
                TableView<Training> tableView = new TableView<>();
                ObservableList<Training> validTraining = FXCollections.observableArrayList();

                for (Training training : dataListTraining) {
                    if (training.getTrainingID() == trainingID) {
                        validTraining.add(training);
                    }
                }

                TableColumn<Training, String> nameCol = new TableColumn<>("Training Name");
                nameCol.setCellValueFactory(new PropertyValueFactory<>("trainingName"));

                TableColumn<Training, Integer> cidCol = new TableColumn<>("Coach ID");
                cidCol.setCellValueFactory(new PropertyValueFactory<>("coachID"));

                TableColumn<Training, String> ttCol = new TableColumn<>("Training Time");
                ttCol.setCellValueFactory(new PropertyValueFactory<>("trainingTime"));

                tableView.getColumns().addAll(nameCol, cidCol, ttCol);

                tableView.setItems(validTraining);

                Button backButton = new Button("Back");
                backButton.setOnAction(e -> stage.setScene(previousScene));

                VBox vbox = new VBox();
                vbox.setSpacing(10);
                vbox.setPadding(new Insets(10));
                vbox.getChildren().addAll(tableView, backButton);

                Scene scene = new Scene(vbox, 800, 600);
                stage.setScene(scene);

            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                showAlert("Error", "Enter an integer");
            }
        });
    }
    private void TrainingCustomer(Stage stage, Scene previousScene) {
        // Create a stage for user input
        Stage askStage = new Stage();
        VBox askBox = new VBox();
        askBox.setAlignment(Pos.CENTER);
        askBox.setSpacing(10);
        askBox.setPadding(new Insets(20));

        // Create input fields
        Label idLabel = new Label("Training ID:");
        TextField idField = new TextField();

        HBox idBox = new HBox();
        idBox.setAlignment(Pos.CENTER);
        idBox.setSpacing(10);
        idField.setPadding(new Insets(5));
        idBox.getChildren().addAll(idLabel, idField);

        Button nextButton = new Button("Next");

        // Add input fields and buttons to the VBox
        askBox.getChildren().addAll(idBox, nextButton);

        // Create the scene for user input
        Scene askScene = new Scene(askBox, 300, 200);
        askStage.setScene(askScene);
        askStage.setTitle("Training -> Customer");
        askStage.show();

        // Define action for the "Next" button
        nextButton.setOnAction(event -> {
            try {
                // Get the training ID from the input field
                int trainingID = Integer.parseInt(idField.getText());

                // Close the user input stage
                askStage.close();

                // Connect to the database
                try (Connection connection = connectDB()) {
                    // Prepare the SQL statement
                    String query = "SELECT t.trainingID, t.trainingName, COUNT(ct.customerID) AS num_customers " +
                            "FROM training t LEFT JOIN customer_training ct ON t.trainingID = ct.trainingID " +
                            "WHERE t.trainingID = ? " +
                            "GROUP BY t.trainingID, t.trainingName";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, trainingID);

                    // Execute the query
                    ResultSet resultSet = statement.executeQuery();

                    // Process the results and display on the interface
                    if (resultSet.next()) {
                        int trainingId = resultSet.getInt("trainingID");
                        String trainingName = resultSet.getString("trainingName");
                        int numCustomers = resultSet.getInt("num_customers");

                        // Create labels to display the result
                        Label resultLabel = new Label("Training ID: " + trainingId + ", Training Name: " + trainingName + ", Number of Customers: " + numCustomers);

                        resultLabel.setStyle("-fx-font-size: 20px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;");

                        // Create a VBox to hold the result label and back button
                        VBox resultBox = new VBox();
                        resultBox.setAlignment(Pos.CENTER);
                        resultBox.setSpacing(10);
                        resultBox.getChildren().add(resultLabel);

                        // Create the back button
                        Button backButton = new Button("Back");
                        backButton.setStyle("-fx-font-size: 20px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;");
                        backButton.setPadding(new Insets(20));
                        backButton.setOnAction(e -> stage.setScene(previousScene));
                        resultBox.getChildren().add(backButton);

                        // Create the scene for displaying the result
                        Scene resultScene = new Scene(resultBox, 800, 600);

                        // Set the scene to the stage
                        stage.setScene(resultScene);
                    } else {
                        showAlert("Error", "No data found for the specified training ID.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert("Error", "Failed to execute the query.");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                showAlert("Error", "Please enter a valid integer for Training ID.");
            }
        });
    }
    private void SpecificCustomerTraining(Stage stage, Scene previousScene)
    {
        Stage askStage = new Stage();
        VBox askBox = new VBox();
        askBox.setAlignment(Pos.CENTER);
        askBox.setSpacing(10);
        askBox.setPadding(new Insets(20));

        Label idLabel = new Label("Training ID:\n The customer who has a monthly\n Subscription Type and\n their age >= 20 will appear. ");
        TextField idField = new TextField();

        HBox WHBox = new HBox();
        WHBox.setAlignment(Pos.CENTER);
        WHBox.setSpacing(10);
        idField.setPadding(new Insets(5));
        WHBox.getChildren().addAll(idLabel, idField);

        Button next = new Button("Next");

        askBox.getChildren().addAll(WHBox, next);
        Scene askScene = new Scene(askBox, 500, 200);
        askStage.setScene(askScene);
        askStage.setTitle("Training -> Customer");
        askStage.show();

        next.setOnAction(event -> {
            try
            {
                Integer userid = Integer.parseInt(idField.getText());
                askStage.close();
                TableView<Customer> tableView = new TableView<>();
                ObservableList<Customer> ValidCustomer = FXCollections.observableArrayList();

                for (Customer_Training ct: dataListCT)
                {
                    if (ct.getTrainingID() == userid)
                    {
                        for (Customer customer: dataList)
                        {
                            if (customer.getId() == ct.getCustomerID() && customer.getAge() >=20 && customer.getSubType().equals("monthly"))
                            {
                                ValidCustomer.add(customer);
                            }
                        }

                    }
                }

                TableColumn<Customer, Integer> idCol = new TableColumn<>("Customer ID");
                idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

                TableColumn<Customer, String> nameCol = new TableColumn<>("Name");
                nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

                TableColumn<Customer, String> emailCol = new TableColumn<>("Email");
                emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

                TableColumn<Customer, String> addressCol = new TableColumn<>("Address");
                addressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));

                TableColumn<Customer, String> genderCol = new TableColumn<>("Gender");
                genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));

                TableColumn<Customer, Integer> ageCol = new TableColumn<>("Age");
                ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

                tableView.getColumns().addAll(idCol, nameCol, emailCol, addressCol, genderCol, ageCol);

                tableView.setItems(ValidCustomer);

                Button backButton = new Button("Back");
                backButton.setOnAction(e -> stage.setScene(previousScene));

                VBox vbox = new VBox();
                vbox.setSpacing(10);
                vbox.setPadding(new Insets(10));
                vbox.getChildren().addAll(tableView, backButton);

                Scene scene = new Scene(vbox, 800, 600);
                stage.setScene(scene);

            }
            catch (NumberFormatException ex) {
                ex.printStackTrace();
                showAlert("Error", "Enter an integer");
            }
        });
    }

    //************************************************************

    private void CustomerScreen(Stage stage, Scene adminScene) {
        BorderPane borderPane = new BorderPane();

        // Create the image view and set properties
        Image backgroundImage = new Image("file:///C:\\Users\\hp\\Desktop\\Database\\Cus.jpg");
        ImageView imageView = new ImageView(backgroundImage);
        imageView.setPreserveRatio(true);
        imageView.fitWidthProperty().bind(stage.widthProperty().multiply(2.0 / 3.0));
        imageView.fitHeightProperty().bind(stage.heightProperty());
        borderPane.setCenter(imageView);

        // Create two VBox for the buttons
        VBox leftButtonBox = new VBox(15); // Add spacing between buttons
        VBox rightButtonBox = new VBox(15); // Add spacing between buttons
        leftButtonBox.setAlignment(Pos.CENTER);
        rightButtonBox.setAlignment(Pos.CENTER);
        leftButtonBox.setPadding(new Insets(10));
        rightButtonBox.setPadding(new Insets(10));

        // Initialize all buttons
        Button showTableButton = new Button("Show Table");
        Button addButton = new Button("Add");
        Button deleteButton = new Button("Delete");
        Button backButton = new Button("Back");
        Button showUnpaidButton = new Button("Show Unpaid Customers");
        Button showSubscriptionTypButton = new Button("Search By Subscription Type");
        Button showCustomerDGButton = new Button("Search By Gender");
        Button addressButton = new Button("Search By Address");
        Button SpecificCustomerButton = new Button("Info of a specific customer");
        Button CustomerStatisticsButton = new Button("Statistics");
        Button showTrainingButton = new Button("Show Customer's Training");
        Button editCustomerButton = new Button("Edit Customer");

        // Set style for buttons and make the size uniform
        String buttonStyle = "-fx-font-size: 14px; " +
                "-fx-background-color: black; " +
                "-fx-text-fill: white; " +
                "-fx-padding: 10; " +
                "-fx-border-radius: 5; " +
                "-fx-background-radius: 5; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 10);";

        showTableButton.setStyle(buttonStyle);
        addButton.setStyle(buttonStyle);
        deleteButton.setStyle(buttonStyle);
        backButton.setStyle(buttonStyle);
        showUnpaidButton.setStyle(buttonStyle);
        showSubscriptionTypButton.setStyle(buttonStyle);
        showCustomerDGButton.setStyle(buttonStyle);
        addressButton.setStyle(buttonStyle);
        SpecificCustomerButton.setStyle(buttonStyle);
        CustomerStatisticsButton.setStyle(buttonStyle);
        showTrainingButton.setStyle(buttonStyle);
        editCustomerButton.setStyle(buttonStyle);

        // Set preferred size for all buttons
        double buttonWidth = 200;
        double buttonHeight = 50;

        showTableButton.setPrefSize(buttonWidth, buttonHeight);
        addButton.setPrefSize(buttonWidth, buttonHeight);
        deleteButton.setPrefSize(buttonWidth, buttonHeight);
        backButton.setPrefSize(buttonWidth, buttonHeight);
        showUnpaidButton.setPrefSize(buttonWidth, buttonHeight);
        showSubscriptionTypButton.setPrefSize(buttonWidth, buttonHeight);
        showCustomerDGButton.setPrefSize(buttonWidth, buttonHeight);
        addressButton.setPrefSize(buttonWidth, buttonHeight);
        SpecificCustomerButton.setPrefSize(buttonWidth, buttonHeight);
        CustomerStatisticsButton.setPrefSize(buttonWidth, buttonHeight);
        showTrainingButton.setPrefSize(buttonWidth, buttonHeight);
        editCustomerButton.setPrefSize(buttonWidth, buttonHeight);
        // Add buttons to respective VBoxes
        leftButtonBox.getChildren().addAll(showTableButton, addButton, deleteButton, CustomerStatisticsButton, showCustomerDGButton,editCustomerButton);
        rightButtonBox.getChildren().addAll(showTrainingButton, addressButton, showUnpaidButton, SpecificCustomerButton, showSubscriptionTypButton, backButton);

        // Create an HBox to hold both VBoxes
        HBox hBox = new HBox(20, leftButtonBox, rightButtonBox); // Add spacing between VBoxes
        hBox.setAlignment(Pos.CENTER);

        // Wrap the HBox in a VBox to center it vertically
        VBox centeredButtonBox = new VBox(hBox);
        centeredButtonBox.setAlignment(Pos.CENTER);

        // Add the centered button box to the left side of the border pane
        borderPane.setLeft(centeredButtonBox);

        Scene customerScene = new Scene(borderPane, stage.getWidth(), stage.getHeight());
        stage.setScene(customerScene);

        // Set button actions
        SpecificCustomerButton.setOnAction(e -> SpecificCustomer(stage, customerScene));
        addressButton.setOnAction(e -> showCustomerDADD(stage, customerScene));
        showSubscriptionTypButton.setOnAction(e -> showSubscriptionTyp(stage, customerScene));
        showCustomerDGButton.setOnAction(e -> showCustomerDG(stage, customerScene));
        showTableButton.setOnAction(e -> showTable(stage, customerScene));
        addButton.setOnAction(e -> addCustomer(stage));
        deleteButton.setOnAction(e -> deleteCustomer());
        backButton.setOnAction(e -> stage.setScene(adminScene));
        showUnpaidButton.setOnAction(e -> showUnpaidCustomers(stage, customerScene));
        CustomerStatisticsButton.setOnAction(e -> StatisticsCustomerScreen(stage, customerScene));
        editCustomerButton.setOnAction(event -> promptForCustomerIdAndShowOptions());
        showTrainingButton.setOnAction(event -> {
            // Prompt the user for customer ID
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Enter Customer ID");
            dialog.setHeaderText(null);
            dialog.setContentText("Please enter customer ID:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(customerID -> {
                try {
                    // Convert customer ID to integer
                    int id = Integer.parseInt(customerID);
                    // Retrieve training sessions for the specified customer
                    ObservableList<Training> customerTrainings = getTrainingSessionsForCustomer(new Customer(id, null, null, null, null, null, null, 0, 0, 0));
                    // Display the training sessions
                    if (!customerTrainings.isEmpty()) {
                        displayTrainingSessions(customerTrainings);
                    } else {
                        showAlert("No Training Sessions", "There are no training sessions for the specified customer.");
                    }
                } catch (NumberFormatException e) {
                    showAlert("Invalid Input", "Please enter a valid customer ID.");
                }
            });
        });
    }
    private void promptForCustomerIdAndShowOptions() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Customer ID");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the Customer ID:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(customerIDString -> {
            try {
                int customerID = Integer.parseInt(customerIDString);
                showEditCustomerOptions(customerID);
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Please enter a valid customer ID.");
            }
        });
    }

    private void showEditCustomerOptions(int customerID) {
        // Create a new stage for the edit options
        Stage editStage = new Stage();
        editStage.setTitle("Edit Customer Options");

        // Create a VBox to hold the edit buttons
        VBox editButtonBox = new VBox(20); // Add spacing between buttons
        editButtonBox.setAlignment(Pos.CENTER);
        editButtonBox.setPadding(new Insets(20));

        // Initialize the edit buttons
        Button editEmailButton = new Button("Edit Customer Email");
        Button editAddressButton = new Button("Edit Customer Address");
        Button editSubtypeButton = new Button("Edit Customer Subtype");
        Button editPaymentButton = new Button("Edit Customer Payment");

        // Set style for the edit buttons
        String buttonStyle = "-fx-font-size: 14px; " +
                "-fx-background-color: black; " +
                "-fx-text-fill: white; " +
                "-fx-padding: 10; " +
                "-fx-border-radius: 5; " +
                "-fx-background-radius: 5; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 10);";

        editEmailButton.setStyle(buttonStyle);
        editAddressButton.setStyle(buttonStyle);
        editSubtypeButton.setStyle(buttonStyle);
        editPaymentButton.setStyle(buttonStyle);

        // Set preferred size for the edit buttons
        double buttonWidth = 250;
        double buttonHeight = 40;

        editEmailButton.setPrefSize(buttonWidth, buttonHeight);
        editAddressButton.setPrefSize(buttonWidth, buttonHeight);
        editSubtypeButton.setPrefSize(buttonWidth, buttonHeight);
        editPaymentButton.setPrefSize(buttonWidth, buttonHeight);

        // Add edit buttons to the VBox
        editButtonBox.getChildren().addAll(editEmailButton, editAddressButton, editPaymentButton);

        // Create a scene for the editStage
        Scene editScene = new Scene(editButtonBox, 300, 400);
        editStage.setScene(editScene);
        editStage.show();

        // Set actions for the buttons
        editEmailButton.setOnAction(e -> editCustomerEmail(customerID));
        editPaymentButton.setOnAction(e->editCustomerPayment(customerID));
        editAddressButton.setOnAction(e->editCustomerAddress(customerID));

        // Similarly, set actions for the other buttons if needed
    }
    private boolean updateEmailInDatabase(int customerID, String newEmail) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PureGim?useSSL=false", "root", "yara");

            // Prepare SQL statement to update the customer's email
            String sql = "UPDATE Customer SET email = ? WHERE customerID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newEmail);
            preparedStatement.setInt(2, customerID);

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL exceptions
            return false;
        } finally {
            // Close the resources
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String getCurrentEmail(int customerID) {
        String currentEmail = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Establish database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PureGim?useSSL=false", "root", "yara");

            // Prepare SQL statement to retrieve the current email of the customer
            String sql = "SELECT email FROM Customer WHERE customerID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerID);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Retrieve the email if the customer exists
            if (resultSet.next()) {
                currentEmail = resultSet.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL exceptions
        } finally {
            // Close the resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return currentEmail;
    }

    private void editCustomerEmail(int customerID) {
        // Retrieve the current email of the customer based on the customerID
        String currentEmail = getCurrentEmail(customerID); // Implement this method to retrieve the current email

        // Show a dialog box or input field to get the new email address from the user
        TextInputDialog dialog = new TextInputDialog(currentEmail);
        dialog.setTitle("Edit Customer Email");
        dialog.setHeaderText("Edit Customer Email Address");
        dialog.setContentText("Enter the new email address:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newEmail -> {
            // Update the email address in the database
            boolean updated = updateEmailInDatabase(customerID, newEmail); // Implement this method to update the email

            // Display a confirmation message
            if (updated) {
                showAlert("Email Updated", "Email address updated successfully.");
            } else {
                showAlert("Error", "Failed to update email address.");
            }
        });
    }

    private boolean updateAddressInDatabase(int customerID, String newAddress) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PureGim?useSSL=false", "root", "yara");

            // Prepare SQL statement to update the customer's address
            String sql = "UPDATE Customer SET CustomerAddress = ? WHERE customerID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newAddress);
            preparedStatement.setInt(2, customerID);

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL exceptions
            return false;
        } finally {
            // Close the resources
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String getCurrentAddress(int customerID) {
        String currentAddress = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Establish database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PureGim?useSSL=false", "root", "yara");

            // Prepare SQL statement to retrieve the current address of the customer
            String sql = "SELECT CustomerAddress FROM Customer WHERE customerID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerID);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Retrieve the address if the customer exists
            if (resultSet.next()) {
                currentAddress = resultSet.getString("CustomerAddress");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL exceptions
        } finally {
            // Close the resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return currentAddress;
    }

    private void editCustomerAddress(int customerID) {
        // Retrieve the current address of the customer based on the customerID
        String currentAddress = getCurrentAddress(customerID); // Implement this method to retrieve the current address

        // Show a dialog box or input field to get the new address from the user
        TextInputDialog dialog = new TextInputDialog(currentAddress);
        dialog.setTitle("Edit Customer Address");
        dialog.setHeaderText("Edit Customer Address");
        dialog.setContentText("Enter the new address:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newAddress -> {
            // Update the address in the database
            boolean updated = updateAddressInDatabase(customerID, newAddress); // Implement this method to update the address

            // Display a confirmation message
            if (updated) {
                showAlert("Address Updated", "Address updated successfully.");
            } else {
                showAlert("Error", "Failed to update address.");
            }
        });
    }



    private void editCustomerPayment(int customerID) {
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement1 = null;
        ResultSet resultSet = null;
        ResultSet resultSet1 = null;

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PureGim?useSSL=false", "root", "yara");

            // Check if the customer has already paid
            String sql = "SELECT Cpayment FROM Customer WHERE customerID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerID);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                double cpayment = resultSet.getDouble("Cpayment");
                if (cpayment != 0) {
                    showAlert("Customer Payment", "The customer has already paid.");
                } else {
                    // Get the subtype of the customer
                    String sql1 = "SELECT Subtype FROM Customer WHERE customerID = ?";
                    preparedStatement1 = connection.prepareStatement(sql1);
                    preparedStatement1.setInt(1, customerID);
                    resultSet1 = preparedStatement1.executeQuery();

                    if (resultSet1.next()) {
                        String subtype = resultSet1.getString("Subtype");
                        double expectedAmount = 0;
                        String headerText = "";

                        switch (subtype) {
                            case "yearly":
                                expectedAmount = 3000;
                                headerText = "Invoice for Yearly Subscription is 3000";
                                break;
                            case "monthly":
                                expectedAmount = 300;
                                headerText = "Invoice for Monthly Subscription is 300";
                                break;
                            case "weekly":
                                expectedAmount = 40;
                                headerText = "Invoice for Weekly Subscription is 40";
                                break;
                            default:
                                showAlert("Subscription Error", "Unknown subscription type.");
                                return;
                        }

                        final double finalExpectedAmount = expectedAmount; // Declare as final and assign its value
                        // Show a dialog to prompt for the payment amount
                        TextInputDialog paymentDialog = new TextInputDialog();
                        paymentDialog.setTitle("Customer Payment");
                        paymentDialog.setHeaderText(headerText);
                        paymentDialog.setContentText("Please enter the payment:");

                        Optional<String> result = paymentDialog.showAndWait();
                        result.ifPresent(amount -> {
                            try {
                                double paymentAmount = Double.parseDouble(amount);
                                if (paymentAmount == finalExpectedAmount) { // Use the final variable inside the lambda
                                    // Update the payment status in the database
                                    String updateSql = "UPDATE Customer SET Cpayment = ? WHERE customerID = ?";
                                    PreparedStatement updateStatement = connection.prepareStatement(updateSql);
                                    updateStatement.setDouble(1, paymentAmount);
                                    updateStatement.setInt(2, customerID);
                                    updateStatement.executeUpdate();

                                    showAlert("Payment Confirmation", "The customer has successfully paid.");
                                } else {
                                    showAlert("Payment Error", "The entered amount is incorrect.");
                                }
                            } catch (NumberFormatException e) {
                                showAlert("Payment Error", "Invalid input. Please enter a numeric value.");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Clean up database resources
            try {
                if (resultSet != null) resultSet.close();
                if (resultSet1 != null) resultSet1.close();
                if (preparedStatement != null) preparedStatement.close();
                if (preparedStatement1 != null) preparedStatement1.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private void displayTrainingSessions(ObservableList<Training> trainingSessions) {
        // Create a new Stage (window) to display the training sessions
        Stage stage = new Stage();
        stage.setTitle("Training Sessions");

        // Create a TableView to display the training sessions
        TableView<Training> tableView = new TableView<>(trainingSessions);

        // Define columns
        TableColumn<Training, Integer> idColumn = new TableColumn<>("Training ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("trainingID"));

        TableColumn<Training, String> nameColumn = new TableColumn<>("Training Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("trainingName"));

        TableColumn<Training, Integer> coachColumn = new TableColumn<>("Coach ID");
        coachColumn.setCellValueFactory(new PropertyValueFactory<>("coachID"));

        TableColumn<Training, String> timeColumn = new TableColumn<>("Training Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("trainingTime"));

        // Add columns to the table
        tableView.getColumns().addAll(idColumn, nameColumn, coachColumn, timeColumn);

        // Create a scene and add the table to it
        Scene scene = new Scene(new VBox(tableView), 400, 300);
        stage.setScene(scene);

        // Show the new stage
        stage.show();
    }

    private void showSubscriptionTyp(Stage stage, Scene previousScene)
    {
        Stage askStage = new Stage();

        // Create radio buttons
        RadioButton option1 = new RadioButton("weekly");
        RadioButton option2 = new RadioButton("monthly");
        RadioButton option3 = new RadioButton("yearly");

        // Create a toggle group
        ToggleGroup group = new ToggleGroup();

        // Add radio buttons to the toggle group
        option1.setToggleGroup(group);
        option2.setToggleGroup(group);
        option3.setToggleGroup(group);

        // Create a layout to hold the radio buttons
        VBox askBox = new VBox();
        Button next = new Button("Next");
        askBox.getChildren().addAll(option1, option2, option3, next);

        // Create a Scene
        Scene askScene = new Scene(askBox, 300, 200);

        // Set the Scene on the Stage
        askStage.setScene(askScene);
        askStage.setTitle("Subscription Type");
        askStage.show();

        next.setOnAction(event -> {
            try
            {
                askStage.close();
                RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
                TableView<Customer> tableView = new TableView<>();
                ObservableList<Customer> ValidCustomer = FXCollections.observableArrayList();
                if (selectedRadioButton != null)
                {
                    for (Customer customer : dataList) {
                        if (customer.getSubType().equals(selectedRadioButton.getText())) {
                            ValidCustomer.add(customer);
                        }
                    }
                }
                TableColumn<Customer, Integer> idCol = new TableColumn<>("Customer ID");
                idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

                TableColumn<Customer, String> nameCol = new TableColumn<>("Name");
                nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

                TableColumn<Customer, String> emailCol = new TableColumn<>("Email");
                emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

                TableColumn<Customer, LocalDate> joinDateCol = new TableColumn<>("Join Date");
                joinDateCol.setCellValueFactory(new PropertyValueFactory<>("joinDate"));

                TableColumn<Customer, String> addressCol = new TableColumn<>("Address");
                addressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));

                TableColumn<Customer, Integer> coachIDCol = new TableColumn<>("Coach ID");
                coachIDCol.setCellValueFactory(new PropertyValueFactory<>("coachID"));

                TableColumn<Customer, Double> cPaymentCol = new TableColumn<>("Payment");
                cPaymentCol.setCellValueFactory(new PropertyValueFactory<>("cPayment"));

                tableView.getColumns().addAll(idCol, nameCol, emailCol, joinDateCol, addressCol, coachIDCol, cPaymentCol);


                tableView.setItems(ValidCustomer);

                Button backButton = new Button("Back");
                backButton.setOnAction(e -> stage.setScene(previousScene));

                VBox vbox = new VBox();
                vbox.setSpacing(10);
                vbox.setPadding(new Insets(10));
                vbox.getChildren().addAll(tableView, backButton);

                Scene scene = new Scene(vbox, 800, 600);
                stage.setScene(scene);

            }
            catch (NumberFormatException ex) {
                ex.printStackTrace();
                showAlert("Error", "Something Wrong");
            }
        });
    }

    private void showCustomerDG(Stage stage, Scene previousScene)
    {
        Stage askStage = new Stage();

        // Create radio buttons
        RadioButton option1 = new RadioButton("male");
        RadioButton option2 = new RadioButton("female");

        // Create a toggle group
        ToggleGroup group = new ToggleGroup();

        // Add radio buttons to the toggle group
        option1.setToggleGroup(group);
        option2.setToggleGroup(group);

        // Create a layout to hold the radio buttons
        VBox askBox = new VBox();
        Button next = new Button("Next");
        askBox.getChildren().addAll(option1, option2, next);

        // Create a Scene
        Scene askScene = new Scene(askBox, 300, 200);

        // Set the Scene on the Stage
        askStage.setScene(askScene);
        askStage.setTitle("Gender");
        askStage.show();

        next.setOnAction(event -> {
            try
            {
                askStage.close();
                RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
                TableView<Customer> tableView = new TableView<>();
                ObservableList<Customer> ValidCustomer = FXCollections.observableArrayList();
                if (selectedRadioButton != null)
                {
                    for (Customer customer : dataList) {
                        if (customer.getGender().equals(selectedRadioButton.getText())) {
                            ValidCustomer.add(customer);
                        }
                    }
                }
                TableColumn<Customer, Integer> idCol = new TableColumn<>("Customer ID");
                idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

                TableColumn<Customer, String> nameCol = new TableColumn<>("Name");
                nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

                TableColumn<Customer, String> emailCol = new TableColumn<>("Email");
                emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

                TableColumn<Customer, String> addressCol = new TableColumn<>("Address");
                addressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));

                TableColumn<Customer, Integer> coachIDCol = new TableColumn<>("Coach ID");
                coachIDCol.setCellValueFactory(new PropertyValueFactory<>("coachID"));

                tableView.getColumns().addAll(idCol, nameCol, emailCol, addressCol, coachIDCol);


                tableView.setItems(ValidCustomer);

                Button backButton = new Button("Back");
                backButton.setOnAction(e -> stage.setScene(previousScene));

                VBox vbox = new VBox();
                vbox.setSpacing(10);
                vbox.setPadding(new Insets(10));
                vbox.getChildren().addAll(tableView, backButton);

                Scene scene = new Scene(vbox, 800, 600);
                stage.setScene(scene);

            }
            catch (NumberFormatException ex) {
                ex.printStackTrace();
                showAlert("Error", "Something wrong");
            }
        });
    }


    private void SpecificCustomer(Stage stage, Scene previousScene)
    {
        Stage askStage = new Stage();
        VBox askBox = new VBox();
        askBox.setAlignment(Pos.CENTER);
        askBox.setSpacing(10);
        askBox.setPadding(new Insets(20));

        Label idLabel = new Label("Customer ID:");
        TextField idField = new TextField();

        HBox WHBox = new HBox();
        WHBox.setAlignment(Pos.CENTER);
        WHBox.setSpacing(10);
        idField.setPadding(new Insets(5));
        WHBox.getChildren().addAll(idLabel, idField);

        Button next = new Button("Next");

        askBox.getChildren().addAll(WHBox, next);
        Scene askScene = new Scene(askBox, 300, 200);
        askStage.setScene(askScene);
        askStage.setTitle("Specific Customer");
        askStage.show();

        next.setOnAction(event -> {
            try
            {
                Integer userID = Integer.parseInt(idField.getText());
                askStage.close();
                TableView<Customer> tableView = new TableView<>();
                ObservableList<Customer> ValidCustomer = FXCollections.observableArrayList();

                for (Customer customer : dataList) {
                    if (customer.getId() == userID) {
                        ValidCustomer.add(customer);
                    }
                }

                TableColumn<Customer, Integer> idCol = new TableColumn<>("Customer ID");
                idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

                TableColumn<Customer, String> nameCol = new TableColumn<>("Name");
                nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

                TableColumn<Customer, String> emailCol = new TableColumn<>("Email");
                emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

                TableColumn<Customer, LocalDate> joinDateCol = new TableColumn<>("Join Date");
                joinDateCol.setCellValueFactory(new PropertyValueFactory<>("joinDate"));

                TableColumn<Customer, String> subTypeCol = new TableColumn<>("Subscription Type");
                subTypeCol.setCellValueFactory(new PropertyValueFactory<>("subType"));

                TableColumn<Customer, String> addressCol = new TableColumn<>("Address");
                addressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));

                TableColumn<Customer, String> genderCol = new TableColumn<>("Gender");
                genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));

                TableColumn<Customer, Integer> ageCol = new TableColumn<>("Age");
                ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

                TableColumn<Customer, Integer> coachIDCol = new TableColumn<>("Coach ID");
                coachIDCol.setCellValueFactory(new PropertyValueFactory<>("coachID"));

                TableColumn<Customer, Double> cPaymentCol = new TableColumn<>("Payment");
                cPaymentCol.setCellValueFactory(new PropertyValueFactory<>("cPayment"));

                tableView.getColumns().addAll(idCol, nameCol, emailCol, joinDateCol, subTypeCol, addressCol, genderCol, ageCol, coachIDCol, cPaymentCol);

                tableView.setItems(ValidCustomer);

                Button backButton = new Button("Back");
                backButton.setOnAction(e -> stage.setScene(previousScene));

                VBox vbox = new VBox();
                vbox.setSpacing(10);
                vbox.setPadding(new Insets(10));
                vbox.getChildren().addAll(tableView, backButton);

                Scene scene = new Scene(vbox, 800, 600);
                stage.setScene(scene);

            }
            catch (NumberFormatException ex) {
                ex.printStackTrace();
                showAlert("Error", "Enter an integer");
            }
        });
    }

    private void showCustomerDADD(Stage stage, Scene previousScene)
    {
        Stage askStage = new Stage();
        VBox askBox = new VBox();
        askBox.setAlignment(Pos.CENTER);
        askBox.setSpacing(10);
        askBox.setPadding(new Insets(20));

        Label WHLabel = new Label("Village / City:");
        TextField WHField = new TextField();

        HBox WHBox = new HBox();
        WHBox.setAlignment(Pos.CENTER);
        WHBox.setSpacing(10);
        WHField.setPadding(new Insets(5));
        WHBox.getChildren().addAll(WHLabel, WHField);

        Button next = new Button("Next");

        askBox.getChildren().addAll(WHBox, next);
        Scene askScene = new Scene(askBox, 300, 200);
        askStage.setScene(askScene);
        askStage.setTitle("Village / City");
        askStage.show();

        next.setOnAction(event -> {
            try
            {
                String userAdd = WHField.getText();
                askStage.close();
                TableView<Customer> tableView = new TableView<>();
                ObservableList<Customer> ValidCustomer = FXCollections.observableArrayList();
                String str1 = Character.toUpperCase(userAdd.charAt(0)) + userAdd.substring(1);
                String str2 = Character.toLowerCase(userAdd.charAt(0)) + userAdd.substring(1);
                for (Customer customer : dataList) {
                    if (customer.getCustomerAddress().equals(str1) || customer.getCustomerAddress().equals(str2)){
                        ValidCustomer.add(customer);
                    }
                }

                TableColumn<Customer, Integer> idCol = new TableColumn<>("Customer ID");
                idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

                TableColumn<Customer, String> nameCol = new TableColumn<>("Name");
                nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

                TableColumn<Customer, String> emailCol = new TableColumn<>("Email");
                emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

                TableColumn<Customer, Integer> coachIDCol = new TableColumn<>("Coach ID");
                coachIDCol.setCellValueFactory(new PropertyValueFactory<>("coachID"));

                tableView.getColumns().addAll(idCol, nameCol, emailCol, coachIDCol);

                tableView.setItems(ValidCustomer);

                Button backButton = new Button("Back");
                backButton.setOnAction(e -> stage.setScene(previousScene));

                VBox vbox = new VBox();
                vbox.setSpacing(10);
                vbox.setPadding(new Insets(10));
                vbox.getChildren().addAll(tableView, backButton);

                Scene scene = new Scene(vbox, 800, 600);
                stage.setScene(scene);

            }
            catch (NumberFormatException ex) {
                ex.printStackTrace();
                showAlert("Error", "Enter an integer");
            }
        });
    }
    // *************

    private void CoachScreen(Stage stage, Scene adminScene)
    {
        Image backgroundImage = new Image("file:///C:\\Users\\hp\\Desktop\\Database\\coach.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setPreserveRatio(false);
        backgroundImageView.setFitWidth(900);
        backgroundImageView.setFitHeight(600);

        // Create a VBox for the buttons
        VBox buttonBox = new VBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(15);
        buttonBox.setPadding(new Insets(40));

        // Create buttons with custom styling
        Button add = new Button("Add Coach");
        add.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;");
        add.setOnMouseEntered(e -> add.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: Turquoise; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color on mouse enter
        add.setOnMouseExited(e -> add.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color back on mouse exit


        Button showT = new Button("Show Table");
        showT.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;");
        showT.setOnMouseEntered(e -> showT.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: Turquoise; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color on mouse enter
        showT.setOnMouseExited(e -> showT.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color back on mouse exit


        Button delete = new Button("Delete Coach");
        delete.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;");
        delete.setOnMouseEntered(e -> delete.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: Turquoise; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color on mouse enter
        delete.setOnMouseExited(e -> delete.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color back on mouse exit


        Button showS = new Button("Coach's Salary");
        showS.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;");
        showS.setOnMouseEntered(e -> showS.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: Turquoise; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color on mouse enter
        showS.setOnMouseExited(e -> showS.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color back on mouse exit


        Button showW = new Button("Coach's Work Hours");
        showW.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;");
        showW.setOnMouseEntered(e -> showW.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: Turquoise; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color on mouse enter
        showW.setOnMouseExited(e -> showW.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color back on mouse exit

        Button C_customer = new Button("Show Coach customers");
        C_customer.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;");
        C_customer.setOnMouseEntered(e -> C_customer.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: Turquoise; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color on mouse enter
        C_customer.setOnMouseExited(e -> C_customer.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color back on mouse exit


        Button specificCoach = new Button("Info of a specific coach");
        specificCoach.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;");
        specificCoach.setOnMouseEntered(e -> specificCoach.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: Turquoise; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color on mouse enter
        specificCoach.setOnMouseExited(e -> specificCoach.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color back on mouse exit


        Button StatisticsCoach = new Button("Statistics Coach");
        StatisticsCoach.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;");
        StatisticsCoach.setOnMouseEntered(e -> StatisticsCoach.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: Turquoise; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color on mouse enter
        StatisticsCoach.setOnMouseExited(e -> StatisticsCoach.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color back on mouse exit

        Button CoachTraining = new Button("Show Coach trainings");
        CoachTraining.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;");
        CoachTraining.setOnMouseEntered(e -> CoachTraining.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: Turquoise; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color on mouse enter
        CoachTraining.setOnMouseExited(e -> CoachTraining.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color back on mouse exit

        Button EditCoach = new Button("Update Coach");
        EditCoach.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;");
        EditCoach.setOnMouseEntered(e -> EditCoach.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: Turquoise; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color on mouse enter
        EditCoach.setOnMouseExited(e -> EditCoach.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color back on mouse exit


        Button back = new Button("Back");
        back.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;");
        back.setOnMouseEntered(e -> back.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: Turquoise; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color on mouse enter
        back.setOnMouseExited(e -> back.setStyle("-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;")); // Change color back on mouse exit

        // Add buttons to the VBox
        buttonBox.getChildren().addAll(add, showT, delete, showS, showW, C_customer,specificCoach,StatisticsCoach,CoachTraining,EditCoach, back);

        // Create a BorderPane to hold the background image and the buttons
        BorderPane borderPane = new BorderPane();
        Scene coachScene = new Scene(borderPane, 1370, 730);

        // Set the background image to the center of the BorderPane
        borderPane.setCenter(backgroundImageView);

        // Set the VBox containing the buttons to the right of the BorderPane
        borderPane.setRight(buttonBox);

        // Set button actions
        specificCoach.setOnAction(e -> SpecificCoach(stage, coachScene));
        C_customer.setOnAction(e -> CoachCustomer(stage, coachScene));
        showW.setOnAction(e -> showCoachDWH(stage, coachScene));
        back.setOnAction(e -> stage.setScene(adminScene));
        showT.setOnAction(e -> showCoachTable(stage, coachScene));
        showS.setOnAction(e -> showCoachDSalary(stage, coachScene));
        StatisticsCoach.setOnAction(e -> StatisticsCoachScreen(stage, coachScene));
        add.setOnAction(e -> addCoach(stage));
        delete.setOnAction(e -> deleteCoach());
        CoachTraining.setOnAction(e -> CoachTraining(stage, coachScene));
        EditCoach.setOnAction(e -> UpdateCoach(stage, coachScene));
        // Set the mainScene to the stage
        stage.setScene(coachScene);
    }
    private void UpdateCoach(Stage stage, Scene previousScene)
    {
        Stage askStage = new Stage();
        VBox askBox = new VBox();
        askBox.setAlignment(Pos.CENTER);
        askBox.setSpacing(10);
        askBox.setPadding(new Insets(20));

        Label idLabel = new Label("Coach ID:");
        TextField idField = new TextField();

        HBox WHBox = new HBox();
        WHBox.setAlignment(Pos.CENTER);
        WHBox.setSpacing(10);
        idField.setPadding(new Insets(5));
        WHBox.getChildren().addAll(idLabel, idField);

        Button next = new Button("Next");

        askBox.getChildren().addAll(WHBox, next);
        Scene askScene = new Scene(askBox, 300, 200);
        askStage.setScene(askScene);
        askStage.setTitle("Update Coach");
        askStage.show();

        next.setOnAction(event -> {
            try
            {
                Integer userID = Integer.parseInt(idField.getText());
                askStage.close();
                TableView<Coach> tableView = new TableView<>();
                ObservableList<Coach> ValidCoach = FXCollections.observableArrayList();

                for (Coach coach : dataListCoach) {
                    if (coach.getId() == userID) {
                        ValidCoach.add(coach);
                    }
                }

                TableColumn<Coach, String> nameCol = new TableColumn<>("Name");
                nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

                TableColumn<Coach, Integer> salaryCol = new TableColumn<>("Salary");
                salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));

                TableColumn<Coach, Integer> whCol = new TableColumn<>("Work Hours");
                whCol.setCellValueFactory(new PropertyValueFactory<>("work_hours"));

                tableView.getColumns().addAll(nameCol, salaryCol, whCol);

                tableView.setItems(ValidCoach);


                // Create VBox
                VBox EditForm = new VBox(10);
                EditForm.setAlignment(Pos.CENTER_LEFT);
                EditForm.setPadding(new Insets(10));
                EditForm.setStyle("-fx-background-color: white; -fx-padding: 50; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 10);");

                // Create form elements
                Label SalaryLabel = new Label("Salary");
                SalaryLabel.setStyle("-fx-font-size: 25px; -fx-text-fill: darkcyan; -fx-font-weight: bold;");

                TextField SalaryField = new TextField();
                SalaryField.setPromptText("Salary");
                SalaryField.setStyle("-fx-font-size: 15px;");

                Label WHLabel = new Label("Work Hours");
                WHLabel.setStyle("-fx-font-size: 25px; -fx-text-fill: darkcyan; -fx-font-weight: bold;");

                TextField WHField = new TextField();
                WHField.setPromptText("Work Hours");
                WHField.setStyle("-fx-font-size: 15px;");

                Button button1 = new Button("Update");
                button1.setStyle("-fx-font-size: 15px; -fx-pref-width: 100px; -fx-pref-height: 30px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 10px;");

                // salary updating
                button1.setOnAction(e ->
                {
                    Coach selectedCoach = null;
                    for (Coach coach : dataListCoach) {
                        if (coach.getId() == userID) {
                            selectedCoach = coach;
                            break;
                        }
                    }
                    if (selectedCoach != null)
                    {
                        button1.setDisable(true);
                        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmationAlert.setTitle("Update Couch Salary");
                        confirmationAlert.setHeaderText(null);
                        confirmationAlert.setContentText("Are you sure you want to Update " + selectedCoach.getName() + " Salary to " + Integer.parseInt(SalaryField.getText()) + " ?");

                        Optional<ButtonType> deleteResult = confirmationAlert.showAndWait();
                        if (deleteResult.isPresent() && deleteResult.get() == ButtonType.OK) {
                            try {
                                updateCoachSalaryDB(selectedCoach, Integer.parseInt(SalaryField.getText()));
                                for (Coach coach : dataListCoach)
                                {
                                    if (coach.getId() == userID)
                                    {
                                        coach.setSalary(Integer.parseInt(SalaryField.getText()));
                                        break;
                                    }
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                showAlert("Error", "Unable to update coach salary, please try again.");
                            }
                        }
                    } else {
                        showAlert("Customer Not Found", "The coach with ID " + userID + " does not exist.");
                    }
                });


                Button button2 = new Button("Update");
                button2.setStyle("-fx-font-size: 15px; -fx-pref-width: 100px; -fx-pref-height: 30px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 10px;");

                // work hours updating
                button2.setOnAction(e ->
                {
                    Coach selectedCoach = null;
                    for (Coach coach : dataListCoach) {
                        if (coach.getId() == userID) {
                            selectedCoach = coach;
                            break;
                        }
                    }
                    if (selectedCoach != null)
                    {
                        button2.setDisable(true);
                        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmationAlert.setTitle("Update Couch Work Hours");
                        confirmationAlert.setHeaderText(null);
                        confirmationAlert.setContentText("Are you sure you want to Update " + selectedCoach.getName() + " Work Hours to " + Integer.parseInt(WHField.getText()) + " ?");

                        Optional<ButtonType> deleteResult = confirmationAlert.showAndWait();
                        if (deleteResult.isPresent() && deleteResult.get() == ButtonType.OK) {
                            try {
                                updateCoachWHDB(selectedCoach, Integer.parseInt(WHField.getText()));
                                for (Coach coach : dataListCoach)
                                {
                                    if (coach.getId() == userID)
                                    {
                                        coach.setWork_hours(Integer.parseInt(WHField.getText()));
                                        break;
                                    }
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                showAlert("Error", "Unable to update coach work hours, please try again.");
                            }
                        }
                    } else {
                        showAlert("Customer Not Found", "The coach with ID " + userID + " does not exist.");
                    }
                });


                // Add form elements to the VBox
                EditForm.getChildren().addAll(SalaryLabel, SalaryField, button1, WHLabel, WHField, button2);


                // Create a BorderPane to hold the background image and the login form
                BorderPane borderPane = new BorderPane();
                borderPane.setLeft(EditForm);
                borderPane.setStyle("-fx-background-color: white;"); // Set the background color to white
                BorderPane.setMargin(EditForm, new Insets(30));


                Button back = new Button("Back");
                back.setStyle("-fx-font-size: 15px; -fx-pref-width: 100px; -fx-pref-height: 30px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 10px;");

                VBox vbox = new VBox();
                vbox.getChildren().addAll(tableView, borderPane, back);
                back.setOnAction(e -> stage.setScene(previousScene));

                Scene scene = new Scene(vbox, 600, 400);
                stage.setScene(scene);

            }
            catch (NumberFormatException ex) {
                ex.printStackTrace();
                showAlert("Error", "Enter an integer");
            }
        });
    }

    private void updateCoachSalaryDB(Coach coach, int newS) throws SQLException {
        try (Connection con = connectDB()) {
            String query = "UPDATE Coach SET salary = ? WHERE coachID = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setInt(1, newS);
                pstmt.setInt(2, coach.getId());
                pstmt.executeUpdate();
            }
        }
    }

    private void updateCoachWHDB(Coach coach, int newWH) throws SQLException {
        try (Connection con = connectDB()) {
            String query = "UPDATE Coach SET work_hours = ? WHERE coachID = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setInt(1, newWH);
                pstmt.setInt(2, coach.getId());
                pstmt.executeUpdate();
            }
        }
    }

    private void CoachTraining(Stage stage, Scene previousScene)
    {
        Stage askStage = new Stage();
        VBox askBox = new VBox();
        askBox.setAlignment(Pos.CENTER);
        askBox.setSpacing(10);
        askBox.setPadding(new Insets(20));

        Label idLabel = new Label("Coach ID:");
        TextField idField = new TextField();

        HBox WHBox = new HBox();
        WHBox.setAlignment(Pos.CENTER);
        WHBox.setSpacing(10);
        idField.setPadding(new Insets(5));
        WHBox.getChildren().addAll(idLabel, idField);

        Button next = new Button("Next");

        askBox.getChildren().addAll(WHBox, next);
        Scene askScene = new Scene(askBox, 300, 200);
        askStage.setScene(askScene);
        askStage.setTitle("Coach -> Training");
        askStage.show();

        next.setOnAction(event -> {
            try
            {
                Integer userid = Integer.parseInt(idField.getText());
                askStage.close();
                TableView<Training> tableView = new TableView<>();
                ObservableList<Training> ValidCoach = FXCollections.observableArrayList();

                for (Training training: dataListTraining) {
                    if (training.getCoachID() == userid) {
                        ValidCoach.add(training);
                    }
                }

                TableColumn<Training, Integer> trainingIDColumn = new TableColumn<>("Training ID");
                trainingIDColumn.setCellValueFactory(new PropertyValueFactory<>("trainingID"));

                TableColumn<Training, String> trainingNameColumn = new TableColumn<>("Training Name");
                trainingNameColumn.setCellValueFactory(new PropertyValueFactory<>("trainingName"));

                TableColumn<Training, String> trainingTimeColumn = new TableColumn<>("Training Time");
                trainingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("trainingTime"));

                tableView.getColumns().addAll(trainingIDColumn, trainingNameColumn, trainingTimeColumn);

                tableView.setItems(ValidCoach);

                Button backButton = new Button("Back");
                backButton.setOnAction(e -> stage.setScene(previousScene));

                VBox vbox = new VBox();
                vbox.setSpacing(10);
                vbox.setPadding(new Insets(10));
                vbox.getChildren().addAll(tableView, backButton);

                Scene scene = new Scene(vbox, 800, 600);
                stage.setScene(scene);

            }
            catch (NumberFormatException ex) {
                ex.printStackTrace();
                showAlert("Error", "Enter an integer");
            }
        });
    }
    private void SpecificCoach(Stage stage, Scene previousScene)
    {
        Stage askStage = new Stage();
        VBox askBox = new VBox();
        askBox.setAlignment(Pos.CENTER);
        askBox.setSpacing(10);
        askBox.setPadding(new Insets(20));

        Label idLabel = new Label("Coach ID:");
        TextField idField = new TextField();

        HBox WHBox = new HBox();
        WHBox.setAlignment(Pos.CENTER);
        WHBox.setSpacing(10);
        idField.setPadding(new Insets(5));
        WHBox.getChildren().addAll(idLabel, idField);

        Button next = new Button("Next");

        askBox.getChildren().addAll(WHBox, next);
        Scene askScene = new Scene(askBox, 300, 200);
        askStage.setScene(askScene);
        askStage.setTitle("Specific Coach");
        askStage.show();

        next.setOnAction(event -> {
            try
            {
                Integer userID = Integer.parseInt(idField.getText());
                askStage.close();
                TableView<Coach> tableView = new TableView<>();
                ObservableList<Coach> ValidCoach = FXCollections.observableArrayList();

                for (Coach coach : dataListCoach) {
                    if (coach.getId() == userID) {
                        ValidCoach.add(coach);
                    }
                }

                /*TableColumn<Coach, Integer> idCol = new TableColumn<>("Coach ID");
                idCol.setCellValueFactory(new PropertyValueFactory<>("id"));*/

                TableColumn<Coach, String> nameCol = new TableColumn<>("Name");
                nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

                TableColumn<Coach, Integer> salaryCol = new TableColumn<>("Salary");
                salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));

                TableColumn<Coach, Integer> whCol = new TableColumn<>("Work Hours");
                whCol.setCellValueFactory(new PropertyValueFactory<>("work_hours"));

                tableView.getColumns().addAll(nameCol, salaryCol, whCol);

                tableView.setItems(ValidCoach);

                Button backButton = new Button("Back");
                backButton.setOnAction(e -> stage.setScene(previousScene));

                VBox vbox = new VBox();
                vbox.setSpacing(10);
                vbox.setPadding(new Insets(10));
                vbox.getChildren().addAll(tableView, backButton);

                Scene scene = new Scene(vbox, 800, 600);
                stage.setScene(scene);

            }
            catch (NumberFormatException ex) {
                ex.printStackTrace();
                showAlert("Error", "Enter an integer");
            }
        });
    }


    private void CoachCustomer(Stage stage, Scene previousScene)
    {
        Stage askStage = new Stage();
        VBox askBox = new VBox();
        askBox.setAlignment(Pos.CENTER);
        askBox.setSpacing(10);
        askBox.setPadding(new Insets(20));

        Label idLabel = new Label("Coach ID:");
        TextField idField = new TextField();

        HBox WHBox = new HBox();
        WHBox.setAlignment(Pos.CENTER);
        WHBox.setSpacing(10);
        idField.setPadding(new Insets(5));
        WHBox.getChildren().addAll(idLabel, idField);

        Button next = new Button("Next");

        askBox.getChildren().addAll(WHBox, next);
        Scene askScene = new Scene(askBox, 300, 200);
        askStage.setScene(askScene);
        askStage.setTitle("Coach -> Customer");
        askStage.show();

        next.setOnAction(event -> {
            try
            {
                Integer userid = Integer.parseInt(idField.getText());
                askStage.close();
                TableView<Customer> tableView = new TableView<>();
                ObservableList<Customer> ValidCoach = FXCollections.observableArrayList();

                for (Customer customer: dataList) {
                    if (customer.getCoachID() == userid) {
                        ValidCoach.add(customer);
                    }
                }

                TableColumn<Customer, Integer> idCol = new TableColumn<>("Customer ID");
                idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

                TableColumn<Customer, String> nameCol = new TableColumn<>("Name");
                nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

                TableColumn<Customer, String> emailCol = new TableColumn<>("Email");
                emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

                TableColumn<Customer, String> subTypeCol = new TableColumn<>("Subscription Type");
                subTypeCol.setCellValueFactory(new PropertyValueFactory<>("subType"));

                TableColumn<Customer, String> addressCol = new TableColumn<>("Address");
                addressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));

                TableColumn<Customer, String> genderCol = new TableColumn<>("Gender");
                genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));

                TableColumn<Customer, Integer> ageCol = new TableColumn<>("Age");
                ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

                /* TableColumn<Customer, Integer> coachIDCol = new TableColumn<>("Coach ID");
                   coachIDCol.setCellValueFactory(new PropertyValueFactory<>("coachID")); */

                tableView.getColumns().addAll(idCol, nameCol, emailCol, subTypeCol, addressCol, genderCol, ageCol);

                tableView.setItems(ValidCoach);

                Button backButton = new Button("Back");
                backButton.setOnAction(e -> stage.setScene(previousScene));

                VBox vbox = new VBox();
                vbox.setSpacing(10);
                vbox.setPadding(new Insets(10));
                vbox.getChildren().addAll(tableView, backButton);

                Scene scene = new Scene(vbox, 800, 600);
                stage.setScene(scene);

            }
            catch (NumberFormatException ex) {
                ex.printStackTrace();
                showAlert("Error", "Enter an integer");
            }
        });
    }

    private void addCoach(Stage stage)
    {
        Stage addStage = new Stage();
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        // Create labeled fields for each input
        Label idLabel = new Label("Coach ID:");
        TextField idField = new TextField();
        HBox idBox = new HBox(10, idLabel, idField);

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        HBox nameBox = new HBox(10, nameLabel, nameField);

        Label salaryLabel = new Label("Salary:");
        TextField salaryField = new TextField();
        HBox salaryBox = new HBox(10, salaryLabel, salaryField);

        Label whLabel = new Label("Work Hours:");
        TextField whField = new TextField();
        HBox whBox = new HBox(10, whLabel, whField);

        Button saveB = new Button("Save");
        vBox.getChildren().addAll(idBox, nameBox, salaryBox, whBox, saveB);
        Scene scene = new Scene(vBox, 400, 500);
        addStage.setScene(scene);
        addStage.setTitle("Add Coach");
        addStage.show();

        saveB.setOnAction(e ->
        {
            try
            {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                int salary = Integer.parseInt(salaryField.getText());
                int wh = Integer.parseInt(whField.getText());

                Coach coach = new Coach(id, name, salary, wh);
                addCoachToDB(coach);
                dataListCoach.add(coach);
                addStage.close();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                showAlert("Error", "Invalid input, please try again.");
            }
        });
    }

    private void addCoachToDB(Coach coach) throws SQLException
    {
        try (Connection con = connectDB()) {
            String query = "INSERT INTO Coach (coachID, coachName, salary, work_hours) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(query))
            {
                pstmt.setInt(1, coach.getId());
                pstmt.setString(2, coach.getName());
                pstmt.setInt(3, coach.getSalary());
                pstmt.setInt(4, coach.getWork_hours());
                pstmt.executeUpdate();
            }
        }
    }

    private void deleteCoach()
    {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Coach");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the Coach ID:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(coachID -> {
            try {
                int id = Integer.parseInt(coachID);
                Coach selectedCoach = null;
                for (Coach coach : dataListCoach) {
                    if (coach.getId() == id) {
                        selectedCoach = coach;
                        break;
                    }
                }
                if (selectedCoach != null) {
                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Delete Coach");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText("Are you sure you want to delete " + selectedCoach.getName() + "?");

                    Optional<ButtonType> deleteResult = confirmationAlert.showAndWait();
                    if (deleteResult.isPresent() && deleteResult.get() == ButtonType.OK) {
                        try {
                            deleteCoachFromDB(selectedCoach);
                            dataListCoach.remove(selectedCoach);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            showAlert("Error", "Unable to delete coach, please try again.");
                        }
                    }
                } else {
                    showAlert("Customer Not Found", "The coach with ID " + id + " does not exist.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid customer ID.");
            }
        });
    }

    private void deleteCoachFromDB(Coach coach) throws SQLException
    {
        try (Connection con = connectDB()) {
            String query = "DELETE FROM Coach WHERE coachID = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setInt(1, coach.getId());
                pstmt.executeUpdate();
            }
        }
    }

    private void showCoachDWH(Stage stage, Scene previousScene)
    {
        Stage askStage = new Stage();
        VBox askBox = new VBox();
        askBox.setAlignment(Pos.CENTER);
        askBox.setSpacing(10);
        askBox.setPadding(new Insets(20));

        Label WHLabel = new Label("Work Hours:");
        TextField WHField = new TextField();

        HBox WHBox = new HBox();
        WHBox.setAlignment(Pos.CENTER);
        WHBox.setSpacing(10);
        WHField.setPadding(new Insets(5));
        WHBox.getChildren().addAll(WHLabel, WHField);

        Button next = new Button("Next");

        askBox.getChildren().addAll(WHBox, next);
        Scene askScene = new Scene(askBox, 300, 200);
        askStage.setScene(askScene);
        askStage.setTitle("work hours");
        askStage.show();

        next.setOnAction(event -> {
            try
            {
                Integer userWH = Integer.parseInt(WHField.getText());
                askStage.close();
                TableView<Coach> tableView = new TableView<>();
                ObservableList<Coach> ValidCoach = FXCollections.observableArrayList();

                for (Coach coach : dataListCoach) {
                    if (coach.getWork_hours() >= userWH) {
                        ValidCoach.add(coach);
                    }
                }

                TableColumn<Coach, Integer> idCol = new TableColumn<>("Coach ID");
                idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

                TableColumn<Coach, String> nameCol = new TableColumn<>("Name");
                nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

                TableColumn<Coach, Integer> salaryCol = new TableColumn<>("Salary");
                salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));

                TableColumn<Coach, Integer> whCol = new TableColumn<>("Work Hours");
                whCol.setCellValueFactory(new PropertyValueFactory<>("work_hours"));

                tableView.getColumns().addAll(idCol, nameCol, salaryCol, whCol);

                tableView.setItems(ValidCoach);

                Button backButton = new Button("Back");
                backButton.setOnAction(e -> stage.setScene(previousScene));

                VBox vbox = new VBox();
                vbox.setSpacing(10);
                vbox.setPadding(new Insets(10));
                vbox.getChildren().addAll(tableView, backButton);

                Scene scene = new Scene(vbox, 800, 600);
                stage.setScene(scene);

            }
            catch (NumberFormatException ex) {
                ex.printStackTrace();
                showAlert("Error", "Enter an integer");
            }
        });
    }

    private void showCoachDSalary(Stage stage, Scene previousScene)
    {
        Stage askStage = new Stage();
        VBox askBox = new VBox();
        askBox.setAlignment(Pos.CENTER);
        askBox.setSpacing(10);
        askBox.setPadding(new Insets(20));

        Label salaryLabel = new Label("Salary:");
        TextField salaryField = new TextField();

        HBox salaryBox = new HBox();
        salaryBox.setAlignment(Pos.CENTER);
        salaryBox.setSpacing(10);
        salaryField.setPadding(new Insets(5));
        salaryBox.getChildren().addAll(salaryLabel, salaryField);

        Button next = new Button("Next");

        askBox.getChildren().addAll(salaryBox, next);
        Scene askScene = new Scene(askBox, 300, 200);
        askStage.setScene(askScene);
        askStage.setTitle("Salary");
        askStage.show();

        next.setOnAction(event -> {
            try
            {
                Integer userSalary = Integer.parseInt(salaryField.getText());
                askStage.close();
                TableView<Coach> tableView = new TableView<>();
                ObservableList<Coach> ValidCoach = FXCollections.observableArrayList();

                for (Coach coach : dataListCoach) {
                    if (coach.getSalary() >= userSalary) {
                        ValidCoach.add(coach);
                    }
                }

                TableColumn<Coach, Integer> idCol = new TableColumn<>("Coach ID");
                idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

                TableColumn<Coach, String> nameCol = new TableColumn<>("Name");
                nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

                TableColumn<Coach, Integer> salaryCol = new TableColumn<>("Salary");
                salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));

                TableColumn<Coach, Integer> whCol = new TableColumn<>("Work Hours");
                whCol.setCellValueFactory(new PropertyValueFactory<>("work_hours"));

                tableView.getColumns().addAll(idCol, nameCol, salaryCol, whCol);

                tableView.setItems(ValidCoach);

                Button backButton = new Button("Back");
                backButton.setOnAction(e -> stage.setScene(previousScene));

                VBox vbox = new VBox();
                vbox.setSpacing(10);
                vbox.setPadding(new Insets(10));
                vbox.getChildren().addAll(tableView, backButton);

                Scene scene = new Scene(vbox, 800, 600);
                stage.setScene(scene);

            }
            catch (NumberFormatException ex) {
                ex.printStackTrace();
                showAlert("Error", "Enter an integer");
            }
        });
    }
    private void showCoachTable(Stage stage, Scene coachScene)
    {
        TableView<Coach> tableView = new TableView<>();
        tableView.setItems(dataListCoach);

        TableColumn<Coach, Integer> idCol = new TableColumn<>("Coach ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Coach, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Coach, Integer> salaryCol = new TableColumn<>("Salary");
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));

        TableColumn<Coach, Integer> whCol = new TableColumn<>("work hours");
        whCol.setCellValueFactory(new PropertyValueFactory<>("work_hours"));

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> stage.setScene(coachScene));

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(tableView, backButton);
        tableView.getColumns().addAll(idCol, nameCol, salaryCol, whCol);

        Scene scene = new Scene(vbox, 800, 600);
        stage.setScene(scene);
    }
    //***********************************************
    private void showTrainingTable(Stage stage, Scene previousScene) {
        TableView<Training> tableView = new TableView<>();

        // Define columns
        TableColumn<Training, Integer> trainingIDColumn = new TableColumn<>("Training ID");
        trainingIDColumn.setCellValueFactory(new PropertyValueFactory<>("trainingID"));

        TableColumn<Training, String> trainingNameColumn = new TableColumn<>("Training Name");
        trainingNameColumn.setCellValueFactory(new PropertyValueFactory<>("trainingName"));

        TableColumn<Training, Integer> coachIDColumn = new TableColumn<>("Coach ID");
        coachIDColumn.setCellValueFactory(new PropertyValueFactory<>("coachID"));

        TableColumn<Training, String> trainingTimeColumn = new TableColumn<>("Training Time");
        trainingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("trainingTime"));

        // Add columns to the table
        tableView.getColumns().addAll(trainingIDColumn, trainingNameColumn, coachIDColumn, trainingTimeColumn);

        // Set data to the table
        tableView.setItems(dataListTraining);

        // Create a VBox to hold the table and a back button
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 16px;");
        backButton.setOnAction(e -> stage.setScene(previousScene));

        vBox.getChildren().addAll(tableView, backButton);

        // Create a new scene for the table view
        Scene trainingScene = new Scene(vBox, 800, 600);
        stage.setScene(trainingScene);




    }

    //********************************************
    private void addTraining(Stage stage) {
        Stage addStage = new Stage();
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        // Create labeled fields for each input
        Label idLabel = new Label("Training ID:");
        TextField idField = new TextField();
        HBox idBox = new HBox(10, idLabel, idField);

        Label nameLabel = new Label("Training Name:");
        TextField nameField = new TextField();
        HBox nameBox = new HBox(10, nameLabel, nameField);

        Label cidLabel = new Label("Coach ID:");
        TextField cidField = new TextField();
        HBox cidBox = new HBox(10, cidLabel, cidField);

        Label ttLabel = new Label("Training Time:");
        TextField ttField = new TextField();
        HBox ttBox = new HBox(10, ttLabel, ttField);

        Button saveT = new Button("Save");
        vBox.getChildren().addAll(idBox, nameBox, cidBox, ttBox, saveT);
        Scene scene = new Scene(vBox, 400, 500);
        addStage.setScene(scene);
        addStage.setTitle("Add Training");
        addStage.show();

        saveT.setOnAction(e ->
        {
            try
            {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                int cid = Integer.parseInt(cidField.getText());
                String tt = ttField.getText();

                Training training = new Training(id, name, cid, tt);
                addTrainingToDB(training);
                dataListTraining.add(training);
                addStage.close();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                showAlert("Error", "Invalid input, please try again.");
            }
        });
    }

    private void addTrainingToDB(Training training ) throws SQLException
    {
        try (Connection con = connectDB()) {
            String query = "INSERT INTO Training (trainingID, trainingName, coachID, trainingTime) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(query))
            {
                pstmt.setInt(1, training.getTrainingID());
                pstmt.setString(2, training.getTrainingName());
                pstmt.setInt(3, training.getCoachID());
                pstmt.setString(4, training.getTrainingTime());
                pstmt.executeUpdate();
            }
        }
    }

    //***********************************************
    private void deleteTraining()
    {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Training");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the Training ID:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(trainingID -> {
            try {
                int id = Integer.parseInt(trainingID);
                Training selectedTraining = null;
                for (Training training : dataListTraining) {
                    if (training.getTrainingID() == id) {
                        selectedTraining = training;
                        break;
                    }
                }
                if (selectedTraining != null) {
                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Delete Training");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText("Are you sure you want to delete " + selectedTraining.getTrainingName() + "?");

                    Optional<ButtonType> deleteResult = confirmationAlert.showAndWait();
                    if (deleteResult.isPresent() && deleteResult.get() == ButtonType.OK) {
                        try {
                            deleteTrainingFromDB(selectedTraining);
                            dataListTraining.remove(selectedTraining);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            showAlert("Error", "Unable to delete training, please try again.");
                        }
                    }
                } else {
                    showAlert("Training Not Found", "The training with ID " + id + " does not exist.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid training ID.");
            }
        });
    }
    private boolean updateCoachInDatabase(int customerID, String newCoach) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PureGim?useSSL=false", "root", "yara");

            // Prepare SQL statement to update the customer's coach
            String sql = "UPDATE Customer SET Coach = ? WHERE customerID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newCoach);
            preparedStatement.setInt(2, customerID);

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL exceptions
            return false;
        } finally {
            // Close the resources
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }




    private void deleteTrainingFromDB(Training training) throws SQLException
    {
        try (Connection con = connectDB()) {
            String query = "DELETE FROM Training WHERE trainingID = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setInt(1, training.getTrainingID());
                pstmt.executeUpdate();
            }
        }
    }
    //*************************************************************************************************

    private void showUnpaidCustomers(Stage stage, Scene previousScene) {
        TableView<Customer> tableView = new TableView<>();
        ObservableList<Customer> unpaidCustomers = FXCollections.observableArrayList();

        for (Customer customer : dataList) {
            if (customer.getCPayment() == 0) {
                unpaidCustomers.add(customer);
            }
        }

        TableColumn<Customer, Integer> idCol = new TableColumn<>("Customer ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Customer, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Customer, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Customer, LocalDate> joinDateCol = new TableColumn<>("Join Date");
        joinDateCol.setCellValueFactory(new PropertyValueFactory<>("joinDate"));

        TableColumn<Customer, String> subTypeCol = new TableColumn<>("Subscription Type");
        subTypeCol.setCellValueFactory(new PropertyValueFactory<>("subType"));

        TableColumn<Customer, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));

        TableColumn<Customer, String> genderCol = new TableColumn<>("Gender");
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));

        TableColumn<Customer, Integer> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Customer, Integer> coachIDCol = new TableColumn<>("Coach ID");
        coachIDCol.setCellValueFactory(new PropertyValueFactory<>("coachID"));

        TableColumn<Customer, Double> cPaymentCol = new TableColumn<>("Payment");
        cPaymentCol.setCellValueFactory(new PropertyValueFactory<>("cPayment"));

        tableView.getColumns().addAll(idCol, nameCol, emailCol, joinDateCol, subTypeCol, addressCol, genderCol, ageCol, coachIDCol, cPaymentCol);

        tableView.setItems(unpaidCustomers);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> stage.setScene(previousScene));

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(tableView, backButton);

        Scene scene = new Scene(vbox, 800, 600);
        stage.setScene(scene);
    }

    private void showTable(Stage stage, Scene customerScene) {
        TableView<Customer> tableView = new TableView<>();
        tableView.setItems(dataList);

        TableColumn<Customer, Integer> idCol = new TableColumn<>("Customer ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Customer, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Customer, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Customer, LocalDate> joinDateCol = new TableColumn<>("Join Date");
        joinDateCol.setCellValueFactory(new PropertyValueFactory<>("joinDate"));

        TableColumn<Customer, String> subTypeCol = new TableColumn<>("Subscription Type");
        subTypeCol.setCellValueFactory(new PropertyValueFactory<>("subType"));

        TableColumn<Customer, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));

        TableColumn<Customer, String> genderCol = new TableColumn<>("Gender");
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));

        TableColumn<Customer, Integer> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Customer, Integer> coachIDCol = new TableColumn<>("Coach ID");
        coachIDCol.setCellValueFactory(new PropertyValueFactory<>("coachID"));

        TableColumn<Customer, Double> cPaymentCol = new TableColumn<>("Payment");
        cPaymentCol.setCellValueFactory(new PropertyValueFactory<>("cPayment"));

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> stage.setScene(customerScene));

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(tableView, backButton);
        tableView.getColumns().addAll(idCol, nameCol, emailCol, joinDateCol, subTypeCol, addressCol, genderCol, ageCol, coachIDCol, cPaymentCol);

        Scene scene = new Scene(vbox, 800, 600);
        stage.setScene(scene);
    }

    private void addCustomer(Stage stage) {
        Stage addStage = new Stage();
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        // Create labeled fields for each input
        Label idLabel = new Label("Customer ID:");
        TextField idField = new TextField();
        HBox idBox = new HBox(10, idLabel, idField);

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        HBox nameBox = new HBox(10, nameLabel, nameField);

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        HBox emailBox = new HBox(10, emailLabel, emailField);

        Label joinDateLabel = new Label("Join Date:");
        DatePicker joinDatePicker = new DatePicker();
        HBox joinDateBox = new HBox(10, joinDateLabel, joinDatePicker);

        Label subTypeLabel = new Label("Subscription Type:");
        ComboBox<String> subTypeField = new ComboBox<>();
        subTypeField.getItems().addAll("Weekly", "Monthly", "Yearly");
        HBox subTypeBox = new HBox(10, subTypeLabel, subTypeField);

        Label addressLabel = new Label("Address:");
        TextField addressField = new TextField();
        HBox addressBox = new HBox(10, addressLabel, addressField);

        Label genderLabel = new Label("Gender:");
        ComboBox<String> genderField = new ComboBox<>();
        genderField.getItems().addAll("Female", "Male");
        HBox genderBox = new HBox(10, genderLabel, genderField);

        Label ageLabel = new Label("Age:");
        TextField ageField = new TextField();
        HBox ageBox = new HBox(10, ageLabel, ageField);

        Label coachIDLabel = new Label("Coach ID:");
        TextField coachIDField = new TextField();
        HBox coachIDBox = new HBox(10, coachIDLabel, coachIDField);

        Label cPaymentLabel = new Label("Payment:");
        TextField cPaymentField = new TextField();
        HBox cPaymentBox = new HBox(10, cPaymentLabel, cPaymentField);

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String email = emailField.getText();
                LocalDate joinDate = joinDatePicker.getValue();
                String subType = subTypeField.getValue();
                String address = addressField.getText();
                String gender = genderField.getValue();
                int age = Integer.parseInt(ageField.getText());
                int coachID = Integer.parseInt(coachIDField.getText());
                double cPayment = Double.parseDouble(cPaymentField.getText());

                Customer customer = new Customer(id, name, email, joinDate, subType, address, gender, age, coachID, cPayment);
                addCustomerToDB(customer);
                dataList.add(customer);
                addStage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert("Error", "Invalid input, please try again.");
            }
        });

        vBox.getChildren().addAll(idBox, nameBox, emailBox, joinDateBox, subTypeBox, addressBox, genderBox, ageBox, coachIDBox, cPaymentBox, saveButton);

        Scene scene = new Scene(vBox, 400, 500);
        addStage.setScene(scene);
        addStage.setTitle("Add Customer");
        addStage.show();
    }

    private void deleteCustomer() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Customer");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the Customer ID:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(customerID -> {
            try {
                int id = Integer.parseInt(customerID);
                Customer selectedCustomer = null;
                for (Customer customer : dataList) {
                    if (customer.getId() == id) {
                        selectedCustomer = customer;
                        break;
                    }
                }
                if (selectedCustomer != null) {
                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Delete Customer");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText("Are you sure you want to delete " + selectedCustomer.getName() + "?");

                    Optional<ButtonType> deleteResult = confirmationAlert.showAndWait();
                    if (deleteResult.isPresent() && deleteResult.get() == ButtonType.OK) {
                        try {
                            deleteCustomerFromDB(selectedCustomer);
                            dataList.remove(selectedCustomer);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            showAlert("Error", "Unable to delete customer, please try again.");
                        }
                    }
                } else {
                    showAlert("Customer Not Found", "The customer with ID " + id + " does not exist.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid customer ID.");
            }
        });
    }

    private void addCustomerToDB(Customer customer) throws SQLException {
        try (Connection con = connectDB()) {
            String query = "INSERT INTO Customer (customerID, customerName, email, joinDate, subType, customerAddress, gender, age, coachID, cPayment) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setInt(1, customer.getId());
                pstmt.setString(2, customer.getName());
                pstmt.setString(3, customer.getEmail());
                pstmt.setDate(4, Date.valueOf(customer.getJoinDate()));
                pstmt.setString(5, customer.getSubType());
                pstmt.setString(6, customer.getCustomerAddress());
                pstmt.setString(7, customer.getGender());
                pstmt.setInt(8, customer.getAge());
                pstmt.setInt(9, customer.getCoachID());
                pstmt.setDouble(10, customer.getCPayment());
                pstmt.executeUpdate();
            }
        }
    }

    private void deleteCustomerFromDB(Customer customer) throws SQLException {
        try (Connection con = connectDB()) {
            String query = "DELETE FROM Customer WHERE customerID = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setInt(1, customer.getId());
                pstmt.executeUpdate();
            }
        }
    }

    //************************************** Machine Screen ****************************************
    private void MachineScreen(Stage stage, Scene adminScene) {
        Image backgroundImage = new Image("file:///C:\\Users\\hp\\Desktop\\Database\\machine.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setPreserveRatio(false);
        backgroundImageView.setFitWidth(900);
        backgroundImageView.setFitHeight(600);
        stage.setFullScreen(true );
        // Create a VBox for the buttons
        VBox buttonBox = new VBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(15);
        buttonBox.setPadding(new Insets(40));

        // Create buttons with custom styling
        Button add = new Button("Insert New Machine");
        styleButton(add);

        Button showT = new Button("Show Machine Table");
        styleButton(showT);

        Button delete = new Button("Delete Machine");
        styleButton(delete);

        Button M_Training = new Button("Get Machine Names by Training ID");
        styleButton(M_Training);

        Button updateMachine = new Button("Update Machine Table");
        styleButton(updateMachine);

        Button T_Machine = new Button("Get Training Names for a Specific Machine");
        styleButton(T_Machine);

        Button T2_Machine = new Button("Training Programs for Specific Time");
        styleButton(T2_Machine);

        Button back = new Button("Back");
        styleButton(back);

        // Add buttons to the VBox
        buttonBox.getChildren().addAll(showT, add, delete, M_Training,T_Machine,T2_Machine, updateMachine,back);

        // Create a BorderPane to hold the background image and the buttons
        BorderPane borderPane = new BorderPane();
        Scene machineScene = new Scene(borderPane, 1370, 730);

        // Set the background image to the center of the BorderPane
        borderPane.setCenter(backgroundImageView);

        // Set the VBox containing the buttons to the right of the BorderPane
        borderPane.setRight(buttonBox);

        // Set button actions
        back.setOnAction(e -> stage.setScene(adminScene));
        showT.setOnAction(e -> showMachineTable(stage, machineScene));
        add.setOnAction(e -> addMachine(stage));
        delete.setOnAction(e -> deleteMachine());
        updateMachine.setOnAction(e -> updateMachineName());
        M_Training.setOnAction(e -> MachineTraining(stage, machineScene));
        T_Machine.setOnAction(e -> TrainingMachine(stage, machineScene));
        T2_Machine.setOnAction(e -> TrainingTimeMachine(stage, machineScene));

        // Set the mainScene to the stage
        stage.setScene(machineScene);
    }

    // Method to style buttons and add hover effects
    private void styleButton(Button button) {
        String baseStyle = "-fx-font-size: 20px; -fx-pref-width: 350px; -fx-pref-height: 40px; -fx-background-color: darkcyan; -fx-text-fill: white; -fx-background-radius: 20px;";
        String hoverStyle = "-fx-background-color: lightseagreen; -fx-cursor: hand;";

        button.setStyle(baseStyle);
        button.setOnMouseEntered(e -> button.setStyle(baseStyle + hoverStyle));
        button.setOnMouseExited(e -> button.setStyle(baseStyle));
    }

    //****************************Machine****************************************
    private void showMachineTable(Stage stage, Scene previousScene) {
        TableView<Machine> tableView = new TableView<>();

        // Define columns
        TableColumn<Machine, Integer> machineIDColumn = new TableColumn<>("Machine ID");
        machineIDColumn.setCellValueFactory(new PropertyValueFactory<>("machineID"));

        TableColumn<Machine, String> machineNameColumn = new TableColumn<>("Machine Name");
        machineNameColumn.setCellValueFactory(new PropertyValueFactory<>("machineName"));


        // Add columns to the table
        tableView.getColumns().addAll(machineIDColumn, machineNameColumn);

        // Set data to the table
        tableView.setItems(dataListMachine);

        // Create a VBox to hold the table and a back button
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-font-size: 16px;");
        backButton.setOnAction(e -> stage.setScene(previousScene));

        vBox.getChildren().addAll(tableView, backButton);

        // Create a new scene for the table view
        Scene machineScene = new Scene(vBox, 800, 600);
        stage.setScene(machineScene);

    }
    //******************************************* Add Machine **********************************************
    private void addMachine(Stage stage) {
        Stage addStage = new Stage();
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));

        // Create labeled fields for each input
        Label idLabel = new Label("Machine ID:");
        TextField idField = new TextField();
        HBox idBox = new HBox(10, idLabel, idField);

        Label nameLabel = new Label("Machine Name:");
        TextField nameField = new TextField();
        HBox nameBox = new HBox(10, nameLabel, nameField);


        Button saveT = new Button("Save");
        vBox.getChildren().addAll(idBox, nameBox, saveT);
        Scene scene = new Scene(vBox, 400, 500);
        addStage.setScene(scene);
        addStage.setTitle("Add Machine");
        addStage.show();

        saveT.setOnAction(e ->
        {
            try
            {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();

                Machine machine = new Machine(id, name);
                addMachineToDB(machine);
                dataListMachine.add(machine);
                addStage.close();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                showAlert("Error", "Invalid input, please try again.");
            }
        });
    }
    //**************************************** Add Machine to DB ************************************************
    private void addMachineToDB(Machine machine ) throws SQLException
    {
        try (Connection con = connectDB()) {
            String query = "INSERT INTO Machine (machineID, machineName) VALUES (?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(query))
            {
                pstmt.setInt(1, machine.getMachineID());
                pstmt.setString(2, machine.getMachineName());
                pstmt.executeUpdate();
            }
        }
    }
    //************************************ Update Machine ******************************************
    private void updateMachineName() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Update Machine Name");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the Machine ID:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(machineID -> {
            try {
                int id = Integer.parseInt(machineID);
                final Machine[] selectedMachine = {null}; // Final array to hold the reference

                for (Machine machine : dataListMachine) {
                    if (machine.getMachineID() == id) {
                        selectedMachine[0] = machine; // Update the array with selected machine
                        break;
                    }
                }

                if (selectedMachine[0] != null) {
                    TextInputDialog nameDialog = new TextInputDialog();
                    nameDialog.setTitle("Update Machine Name");
                    nameDialog.setHeaderText(null);
                    nameDialog.setContentText("Enter the new name for " + selectedMachine[0].getMachineName() + ":");

                    Optional<String> newNameResult = nameDialog.showAndWait();
                    newNameResult.ifPresent(newName -> {
                        try {
                            updateMachineNameInDB(id, newName); // Method to update machine name in the database
                            selectedMachine[0].setMachineName(newName); // Update machine name in data list
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            showAlert("Error", "Unable to update machine name, please try again.");
                        }
                    });
                } else {
                    showAlert("Machine Not Found", "The machine with ID " + id + " does not exist.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid machine ID.");
            }
        });
    }
    //***************************************** Update Machine on DB ***************************
    private void updateMachineNameInDB(int machineID, String newName) throws SQLException {
        try (Connection con = connectDB()) {
            String query = "UPDATE Machine SET machineName = ? WHERE machineID = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, newName);
                pstmt.setInt(2, machineID);
                pstmt.executeUpdate();
            }
        }
    }
    //**************************************** Delete Machine *************************************************
    private void deleteMachine()
    {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Machine");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the Machine ID:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(machineID -> {
            try {
                int id = Integer.parseInt(machineID);
                Machine selectedMachine = null;
                for (Machine machine : dataListMachine) {
                    if (machine.getMachineID() == id) {
                        selectedMachine = machine;
                        break;
                    }
                }
                if (selectedMachine != null) {
                    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Delete Machine");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText("Are you sure you want to delete " + selectedMachine.getMachineName() + "?");

                    Optional<ButtonType> deleteResult = confirmationAlert.showAndWait();
                    if (deleteResult.isPresent() && deleteResult.get() == ButtonType.OK) {
                        try {
                            deleteMachineFromDB(selectedMachine);
                            dataListMachine.remove(selectedMachine);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            showAlert("Error", "Unable to delete machine, please try again.");
                        }
                    }
                } else {
                    showAlert("Training Not Found", "The machine with ID " + id + " does not exist.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid machine ID.");
            }
        });
    }
    //************************************** Delete Machine from DB *********************************************
    private void deleteMachineFromDB(Machine machine) throws SQLException
    {
        try (Connection con = connectDB()) {
            String query = "DELETE FROM machine WHERE machineID = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setInt(1, machine.getMachineID());
                pstmt.executeUpdate();
            }
        }
    }
//************************************* Query *********************************************

    private void TrainingMachine(Stage stage, Scene previousScene) {
        Stage askStage = new Stage();
        VBox askBox = new VBox();
        askBox.setAlignment(Pos.CENTER);
        askBox.setSpacing(10);
        askBox.setPadding(new Insets(20));

        Label idLabel = new Label("Machine ID:");
        TextField idField = new TextField();

        HBox WHBox = new HBox();
        WHBox.setAlignment(Pos.CENTER);
        WHBox.setSpacing(10);
        idField.setPadding(new Insets(5));
        WHBox.getChildren().addAll(idLabel, idField);

        Button next = new Button("Next");

        askBox.getChildren().addAll(WHBox, next);
        Scene askScene = new Scene(askBox, 300, 200);
        askStage.setScene(askScene);
        askStage.setTitle("Training -> Machine");
        askStage.show();

        next.setOnAction(event -> {
            try {
                int machineId = Integer.parseInt(idField.getText());
                askStage.close();

                // Query the database to retrieve all machine names associated with the entered training ID
                ObservableList<String> trainingNames = getTrainingNamesForMachine(machineId);

                // Create a ListView to display the machine names
                ListView<String> machineListView = new ListView<>(trainingNames);

                // Add a back button to return to the previous scene
                Button backButton = new Button("Back");
                backButton.setOnAction(e -> stage.setScene(previousScene));

                // Create a VBox to hold the machine list and back button
                VBox vbox = new VBox();
                vbox.setSpacing(10);
                vbox.setPadding(new Insets(10));
                vbox.getChildren().addAll(machineListView, backButton);

                // Create a new scene with the VBox
                Scene scene = new Scene(vbox, 300, 400);
                stage.setScene(scene);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                showAlert("Error", "Enter an integer for the training ID");
            }
        });
    }

    // Method to query the database for machine names associated with the given training ID
    private ObservableList<String> getTrainingNamesForMachine(int machineId) {

        ObservableList<String> trainingNames = FXCollections.observableArrayList();

        // Query the database to retrieve machine names associated with the given training ID
        String query = "SELECT trainingName FROM machine_training JOIN training ON machine_training.trainingID = training.trainingID WHERE machineID = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PureGim", "root", "yara");
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, machineId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                trainingNames.add(resultSet.getString("trainingName"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return trainingNames;
    }
    //*************************************** Query ******************************************************
    private void TrainingTimeMachine(Stage stage, Scene previousScene) {
        Stage askStage = new Stage();
        VBox askBox = new VBox();
        askBox.setAlignment(Pos.CENTER);
        askBox.setSpacing(10);
        askBox.setPadding(new Insets(20));

        Label idLabel = new Label("Machine ID:");
        TextField idField = new TextField();

        HBox WHBox = new HBox();
        WHBox.setAlignment(Pos.CENTER);
        WHBox.setSpacing(10);
        idField.setPadding(new Insets(5));
        WHBox.getChildren().addAll(idLabel, idField);

        Button next = new Button("Next");

        askBox.getChildren().addAll(WHBox, next);
        Scene askScene = new Scene(askBox, 300, 200);
        askStage.setScene(askScene);
        askStage.setTitle("Training -> Machine");
        askStage.show();
        String DB_URL = "jdbc:mysql://localhost:3306/PureGim?useSSL=false";
        String USER = "root";
        String PASS = "yara";

        next.setOnAction(event -> {
            try {
                int machineId = Integer.parseInt(idField.getText());
                askStage.close();

                // Query the database to retrieve all machine names associated with the entered training ID
                ObservableList<String> trainingNames = getTrainingNamesTimesForMachine(machineId, DB_URL, USER, PASS);

                // Create a ListView to display the machine names
                ListView<String> machineListView = new ListView<>(trainingNames);

                // Add a back button to return to the previous scene
                Button backButton = new Button("Back");
                backButton.setOnAction(e -> stage.setScene(previousScene));

                // Create a VBox to hold the machine list and back button
                VBox vbox = new VBox();
                vbox.setSpacing(10);
                vbox.setPadding(new Insets(10));
                vbox.getChildren().addAll(machineListView, backButton);

                // Create a new scene with the VBox
                Scene scene = new Scene(vbox, 300, 400);
                stage.setScene(scene);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                showAlert("Error", "Enter an integer for the training ID");
            }
        });
    }


    private ObservableList<String> getTrainingNamesTimesForMachine(int machineId, String dbUrl, String user, String pass) {

        ObservableList<String> trainingInfoList = FXCollections.observableArrayList();

        String query = "SELECT training.trainingID, training.trainingName, training.trainingTime " +
                "FROM machine_training " +
                "JOIN training ON machine_training.trainingID = training.trainingID " +
                "WHERE machineID = ? AND training.trainingTime = '30'";

        try (Connection conn = DriverManager.getConnection(dbUrl, user, pass);
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, machineId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int trainingID = resultSet.getInt("trainingID");
                String trainingName = resultSet.getString("trainingName");
                String trainingTime = resultSet.getString("trainingTime");
                String trainingInfo = "ID: " + trainingID + ", Name: " + trainingName + ", Time: " + trainingTime;
                trainingInfoList.add(trainingInfo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Error", "An error occurred while retrieving training information");
        }

        return trainingInfoList;
    }
    //****************************************** Query *****************************************8
    private void MachineTraining(Stage stage, Scene previousScene) {
        Stage askStage = new Stage();
        VBox askBox = new VBox();
        askBox.setAlignment(Pos.CENTER);
        askBox.setSpacing(10);
        askBox.setPadding(new Insets(20));

        Label idLabel = new Label("Training ID:");
        TextField idField = new TextField();

        HBox WHBox = new HBox();
        WHBox.setAlignment(Pos.CENTER);
        WHBox.setSpacing(10);
        idField.setPadding(new Insets(5));
        WHBox.getChildren().addAll(idLabel, idField);

        Button next = new Button("Next");

        askBox.getChildren().addAll(WHBox, next);
        Scene askScene = new Scene(askBox, 300, 200);
        askStage.setScene(askScene);
        askStage.setTitle("Training -> Machine");
        askStage.show();

        next.setOnAction(event -> {
            try {
                int trainingId = Integer.parseInt(idField.getText());
                askStage.close();

                // Query the database to retrieve all machine names associated with the entered training ID
                ObservableList<String> machineNames = getMachineNamesForTraining(trainingId);

                // Create a ListView to display the machine names
                ListView<String> machineListView = new ListView<>(machineNames);

                // Add a back button to return to the previous scene
                Button backButton = new Button("Back");
                backButton.setOnAction(e -> stage.setScene(previousScene));

                // Create a VBox to hold the machine list and back button
                VBox vbox = new VBox();
                vbox.setSpacing(10);
                vbox.setPadding(new Insets(10));
                vbox.getChildren().addAll(machineListView, backButton);

                // Create a new scene with the VBox
                Scene scene = new Scene(vbox, 300, 400);
                stage.setScene(scene);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                showAlert("Error", "Enter an integer for the training ID");
            }
        });
    }

    // Method to query the database for machine names associated with the given training ID
    private ObservableList<String> getMachineNamesForTraining(int trainingId) {

        ObservableList<String> machineNames = FXCollections.observableArrayList();

        // Query the database to retrieve machine names associated with the given training ID
        String query = "SELECT machineName FROM machine_training JOIN machine ON machine_training.machineID = machine.machineID WHERE trainingID = ?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/PureGim", "root", "yara");
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, trainingId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                machineNames.add(resultSet.getString("machineName"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return machineNames;
    }
    private void getData() throws Exception {
        Connection con = connectDB();

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Customer");

        Statement stmt_coach = con.createStatement();
        ResultSet rs_coach = stmt_coach.executeQuery("SELECT * FROM Coach");

        Statement stmt_training = con.createStatement();
        ResultSet rs_training = stmt_training.executeQuery("SELECT * FROM Training");


        Statement stmt_machine = con.createStatement();
        ResultSet rs_machine = stmt_machine.executeQuery("SELECT * FROM Machine");

        Statement stmt_admin = con.createStatement();
        ResultSet rs_admin = stmt_admin.executeQuery("SELECT * FROM GymAdmin");

        while (rs_admin.next())
        {
            dataListAdmin.add(new GymAdmin(
                    rs_admin.getInt("adminID"),
                    rs_admin.getString("adminName"),
                    rs_admin.getString("adminPassword")
            ));
        }

        rs_admin.close();
        stmt_admin.close();



        while (rs.next()) {
            dataList.add(new Customer(
                    rs.getInt("customerID"),
                    rs.getString("customerName"),
                    rs.getString("email"),
                    rs.getDate("joinDate").toLocalDate(),
                    rs.getString("subType"),
                    rs.getString("customerAddress"),
                    rs.getString("gender"),
                    rs.getInt("age"),
                    rs.getInt("coachID"),
                    rs.getDouble("cPayment")
            ));
        }

        rs.close();
        stmt.close();

        while (rs_coach.next()) {
            dataListCoach.add(new Coach(
                    rs_coach.getInt("coachID"),
                    rs_coach.getString("coachName"),
                    rs_coach.getInt("salary"),
                    rs_coach.getInt("work_hours")
            ));
        }

        rs_coach.close();
        stmt_coach.close();

        while (rs_training.next()) {
            dataListTraining.add(new Training(
                    rs_training.getInt("trainingID"),
                    rs_training.getString("trainingName"),
                    rs_training.getInt("coachID"),
                    rs_training.getString("trainingTime")
            ));
        }

        rs_training.close();
        stmt_training.close();


        while (rs_machine.next()) {
            dataListMachine.add(new Machine(
                    rs_machine.getInt("machineID"),
                    rs_machine.getString("machineName")
            ));

        }

        rs_machine.close();
        stmt_machine.close();
        Statement stmt_CT = con.createStatement();
        ResultSet rs_CT = stmt_CT.executeQuery("SELECT * FROM Customer_Training");


        while (rs_CT.next())
        {
            dataListCT.add(new Customer_Training(
                    rs_CT.getInt("customerID"),
                    rs_CT.getInt("trainingID")
            ));
        }

        rs_CT.close();
        stmt_CT.close();

        con.close();
    }


    private Connection connectDB() throws SQLException {
        String dbUsername = "root";
        String dbPassword = "yara";
        String dbURL = "jdbc:mysql://localhost:3306/PureGim?useSSL=false";

        return DriverManager.getConnection(dbURL, dbUsername, dbPassword);
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private Button CreateButtonWithImage(String text, Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(50); // Set the width of the image
        imageView.setFitHeight(50); // Set the height of the image
        imageView.setPreserveRatio(true);

        Label label = new Label(text);
        label.setStyle("-fx-font-size: 16px;");

        HBox hBox = new HBox(imageView, label);
        hBox.setAlignment(Pos.CENTER); // Align contents to the left side
        hBox.setSpacing(10); // Add some spacing between image and text

        Button button = new Button();
        button.setGraphic(hBox);
        button.setContentDisplay(ContentDisplay.RIGHT); // Position text to the right

        return button;
    }
    private void StatisticsCustomerScreen(Stage stage, Scene adminScene) {
        StackPane stackPane = new StackPane();

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_LEFT); // Align items to the top left
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));

        Label totalPLabel = new Label("Total payment received from all customer:");
        Button button = new Button("Show");
        TextArea TA1 = new TextArea();
        TA1.setEditable(false);
        TA1.setPrefWidth(10);
        TA1.setPrefHeight(50);
        TA1.setVisible(false);

        button.setOnAction(e -> {
            int sum = 0;
            for (Customer customer : dataList) {
                sum += customer.getCPayment();
            }
            TA1.setText("Total payments: " + sum);
            TA1.setVisible(true);
            button.setDisable(true);
        });

        Label customerAA = new Label("Average age of customers:");
        Button button2 = new Button("Show");
        TextArea TA2 = new TextArea();
        TA2.setEditable(false);
        TA2.setPrefWidth(10);
        TA2.setPrefHeight(50);
        TA2.setVisible(false);

        button2.setOnAction(e -> {
            int sum = 0;
            int count = 0;
            for (Customer customer : dataList) {
                sum += customer.getAge();
                count++;
            }
            TA2.setText("Total Salary: " + sum/count);
            TA2.setVisible(true);
            button2.setDisable(true);
        });

        Label customerAAF = new Label("Average age of female customers:");
        TextArea TA3 = new TextArea();
        TA3.setEditable(false);
        TA3.setPrefWidth(10);
        TA3.setPrefHeight(50);
        TA3.setVisible(false);

        Label customerAAM = new Label("Average age of male customers:");
        Button button4 = new Button("Show");
        TextArea TA4 = new TextArea();
        TA4.setEditable(false);
        TA4.setPrefWidth(10);
        TA4.setPrefHeight(50);
        TA4.setVisible(false);

        button4.setOnAction(e -> {
            int sumF = 0;
            int countF = 0;
            int sumM = 0;
            int countM = 0;
            for (Customer customer: dataList)
            {
                if (customer.getGender().equals("female"))
                {
                    sumF += customer.getAge();
                    countF++;
                }
                else
                {
                    sumM += customer.getAge();
                    countM++;
                }
            }
            TA3.appendText("Female: " + sumF/countF + "\n");
            TA4.appendText("Male: " + sumM/countM + "\n");

            TA3.setVisible(true);
            TA4.setVisible(true);
            button4.setDisable(true);
        });

        Button back = new Button("Back");
        HBox b = new HBox();
        b.getChildren().add(back);
        b.setAlignment(Pos.CENTER);
        back.setOnAction(e -> stage.setScene(adminScene));

        vBox.getChildren().addAll(totalPLabel, TA1, button, customerAA, TA2, button2, customerAAF, TA3, customerAAM, TA4, button4,b);
        stackPane.getChildren().addAll(vBox);

        Scene scene = new Scene(stackPane, 600, 400);
        stage.setScene(scene);
        stage.show();
    }



    private ObservableList<Training> getTrainingSessionsForCustomer(Customer customer) {
        ObservableList<Training> trainingSessions = FXCollections.observableArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Establish database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PureGim?useSSL=false", "root", "yara");

            // Prepare SQL statement to retrieve training sessions for the customer
            String sql = "SELECT * FROM Training WHERE trainingID IN (SELECT trainingID FROM Customer_Training WHERE customerID = ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customer.getId());

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Populate the trainingSessions list
            while (resultSet.next()) {
                int trainingID = resultSet.getInt("trainingID");
                String trainingName = resultSet.getString("trainingName");
                int coachID = resultSet.getInt("coachID");
                String trainingTime = resultSet.getString("trainingTime");

                // Create a new Training object and add it to the trainingSessions list
                Training training = new Training(trainingID, trainingName, coachID, trainingTime);
                trainingSessions.add(training);

                // Debug output
                System.out.println("Retrieved Training: ID = " + trainingID + ", Name = " + trainingName +
                        ", CoachID = " + coachID + ", Time = " + trainingTime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQL exceptions
        } finally {
            // Close the resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return trainingSessions;
    }






    private void StatisticsCoachScreen(Stage stage, Scene adminScene) {
        StackPane stackPane = new StackPane();
        Image backgroundImage = new Image("file:///C:/Users/hp/Desktop/Screenshot%2%20203706.png");
        ImageView imageView = new ImageView(backgroundImage);
        imageView.fitWidthProperty().bind(stage.widthProperty());
        imageView.fitHeightProperty().bind(stage.heightProperty());

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_LEFT); // Align items to the top left
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));

        Label totalNHLabel = new Label("Total number of hours all coaches work per week:");
        Button button = new Button("Show");
        TextArea totalNHArea = new TextArea();
        totalNHArea.setEditable(false);
        totalNHArea.setPrefWidth(10);
        totalNHArea.setPrefHeight(50);
        totalNHArea.setVisible(false);

        button.setOnAction(e -> {
            int sum = 0;
            for (Coach coach : dataListCoach) {
                sum += coach.getWork_hours();
            }
            totalNHArea.setText("Total Hours: " + sum);
            totalNHArea.setVisible(true);
            button.setDisable(true);
        });

        Label Salary = new Label("Total salary expends for all coaches:");
        Button button2 = new Button("Show");
        TextArea totalSArea = new TextArea();
        totalSArea.setEditable(false);
        totalSArea.setPrefWidth(10);
        totalSArea.setPrefHeight(50);
        totalSArea.setVisible(false);

        button2.setOnAction(e -> {
            int sum = 0;
            for (Coach coach : dataListCoach) {
                sum += coach.getSalary();
            }
            totalSArea.setText("Total Salary: " + sum);
            totalSArea.setVisible(true);
            button2.setDisable(true);
        });

        Label Customer = new Label("Number of Customer per Coach:");
        Button button3 = new Button("Show");
        TextArea totalCPC = new TextArea(); //total customer per coach
        totalCPC.setEditable(false);
        totalCPC.setPrefWidth(10);
        totalCPC.setPrefHeight(50);
        totalCPC.setVisible(false);

        button3.setOnAction(e -> {
            int sum = 0;
            for (Coach coach: dataListCoach)
            {
                for (Customer customer: dataList)
                {
                    if (customer.getCoachID() == coach.getId()) {
                        sum ++;
                    }
                }
                totalCPC.appendText("Number of Customer for coach: " + coach.getName() + " = " + sum + "\n");
                sum = 0;
            }
            totalCPC.setVisible(true);
            button3.setDisable(true);
        });

        Button back = new Button("Back");
        HBox b = new HBox();
        b.getChildren().add(back);
        b.setAlignment(Pos.CENTER);
        back.setOnAction(e -> stage.setScene(adminScene));

        vBox.getChildren().addAll(totalNHLabel, totalNHArea, button, Salary, totalSArea, button2, Customer, totalCPC, button3, b);
        stackPane.getChildren().addAll(imageView, vBox);

        Scene scene = new Scene(stackPane, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

}