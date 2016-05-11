package xyz.kowalski.vmh;

import java.io.Serializable;

import org.javers.core.diff.Diff;

public interface Versionable<T extends Serializable> {

    int update(T update);
    int update(Diff diff);

    T previousVersion();

    T nextVersion();

    T loadVersion(int version);

    T patch(Diff diff);

    Diff diff(T update);

}
