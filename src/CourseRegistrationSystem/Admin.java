package CourseRegistrationSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class Admin extends User implements Admin_Interface, java.io.Serializable {
	
	// admin can keep track of all the students
	static ArrayList<Student> fullstudentList = new ArrayList<Student>();
	
	// default constructor
	public Admin() {}
	
	// constructor
	public Admin(String firstName, String lastName, String username, String password) {
		super(firstName, lastName, username, password);
		// username and password is given in instructions
	}

	public static ArrayList<Student> getFullstudentList() {
		return fullstudentList;
	}

	public static void setFullstudentList(ArrayList<Student> fullstudentList) {
		Admin.fullstudentList = fullstudentList;
	}

	@Override 
	// admin can add new course
	public void newCourse() throws IOException {
		// to get user input
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		// collect information needed to create a new course
		System.out.println("\nPlease enter the necessary information below to create a new course. Please ensure that your input is correct. ");
		// course name
		System.out.print("Course name: ");
		this.name = read.readLine();
		// course id
		System.out.print("Course ID: ");
		this.id = read.readLine();
		// maximum number of student (ensure integer input)
		while (true) {
			try {
				System.out.print("Maximum number of students: ");
				this.maximumStudents = Integer.parseInt(read.readLine());
				if (maximumStudents > 0) {
					break;
				}
				else {
					System.out.println("Sorry, your input was invalid. Please enter a valid section number. ");
				}
			}
			catch (Exception e) {
				System.out.println("Sorry, your input was invalid. Please enter a valid number of students. ");
			}
		}
		// assume no students since its a new course
		this.currentStudents = 0;
		// instructor name
		System.out.print("Instructor's name: ");
		this.instructor = read.readLine();
		// section number (ensure valid integer input)
		while (true) {
			try {
				System.out.print("Course section number: ");
				this.section = Integer.parseInt(read.readLine());
				if (section > 0) {
					break;
				}
				else {
					System.out.println("Sorry, your input was invalid. Please enter a valid section number. ");
				}
			}
			catch (Exception e) {
				System.out.println("Sorry, your input was invalid. Please enter a valid section number. ");
			}
		}
		// course location
		System.out.print("Course location: ");
		this.location = read.readLine();
		// add new course to course list
		courseList.add(new Course(this.name, this.id, this.maximumStudents, this.currentStudents, this.instructor, this.section, this.location));
		// tell user course has been added
		System.out.println("\nYou have successfully added " + this.name + " as a new course. Students can now login and start registering for this course. ");
	}

	@Override
	//admin can delete course
	public void deleteCourse() throws IOException {
		// to get user input
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		// keep user trapped until valid input supplied
		System.out.println("\nPlease enter the necessary information below to delete a course. Please ensure that your input is correct. ");
		System.out.print("Enter Course Name: ");
		String name_input = read.readLine();
		int sec_input = 0;
		while (true) {
			try {
				System.out.print("Enter Course Section: ");
				sec_input = Integer.parseInt(read.readLine());
				if (sec_input > 0) {
					break;
				}
			}
			catch (Exception e) {
				System.out.println("Sorry, your input was invalid. Please enter a valid section number. ");
			}
		}
		boolean find = false;
		// iterate through for loop to find the course
		for (int i = 0; i < courseList.size(); i++) {
			// finds the course
			if (name_input.contentEquals(courseList.get(i).getName())&& sec_input == courseList.get(i).getSection()){
				courseList.remove(i); // removes the course
				System.out.println("The course " + name_input + " has been successfully removed!");
				find = true;
				break;
			} 
		}
		if (find == false) {
			System.out.println("This course does not exist yet. Please check your information and try again. ");
		}

	}


	@Override
	// can edit any information except course id and course name
	public void editCourse() throws IOException {
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\nPlease choose from the following options what you would like to change about a course. ");
		System.out.println("1) Maximum Number of Students\n2) Current Number of Students\n3) Instructor Name\n4) Course Section\n5) Course Location");
		System.out.print("Enter your selection: ");
		String choice = read.readLine();
		System.out.print("\nEnter the name of the course you would like to edit: ");
		String courseName = read.readLine();
		int section = 0;
		// section number must be integer greater than 0
		while (true) {
			try {
				System.out.print("Enter the section number of the course you would like to edit: ");
				section = Integer.parseInt(read.readLine());
				if (section > 0) {
					break;
				}
			}
			catch (Exception e) {
				System.out.println("Sorry, your input was invalid. Try again. ");
			}
		}
		for (int i = 0; i < courseList.size(); i++) {
			// finds the course
			if (courseName.contentEquals(courseList.get(i).getName())&& section == courseList.get(i).getSection()){
				// change maximum
				if (choice.contentEquals("1")) {
					while (true) {
						try {
							System.out.print("Enter a new maximum number of students: ");
							int maximumStudents = Integer.parseInt(read.readLine());
							// maximum cannot exceed current enrolled
							if (maximumStudents > 0 && maximumStudents >= courseList.get(i).getCurrentStudents()) {
								courseList.get(i).setMaximumStudents(maximumStudents);
								break;
							}
							else {
								System.out.println("Sorry, your input was invalid. Try again. ");
							}
						}
						catch (Exception e) {
							System.out.println("Sorry, your input was invalid. Try again. ");
						}
					}
				}
				// change current enrolled
				else if (choice.contentEquals("2")) {
					// must delete or add one by one so you can keep track of who is in the course and who is not
					System.out.println("To change the number of students, you can only add or delete individual students. Please choose from the following options: \n1) Add Student\n2) Delete Student");
					String change = read.readLine();
					boolean student = false;
					// add student
					if (change.equals("1")) {
						System.out.print("Enter your student name: ");
						String firstName = read.readLine();
						System.out.print("Enter your student name: ");
						String lastName = read.readLine();
						// can only add if course is not full
						if (courseList.get(i).getCurrentStudents()<courseList.get(i).getMaximumStudents()) {
							for (int s = 0; s<courseList.get(i).studentList.size();s++) {
								if (firstName.equals(courseList.get(i).studentList.get(s).getFirstName()) && lastName.equals(courseList.get(i).studentList.get(s).getLastName())) {
									courseList.get(i).studentList.add(new Student(firstName, lastName, courseList.get(i).studentList.get(s).getUsername(), courseList.get(i).studentList.get(s).getPassword()));
									student = true;
									break;
								}	
							}
						}
						else {
							System.out.println("This course is already full. ");
						}
					}
					// cannot delete students if there are no student enrolled
					else if (change.equals("2") && courseList.get(i).currentStudents == 0) {
						System.out.println("There are no students in this course yet. ");
					}
					else if (change.equals("2")) {
						System.out.print("Enter your student name: ");
						String firstName = read.readLine();
						System.out.print("Enter your student name: ");
						String lastName = read.readLine();
						for (int s = 0; s<courseList.get(i).studentList.size();s++) {
							if (firstName.equals(courseList.get(i).studentList.get(s).getFirstName()) && lastName.equals(courseList.get(i).studentList.get(s).getLastName())) {
								courseList.get(i).studentList.remove(i);
								student = true;
								break;
							
							}	
						}
					}
					// student does not exist in system yet
					else if (change.equals("2") && student == false) {
						System.out.println("Student not found.");
					}
				}
				// change instructor name
				else if (choice.contentEquals("3")) {
					System.out.print("Enter new instructor name: ");
					String instructor = read.readLine();
					courseList.get(i).setInstructor(instructor);
				}
				// change section number
				else if (choice.contentEquals("4")) {
					// must be greater than 0
					while (true) {
						try {
							System.out.print("Enter a new course section: ");
							int input_section = Integer.parseInt(read.readLine());
							if (input_section > 0) {
								courseList.get(i).setSection(input_section);
								break;
							}
							else {
								System.out.println("Sorry, your input was invalid. Try again. ");
							}
						}
						catch (Exception e) {
							System.out.println("Sorry, your input was invalid. Try again. ");
						}
					}
				}
				// change location
				else if (choice.contentEquals("5")) {
					System.out.print("Enter a location: ");
					String loc = read.readLine();
					courseList.get(i).setLocation(loc);
				}
			}
		}
	}
	
	// Method Overload - display information about all courses
	public void courseInfo() {
		System.out.println("* * * * * * * * * * * * * * *");
		for (int i = 0; i < courseList.size(); i++) {
			// prints out the courses within the course list
			System.out.println("Course name: "+courseList.get(i).getName());
			System.out.println("Course ID: "+courseList.get(i).getId());
			System.out.println("Maximum number of students: "+courseList.get(i).getMaximumStudents());
			if (courseList.get(i).getCurrentStudents()!= 0) {
				System.out.println("Number of students registered: "+courseList.get(i).getId());
			}
			else {
				System.out.println("There are no students registered yet. ");
			}
			System.out.println("Instructor: "+courseList.get(i).getInstructor());
			System.out.println("Section: "+courseList.get(i).getSection());
			System.out.println("Location: "+courseList.get(i).getLocation());
			System.out.println("* * * * * * * * * * * * * * *");
		}
	}
	
	// Method Overload - display information about courses with same course id
	// section does not matter
	public void courseInfo(String id) throws IOException {
		System.out.println("* * * * * * * * * * * * * * *");
		for (int i = 0; i < courseList.size(); i++) {
			if (id.equals(courseList.get(i).getId())) {
				// prints out the courses within the course list
				System.out.println("Course name: "+courseList.get(i).getName());
				System.out.println("Course ID: "+courseList.get(i).getId());
				System.out.println("Maximum number of students: "+courseList.get(i).getMaximumStudents());
				if (courseList.get(i).getCurrentStudents()!= 0) {
					System.out.println("Number of students registered: "+courseList.get(i).getId());
				}
				else {
					System.out.println("There are no students registered yet. ");
				}
				System.out.println("Instructor: "+courseList.get(i).getInstructor());
				System.out.println("Section: "+courseList.get(i).getSection());
				System.out.println("Location: "+courseList.get(i).getLocation());
				System.out.println("* * * * * * * * * * * * * * *");
			}
		}
	}

	@Override
	// add student (collect all info from student)
	public void registerStudent() throws IOException {
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\nPlease enter the necessary information below to add a new student. ");
		System.out.print("Enter the new student's first name: ");
		String firstName = read.readLine();
		System.out.print("Enter the new student's last name: ");
		String lastName = read.readLine();
		System.out.print("Enter the new student's login username: ");
		String username = read.readLine();
		System.out.print("Enter the new student's login password: ");
		String password = read.readLine();
		// add to student list 
		Student s = new Student(firstName, lastName, username, password);
		fullstudentList.add(s);
		System.out.println("The new student has been successfully added. ");
	}


	@Override
	// display courses that are full
	public ArrayList<Course> viewFull() {
		ArrayList<Course> fullCourses = new ArrayList<Course>();
		System.out.println("* * * * * * * * * * * * * * *");
		for (int i = 0; i < courseList.size(); i++) {
			if (courseList.get(i).getCurrentStudents() == courseList.get(i).getMaximumStudents()) {
				// prints out the courses within the course list
				System.out.println("Course name: "+courseList.get(i).getName());
				System.out.println("Course ID: "+courseList.get(i).getId());
				System.out.println("Maximum number of students: "+courseList.get(i).getMaximumStudents());
				if (courseList.get(i).getCurrentStudents()!= 0) {
					System.out.println("Number of students registered: "+courseList.get(i).getId());
				}
				else {
					System.out.println("There are no students registered yet. ");
				}
				System.out.println("Instructor: "+courseList.get(i).getInstructor());
				System.out.println("Section: "+courseList.get(i).getSection());
				System.out.println("Location: "+courseList.get(i).getLocation());
				fullCourses.add(courseList.get(i));
				System.out.println("* * * * * * * * * * * * * * *");
			}
		}
		return fullCourses;
	}
	
	// write course to file when course is full
	@Override
	public void writeToFileFullCourses() {
		String fileName = "FullCourses.txt";
		try {
			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write("* * * * * * * * * * * * * * *");
			bufferedWriter.newLine();
			for (int i = 0; i < courseList.size(); i++) {
				if (courseList.get(i).getCurrentStudents() == courseList.get(i).getMaximumStudents()) {
					// prints out the courses within the course list
					bufferedWriter.write("Course name: "+courseList.get(i).getName());
					bufferedWriter.newLine();
					bufferedWriter.write("Course ID: "+courseList.get(i).getId());
					bufferedWriter.newLine();
					bufferedWriter.write("Maximum number of students: "+courseList.get(i).getMaximumStudents());
					bufferedWriter.newLine();
					if (courseList.get(i).getCurrentStudents()!= 0) {
						bufferedWriter.write("Number of students registered: "+courseList.get(i).getId());
						bufferedWriter.newLine();
					}
					else {
						bufferedWriter.write("There are no students registered yet. ");
						bufferedWriter.newLine();
					}
					bufferedWriter.write("Instructor: "+courseList.get(i).getInstructor());
					bufferedWriter.newLine();
					bufferedWriter.write("Section: "+courseList.get(i).getSection());
					bufferedWriter.newLine();
					bufferedWriter.write("Location: "+courseList.get(i).getLocation());
					bufferedWriter.newLine();
					bufferedWriter.write("* * * * * * * * * * * * * * *");
					bufferedWriter.newLine();
					
				}
			}
			bufferedWriter.close(); // close
		}
		catch (IOException exk) {
			System.out.println("Error writing file '" + fileName + "'");
			exk.printStackTrace();
		}
	}

	@Override
	// names of all student in certain course (course section matters)
	public void RegisteredStudents() throws IOException {
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("Enter the course name: ");
		String courseName = read.readLine();
		System.out.print("Enter the course section: ");
		int section = Integer.parseInt(read.readLine());
		System.out.println("\nAll students registered in "+courseName+" section "+section+": ");
		for (int i = 0; i < courseList.size(); i++) {
			if (courseName == courseList.get(i).getName() && section == courseList.get(i).getSection()) {
				for (int j = 0; i < courseList.get(i).getStudentList().size(); j++) {
					System.out.println(" * "+courseList.get(i).getStudentList().get(j).getFirstName() + " " + courseList.get(i).getStudentList().get(j).getLastName());
				}		
			}
		}
	}

	@Override
	// uses first name and last name to find all courses student is in
	public void allStudentCourses() throws IOException {
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("Enter the new student's first name: ");
		String firstName = read.readLine();
		System.out.print("Enter the new student's last name: ");
		String lastName = read.readLine();

		System.out.println("All courses "+firstName+" "+lastName+" is registered in: ");
		for (int i = 0; i < courseList.size(); i++) {
			for (int j = 0; j < courseList.get(i).getStudentList().size(); j++) {
				if (firstName == courseList.get(i).getStudentList().get(j).getFirstName() && lastName == courseList.get(i).getStudentList().get(j).getLastName()) {
					System.out.println(" * "+courseList.get(i).getName());
				}
			}
		}
	}

	@Override
	// ort courses based on the current number of students registered
	public void sortCourses() {
		for (int i = 0; i < courseList.size(); i++) {
			for (int j = courseList.size() - 1; j > i; j--) {
				if (courseList.get(i).getCurrentStudents() > courseList.get(j).getCurrentStudents()) {
					courseList.set(i, courseList.get(j));
					courseList.set(j, courseList.get(i));
				}
			}
		}
		for (int i = 0; i < courseList.size(); i++) {
			// prints out the courses within the course list
			System.out.println("* * * * * * * * * * * * * * *");
			System.out.println("Course name: "+courseList.get(i).getName());
			System.out.println("Course ID: "+courseList.get(i).getId());
			if (courseList.get(i).getCurrentStudents()!= 0) {
				System.out.println("Number of students registered: "+courseList.get(i).getId());
			}
			else {
				System.out.println("There are no students registered yet. ");
			}
			System.out.println("Course ID: "+courseList.get(i).getMaximumStudents());
		}
		System.out.println("* * * * * * * * * * * * * * *");
	}

}