package xyz.kowalski.vmh;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.core.diff.changetype.container.ContainerElementChange;
import org.javers.core.diff.changetype.container.ListChange;
import org.javers.core.diff.changetype.container.ValueAdded;

public class Versioner<T extends Versionable> {

    protected final Javers javers = JaversBuilder.javers().build();

    public Optional<List<Modification>> diff(final T original, final T updated) {
        List<Modification> changes = null;

        final Diff diff = javers.compare(original, updated);

        if (diff.hasChanges()) {
            changes = new ArrayList<Modification>();

            for (final ValueChange valueChange : diff.getChangesByType(ValueChange.class)) {
                final FieldModification change = new FieldModification(valueChange.getPropertyName(),
                        valueChange.getLeft(), valueChange.getRight());
                changes.add(change);
            }

            for (final ListChange listChange : diff.getChangesByType(ListChange.class)) {
                for (final ContainerElementChange elementChange : listChange.getChanges()) {
                    if (elementChange instanceof ValueAdded) {
                        final String fieldName = listChange.getPropertyName();
                        try {
                            final Field updatedListField = updated.getClass().getDeclaredField(fieldName);
                            updatedListField.setAccessible(true);
                            final List<Object> listInstance = (List<Object>) updatedListField.get(updated);
                            changes.add(new ListModification(listChange.getPropertyName(), ModificationEvent.ADDED,
                                    elementChange.getIndex(), listInstance.get(elementChange.getIndex())));
                        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException
                                | IllegalAccessException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }

        return Optional.ofNullable(changes);
    }

    private void applyPatch(final T obj, final List<Modification> changes, final boolean isNewVersion) {

        final Set<Field> fields = new HashSet<Field>(Arrays.asList(obj.getClass().getDeclaredFields()));

        for (final Modification change : changes) {
            try {
                if (change instanceof FieldModification) {
                    final Field field = obj.getClass().getDeclaredField(((FieldModification) change).getPropertyName());
                    if (field.getAnnotation(Versioned.class) != null) {
                        field.setAccessible(true);
                        field.set(obj, ((FieldModification) change).getAfter());
                    }
                    fields.remove(field);
                } else if (change instanceof ListModification) {
                    final Field field = obj.getClass().getDeclaredField(((ListModification) change).getPropertyName());
                    if (field.getAnnotation(Versioned.class) != null) {
                        field.setAccessible(true);

                        if (((ListModification) change).getModificationEvent().equals(ModificationEvent.ADDED)) {
                            try {

                                Object listObj = field.get(obj);

                                if (listObj == null) {
                                    listObj = field.getType().newInstance();
                                }

                                final Object newValue = ((ListModification) change).getValue();
                                final Method add = List.class.getDeclaredMethod("add", Object.class);
                                add.invoke(listObj, newValue);
                            } catch (final NoSuchMethodException | InvocationTargetException | InstantiationException e) {
                                System.err.println(e);
                            }
                        }

                        fields.remove(field);
                    }
                }
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        for (final Field field : fields) {
            try {
                if (field.getAnnotation(Versioned.class) != null && !Modifier.isFinal(field.getModifiers())) {
                    field.setAccessible(true);
                    field.set(obj, null);
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (isNewVersion) {
            final List<Version> versions = obj.getVersions();
            final Version newVersion = new Version(versions.size() + 1, changes);
            versions.add(newVersion);
        }
    }

    public void update(final T original, final T updated) {
        final Optional<List<Modification>> potentialChanges = diff(original, updated);

        if (potentialChanges.isPresent()) {
            applyPatch(original, potentialChanges.get(), true);
        }
    }

    public void loadVersion(final T obj, final int version) {
        applyPatch(obj, obj.getVersions().get(version).getChanges(), false);
    }

}
