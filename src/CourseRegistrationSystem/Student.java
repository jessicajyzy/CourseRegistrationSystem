package CourseRegistrationSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Student extends User implements Student_Interface, java.io.Serializable {

	public Student(String firstName, String lastName, String username, String password) {
		super(firstName, lastName, username, password);
	}

	@Override
	public void studentViewAll() {
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

	@Override
	public void availableCourses() { 
		System.out.println("* * * * * * * * * * * * * * *");
		for (int i = 0; i < courseList.size(); i++) {
			// prints out the courses within the course list
			if (courseList.get(i).getCurrentStudents() < courseList.get(i).getMaximumStudents()) {
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
	// student added to course
	public void register() throws IOException {
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("Enter the course name: ");
		String courseName = read.readLine();
		int section = 0;
		while (true) {
			try {
				System.out.print("Enter the Section Number: ");
				section = Integer.parseInt(read.readLine());
				if (section > 0) {
					break;
				}
			}
			catch (Exception e) {
				System.out.println("Sorry, your input was invalid. Try again. ");
			}
		}
		String firstName = this.getFirstName();
		String lastName = this.getLastName();
		String username = this.getUsername();
		String password = this.getPassword();

		for (int i = 0; i < courseList.size(); i++) {
			if (courseName.equals(courseList.get(i).getName()) && section == courseList.get(i).getSection()) {
				courseList.get(i).studentList.add(new Student(firstName, lastName, username, password));
			}
		}
	}

	@Override
	// student withdraw from course
	public void withdraw() throws IOException {
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));

		System.out.print("Enter the course name: ");
		String courseName = read.readLine();
		System.out.print("Enter the course section: ");
		String section = read.readLine();
		String firstName = this.getFirstName();
		String lastName = this.getLastName();

		for (int i = 0; i < courseList.size(); i++) {
			if (courseName.equals(courseList.get(i).getName()) && section.equals(courseList.get(i).getSection())) {
				for (int j = 0; j < courseList.get(i).studentList.size(); j++) {
					if (firstName.equals(studentList.get(i).getFirstName()) && lastName.equals(studentList.get(i).getLastName())) {
						courseList.get(i).studentList.remove(j);
						System.out.println("Successfully removed");
					}
				}
			}
		}
	}

	// courses student is in
	public void myCourses() throws IOException {
		String firstName = this.getFirstName();
		String lastName = this.getLastName();

		System.out.println("All courses "+firstName+" "+lastName+" is registered in: ");
		for (int i = 0; i < courseList.size(); i++) {
			for (int j = 0; j < courseList.get(i).studentList.size(); j++) {
				if (firstName.equals(courseList.get(i).getStudentList().get(j).getFirstName()) && lastName.equals(courseList.get(i).getStudentList().get(j).getLastName())) {
					System.out.println(courseList.get(i).getName());
				}
			}
		}
	}
}