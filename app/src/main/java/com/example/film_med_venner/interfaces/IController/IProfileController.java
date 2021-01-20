package com.example.film_med_venner.interfaces.IController;


import com.example.film_med_venner.interfaces.IDatabase;
import com.example.film_med_venner.interfaces.runnable.RunnableErrorUI;
import com.example.film_med_venner.interfaces.runnable.RunnableFullProfileUI;
import com.example.film_med_venner.interfaces.runnable.RunnableUI;

public interface IProfileController extends IController {
    void sendFriendRequest(String friendID) throws FriendException;
    void sendFriendRequestByMail(String email, RunnableErrorUI runnableErrorUI);
    void sendFriendRequestByMail(String email) throws FriendException;
    /**
     * Get friends with a certain status on them
     * @param status Requester status
     * @param runnableFullProfileUI method to run on complete
     * @throws FriendException
     */
    void getFriendType(String userID, int status, RunnableFullProfileUI runnableFullProfileUI) throws FriendException;
    void respondToFriendRequest(String friendID, int reqStatus, RunnableUI runnableUI) throws FriendException;

    class FriendException extends IDatabase.DatabaseException{
        public FriendException(String message) {
            super(message);
        }

        public FriendException(String message, Throwable cause) {
            super(message, cause);
        }

        public FriendException(String message, Throwable cause, int errorID) {
            super(message, cause, errorID);
        }
    }
}
