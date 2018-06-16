package org.luwenbin888.protobuf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        AddressBookProtos.Person person1 = getPerson();
        System.out.println("Person look like:");
        System.out.println(person1);

        String filePath = "/tmp/Person.dat";

        System.out.println("Serialize person to disk /tmp/Person.dat");
        writeToFile(person1, filePath);
        System.out.println("Serialization completed...");
        System.out.println();

        System.out.println("Deserialize person from disk /tmp/Person.dat");
        AddressBookProtos.Person person2 = readFromFile(filePath);
        System.out.println(person2);
    }

    public static AddressBookProtos.Person getPerson() {
        AddressBookProtos.Person person = AddressBookProtos.Person.newBuilder()
                .setId(1234).setName("Mike").setEmail("mike@example.com")
                .addPhones(AddressBookProtos.Person.PhoneNumber.newBuilder()
                        .setNumber("123-567")
                        .setType(AddressBookProtos.Person.PhoneType.HOME))
                .build();

        return person;
    }

    public static void writeToFile(AddressBookProtos.Person person1, String filePath) {
        assert person1 != null;
        assert filePath != null && !filePath.equals("");

        try {
            FileOutputStream stream = new FileOutputStream(filePath);
            person1.writeTo(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AddressBookProtos.Person readFromFile(String filePath) {
        assert filePath != null && !filePath.equals("");

        AddressBookProtos.Person person = null;

        try {
            FileInputStream stream = new FileInputStream(filePath);
            person = AddressBookProtos.Person.newBuilder().mergeFrom(stream).build();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return person;
    }
}
