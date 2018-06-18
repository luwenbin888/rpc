package org.luwenbin888.utils;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Apache commons-lang3 HashCodeBuilder {@link org.apache.commons.lang3.builder.HashCodeBuilder}
 */
public class HashCodeBuilderTest {

    public static class Employee {
        private String name;
        private int age;
        private String employeeId;
        private String departmentId;
        private String managerId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(String employeeId) {
            this.employeeId = employeeId;
        }

        public String getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(String departmentId) {
            this.departmentId = departmentId;
        }

        public String getManagerId() {
            return managerId;
        }

        public void setManagerId(String managerId) {
            this.managerId = managerId;
        }

        @Override
        public int hashCode() {
            HashCodeBuilder builder = new HashCodeBuilder();
            return builder.append(name).append(age).append(employeeId).append(departmentId)
                    .append(managerId).toHashCode();
        }
    }

    public static void main(String[] args) {
        Employee employee1 = new Employee();
        employee1.setName("Mike");
        employee1.setAge(30);
        employee1.setEmployeeId("10001");
        employee1.setDepartmentId("tech1");
        employee1.setManagerId("100");

        int employee1HashCode = employee1.hashCode();
        System.out.println("Employee1 hash code: " + employee1HashCode);

        Employee employee2 = new Employee();
        employee2.setName("Mike");
        employee2.setAge(30);
        employee2.setEmployeeId("10006");
        employee2.setDepartmentId("tech2");
        employee2.setManagerId("102");

        int employee2HashCode = employee2.hashCode();
        System.out.println("Employee2 hash code: " + employee2HashCode);

        System.out.println("Two employee hash code equals:" + (employee1HashCode == employee2HashCode));
    }
}
