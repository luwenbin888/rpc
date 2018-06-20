package org.luwenbin888.avro.codegen;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<User> createUsers() {
        User user1 = new User();
        user1.setName("Alyssa");
        user1.setFavoriteNumber(256);
        // Leave favorite color null

        // Alternate constructor
        User user2 = new User("Ben", 7, "red");

        // Construct via builder
        User user3 = User.newBuilder()
                .setName("Charlie")
                .setFavoriteColor("blue")
                .setFavoriteNumber(null)
                .build();

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        return users;
    }

    private static void serializeToDisk(String filePath, List<User> users) throws IOException {
        // Serialize user1, user2 and user3 to disk
        DatumWriter<User> userDatumWriter = new SpecificDatumWriter<User>(User.class);
        DataFileWriter<User> dataFileWriter = new DataFileWriter<User>(userDatumWriter);

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (i == 0) {
                dataFileWriter.create(user.getSchema(), new File(filePath));
            }
            dataFileWriter.append(user);
        }

        dataFileWriter.close();
    }

    private static List<User> deserializeFromDisk(String filePath) throws IOException {
        // Deserialize Users from disk
        DatumReader<User> userDatumReader = new SpecificDatumReader<User>(User.class);
        DataFileReader<User> dataFileReader = new DataFileReader<User>(new File(filePath), userDatumReader);
        List<User> users = new ArrayList<>();
        while (dataFileReader.hasNext()) {
            // Reuse user object by passing it to next(). This saves us from
            // allocating and garbage collecting many objects for files with
            // many items.
            User user = null;
            user = dataFileReader.next(user);
            users.add(user);
        }

        return users;
    }

    public static void main(String[] args) {
        List<User> users = createUsers();
        users.stream().forEach(user -> System.out.println(user));

        System.out.println("Serialize to /tmp/users.avro");

        String avroPath = "/tmp/users.avro";

        try {

            serializeToDisk(avroPath, users);

            assert Files.exists(Paths.get(avroPath));

            List<User> usersDeserialized = deserializeFromDisk(avroPath);

            usersDeserialized.stream().forEach(user -> System.out.println(user));

        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
