package de.softinva.multitimer.fragments.dialogimportdataresult;

import java.util.LinkedList;

import de.softinva.multitimer.utility.UtilityMethods;

public class ImportDataMessages {
    private LinkedList<String> errorMessages;
    private LinkedList<String> successMessages;

    public void setErrorMessages(LinkedList<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public void setSuccessMessages(LinkedList<String> successMessages) {
        this.successMessages = successMessages;
    }

    public String getErrorMessagesAsString() {
        return UtilityMethods.getStringFromList(errorMessages);
    }


    public String getSuccessMessagesAsString() {
        return UtilityMethods.getStringFromList(successMessages);
    }
}
