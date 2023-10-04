package CourseRegistrationSystem;

import java.io.IOException;
import java.util.ArrayList;
//blueprint for Admin class
public interface Admin_Interface {
	public void newCourse() throws IOException;
	public void deleteCourse() throws IOException;
	public void editCourse() throws IOException;
	public void courseInfo();
	public void courseInfo(String id) throws IOException;//
	public void registerStudent() throws IOException;
	public ArrayList<Course> viewFull();
	public void RegisteredStudents() throws IOException;
	public void allStudentCourses() throws IOException;
	public void sortCourses();
	public void writeToFileFullCourses();
}