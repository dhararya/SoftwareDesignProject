package Schedule;

/**
 * A presenter class for organizer schedules.
 */

public class OrgSchedulePresenter extends UserSchedulePresenter {

    /**
     * Prints the menu.
     */

    public void printMenu(){
        printUserMenu();
        System.out.println("Press 7 to request to add an Event " + System.lineSeparator() +
                "Press 8 to register a room " + System.lineSeparator() +
                "Press 9 to request to register a user" + System.lineSeparator() +
                "Press 10 to cancel an event" + System.lineSeparator() +
                "Press 11 to change an event's capacity" + System.lineSeparator() +
                "Press 12 to review user requests");
        printGoBack();
    }

    /**
     * Prints attendee emails and the amount of request s.
     *
     * @param i an int representing the case
     */

    public void printReviewRequests(int i){
        switch (i){
            case 1:
                System.out.println("Which attendee's request would you like to review?");
                break;
            case 2:
                System.out.println("What status would like to set request to? approved or rejected?");
                break;
            case 3:
                System.out.println("Request status successfully updated.");
                break;
            case 4:
                System.out.println("Invalid status; please enter either \"approved\" or \"rejected.\"");
        }
    }

    /**
     * Presents the process of cancelling an event.
     *
     * @param i an int representing the case
     * @param event a String representing the event
     */

    public void changeEvent(int i, String event){
        switch (i){
            case 1:
                choose(event);
                System.out.println("Which event would you like to cancel");
                break;
            case 2:
                System.out.println("Event " + event + " cancelled successfully");
                break;
            case 3:
                System.out.println("No events available to cancel.");
                break;
            case 4:
                System.out.println("Choose the event for which you would like to change its capacity:");
                break;
            case 5:
                System.out.println("Capacity chosen is lower then the number of attendees registered, " +
                        "cannot double book attendees for an event. Please choose capacity again.");
                break;
                }}


    /**
     * Prints the process of registering a new user.
     *
     * @param i an int representing a case
     */

    public void registerUserMenu(int i){
        switch (i){
            case 1:
                System.out.println("Enter the credentials of the new user to register" + System.lineSeparator() +
                "Enter the name" + System.lineSeparator() +
                "Enter the password" + System.lineSeparator() +
                "Enter the e-mail" + System.lineSeparator() +
                "Enter the type of user (Attendee/ Speaker/ Organizer)");
                break;
            case 2:
                System.out.println("Enter VIP to allow the new user into VIP restricted events" +
                        System.lineSeparator()+ "Press any other key to continue");
                break;
    }}

    /**
     * Prints the process of registering a new room.
     *
     * @param i an int representing a case
     */

    public void printRegisterRoom(int i){
        switch (i) {
            case 1:
                System.out.println("Enter the name of the new room to register, press 0 to go back");
                break;
            case 2:
                System.out.println("enter the capacity for this room:");
                break;
            case 3:
                System.out.println("Room already exists in the system, try registering a different room.");
                break;
        } }

    /**
     * Prints the process of requesting a new event.
     *
     * @param i an int representing a case
     */

    public void printRequestEventMenu(int i){
        switch (i){
            case 1:
                System.out.println("Enter the capacity for this event");
                break;
            case 2:
                System.out.println("Capacity above room capacity");
                break;
            case 3:
                System.out.println("Enter the title of the event.");
                break;
            case 4:
                System.out.println("Enter VIP if the event is restricted, none otherwise");
                break;
            case 5:
                System.out.println("Invalid room or time. Please select new room and time");
                break;
            case 6:
                System.out.println("Valid speakers, room, time and capacity for an event.");
                break;
            case 7:
                System.out.println("Pick starting time of event:");
                break;
            case 8:
                System.out.println("Pick end time for event");
                break;
            case 9:
                System.out.println("End time must be after the start time. Pick another end time");
                break;
            case 10:
                System.out.println("Event capacity must be above 0.");
                break;
        }
    }

    /**
     * A series of phrases output depending on what the organizer does.
     * @param i An int corresponding to specific output.
     */

    public void printRequestEventProcess(int i){
        switch (i) {
            case 1:
                System.out.println("Please choose a day of the conference for your requested event (By indicating day NOVEMBER 21, 22 or 23)");
                break;
            case 2:
                System.out.println("Choose a time (by hour) for your requested event." + System.lineSeparator() +
                        "Events may start or from 9:00 to 16:00 and end from 10:00 to 17:00" + System.lineSeparator() +
                        "Indicate your chosen time by a number from 9 to 17");
                break;
            case 3:
                System.out.println("Chosen speaker is not available in the chosen hour and day.");
                break;
            case 4:
                System.out.println("Chosen room is not available in the chosen hour and day.");
                break;
            case 5:
                System.out.println("Pick again or press 0 to go back to the scheduling menu");
                break;
            case 6:
                System.out.println("Both chosen room and speaker are not available in the chosen hour and day.");
                break;
            case 7:
                System.out.println("Event added successfully");
                break;
            case 8:
                System.out.println("An error occurred, an event was not added");
                break;
            case 9:
                System.out.println("Enter event title:");
                break;
        }
    }

    /**
     * Prints the process of choosing a speaker.
     *
     * @param i an int representing a case
     * @param str a String representing the number of speakers
     */

    public void printChoosingSpeakersProcess(int i, String str){
        switch (i){
            case 1:
                if (str.equals("1")){
                    System.out.println(str + " speaker chosen");
                }
                else if(str.equals("0")){
                    System.out.println("No speakers chosen");
                }
                else{System.out.println( str + " speakers chosen");}
                break;
            case 2:
                System.out.println(str + " added successfully");
                break;
            case 3:
                System.out.println("Too many speakers. Please choose at most " + str + " speakers.");
                break;
            case 4:
                System.out.println("Capacity of the chosen room is " + str +".");
        }
    }

    /**
     * Prints the process of choosing speakers.
     *
     * @param i an int representing a case
     */

    public void printChooseSpeakers(int i){
        switch (i){
            case 1:
                System.out.println("Enter the number of speakers for the event:");
                break;
            case 2:
                System.out.println("Speaker added");
                break;
            case 3:
                System.out.println("Speaker already chosen. Please choose another speaker.");
                break;
            case 4:
                System.out.println("Please choose at least 0 speakers for the event.");
                break;
        }
    }

    /**
     * Prints a schedule.
     *
     * @param object a String representing the object whose schedule is to be viewed
     */

    public void printSchedule(String object){
        System.out.println("Schedule for chosen " + object + ":");
    }
}
