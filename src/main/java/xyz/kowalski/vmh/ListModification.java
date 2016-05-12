package xyz.kowalski.vmh;

public class ListModification implements Modification {

    private static final long serialVersionUID = -2893663636777625058L;

    private final String propertyName;
    private final ModificationEvent modificationEvent;
    private final int position;
    private final Object value;

    public ListModification(final String propertyName, final ModificationEvent modificationEvent, final int position, final Object value) {
        this.propertyName = propertyName;
        this.modificationEvent = modificationEvent;
        this.position = position;
        this.value = value;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public ModificationEvent getModificationEvent() {
        return modificationEvent;
    }

    public int getPosition() {
        return position;
    }

    public Object getValue() {
        return value;
    }

}
