package xyz.kowalski.vmh;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.core.metamodel.annotation.Id;

public abstract class AbstractVersionable<T extends Serializable> implements Versionable<T>, Serializable{

    private static final long serialVersionUID = -7574115284452334663L;

    protected static final Javers javers = JaversBuilder.javers().build();

    @Id
    private UUID id;

    private List<Diff> versions;

    public AbstractVersionable() {
        versions = new ArrayList<Diff>();
    }

    @Override
    public T previousVersion() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T nextVersion() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T loadVersion(final int version) {
        return patch(versions.get(version));
    }

    @Override
    public T patch(final Diff diff) {
        for (final ValueChange change : diff.getChangesByType(ValueChange.class)) {
            try {
                final Field field = this.getClass().getDeclaredField(change.getPropertyName());
                field.setAccessible(true);
                field.set(this, change.getRight());
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public int update(final Diff diff) {

        if (diff.hasChanges()) {
            versions.add(diff);
            patch(diff);
        }

        return versions.size();
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public List<Diff> getVersions() {
        return versions;
    }

    public void setVersions(final List<Diff> versions) {
        this.versions = versions;
    }

}
