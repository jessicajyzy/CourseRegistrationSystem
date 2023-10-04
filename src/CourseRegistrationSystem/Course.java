package CourseRegistrationSystem;

import java.io.*;
import java.util.ArrayList;

public class Course implements java.io.Serializable {
	public String name;
	public String id;
	public int maximumStudents;
	public int currentStudents;
	public ArrayList<Student> studentList;
	public  String instructor;
	public  int section;
	public  String location;
	public static ArrayList<Course> courseList = new ArrayList<Course>();
	
	//constructors
	Course() {}
	Course(String courseName, String courseID, int maxStudents, int currentStudents, String instructorName,
			int courseSection, String courseLocation) {
		this.name = courseName;
		this.id = courseID;
		this.maximumStudents = maxStudents;
		this.studentList = new ArrayList<Student>();
		this.currentStudents = studentList.size();
		this.instructor = instructorName;
		this.section = courseSection;
		this.location = courseLocation;
	}
	
	// getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getMaximumStudents() {
		return maximumStudents;
	}

	public void setMaximumStudents(int maximumStudents) {
		this.maximumStudents = maximumStudents;
	}

	public int getCurrentStudents() {
		return currentStudents;
	}

	public void setCurrentStudents(int currentStudents) {
		this.currentStudents = currentStudents;
	}

	public ArrayList<Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(ArrayList<Student> studentList) {
		this.studentList = studentList;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public static ArrayList<Course> getCourseList() {
		return courseList;
	}

	public static void setCourseList(ArrayList<Course> courseList) {
		Course.courseList = courseList;
	}
	
	// serialize
	public static void serialize() {
		try {
			FileOutputStream in = new FileOutputStream("CourseData.ser");
			ObjectOutputStream out = new ObjectOutputStream(in);
			out.writeObject(courseList);
			out.close();
			in.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	//deserialize
	public static void deserialize() {
		try {
			FileInputStream in = new FileInputStream("CourseData.ser");
			ObjectInputStream out = new ObjectInputStream(in);
			courseList = (ArrayList<Course>) out.readObject(); 
			out.close();
			in.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException c) {
			System.out.println("Error: Class not found.");
			c.printStackTrace();
		}
	}
	


}