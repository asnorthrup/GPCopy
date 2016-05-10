/**
 * 
 */
package edu.ncsu.csc216.course_manager.io;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;




//probably should have any courses in here
import edu.ncsu.csc216.course_manager.courses.Course;
import edu.ncsu.csc216.course_manager.manager.CourseManager;
import edu.ncsu.csc216.course_manager.users.Student;

/**
 * This class interacts with student records in a given file. The class reads in students
 * and writes back the students to the file specified. The class throws an error exception
 * if it encounters any errors in processing the files.
 * @author Andrew Northrup
 *
 */
public class StudentRecordIO {
	/**
	 * Reads Student records from the given file.  If the file doesn't exist
	 * a FileNotFoundException is thrown.  A line with a format error will be
	 * ignored.
	 * @param fileName name of file to read student records out of
	 * @return Student a student object based on file contents
	 * @throws FileNotFoundException if the file doesn't exist
	 */
	public static List<Student> readStudentRecords(String fileName) throws FileNotFoundException {
		List<Student> student = new ArrayList<Student>();
			
		Scanner fileScanner = new Scanner(new FileInputStream(fileName));
		while (fileScanner.hasNextLine()) {
			try {
				student.add(processStudent(fileScanner.nextLine()));
			} catch (IllegalArgumentException e) {
				//if the exception is thrown, ignore the Student line.  
			}
		}
		
		fileScanner.close();
		return student;
	}
	/**
	 * Reads a line sent from the file scanner read from the Student file. The
	 * line scanner reads in the string and uses commas as the delimiter to
	 * set up the string to be read by the Student constructor. This method also enrolls
	 * a student in the course if they get a true when calling the canAddCourse method. 
	 * @param studentLine for the single line read in by the scanner
	 * @return Student new student is created
	 * @throws FileNotFoundException if the the elements read in from the line don't match the constructor for student
	 */
	private static Student processStudent(String studentLine) {
		Scanner lineScanner = new Scanner(studentLine);
		try {
			lineScanner.useDelimiter(",");
			String firstname = lineScanner.next();
			String lastname = lineScanner.next();
			String id = lineScanner.next();
			String email = lineScanner.next();
			String password = lineScanner.next();
			Student student = new Student(firstname, lastname, id, email, password);
			student.setMaxCredits(lineScanner.nextInt());
			//Student is created and max credits are set, not read in while has next for course list of student
			while(lineScanner.hasNext()){
				Course c = CourseManager.getInstance().getCourseByName(lineScanner.next());
				if (c == null){
					lineScanner.close();
					throw new IllegalArgumentException();
				}
				if (student.canAddCourse(c)){
					student.addCourse(c);
					c.enroll(student);
				}
				else{
					lineScanner.close();
					throw new IllegalArgumentException();
				}
			}
			lineScanner.close();
			return student;
		} catch (NoSuchElementException e) {
			lineScanner.close();
			throw new IllegalArgumentException();
		}
	}
	/**
	 * Writes the information about the students to the given file.
	 * @param fileName file name to record data
	 * @param student list of students
	 * @throws IOException if cannot write to file
	 */
	public static void writeStudentRecords(String fileName, List<Student> student) throws IOException {
		PrintWriter fileOut = new PrintWriter(new FileWriter(fileName));
		
		for (Student s: student) {
			fileOut.println(s.toString());
		}
		
		fileOut.close();
	}
}
