package com.studentnetwork.studentnetwork;

import com.studentnetwork.studentnetwork.DAO.CourseDAO;
import com.studentnetwork.studentnetwork.DAO.FaculityDAO;
import com.studentnetwork.studentnetwork.DAO.OrganizatonsDAO;
import com.studentnetwork.studentnetwork.model.Course;
import com.studentnetwork.studentnetwork.model.Faculity;
import com.studentnetwork.studentnetwork.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class StudentnetworkApplication implements CommandLineRunner
{
	@Autowired
	private OrganizatonsDAO organizatonsDAO;
	@Autowired
	private CourseDAO courseDAO;
	@Autowired
	private FaculityDAO faculityDAO;

	private static boolean SHOULD_INIT = false;

	public static void main(String[] args) {
		SpringApplication.run(StudentnetworkApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(SHOULD_INIT){
			Organization org = new Organization();
			org.setName("Ort");
			org.setType(Organization.TYPE_COLLEGE);
			org.setLatitude(0);
			org.setLongitude(0);
			org = organizatonsDAO.addOrganization(org);

			System.err.println("ORG: "+org);
			Faculity faculity = new Faculity();
			faculity.setName("Software engineering");
			faculity.setOrgID(org.getId());
			faculity=faculityDAO.addFaculity(faculity);
			System.err.println("FAC: "+faculity);

			Course course = new Course();
			course.setName("Algebra");
			course.setFaculityID(faculity.getId());
			course = courseDAO.addCourse(course);

			System.err.println("COURSE: "+course);
		}

	}
}
