package Schedule;

import UserLogin.MainMenuController;
import UserLogin.User;
import UserLogin.UserStorage;

import java.util.*;

/**
 * A controller class describing the actions a user can perform in the program.
 */
public class UserScheduleController{
    String email;
    UserStorage userStorage;
    EventManager eventManager;
    MainMenuController mainMenuController;
    RoomStorage roomStorage;
    UserSchedulePresenter presenter;
    Scanner scan;

    /**
     * Initializes a new controller for the user.
     * @param email The email of the user of the program.
     * @param userStorage The userStorage associated with the conference.
     * @param eventManager The talkManager of the conference.
     * @param mainMenuController The menu of the conference.
     * @param scanner The scanner of MainMenuController.
     */
    public UserScheduleController(String email, EventManager eventManager, UserStorage userStorage,
                                  MainMenuController mainMenuController, RoomStorage roomStorage, Scanner scanner){
        this.email = email;
        this.userStorage = userStorage;
        this.eventManager = eventManager;
        this.roomStorage = roomStorage;
        this.mainMenuController = mainMenuController;
        presenter = new UserSchedulePresenter();
        this.scan = scanner;
    }

    /**
     * Let a user sign up for an event.
     * @param event The talk they want to register for.
     * @param userScheduleManager The userScheduleManager.
     * @param signUpMap The signUpMap.
     * @return A string notifying the user if they have successfully enrolled in
     * the talk or if talk was at full capacity.
     */
    public String signUp(String eventid) {
        if(!(this.eventManager.eventIdAtCapacity(eventid))){
            return "Event is at full capacity.";
        }
        else{
            if (this.eventManager.getEvent(eventid).addUser(email)){
                this.userStorage.addEvent(email, eventid);
                return "User added.";
            }
            return "User already signed up";
        }
    }






    /**
     * Let a user cancel their enrollment in an event.
     * @param event They talk they no longer want to attend.
     * @param signUpMap The signUpMap.
     */
    public void cancelRegistration(String eventId){
        if (eventManager.eventIdToUsersSignedUp(eventId).contains(email)){
            eventManager.eventIdToUsersSignedUp(eventId).remove(email);
            userStorage.emailToTalkList(email).remove(eventId);
        }
    }

    /**
     * Get the talk that corresponds to the specified int. Since talk is stored in an ordered map this is possible.
     * @param talkIndex The position of the talk.
     * @return A talk representing the talk at the specified index.
     */
    public String getEventByIndex(int eventIndex){
        ArrayList<String> eventIds = eventManager.getEventIdsList();
        if (eventIndex -1 >= eventIds.size()){
            return null;
        }
        else{
            String eventId = eventIds.get(eventIndex);
            return eventId;
        }
    }

    /**
     * Let the user see the schedule of events for which they signed up.
     * @param userScheduleManager The userScheduleManager of the user.
     * @return An ArrayList representing the talks the user has signed up for.
     */
    public ArrayList<String> getRegisteredEvents(){
        ArrayList<String> registeredEvents = userStorage.emailToTalkList(email);
        if (registeredEvents.size() == 0){
            presenter.printMenu(13);
            presenter.printMenu(11);
        }
        else{
            Integer i = 1;
            for (String e : registeredEvents){
                presenter.printTalk(i, e, eventManager);
                i++;
            }
        }
        return registeredEvents;
    }

    /**
     * Takes in a user input and registers them for an event/talk.
     * @param presenter The presenter.
     * @param scan The scanner.
     * @param userScheduleManager The UserScheduleManager.
     * @param signUpMap The signUpMap.
     */
    protected void registerTalk(UserSchedulePresenter presenter, Scanner scan){
        // show them a list of all available talks
        presenter.printAllTalks(eventManager);
        //they will pick the number corresponding to each talk
        presenter.printMenu(3);
        //assuming they will have asked to see all talks they could register before selecting command 1
        boolean doContinue  = true;
        while (doContinue){
            String choice = scan.nextLine();
            try {
                int eventIndex = Integer.parseInt(choice);
            if (eventIndex == 0){
                presenter.printMenu(10);
                return;
            }
            else if (getEventByIndex(eventIndex) == null){
                presenter.printMenu(7);
            }
            else{
                String eventIdToRegister = getEventByIndex(eventIndex);
                if (this.signUp(eventIdToRegister).equals("User added.")) {
                    // prints "Success"
                    presenter.printMenu(6);
                    presenter.printMenu(10);
                    return;
                }
                else{
                    if (this.signUp(eventIdToRegister).equals("User already registered for the requested talk.")){
                        presenter.printRegistrationBlocked(1);
                    }
                    else{
                        presenter.printRegistrationBlocked(2);
                    }
                }
            }}
            catch (NumberFormatException nfe){
        presenter.printMenu(8);;
    }}}

    /**
     * Takes in a user's input and shows them all the talks offered.
     * @param presenter The presenter.
     * @param scan The scanner.
     */
    protected void seeAllTalks(UserSchedulePresenter presenter, Scanner scan){
        //use the string representation in TalkManager
        presenter.printAllTalks(eventManager);
        presenter.printMenu(11);
        boolean doContinue  = true;
        while (doContinue){
            String choice = scan.nextLine();
            try {
                int eventIndex = Integer.parseInt(choice);
            if (eventIndex == 0){
                presenter.printMenu(10);
                return;
            }
            else{presenter.printMenu(8);}}
            catch (NumberFormatException nfe){
       presenter.printMenu(8);;
        }
    }}

    /**
     * Takes in a user's input and shows them all the talks they are currently registered for.
     * @param presenter The presenter.
     * @param scan The scanner.
     * @param userScheduleManager The UserScheduleManager.
     */
    protected void seeAllRegistered(UserSchedulePresenter presenter,
                                    Scanner scan){
        ArrayList<String> registeredEvents = this.getRegisteredEvents();
        presenter.printMenu(11);
        boolean doContinue  = true;
        while (doContinue){
            String choice = scan.nextLine();
            try { int talkIndex = Integer.parseInt(choice);
            if (talkIndex == 0){
                presenter.printMenu(10);
                return;
            }
        else{presenter.printMenu(8);
            }}catch (NumberFormatException nfe){
        presenter.printMenu(8);;
    }}}

    /**
     * Takes in a user's input and cancels their registration for a specified talk.
     * @param presenter The presenter.
     * @param scan The scanner.
     * @param userScheduleManager The userScheduleManager.
     * @param signUpMap The signUpMap.
     */
    protected void cancelATalk(UserSchedulePresenter presenter,
                               Scanner scan){
        ArrayList<String> registeredEvents = getRegisteredEvents();
        if (registeredEvents.size() != 0) {
            presenter.printMenu(5);
        boolean doContinue  = true;
        while (doContinue){
            String choice = scan.nextLine();
            try {
                int cancelEventIndex = Integer.parseInt(choice);
            if (cancelEventIndex == 0){
            presenter.printMenu(10);
            return;
        }
        else if (registeredEvents.size() <= Math.abs(cancelEventIndex - 1)){
            presenter.printMenu(7);
        }
        else{
            String eventIdToCancel = registeredEvents.get(cancelEventIndex - 1);
            this.cancelRegistration(eventIdToCancel);
            // prints "Success"
            presenter.printMenu(6);
            presenter.printMenu(10);
            return;
        }}catch (NumberFormatException nfe){
        presenter.printMenu(8);;
    }}}
    else{
            boolean doContinue  = true;
            while (doContinue){
                String choice = scan.nextLine();
                try {
                    int cancelEventIndex = Integer.parseInt(choice);
                    if (cancelEventIndex == 0) {
                        presenter.printMenu(10);
                        return;
                    }}catch (NumberFormatException nfe){
                        presenter.printMenu(8);;
            }
        }}}

    /**
     * Lists all the available actions a user can perform and choose from, takes their input and outputs a text UI.
     */
    public void run(){
        presenter.printHello(userStorage.emailToName(this.email)); //Is this allowed?
        presenter.printMenu(1);
        presenter.printMenu(2);
        boolean doContinue = true;
        while(doContinue) {
            String choice = scan.nextLine();
            try {
                int command = Integer.parseInt(choice);
            //if they want to register for a talk
            if (command == 1) {
                this.registerTalk(presenter, scan);
                presenter.printMenu(1);
                //If they want to see all available talks
            }else if (command == 2) {
                this.seeAllTalks(presenter, scan);
                presenter.printMenu(1);
                //if they want to see all the talks they are currently registered for
            }else if (command == 3) {
                this.seeAllRegistered(presenter, scan);
                presenter.printMenu(1);
                // if they want to cancel a registration
            }else if (command == 4) {
                this.cancelATalk(presenter, scan);
                presenter.printMenu(1);
            }
            else if (command ==0){
                doContinue = false;
                mainMenuController.runMainMenu(this.email);
            }
            else{presenter.printMenu(8);}
        } catch (NumberFormatException nfe){
        presenter.printMenu(8);;
    }
    }}
}
