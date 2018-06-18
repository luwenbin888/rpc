package org.luwenbin888.utils;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class EqualsBuilderTest {
    public static class Student {
        private String name;
        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) return false;
            else if (obj == this) return true;
            else if (obj.getClass() != this.getClass()) return false;
            else {
                Student student = (Student)obj;
                return new EqualsBuilder().append(student.name, this.name)
                        .append(student.id, this.id).isEquals();
            }
        }
    }
    public static void main(String[] args) {
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(1, 1).append("str1", "str1");
        System.out.println(builder.isEquals());

        Student student1 = new Student();
        student1.setName("Jack");
        student1.setId(1);

        Student student2 = new Student();
        student2.setName("Kite");
        student2.setId(2);

        Student student3 = new Student();
        student3.setName("Jack");
        student3.setId(1);

        System.out.println("Student1 == Student2: " + student1.equals(student2));
        System.out.println("Student1 == Student3: " + student1.equals(student3));

        builder.reset();
        // Equals Builder append(Object, Object) uses equals to compare two object
        System.out.println("Student1 equals Student3 using EqualsBuilder: " + builder.append(student1, student3).isEquals());
        System.out.println("Student1 direct equals Student3: " + (student1 == student3));
    }
}
