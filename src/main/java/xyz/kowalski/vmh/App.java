package xyz.kowalski.vmh;

import java.util.UUID;

public class App {

    private static final Versioner<Person> versioner = new Versioner<Person>();

    public static void main(final String[] args) {

        final UUID uuid = UUID.randomUUID();

        final Person person = new Person();
        person.setId(uuid);

        final Person personOrig = new Person();

        personOrig.setId(uuid);
        personOrig.setName("Brandon");
        personOrig.setAge(23);
        personOrig.setTest("hello");
        personOrig.getHobbies().add(new Hobby("programming"));
        personOrig.getHobbies().add(new Hobby("basketball"));

        versioner.update(person, personOrig);
        System.out.println(person);
        for (final Hobby hobby : person.getHobbies()) {
            System.out.println("hobby " + hobby.getName());
        }
        System.out.println("----------------------------------------\n");

        final Person personNew = new Person();

        personNew.setId(uuid);
        personNew.setName("Potato");
        personNew.setAge(6);
        personNew.setAddress("155 Silly Walk Lane");
        personNew.setTest("goodbye");

        versioner.update(person, personNew);
        System.out.println(person);
        for (final Hobby hobby : person.getHobbies()) {
            System.out.println("hobby " + hobby.getName());
        }
        System.out.println("----------------------------------------\n");

        versioner.loadVersion(person, 0);
        System.out.println(person);
        for (final Hobby hobby : person.getHobbies()) {
            System.out.println("hobby " + hobby.getName());
        }
        System.out.println("----------------------------------------\n");

        versioner.loadVersion(person, 1);
        System.out.println(person);
        for (final Hobby hobby : person.getHobbies()) {
            System.out.println("hobby " + hobby.getName());
        }
        System.out.println("----------------------------------------\n");

    }

}
