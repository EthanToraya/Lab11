package src;

/**
 * File: CSCI_1302/JavaFXTest/src/locker_keypad/Lab11Prob02.java
 * Package: src
 * @author Ethan Toraya, Nathan Ross
 * Created on: Apr 25, 2025
 * Last Modified:  Apr 25, 2025
 * Description:  Reading a dat file and writing it back out
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Lab11Prob03 {
	public static void main(String[] args) {

		try (DataInputStream dis = new DataInputStream(new FileInputStream("src/people.dat"));
				DataOutputStream dos = new DataOutputStream(new FileOutputStream("src/people-copy.dat"));
				BufferedWriter writer = new BufferedWriter(new FileWriter("src/people-salary-sorted.dat"));
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/people-salary-sorted-objects.dat"));
				) {
			int age, zip;
			String name, address;
			double salary;
			ArrayList<Person> persons = new ArrayList<>();
			
			try {
				while (true) {
					// Read data
					age = dis.readInt();
					name = dis.readUTF();
					address = dis.readUTF();
					zip = dis.readInt();
					salary = dis.readDouble();
					
					// Person is created from read
					Person p = new Person(age, name, address, zip, salary);
					persons.add(p);
					
					// Print to console
					System.out.println(p.toString());
					
					// Write to new unsorted file
					dos.writeInt(p.getAge());
					dos.writeUTF(p.getName());
					dos.writeUTF(p.getAddress());
					dos.writeInt(p.getZipCode());
					dos.writeDouble(p.getSalary());
				}
			} catch (EOFException eof) {
				// End of file reached
			}
			Collections.sort(persons);
			for (Person p : persons) {
				writer.write(p.toString());
				writer.newLine();
				oos.writeObject(p);
			}
		} catch (IOException e) {
			System.out.println("Error processing file: " + e.getMessage());
			e.printStackTrace();
		}
	}
}

class Person implements Comparable<Person>, Serializable {
	private int age;
	private String name;
	private String address;
	private int zipCode;
	private double salary;

	public Person() {

	}

	public Person(int age, String name, String address, int zipCode, double salary) {
		setAge(age);
		setName(name);
		setAddress(address);
		setZipCode(zipCode);
		setSalary(salary);
	}

	// Getters and Setters
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return String.format("%d %s %s %d $%,.2f", getAge(), getName(), getAddress(), getZipCode(), getSalary());
	}

	@Override
	public int compareTo(Person o) {
		return Double.compare(o.getSalary(), this.getSalary());
	}

}
