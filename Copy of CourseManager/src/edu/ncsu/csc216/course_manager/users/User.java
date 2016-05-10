/**
 * 
 */
package edu.ncsu.csc216.course_manager.users;

import edu.ncsu.csc216.course_manager.courses.Course;

/**
 * Creates a super class for users of the CourseManager program. This is a user profile for name, ID, password.
 * This class includes abstract methods for adding courses and removing courses.
 * @author Andrew Northrup
 *
 */
public abstract class User {
	/** User's first name */
	private String firstName;
	/** User's last name */
	private String lastName;
	/** User's id */
	private String id;
	/** User's email */
	private String email;
	/** User's hashed password */
	private String password;

	/**
	 * Creates a new user with the following information. If any of the inputs
	 * are null or an empty string an IllegalArgumentException is thrown. If the
	 * email does not contain one @ and one '.' after the @, an
	 * IllegalArgumentException is thrown. If any other error occurs when
	 * creating a User, an IllegalArgumentException is thrown.
	 * 
	 * @param firstName user's first name
	 * @param lastName user's last name
	 * @param id user's id
	 * @param email user's email
	 * @param password user's password
	 */
	public User(String firstName, String lastName, String id, String email,
			String password) {
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		setEmail(email);
		setPassword(password);
	}

	/**
	 * Gets user's first name
	 * @return firstName of the User
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the user's first name if it is not passed in as null and the length
	 * is greater than 0.
	 * @param firstName the new first name for the user
	 */
	public void setFirstName(String firstName) {
		if (firstName == null || firstName.length() == 0) {
			throw new IllegalArgumentException();
		}
		this.firstName = firstName;
	}

	/**
	 * Gets user's last name
	 * @return lastName of the User
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the user's last name.	 * 
	 * @param lastName the new last name for the user to be set to
	 */
	public void setLastName(String lastName) {
		if (lastName == null || lastName.length() == 0) {
			throw new IllegalArgumentException();
		}
		this.lastName = lastName;
	}

	/**
	 * Gets user's ID String.
	 * @return the id for the student
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the user's id. The method is private because the user's id cannot be
	 * changed once set.
	 * @param id the user's id.
	 */
	private void setId(String id) {
		if (id == null || id.length() == 0) {
			throw new IllegalArgumentException();
		}
		this.id = id;
	}

	/**
	 * Gets user email
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the user's email to the given string.
	 * 
	 * @param email
	 *            new email for the user
	 */
	public void setEmail(String email) {
		if (email == null || email.length() == 0) {
			throw new IllegalArgumentException();
		}
		int atIdx = email.indexOf("@");
		int dotIdx = email.lastIndexOf(".");
		// This is a very naive email checker
		if (atIdx == -1 || dotIdx == -1 || dotIdx <= atIdx) {
			throw new IllegalArgumentException();
		}
		this.email = email;
	}

	/**
	 * Gets user password
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Stores a hashed password. It is the responsibility of the client to hash
	 * the password coming into the system. See
	 * http://docs.oracle.com/javase/7/docs/api/java/security/MessageDigest.html
	 * for details on the MessageDigest object for hashing a password.
	 * 
	 * Security: NEVER store plain text passwords!
	 * 
	 * @param password
	 *            hashed password to save
	 */
	public void setPassword(String password) {
		if (password == null || password.length() == 0) {
			throw new IllegalArgumentException();
		}
		this.password = password;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return firstName + "," + lastName + "," + id + "," + email + "," + password;
	}
	/**
	 * Returns true if the student can add the course to their schedule.
	 * @param c Course to check
	 * @return true if the course can be added
	 */
	public abstract boolean canAddCourse(Course c);

	/**
	 * Adds a course to the user.  Returns false if the course cannot be 
	 * added.
	 * @param c Course to add
	 * @return true if the course is added
	 */
	public abstract boolean addCourse(Course c);

	/**
	 * Removes a course from the user.  Returns false if the course cannot 
	 * be removed.
	 * @param c Course to remove
	 * @return true if the course is remove
	 */
	public abstract boolean removeCourse(Course c);

	/**
	 * Returns a list of courses for the user.
	 * @return user's courses
	 */
	public abstract Course[] getCourses();
	
}
