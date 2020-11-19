package Schedule;

import UserLogin.*;

import java.time.LocalDateTime;
import java.util.*;

public class OrgScheduleController extends UserScheduleController implements Observer {
    UserScheduleManager organizer;
    TalkManager talkManager;
    RoomStorage roomStorage;
    UserStorage userStorage;
    MainMenuController mainMenuController;
    HashMap<Talk, SignUpAttendeesManager> signUpMap;
    OrgSchedulePresenter orgSchedulePresenter;
    UserSchedulePresenter userSchedulePresenter;
    Scanner scanner;


    public OrgScheduleController(UserScheduleManager organizer, TalkManager talkManager,
                                 MainMenuController mainMenuController, Scanner scanner){
        super(organizer, talkManager, mainMenuController, scanner);
        this.talkManager = talkManager;
        this.mainMenuController = mainMenuController;
        this.scanner = scanner;
        this.organizer = organizer;
        orgSchedulePresenter = new OrgSchedulePresenter();
        userSchedulePresenter = new UserSchedulePresenter();
    }

    public ArrayList<Speaker> getSpeakerList(){
        ArrayList<Speaker> speakerList = new ArrayList<Speaker>();
        for(User u: userStorage.getUserList()){
            if (u instanceof Speaker){
                speakerList.add((Speaker) u);
            }
        }
        return speakerList;
    }

    public Speaker pickSpeaker(Scanner scan) {
        // first they pick a speaker, then they pick a room, then they pick a time and check if it works
        orgSchedulePresenter.printAllSpeakers(getSpeakerList());
        orgSchedulePresenter.printMenu(8);
        boolean doContinue  = true;
        while (doContinue){
            int speakerIndex = scan.nextInt();
            if (speakerIndex == 0){
                orgSchedulePresenter.printMenu(16);
                orgSchedulePresenter.printMenu(1);
                return null;
            }
            else if (speakerIndex >= getSpeakerList().size()){
                orgSchedulePresenter.printMenu(13);
            }
            else{
                Speaker chosenSpeaker = getSpeakerList().get(speakerIndex - 1);
                return chosenSpeaker;
            }}
        return null;}

    public Room pickRoom(Scanner scan) {
        // first they pick a speaker, then they pick a room, then they pick a time and check if it works
        orgSchedulePresenter.printAllRooms(roomStorage.getRoomList());
        orgSchedulePresenter.printMenu(7);
        boolean doContinue  = true;
        while (doContinue){
            int RoomIndex = scan.nextInt();
            if (RoomIndex == 0){
                orgSchedulePresenter.printMenu(16);
                orgSchedulePresenter.printMenu(1);
                return null;
            }
            else if (RoomIndex >= roomStorage.getRoomList().size()){
                orgSchedulePresenter.printMenu(12);
            }
            else{
                Room chosenRoom = roomStorage.getRoomList().get(RoomIndex - 1);
                return chosenRoom;
            }}
        return null;}

    public LocalDateTime pickTime(Scanner scan, Speaker speaker, Room room) {
        // first they pick a speaker, then they pick a room, then they pick a time and check if it works
        //orgSchedulePresenter.printAllSpeakers(getSpeakerList());
        //orgSchedulePresenter.printMenu("Pick speaker");
        boolean doContinue  = true;
        while(doContinue){
            int hours = scan.nextInt();
            int minutes = scan.nextInt();
            if (hours >= 9 && hours <= 17){
                if (minutes >= 0 && minutes <= 60){

                }
            }
        }
        return null;
    }

    public boolean requestTalk(Scanner scan){
        Speaker s = pickSpeaker(scan);
        Room r = pickRoom(scan);
        LocalDateTime availableTime = pickTime(scan, s, r);
        String talkTitle = scan.nextLine();
        return talkManager.createTalk(talkTitle, s.getEmail(), r.roomName, availableTime);
    }


    //there's also a createRoom in RoomStorage with the parameter capacity
    public void addRoom(String roomName) {
        this.roomStorage.createRoom(roomName);
    }

    //can't put anything here since speakerStorage hasn't been made
    public boolean requestSpeaker(String name, String password, String email) {
        return this.userStorage.createUser("Speaker", name, password, email);
    }

    public void registerRoom(Scanner scan){
        orgSchedulePresenter.printMenu(9);
        String roomName = scan.nextLine();
        this.addRoom(roomName);
    }
    public void registerSpeaker(Scanner scan){
        orgSchedulePresenter.printMenu(10);
        String name = scan.nextLine();
        String password = scan.nextLine();
        String email = scan.nextLine();
        if (this.requestSpeaker(name, password, email)){
            orgSchedulePresenter.printMenu(11);
        }
    }
    public void run(){
        orgSchedulePresenter.printHello(organizer);
        orgSchedulePresenter.printMenu(1);
        orgSchedulePresenter.printMenu(2);
        Scanner scan = new Scanner(System.in);
        boolean doContinue = true;
        while(doContinue) {
            String choice = scan.nextLine();
            try {
                int command = Integer.parseInt(choice);
            //if they want to register for a talk
            if (command == 1) {
                this.registerTalk(userSchedulePresenter, scan, organizer, signUpMap);
                //If they want to see all available talks
            }else if (command == 2) {
                this.seeAllTalks(userSchedulePresenter, scan);
                //if they want to see all the talks they are currently registered for
            }else if (command == 3) {
                this.seeAllRegistered(userSchedulePresenter, scan, organizer);
                // if they want to cancel a registration
            }else if (command == 4) {
                this.cancelATalk(userSchedulePresenter, scan, organizer);
            }else if (command == 5){
                this.requestTalk(scan);
            }else if (command == 6){
                this.registerRoom(scan);
            }else if (command == 7){
                this.registerSpeaker(scan);
            }
            else if (command ==0){
                scan.close();
                doContinue = false;
                mainMenuController.runMainMenu(organizer.getUser());
            }
            else{orgSchedulePresenter.printMenu(15);}}
            catch (NumberFormatException nfe){
                orgSchedulePresenter.printMenu(15);;
            }

        }
    }
    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof RoomStorage){
            this.roomStorage = roomStorage;
        }

    }

    public void setSignUpMap(HashMap<Talk, SignUpAttendeesManager> signUpMap){
        this.signUpMap = signUpMap;
    }
}
