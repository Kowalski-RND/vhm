package xyz.kowalski.vmh;

public class FieldModification implements Modification {

    private static final long serialVersionUID = -6089037206009249032L;

    private final String propertyName;
    private final Object before;
    private final Object after;

    public FieldModification(final String propertyName, final Object before, final Object after) {
        this.propertyName = propertyName;
        this.before = before;
        this.after = after;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Object getBefore() {
        return before;
    }

    public Object getAfter() {
        return after;
    }

}
