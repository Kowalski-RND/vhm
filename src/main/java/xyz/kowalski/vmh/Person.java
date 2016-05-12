package xyz.kowalski.vmh;

import java.util.ArrayList;
import java.util.List;

public class Person extends Versionable  {

    private static final long serialVersionUID = 8559167935617547960L;

    @Versioned
    private String name;

    @Versioned
    private int age;

    @Versioned
    private String address;

    @Versioned
    private String test;

    @Versioned
    private final List<Hobby> hobbies;

    public Person() {
        super();
        hobbies = new ArrayList<Hobby>();
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getTest() {
        return test;
    }

    public void setTest(final String test) {
        this.test = test;
    }

    @Override
    public String toString() {
        return name + ", " + age + "\n" + address + "\n" + test + "\n";
    }

    public List<Hobby> getHobbies() {
        return hobbies;
    }

}
