package xyz.kowalski.vmh;

import java.util.UUID;

public class App {

    public static void main(final String[] args) {

        final UUID uuid = UUID.randomUUID();

        final Person person = new Person();
        person.setId(uuid);

        final Person personOrig = new Person();

        personOrig.setId(uuid);
        personOrig.setName("Brandon");
        personOrig.setAge(23);

        person.update(personOrig);

        System.out.println(person.getName());
        System.out.println("Version count: " + person.getVersions().size());

        final Person personNew = new Person();

        personNew.setId(uuid);
        personNew.setName("Potato");
        personNew.setAge(6);

        person.update(personNew);

        System.out.println(person.getName());
        System.out.println("Version count: " + person.getVersions().size());

        person.loadVersion(0);

        System.out.println(person.getName());
        System.out.println("Version count: " + person.getVersions().size());

    }

}
