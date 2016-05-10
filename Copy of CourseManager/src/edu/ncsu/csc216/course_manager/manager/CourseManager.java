/**
 * 
 */
package edu.ncsu.csc216.course_manager.manager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc216.course_manager.courses.Course;
import edu.ncsu.csc216.course_manager.io.CourseRecordIO;
import edu.ncsu.csc216.course_manager.io.StudentRecordIO;
import edu.ncsu.csc216.course_manager.users.Student;
import edu.ncsu.csc216.course_manager.users.User;

/**
 * Creates list of students and courses and allows users to log in and modify the
 * courses. Only one user may be logged in at once. Reads the current users classes
 * and writes back any changes to that students class changes or the list of students to the 
 * appropriate file.
 * @author Andrew Northrup
 *
 */
public class CourseManager {
	/** CourseManager singleton instance */
	private static CourseManager manager;
	/** List of all Courses in the system */
	private ArrayList<Course> courses;
	/** List of all Students in the system */
	private ArrayList<Student> students;
	/** Currently logged in User */
	private User currentUser;
	/** Course records file name */
	private String courseFileName;
	/** Student records file name */
	private String studentFileName;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	/**
	 * Constructor for CourseManager.  It's private so that it can
	 * only be created inside of CourseManager and we can ensure
	 * only a single instance of the class is created.  This makes it 
	 * very easy to work with the CourseManager throughout the system.
	 */
	private CourseManager() {
		courses = new ArrayList<Course>();
		students = new ArrayList<Student>();
	}	
	/**
	 * Returns the singleton instance of CourseManager.  If the instance 
	 * doesn't exist, it will be created.
	 * @return singleton instance
	 */
	public static CourseManager getInstance() {
		if (manager == null) {
			manager = new CourseManager();
		}
		return manager;
	}
	/**
	 * Log user into the system if there is no one else logged in.
	 * @param id user's id
	 * @param password user's password
	 * @return true if user is logged in
	 */
	public boolean login(String id, String password) {
		if (currentUser != null) {
			return false;
		}
		for (Student s: students) {
			if (s.getId().equals(id)) {
				try {
					MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
					digest.update(password.getBytes());
					String hashPW = new String(digest.digest());
					if (s.getPassword().equals(hashPW)) {
						currentUser = s;
						return true;
					}
					return false;
				} catch (NoSuchAlgorithmException e) {
					throw new IllegalArgumentException();
				}	
			}
		}
		return false;
	}
	/**
	 * Log current user out of the system.
	 */
	public void logout() {
		currentUser = null;
	}
	/**
	 * Returns the current logged in user or null if there is 
	 * no logged in user.
	 * @return logged in user
	 */
	public User getCurrentUser() {
		return currentUser;
	}
	/**
	 * Returns a list of all Courses associated with the current User.
	 * @return list of User's courses
	 */
	public Course[] listUserCourses() {
		if (currentUser == null) {
			throw new IllegalArgumentException("User is not logged in.");
		}
		return currentUser.getCourses();
	}

	/**
	 * Returns a list of all Courses in the system.
	 * @return list of all Courses
	 */
	public Course[] listAllCourses() {
		Course [] allCourses = new Course[courses.size()];
		return courses.toArray(allCourses);
	}
	/**
	 * Returns true if the Course is added to the current User's
	 * list of courses.
	 * @param course Course to add
	 * @return true if added to the User
	 */
	public boolean addUserToCourse(Course course) {
		if (currentUser == null) {
			throw new IllegalArgumentException("User is not logged in.");
		}
		if (currentUser.canAddCourse(course) && currentUser instanceof Student) {
			Student s = (Student)currentUser;
			if (course.canEnroll(s)) {
				currentUser.addCourse(course);
				course.enroll(s);
				return true;
			}
		}
		return false;
	}
	/**
	 * Returns true if the Course is removed from the current User's
	 * list of courses.
	 * @param course Course to remove
	 * @return true if removed from the User
	 */
	public boolean removeUserFromCourse(Course course) {
		if (currentUser == null) {
			throw new IllegalArgumentException("User is not logged in.");
		}
		course.drop(currentUser);
		return currentUser.removeCourse(course);
	}
	/**
	 * Clears all course and student data from the Course manager 
	 * without saving.
	 */
	public void clearData() {
		courses = new ArrayList<Course>();
		students = new ArrayList<Student>();
		currentUser = null;
		courseFileName = null;
		studentFileName = null;
	}
	/**
	 * Loads the list of Courses from the given file.
	 * @param fileName name of file containing courses
	 */
	public void loadCourses(String fileName) {
		this.courseFileName = fileName;
		try {
			List<Course> coursesFromFile = CourseRecordIO.readCourseRecords(courseFileName);
			for (Course c : coursesFromFile) {
				addCourse(c);
			}
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * Adds a course to the list of courses. 
	 * @param course Course to add
	 */
	public void addCourse(Course course) {
		for (Course c: courses) {
			if (c.equals(course)) {
				return;
			}
		}
		courses.add(course);
	}
	/**
	 * Writes the list of Courses to the courseFileName.
	 */
	public void saveCourses() {
		try {
			CourseRecordIO.writeCourseRecords(courseFileName, courses);
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	/**
	 * Searches the list of courses based on the name of the course and
	 * returns the course object based on passed in name. Returns null if no
	 * courses match the passed in name.
	 * @param courseName name of course passed in to method
	 * @return Course based on name
	 */
	public Course getCourseByName(String courseName) {		
		for(Course c:courses){
			if(c.getName().equalsIgnoreCase(courseName)){
				return c;
			}
		}
		return null;		
	}
	/**
	 * Loads the list of Students from the given file.
	 * @param fileName name of file containing students
	 */
	public void loadStudents(String fileName) {
		this.studentFileName = fileName;
		try {
			List<Student> studentsFromFile = StudentRecordIO.readStudentRecords(studentFileName);
			for (Student s : studentsFromFile) {
				addStudent(s);
			}
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	/**
	 * Adds a student to the list of students. 
	 * @param student Student to add
	 */
	private void addStudent(Student student) {
		for (Student s: students) {
			if (s.getId().equals (student.getId())) {
				return;
			}
		}
		students.add(student);
	}
	/**
	 * Writes the list of Students to the studentFileName.
	 */
	public void saveStudents() {
		try {
			StudentRecordIO.writeStudentRecords(studentFileName, students);
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
}