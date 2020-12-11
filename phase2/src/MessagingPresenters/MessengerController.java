package MessagingPresenters;

import Schedule.EventManager;
import UserLogin.MainMenuController;
import UserLogin.Organizer;
import UserLogin.Speaker;
import UserLogin.UserManager;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class MessengerController {
    public String email;
    public Scanner scan;
    public MainMenuController mainMenuController;
    public UserManager userManager;
    public ConversationStorage conversationStorage;
    public MessageManager messageManager;
    public EventManager eventManager;

    /**
     * The constructor method
     * @param email The users email.
     * @param scan The scanner.
     * @param mainMenuController The MainMenuController
     * @param userManager The UserManager
     * @param conversationStorage The ConverastionStorage.
     * @param eventManager The EventManager
     */
    public MessengerController(String email, Scanner scan, MainMenuController mainMenuController,
                               UserManager userManager, ConversationStorage conversationStorage, EventManager eventManager) {
        this.email = email;
        this.scan = scan;
        this.mainMenuController = mainMenuController;
        this.userManager = userManager;
        this.conversationStorage = conversationStorage;
        this.eventManager = eventManager;
        if (userManager.emailToUser(email) instanceof Organizer){
            this.messageManager = new OrganizerMessageManager(email, userManager, conversationStorage, eventManager);
        }else if (userManager.emailToUser(email) instanceof Speaker){
            this.messageManager = new SpeakerMessageManager(email, userManager, conversationStorage, eventManager);
        }else{
            this.messageManager = new AttendeeMessageManager(email, userManager, conversationStorage, eventManager);
        }
    }

    /**
     * Method that deletes a senders message by index.
     * @param index The string index.
     * @param senderEmail The sender email.
     */
    public void deleteMessage(int index, String senderEmail){
        messageManager.deleteMessage(senderEmail, index);
    }

    /**
     * Method that returns ArrayList of EventID's.
     * @return ArrayList Strings.
     */
    public ArrayList<String> getEventIDS(){return messageManager.getEventIDs();}

    /**
     * Method that runs an individual chat menu
     * @param presenter The presenter.
     * @param emails The String ArrayList of emails.
     */
    public void runIndividualChatMenu(MessengerPresenter presenter, ArrayList<String> emails) {
        presenter.viewChats(emails);
        int index = Integer.parseInt(scan.nextLine());
        if (index == 0 || emails.size() == 0) {
            return;
        }
        String recipientEmail = emails.get(index - 1);
        Boolean viewingArchivedMessages = false;
        char input = 'a';
        while (input != '0') {
            ArrayList<String> outputMessages = messageManager.getFormattedMessages(viewingArchivedMessages, recipientEmail);
            presenter.viewConversation(outputMessages, viewingArchivedMessages);
            String in = scan.nextLine();
            input = in.toCharArray()[0];
            if (input == 'a') {
                viewingArchivedMessages = !viewingArchivedMessages;
            }
            else if (input != '0') {
                int position = Integer.parseInt(in) - 1;
                String messageBody = messageManager.getContentOfMessageAtIndex(viewingArchivedMessages, position, recipientEmail);
                Boolean isRead = messageManager.getReadStatusOfMessageAtIndex(viewingArchivedMessages, position, recipientEmail, email);
                presenter.viewMessageMenu(messageBody, viewingArchivedMessages, isRead);
                int opt = Integer.parseInt(scan.nextLine());
                if (opt == 1) {
                    // DELETION
                    deleteMessage(position, messageManager.getSenderOfMessageAtIndex(viewingArchivedMessages, position, recipientEmail));
                    presenter.printSuccessfulDeletion();
                }
                else if (opt == 2) {
                    // READ/UNREAD
                    if (messageManager.hasMessageStatus(recipientEmail, position, "Unread")) {
                        messageManager.deleteMessageStatus(recipientEmail, position, "Unread");
                        messageManager.addMessageStatus(recipientEmail, position, "Read");
                    }
                    else {
                        messageManager.deleteMessageStatus(recipientEmail, position, "Read");
                        messageManager.addMessageStatus(recipientEmail, position, "Unread");
                    }
                }
                else if (opt == 3) {
                    // ARCHIVAL
                    if (messageManager.hasMessageStatus(recipientEmail, position, "Archived")) {
                        messageManager.deleteMessageStatus(recipientEmail, position, "Archived");
                        messageManager.addMessageStatus(recipientEmail, position, "Unarchived");
                    }
                    else {
                        messageManager.deleteMessageStatus(recipientEmail, position, "Unarchived");
                        messageManager.addMessageStatus(recipientEmail, position, "Archived");
                    }
                }
            }
        }
    }

    /**
     * Mehtod that runs a group chat menu.
     * @param presenter The presenter.
     * @param talkIDS ArrayList of String talkID's.
     */
    public void runGroupChatMenu(MessengerPresenter presenter, ArrayList<String> talkIDS) {
        presenter.viewGroupChats(talkIDS);
        int index = Integer.parseInt(scan.nextLine());
        if (index == 0 || talkIDS.size() == 0) {
            return;
        }
        String groupChatID = talkIDS.get(index - 1);
        char input = 'a';
        while (input != '0'){
            ArrayList<String> messages = messageManager.getGroupChatMessages(groupChatID);
            presenter.viewGroupChat(messages);
            input = scan.nextLine().toCharArray()[0];
        }
    }


    /**
     * Method that runs a message individuals menu.
     * @param presenter The presenter.
     */
    public void runMessageMenu(MessengerPresenter presenter, boolean individual) {
        presenter.askForEmail();
        String email = "";
        boolean valid_recipient = false;

        while (!valid_recipient) {
            email = scan.nextLine();
            if (email.equals("0")) {
                return;
            }
            if (messageManager.canMessage(email)) {
                valid_recipient = true;
            } else {
                presenter.printSendMessageError();
            }
        }

        presenter.askForMessageBody();
        String body = scan.nextLine();

        if (body.equals("0")) {
            return;
        }

        messageManager.message(email, body, individual);
        presenter.printMessageSentSuccess();
    }

    /**
     * Abstract run method.
     */
    public abstract void run();
}
