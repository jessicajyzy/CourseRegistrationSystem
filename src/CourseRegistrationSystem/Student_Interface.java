package CourseRegistrationSystem;

import java.io.IOException;
//blueprint for Student class
public interface Student_Interface {
	public void studentViewAll();
	public void availableCourses();
	public void register() throws IOException;
	public void withdraw() throws IOException;
	public void myCourses()  throws IOException;
}

