package xyz.kowalski.vmh;

import org.javers.core.diff.Diff;

public class Person extends AbstractVersionable<Person>  {

    private static final long serialVersionUID = 8559167935617547960L;

    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    @Override
    public Diff diff(final Person update) {
        return javers.compare(this, update);
    }

    @Override
    public int update(final Person update) {
        return super.update(diff(update));
    }

}
