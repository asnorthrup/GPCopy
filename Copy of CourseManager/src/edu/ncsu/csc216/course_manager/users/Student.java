/**
 * 
 */
package edu.ncsu.csc216.course_manager.users;

import java.util.ArrayList;

import edu.ncsu.csc216.course_manager.courses.Course;

/**
 * Creates a student user of the course manager program.
 * @author Andrew Northrup
 *
 */
public class Student extends User {
	/**List of a student's courses they are enrolled in*/
	private ArrayList<Course> courses;
	/**Max credits particular student may take*/
	private int maxCredits;
	/**Max credits any student may take*/
	public final static int MAX_CREDITS = 18;
	/**
	 * Constructor for creating a student
	 * @param firstName student's first name
	 * @param lastName student's last name
	 * @param id student's id
	 * @param email student's email address
	 * @param password student's password
	 *
	 */
	public Student(String firstName, String lastName, String id, String email,
			String password) {
		//super(firstName, lastName, id, email, password);
		this(firstName, lastName, id, email, password, MAX_CREDITS);
	}
	/**
	 * Constructor for creating a student and an array list of the student's courses
	 * @param firstName students first name
	 * @param lastName students last name
	 * @param id students id
	 * @param email students email
	 * @param password students password
	 * @param maxCredits for specific student
	 */
	public Student(String firstName, String lastName, String id, String email,
			String password, int maxCredits) {
		super(firstName, lastName, id, email, password);
		courses = new ArrayList<Course>();
		setMaxCredits(maxCredits);
		
	}


	/**
	 * Determines if the student can add the course that is passed in based on maxCredits and their current
	 * course list. Cannot add if already in class or adding class will exceed maxHours.
	 * @param course object for a course that will be checked against whether the student can add it
	 * @return true or false based on whether a student can add the course
	 */
	@Override
	public boolean canAddCourse(Course c) {
		if (c.getCredits() + getCurrentCredits() > maxCredits || courses.contains(c)) {
			return false;
		}
		return true;
	}
	/**Method returns the number of credits a Student 
	 * added to their courses list
	 * @return current number of credits a student is currently in course list.
	 */
	public int getCurrentCredits() {
		int currentCredits = 0;
		for (int i = 0; i < courses.size(); i++) {
			currentCredits = currentCredits + courses.get(i).getCredits();		
		}
		return currentCredits;
	}


	/**Returns true if course is added and false if it is not. Calls 
	 * canAddCourse to determine if student can add the course.
	 * @param course object that student is trying to add
	 * @return true if course was added, false if it was not
	 */
	@Override
	public boolean addCourse(Course c) {
		if (canAddCourse(c)) {
			courses.add(c);
			return true;
		}
		return false;
	}

	/**Returns true if course is removed and false if it is not. Calls 
	 * contains to determine if student can remove the course, as it is in their
	 * course list.
	 * @param course object that student is trying to remove
	 * @return true if course was removed, false if it was not
	 */
	@Override
	public boolean removeCourse(Course c) {
		if (courses.contains(c)) {
			courses.remove(c);
			return true;
		}	
		return false;
	}


	/**Gets list of courses and returns them as array.
	 *@return array of courses the student is in
	 */
	@Override
	public Course[] getCourses() {
		// TODO Auto-generated method stub
	//	Course[] courseList = new Course[courses.size()];
	//	for (int i = 0; i < courses.size(); i++) {
	//		courseList[i] = courses.get(i);		
	//	}
	//	return courseList;
		Course [] c = new Course[courses.size()];
		return courses.toArray(c);
	}

	/**
	 * Gets student's maxCredits allowed to enroll in
	 * @return maxCredits that the particular student object is allowed to enroll in
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * Sets student's maxCredits allowed to enroll in. Determines if max credits is less than current credits or
	 * if maxCredits exceeds all student maximum credit hours or if negative and throws illegal argument
	 * if any of those are met
	 * @param maxCredits the maxCredits to set
	 * @throws IllegalArgumentException	 if max credits is less than current credits or
	 * if maxCredits exceeds all student maximum credit hours or if negative number is passed in for maxCredits* 
	 */
	public void setMaxCredits(int maxCredits) {
		if (maxCredits > MAX_CREDITS || maxCredits < 0 || maxCredits < getCurrentCredits()) {
			throw new IllegalArgumentException();
		}
		this.maxCredits = maxCredits;
	}

	/**
	 * Hashcode auto-generated
	 * @returns result of hashcode 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((courses == null) ? 0 : courses.hashCode());
		result = prime * result + maxCredits;
		return result;
	}

	/**
	 * Equal to method
	 * @param object passed in to compare against
	 * @return true or false depending on whether the objects are equal as specified
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (courses == null) {
			if (other.courses != null)
				return false;
		} else if (!courses.equals(other.courses))
			return false;
		if (maxCredits != other.maxCredits)
			return false;
		return true;
	}

	/**
	 * toString of the student which includes course names, max credits, and user to string
	 * @return student to String as "User to string, maximum credits a student can take, list of 
	 * student courses separated by a comma"
	 */
	@Override
	public String toString() {
		String studentCourses = "";
		for (int i = 0; i < courses.size(); i++) {
			  studentCourses = studentCourses + "," + courses.get(i).getName();
			}
		return super.toString() + "," + maxCredits + studentCourses;
	}

}
