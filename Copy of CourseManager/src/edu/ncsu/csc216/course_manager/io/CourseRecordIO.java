/**
 * 
 */
package edu.ncsu.csc216.course_manager.io;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.course_manager.courses.Course;

/**
 * This class interacts with course records in a given file. The class reads in courses
 * and writes back the courses to the file specified. The class throws an error exception
 * if it encounters any errors in processing the files.
 * @author Andrew Northrup
 *
 */
public class CourseRecordIO {
	/**
	 * Reads Course records from the given file.  If the file doesn't exist
	 * a FileNotFoundException is thrown.  A line with a format error will be
	 * ignored.
	 * @param fileName name of file to read
	 * @return Course records
	 * @throws FileNotFoundException if the file doesn't exist
	 */
	public static List<Course> readCourseRecords(String fileName) throws FileNotFoundException {
		List<Course> courses = new ArrayList<Course>();
			
		Scanner fileScanner = new Scanner(new File(fileName));
		while (fileScanner.hasNextLine()) {
			try {
				courses.add(processCourse(fileScanner.nextLine()));
			} catch (IllegalArgumentException e) {
				//if the exception is thrown, ignore the Student line.  
			}
		}
		
		fileScanner.close();
		return courses;
	}
	/**
	 * Reads a line sent from the file scanner read from the Courses file. The
	 * line scanner reads in the string and uses commas as the delimiter to
	 * set up the string to be read by the Course constructor.
	 * @param courseline for the line read in by the scanner
	 * @return Course new Course is created
	 * @throws FileNotFoundException if the the elements read in from the line don't match the constructor for Course
	 */
	private static Course processCourse(String courseLine) {
		Scanner lineScanner = new Scanner(courseLine);
		try {
			lineScanner.useDelimiter(",");
			String name = lineScanner.next();
			int credits = lineScanner.nextInt();
			int capacity = lineScanner.nextInt();
			lineScanner.close();
			return new Course(name, credits, capacity);
		} catch (NoSuchElementException e) {
			lineScanner.close();
			throw new IllegalArgumentException();
		}
	}
	/**
	 * Writes the information about the courses to the given file.
	 * @param fileName file name to record data
	 * @param courses list of courses
	 * @throws IOException if cannot write to file
	 */
	public static void writeCourseRecords(String fileName, List<Course> courses) throws IOException {
		PrintWriter fileOut = new PrintWriter(new FileWriter(fileName));
		
		for (Course c: courses) {
			fileOut.println(c.toString());
		}
		
		fileOut.close();
	}
}