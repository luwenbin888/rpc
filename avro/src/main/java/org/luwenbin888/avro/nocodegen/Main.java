package org.luwenbin888.avro.nocodegen;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {


    public static List<GenericRecord> getUsers(Schema schema) throws IOException {
        GenericRecord user1 = new GenericData.Record(schema);
        user1.put("name", "Alyssa");
        user1.put("favorite_number", 256);
        // Leave favorite color null

        GenericRecord user2 = new GenericData.Record(schema);
        user2.put("name", "Ben");
        user2.put("favorite_number", 7);
        user2.put("favorite_color", "red");

        List<GenericRecord> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        return users;
    }

    public static Schema getSchema(String schemaPath) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        Schema schema = new Schema.Parser().parse(new File(classloader.getResource(schemaPath).getFile()));

        return schema;
    }

    public static void serializeToDisk(String filePath, List<GenericRecord> users, Schema schema) throws IOException {
        File file = new File(filePath);
        DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(schema);
        DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(datumWriter);
        dataFileWriter.create(schema, file);
        for (GenericRecord user: users) {
            dataFileWriter.append(user);
        }
        dataFileWriter.close();
    }

    public static List<GenericRecord> deserializeFromDisk(String filePath, Schema schema) throws IOException {
        // Deserialize users from disk
        DatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>(schema);
        DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(new File(filePath), datumReader);

        List<GenericRecord> users = new ArrayList<>();

        while (dataFileReader.hasNext()) {
            // Reuse user object by passing it to next(). This saves us from
            // allocating and garbage collecting many objects for files with
            // many items.
            GenericRecord user = null;
            user = dataFileReader.next(user);
            users.add(user);
        }

        return users;
    }

    public static void main(String[] args) {
        try {
            Schema schema = getSchema("avro/user.avsc");

            List<GenericRecord> users = getUsers(schema);
            assert users != null;

            users.stream().forEach(user -> System.out.println(user));

            String avroPath = "/tmp/user.avro";
            serializeToDisk(avroPath, users, schema);

            List<GenericRecord> usersDeserialized = deserializeFromDisk(avroPath, schema);

            usersDeserialized.stream().forEach(user -> System.out.println(user));

            assert usersDeserialized != null && usersDeserialized.size() > 0;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
