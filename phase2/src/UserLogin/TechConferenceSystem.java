package UserLogin;

import Files.CSVReader;
import Files.CSVWriter;
import Schedule.RoomSystem;
import Schedule.SpeakerScheduleManager;
import Schedule.UserScheduleManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

/**
 * A Gateway class that interacts with an external file (.csv) that stores a collection of all accounts currently
 * registered in the database.
 */

public class TechConferenceSystem extends Observable {

    public UserStorage userStorage;
    public ArrayList<User> userList;
    public LogInController logInController;
    public RoomSystem roomSystem;
    public MainMenuController mainMenuController;

    /**
     * A constructor for the TechConferenceSystem that contains an instance of everything needed to run the program,
     * and everything that should be "common" to all instances of TechConferenceSystem.
     */

    public TechConferenceSystem() {
        this.userStorage = new UserStorage();
        this.logInController = new LogInController(this.mainMenuController, this.roomSystem.talkSystem,
                this.roomSystem.talkSystem.messagingSystem);
        this.mainMenuController = new MainMenuController(logInController.scanner, roomSystem,
                roomSystem.talkSystem, roomSystem.talkSystem.messagingSystem, roomSystem.talkSystem.scheduleSystem,
                userStorage,this);
        this.roomSystem = new RoomSystem(userStorage, mainMenuController);
    }

    /**
     * The main run method for the entire program. Observers are initialized so that everything is running over the
     * same systems. The above set methods are called to update everything and notify all observers.
     * The system is run starting with Login, which, if successful, prompts a main menu, which the user can navigate
     * to prompt the different screens and do specific actions. The program will quit when the user logs out, and
     * update any files with possible changes that were made during the active session.
     */

    public void run() {
        this.logInController.addObserver(roomSystem.talkSystem);

        CSVReader file = new CSVReader("src/Resources/Users.csv");
        for(ArrayList<String> user: file.getData()){
            this.userStorage.createUser(user.get(0), user.get(1), user.get(2), user.get(3));
        roomSystem.run();
        mainMenuController.runMainMenu(logInController.getEmail());
    }}

    /**
     * Method to write the changes to the RoomFile, called in MainMenuController.logout().
     */
    public void save() {
        CSVWriter csvWriter = new CSVWriter();
        csvWriter.writeToUsers("src/Resources/Users.csv", this.userStorage.getUserList());
    }
}