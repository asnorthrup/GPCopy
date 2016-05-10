/**
 * 
 */
package edu.ncsu.csc216.course_manager.courses;

import java.util.ArrayList;

import edu.ncsu.csc216.course_manager.users.Student;
import edu.ncsu.csc216.course_manager.users.User;

/**
 *Course class that implements Enrollable. This class creates a course
 *and sets up the name, credits and capacity for the course. This class
 *also handles enrolling users and dropping users from the course.
 * @author Andrew Northrup
 *
 */
public class Course implements Enrollable {
	/**Name of the course */
	private String name;
	/**Number of credits in a course */
	private int credits;
	/**Course capacity */
	private int capacity;
	/**Min number of credit hours for a course */
	public static final int MIN_HOURS = 1;
	/**Max number of credit hours for a course */
	public static final int MAX_HOURS = 4;
	/** Students enrolled in the course */
	private ArrayList<User> enrolledStudents;

	/**
	 * Creates a Course with the given name and credit hours.
	 * @param name course name
	 * @param credits course credit hours
	 * @param capacity course capacity
	 */
	public Course(String name, int credits, int capacity) {
		super();
		enrolledStudents = new ArrayList<User>();
		setName(name);
		setCredits(credits);
		setCapacity(capacity);
	}
	/**
	 * Getter for getting name of course
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 *Sets name of course if meets requirements of not null or blank
	 * @param name the name to set
	 */
	public void setName(String name) {
		if (name == null || name.equals("")) {
			throw new IllegalArgumentException();
		}
		this.name = name;
	}
	/**
	 * Gets the number of credits a class has
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}
	/**
	 * Sets the number of credits a course has
	 * @param credits the credits to set
	 */
	public void setCredits(int credits) {
		if (credits < MIN_HOURS || credits > MAX_HOURS) {
			throw new IllegalArgumentException();
		}
		this.credits = credits;
	}
	/**
	 * Gets capacity of a course
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}
	/**
	 * Sets capacity of course
	 * @param capacity the capacity to set
	 */
	public void setCapacity(int capacity) {
		if (capacity < 1 || capacity < enrolledStudents.size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}

	/**
	 * HashCode
	 * @return results of hashcode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * Equal to
	 * @param object
	 * @return true or false
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


	@Override
	/**
	 * To string for course name, credits and capacity
	 */
	public String toString() {
		return name + "," + credits + "," + capacity ;
	}
	/**
	 * Gets an array of students enrolled in course
	 * @return array of student enrolled in course
	 */
	@Override
	public Student[] getEnrolledStudents() {
		Student [] s = new Student[enrolledStudents.size()];
		return enrolledStudents.toArray(s);
	}
	/**
	 * Checks to see if a user can be added to course based on course capacity
	 * and student is not already in course
	 * @param User to be added to course
	 * @return true/false
	 */
	@Override
	public boolean canEnroll(User user) {
		if (enrolledStudents.size() < capacity) {
			if (user instanceof Student) {
				Student s = (Student) user;
				for (int i = 0; i < enrolledStudents.size(); i++) {
					if (enrolledStudents.get(i).equals(s)) {
						return false;
					}
				}
				return true;
			}
			return false;
		}
		return false;
	}
	/**
	 * Adds a user to course
	 * @param User to be added to course
	 * @return list of enrolled students after adding student
	 */
	@Override
	public boolean enroll(User user) {
		return canEnroll(user) && enrolledStudents.add(user);
	}
	/**
	 * Drops a user from course
	 * @param User to be dropped from course
	 * @return list of enrolled students after dropping
	 */
	@Override
	public boolean drop(User user) {
		return enrolledStudents.remove(user);
	}
}
