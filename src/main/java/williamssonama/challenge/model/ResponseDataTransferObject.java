package williamssonama.challenge.model;

import java.util.*;

/**
 * The data transfer object used for communication between the front and back end of the web application for
 * REST requests.
 *
 * @author wgweis
 *
 */
public class ResponseDataTransferObject
{
    public static final String INSTANCE_NOT_NULL_MSG = "ResponseDatatransferObject instance cannot be null.  Please initialize it first.";

    private List<Integer[]> rangeElements = new ArrayList<>();
    private Set<Notice> errors = new HashSet<>();
    private Set<Notice> warnings = new HashSet<>();
    private Set<Notice> informationals = new HashSet<>();

    public ResponseDataTransferObject()	{}

    public boolean hasErrors()	{
        return errors.size() > 0;
    }

    public boolean hasWarnings()	{
        return warnings.size() > 0;
    }

    public boolean hasInformational()	{
        return informationals.size() > 0;
    }

    public void addElement(Integer[] rangeElement) {
        rangeElements.add(rangeElement);
    }

    public void setElements(List<Integer[]> rangeElements) {
        this.rangeElements = rangeElements;
    }

    public void addError(String message) {
        this.addError(message, null);
    }

    public void addError(String message, String fieldName) {
        errors.add(new Notice(message, fieldName));
    }

    public void addWarning(String message) {
        warnings.add(new Notice(message, null));
    }

    public void addWarning(String message, String fieldName) {
        warnings.add(new Notice(message, fieldName));
    }

    public void addInformational(String message) {
        informationals.add(new Notice(message, null));
    }

    public void addInformational(String message, String fieldName) {
        informationals.add(new Notice(message, fieldName));
    }

    public boolean hasErrorMessage(String previousErrorMessage) {
        return this.errors.stream().anyMatch(error -> error.message.equalsIgnoreCase(previousErrorMessage));
    }

    public boolean hasElements() {
        return !this.rangeElements.isEmpty();
    }

    public  List<Integer[]> getElements() {
        return rangeElements;
    }

    private class Notice
    {
        @SuppressWarnings("unused")
        private String message;

        @SuppressWarnings("unused")
        private String fieldName;

        public Notice(String message, String fieldName) {
            this.message = message;
            this.fieldName = fieldName;
        }
    }
}
