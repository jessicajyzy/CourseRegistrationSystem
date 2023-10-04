package CourseRegistrationSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;

public class School {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		File filename = new File("CourseData.ser");
		// not first time running program
		if (filename.exists()) {
			Course.deserialize();
			// References one line at a time
			String line = null;
			try {
				// get user credentials
				FileReader fileReader = new FileReader("info.txt");
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				while ((line = bufferedReader.readLine()) != null) {
					String[] splited = line.split(",");
					String firstName = splited[0];
					String lastName = splited[1];
					String username = splited[2];
					String password = splited[3];
					Admin.fullstudentList.add(new Student(firstName, lastName, username,password));
				}
				bufferedReader.close();
			}
			catch (FileNotFoundException ex) {
				System.out.println("Unable to open file '" + "info.txt" + "'");
				ex.printStackTrace();
			}
			catch (IOException ex) {
				System.out.println("Error reading file '" + "info.txt" + "'");
				ex.printStackTrace();
			}
		}
		// first time running program
		else {
			filename = new File("MyUniversityCoursesFile.csv");
			try {
				FileReader fileReader = new FileReader(filename);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				bufferedReader.close();
			}
			catch (FileNotFoundException ex) {
				System.out.println("Unable to open file '" + filename + "'");
				ex.printStackTrace();
			}
			catch (IOException ex) {
				System.out.println("Error reading file '" + filename + "'");
				ex.printStackTrace();
			}
			// tokenize the csv file by removing unnecessary tokens
			StringTokenizer tokenize = new StringTokenizer(new Scanner(filename).useDelimiter("\\A").next(), ",\n");
			int count = 0;
			while (tokenize.hasMoreTokens()) {
				if (count > 7) {
					String courseName = tokenize.nextToken();
					String courseID = tokenize.nextToken();
					String trimmedTest = tokenize.nextToken().replace(" ", "");
					int maxStudents = Integer.parseInt(trimmedTest);
					String trimmedTest1 = tokenize.nextToken().replace(" ", "");
					int currentStudents = Integer.parseInt(trimmedTest1);
					tokenize.nextToken();
					String instructorName = tokenize.nextToken();
					String trimmedTest2 = tokenize.nextToken().replace(" ", "");
					int courseSection = Integer.parseInt(trimmedTest2);
					String courseLocation = tokenize.nextToken();
					// creates a course list from the elements found
					Course.courseList.add(new Course(courseName, courseID, maxStudents, currentStudents, instructorName, courseSection, courseLocation));
					count++;
				} else {
					tokenize.nextToken();
					count++;
				}
			}
		} 
		// program starts here!!!
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Welcome to the course selection system. Please choose from one of the following options below. ");
		System.out.println("1) login as Admin\n2) login as Student\n3) Exit");
		System.out.print("\nEnter your selection: ");
		String selection = read.readLine();

    // error message to validate input
		while (!selection.contentEquals("1") && !selection.contentEquals("2") && !selection.contentEquals("3")) {
			System.out.println("Your selection is invalid. Try again.");
			System.out.println("\nWelcome to the course selection system. Please choose from one of the following options below. ");
			System.out.println("1) login as Admin\n2) login as Student\n3) Exit");
			System.out.print("\nEnter your selection: ");
			selection = read.readLine();
		}

		if (selection.contentEquals("1")) {
			Admin admin = new Admin();
			admin.setUsername("Admin");
			admin.setPassword("Admin001");
			System.out.print("Enter the Admin username: ");
			String userInput = read.readLine();
			System.out.print("Enter the Admin password: ");
			String passInput = read.readLine();
      // keep user trapper until credentials pass
			while (!userInput.equals(admin.getUsername()) || !passInput.equals(admin.getPassword())) {
				System.out.println("Incorrect credentials. Please try again. ");
				System.out.print("\nEnter the Admin username: ");
				userInput = read.readLine();
				System.out.print("Enter the Admin password: ");
				passInput = read.readLine();
			}
			System.out.println("\nLog in success.");
			while (true) {
				System.out.println("\nPlease select from the following options: ");
				System.out.println("1) Manage Courses\n2) View Reports\n3) Exit");
				System.out.print("\nEnter your selection: ");
				String selection_admin = read.readLine();
				if (selection_admin.contentEquals("1")) {
	
					System.out.println("\nWelcome to Course Management. Please select from the following options: ");
					System.out.println("1) Create a New Course\n2) Delete a Course\n3) Edit a Course\n4) Display Information for a Given Course\n5) Register a Student\n6) Exit");
					System.out.print("\nEnter your selection: ");
					String admin_manage = read.readLine();
					// methods from admin
					if (admin_manage.contentEquals("1")) {
						admin.newCourse();
					} 
					else if (admin_manage.contentEquals("2")) {
						admin.deleteCourse();
					} 
					else if (admin_manage.contentEquals("3")) {
						admin.editCourse();
					} 
					else if (admin_manage.contentEquals("4")) {
						System.out.print("\nEnter the course ID: ");
						String id = read.readLine();
						admin.courseInfo(id);
					} 
					else if (admin_manage.contentEquals("5")) {
						admin.registerStudent();
					} 
					else if (admin_manage.contentEquals("6")) {
						System.out.println("Exiting . . .");
						Course.serialize();
						try {
							BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("info.txt"));
							for (int i = 0; i < Admin.fullstudentList.size(); i++) {
								bufferedWriter.write(Admin.fullstudentList.get(i).getFirstName() + ",");
								bufferedWriter.write(Admin.fullstudentList.get(i).getLastName() + ",");
								bufferedWriter.write(Admin.fullstudentList.get(i).getUsername() + ",");
								bufferedWriter.write(Admin.fullstudentList.get(i).getPassword() + "\n");
							}
							bufferedWriter.close();
						}
						catch (IOException exk) {
							System.out.println("Error writing file '" + "info.txt" + "'");
							exk.printStackTrace();
						}
						System.exit(0);
					}
				} 
				//reort viewing
				else if (selection_admin.contentEquals("2")) {
	
					System.out.println("\nWelcome to Report Viewing System. Please select from the following options: ");
					System.out.println("1) View All Courses\n2) View All Full Courses\n3) Write to File All Full Courses\n4) View Registered Students of Specific Course\n5) View All Registered Courses of Specific Student\n6) Sort Courses\n7) to Exit");
					System.out.print("Enter your selection: ");
					String admin_view  = read.readLine();
					//admin methods
					if (admin_view.contentEquals("1")) {
						admin.courseInfo();
					} else if (admin_view.contentEquals("2")) {
						admin.viewFull();
					} else if (admin_view.contentEquals("3")) {
						admin.writeToFileFullCourses();
					} else if (admin_view.contentEquals("4")) {
						admin.RegisteredStudents();
					} else if (admin_view.contentEquals("5")) {
						admin.allStudentCourses();
					} else if (admin_view.contentEquals("6")) {
						admin.sortCourses();
					} else {
						System.out.println("Exiting . . .");
						Course.serialize();
						try {
							BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("info.txt"));
							for (int i = 0; i < Admin.fullstudentList.size(); i++) {
								bufferedWriter.write(Admin.fullstudentList.get(i).getFirstName() + ",");
								bufferedWriter.write(Admin.fullstudentList.get(i).getLastName() + ",");
								bufferedWriter.write(Admin.fullstudentList.get(i).getUsername() + ",");
								bufferedWriter.write(Admin.fullstudentList.get(i).getPassword() + "\n");
							}
							// Always close writer
							bufferedWriter.close();
						}
						// Always close files
						catch (IOException exk) {
							System.out.println("Error writing file '" + "info.txt" + "'");
							exk.printStackTrace();
						}
					}
				} 
				else {
					System.out.println("Exiting . . .");
					Course.serialize();
					try {
						BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("info.txt"));
	
						for (int i = 0; i < Admin.fullstudentList.size(); i++) {
							bufferedWriter.write(Admin.fullstudentList.get(i).getFirstName() + ",");
							bufferedWriter.write(Admin.fullstudentList.get(i).getLastName() + ",");
							bufferedWriter.write(Admin.fullstudentList.get(i).getUsername() + ",");
							bufferedWriter.write(Admin.fullstudentList.get(i).getPassword() + "\n");
						}
	
						bufferedWriter.close();
					}
					catch (IOException exk) {
						System.out.println("Error writing file '" + "info.txt" + "'");
						exk.printStackTrace();
					}
					System.exit(0);
				}
			} 
		}
		//student log in
		else if (selection.contentEquals("2")) {
			//no students created yet
			if (Admin.fullstudentList.isEmpty()) {
				System.out.println("There are no students yet. Try again and login as administrator to add students to the system first.");
				System.exit(0);
			}
			else {
				System.out.print("Please enter your username: ");
				String username = read.readLine();
				System.out.print("Please enter your password: ");
				String password = read.readLine();
				boolean find = false;
				for (int i = 0; i < Admin.fullstudentList.size(); i++) {
					if (username.equals(Admin.fullstudentList.get(i).getUsername()) && password.equals(Admin.fullstudentList.get(i).getPassword())){
						find = true;
						break;
					}
				}
				if (find == false) {
					System.out.println("Login invalid. Please check your credentials or contact administration. ");
					System.exit(0);
				}
				else {
					System.out.println("\nLogin success. ");	
				}
				while (true) {
						for (int i = 0; i < Admin.fullstudentList.size(); i++) {
							if (username.contentEquals(Admin.fullstudentList.get(i).getUsername()) && password.contentEquals(Admin.fullstudentList.get(i).getPassword())){
								Student student = new Student(Admin.fullstudentList.get(i).getFirstName(), Admin.fullstudentList.get(i).getLastName(), username, password);
								System.out.println("\nWelcome to the Student Course Selection System. Please choose from the following options: ");
								System.out.println("1) View All Courses\n2) View All Available Courses\n3) Register to a Course\n4) Withdraw from a Course\n5) View All Registered Courses\n6) Exit");
								System.out.print("\nEnter your selection: ");
								String student_selection = read.readLine();
								//student methods
								if (student_selection.contentEquals("1")) {
									student.studentViewAll();
								} else if (student_selection.contentEquals("2")) {
									student.availableCourses();
								} else if (student_selection.contentEquals("3")) {
									student.register();
								} else if (student_selection.contentEquals("4")) {
									student.withdraw();
								} else if (student_selection.contentEquals("5")) {
									student.myCourses();
								} else if (student_selection.contentEquals("6")) {
									System.out.println("Exiting . . .");
									Course.serialize();
									try {
										BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("info.txt"));
										for (int j = 0; j < Admin.fullstudentList.size(); j++) {
											bufferedWriter.write(Admin.fullstudentList.get(i).getFirstName() + ",");
											bufferedWriter.write(Admin.fullstudentList.get(i).getLastName() + ",");
											bufferedWriter.write(Admin.fullstudentList.get(i).getUsername() + ",");
											bufferedWriter.write(Admin.fullstudentList.get(i).getPassword() + "\n");
										}
										bufferedWriter.close();
									}
									catch (IOException exk) {
										System.out.println("Error writing file '" + "info.txt" + "'");
										exk.printStackTrace();
									}
									System.exit(0);
								}
							}
						}
					}
			}

		} 
		// not admin or student
		else if (selection.contentEquals("3")) {
		System.out.println("Exiting . . .");
		Course.serialize();
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("info.txt"));
			for (int i = 0; i < Admin.fullstudentList.size(); i++) {
				bufferedWriter.write(Admin.fullstudentList.get(i).getFirstName() + ",");
				bufferedWriter.write(Admin.fullstudentList.get(i).getLastName() + ",");
				bufferedWriter.write(Admin.fullstudentList.get(i).getUsername() + ",");
				bufferedWriter.write(Admin.fullstudentList.get(i).getPassword() + "\n");
			}
			bufferedWriter.close();
		}
		catch (IOException exk) {
			System.out.println("Error writing file '" + "info.txt" + "'");
			exk.printStackTrace();
			}
		System.exit(0);
		}
	}
}
