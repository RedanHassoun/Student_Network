package com.studentnetwork.studentnetwork.constants;

import com.studentnetwork.studentnetwork.constants.AppConstants.Scheme.CourseAboutTable;
import com.studentnetwork.studentnetwork.constants.AppConstants.Scheme.CourseMaterialTable;
import com.studentnetwork.studentnetwork.constants.AppConstants.Scheme.CourseTable;
import com.studentnetwork.studentnetwork.constants.AppConstants.Scheme.FacebookUsersTable;
import com.studentnetwork.studentnetwork.constants.AppConstants.Scheme.FaculityTable;
import com.studentnetwork.studentnetwork.constants.AppConstants.Scheme.GlobalFeedTable;
import com.studentnetwork.studentnetwork.constants.AppConstants.Scheme.GoogleUsersTable;
import com.studentnetwork.studentnetwork.constants.AppConstants.Scheme.OrganizationsTable;
import com.studentnetwork.studentnetwork.constants.AppConstants.Scheme.TutorLikesTable;
import com.studentnetwork.studentnetwork.constants.AppConstants.Scheme.TutorRequestsTable;
import com.studentnetwork.studentnetwork.constants.AppConstants.Scheme.TutorTable;
import com.studentnetwork.studentnetwork.constants.AppConstants.Scheme.TutoredCoursesTable;
import com.studentnetwork.studentnetwork.constants.AppConstants.Scheme.TutoredStudentsTable;
import com.studentnetwork.studentnetwork.constants.AppConstants.Scheme.UsersTable;

public class AppConstants 
{
	private AppConstants(){}
	
	public enum UserDatabaseStatus
	{
		USER_NOT_EXIST,USER_OK,WRONG_PASSWORD
	}
	
	public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/databs1";
	public static final String DATABASE_USERNAME = "root";
	public static final String DATABASE_PASSWROD = "123";
	public static final String URL_FIREBASE_SINGLE_NOTIFICATION = "https://fcm.googleapis.com/fcm/send";
	public static final String SERVER_KEY_FIREBASE = "AIzaSyB7l-h1bqEeXXmnkFW8Nb-hePejcAQh8-g";
	public static final String FIREBASE_AUTHORIZATION_KEY = "Authorization";
	
	public static final class GeneralStrings{
		/* TODO - strings for notifications should be in the application's string resource file */
		public static final String REQUESTED_TUTORING_ON = " Requested tutoring on ";
		public static final String ACCEPTED_YOUR_TUTOR_REQUEST_ON = "Accepted your tutor request on ";
		public static final String REJECTED_YOUR_TUTOR_REQUEST_ON = "Rejected your tutor request on ";
	}
	
	public static final class ConnectionConstants
	{
		/* Register */
        public static final String REGISTER_STATUS_EXIST = "err_user_already_exist";
        public static final String REGISTER_UNKNOWN_ERROR = "err_cant_register_unknown";
        public static final String REGISTER_STATIS_OK = "register_ok";
        public static final String REGISTER_TYPE_REGULAR = "register regualar";
        public static final String REGISTER_TYPE_GOOGLE = "register google";

        /* Login */
        public static final String LOGIN_OK = "login ok";
        public static final String LOGIN_USER_NOT_EXIST = "login user not exist";
        public static final String LOGIN_WRONG_PASSWORD = "login wrong password"; 
        public static final String USER_EXIST_IN_FACEBOOK = "user exists in facebook";
        public static final String USER_EXIST_IN_GOOGLE = "user exists in google";
        public static final String USER_NOT_EXIST1 = "user not exists";  
		/* Status */
		public static final String STATUS_OK = "status ok";
		public static final String STATUS_NOT_EXIST = "status user not exist";
		public static final String STATUS_WRONG_PASSWORD = "status wrong password";
		public static final String STATUS_NOT_OK = "status not ok";  
		public static final String STATUS_ALREADY_PENDING = "status_already_pending";
		public static final String EDIT_COMPLETED_SUCCESSFULLY = "edit_completed_successfully";
		public static final String EDIT_ERROR = "edit_error";
		public static final String GET_ORG_FRIENDS_COMPLETED_SUCCESFULLY = "get_org_friends_completed_succesfully";
		public static final String GET_ORG_FRIENDS_FAILD = "get_org_friends_faild";
		
		/* Posts */
		public static final int NUM_POSTS_TO_GET = 15;
		
		/* Tutors */
		public static final int REQUEST_PENDING = 0;
		public static final int REQUEST_ACCEPTED = 1;
		public static final int REQUEST_DECLINED = 2;	
		public static final String JSON_KEY_TUTOR_ID = "tutor_id";
		public static final String JSON_KEY_COURSE_NAME = "course_name";
		public static final String JSON_KEY_TARGET_USER_ID = "target_user_id";
		public static final String JSON_KEY_IS_ACCEPTED = "is_accepted";
		public static final String ACCEPTED_YES = "yes";
		public static final String ACCEPTED_NO = "no";
	}
	
	public static final class Scheme
	{
		public static final class UsersTable
		{
			public static final String TABLE_NAME = "users";
			public static final String COLUMN_NAME_USER_ID = "user_id";
			public static final String COLUMN_NAME_NAME = "userName";
			public static final String COLUMN_NAME_EMAIL = "userEmail";
			public static final String COLUMN_NAME_PASSWORD = "userPassword";
			public static final String COLUMN_NAME_ORGANIZATION_ID = "org_id";
			public static final String COLUMN_NAME_FULL_NAME = "full_name";
			public static final String COLUMN_NAME_ABOUT = "about";
			public static final String COLUMN_NAME_IMAGE_URL = "image_url";
			public static final String COLUMN_NAME_FACULITY_ID = "faculty_id";
			public static final String COLUMN_NAME_REGISTER_TYPE = "register_type";
			public static final String COLUMN_NAME_FIREBASE_TOKEN = "firebase_token";
			
		}
		
		public static final class GoogleUsersTable
		{
			public static final String TABLE_NAME = "google_user";
			public static final String COLUMN_NAME_ID = "id";
			public static final String COLUMN_NAME_IMAGE_URL = "image_url";
			public static final String COLUMN_NAME_USER_ID = "Users_userID";
			public static final String COLUMN_NAME_GOOGLE_ID = "google_id";
		}
		
		public static final class FacebookUsersTable
		{
			public static final String TABLE_NAME = "facebook_user";
			public static final String COLUMN_NAME_ID = "id";
			public static final String COLUMN_NAME_USER_ID = "Users_userID";
			public static final String COLUMN_NAME_FACEBOOK_ID = "facebook_id";
		}
		
		public static final class OrganizationsTable
		{
			public static final String TABLE_NAME = "organization";
			public static final String COUMN_NAME_NAME = "org_name";
			public static final String COLUMN_NAME_ID = "org_id";
			public static final String COLUMN_NAME_TYPE = "org_type";
			public static final String COLUMN_NAME_LONGITUDE = "org_longitude";
			public static final String COLUMN_NAME_LATITUDE = "org_latitude";
		}
		
		public static final class FaculityTable
		{
			public static final String TABLE_NAME = "faculty";
			public static final String COLUMN_NAME_ID = "id";
			public static final String COLUMN_NAME_NAME = "name";
			public static final String COLUMN_NAME_ORG_ID = "organization_org_id";
		}
		
		public static final class CourseTable
		{
			public static final String TABLE_NAME = "course";
			public static final String COLUMN_NAME_ID = "courseID";
			public static final String COLUMN_NAME_NAME = "courseName";
			public static final String COLUMN_NAME_FACULITY_ID = "faculty_id";
		}
		
		
		public static final class CourseAboutTable
		{
			public static final String TABLE_NAME = "course_about";
			public static final String COLUMN_NAME_ID = "id";
			public static final String COLUMN_NAME_POINTS = "points";
			public static final String COLUMN_NAME_DESCRIPTION = "description";
			public static final String COLUMN_NAME_TEACHER_NAME = "teacher_name";
			public static final String COLUMN_NAME_COURSE_ID = "Course_courseID";
		}
		
		public static final class CourseMaterialTable
		{
			public static final String TABLE_NAME = "course_material";
			public static final String COLUMN_NAME_ID = "id";
			public static final String COLUMN_NAME_TITLE = "title";
			public static final String COLUMN_NAME_IMAGE_URL = "image_url";
			public static final String COLUMN_NAME_COURSE_ID = "Course_courseID";
			public static final String COLUMN_NAME_EXTERNAL_LINK = "external_link";
		}
		
		public static final class GlobalFeedTable
		{
			public static final String TABLE_NAME = "post";
			public static final String COLUMN_NAME_ID = "id";
			public static final String COLUMN_NAME_USER_ID = "user_id";
			public static final String COLUMN_NAME_POST_IMAGE_URL = "post_image_url";
			public static final String COLUMN_NAME_STATUS = "status";
			public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
			public static final String COLUMN_NAME_EXTERNAL_URL = "external_url";
			public static final String COLUMN_NAME_ORG_ID = "organization_org_id";
			
		}
		
		public static final class TutorTable
		{
			public static final String TABLE_NAME = "tutor";
			public static final String COLUMN_NAME_ID = "tutor_id";
			public static final String COLUMN_NAME_NUM_LIKES = "num_likes";
			public static final String COLUMN_NAME_COMMUNICATION_STR = "communication_str";
			public static final String COLUMN_NAME_ABOUT_TUTOR = "about_tutor";
			public static final String COLUMN_NAME_DATE_STARTED = "date_started";
		}
		
		public static final class TutoredStudentsTable
		{
			public static final String TABLE_NAME = "tutored_student";
			public static final String COLUMN_NAME_STUDENT_ID = "student_id";
			public static final String COLUMN_NAME_TUTOR_ID = "tutor_id";
			public static final String COLUMN_NAME_COURSE_ID = "course_id";
			public static final String COLUMN_NAME_DATE_STARTED = "date_started";
		}
		
		public static final class TutoredCoursesTable
		{
			public static final String TABLE_NAME = "tutored_courses";
			public static final String COLUMN_NAME_TUTOR_ID = "tutor_id";
			public static final String COLUMN_NAME_COURSE_ID = "course_id";
		}
		
		public static final class TutorRequestsTable
		{
			public static final String TABLE_NAME = "tutor_requests";
			public static final String COLUMN_NAME_TUTOR_ID = "tutor_id";
			public static final String COLUMN_NAME_STUDENT_ID = "student_id";
			public static final String COLUMN_NAME_COURSE_ID = "course_id";
			public static final String COLUMN_NAME_STATUS = "status";
			public static final String COLUMN_NAME_TIME = "time";
		}
		
		public static final class TutorLikesTable
		{
			public static final String TABLE_NAME = "tutor_likes";
			public static final String COLUMN_NAME_USER_ID = "user_id";
			public static final String COLUMN_NAME_TUTOR_ID = "tutor_id";
		}
	}
	
	public static final class SqlQuery
	{
		public static final String SQL_UPDATE_USER_IMAGE = "UPDATE "+UsersTable.TABLE_NAME+
														    " SET "+UsersTable.COLUMN_NAME_IMAGE_URL+" = ? "+
															"WHERE "+UsersTable.COLUMN_NAME_USER_ID+" = ? ";
		
		public static final String SQL_GET_USER_WHERE_MAIL = "SELECT * FROM "+UsersTable.TABLE_NAME+
															" WHERE "+UsersTable.COLUMN_NAME_EMAIL+" = ?";
		public static final String SQL_INSERT_USER = "INSERT INTO "+UsersTable.TABLE_NAME+
				" ("+UsersTable.COLUMN_NAME_NAME+", "+UsersTable.COLUMN_NAME_EMAIL+", "+UsersTable.COLUMN_NAME_PASSWORD+
				", "+UsersTable.COLUMN_NAME_ORGANIZATION_ID+", "+UsersTable.COLUMN_NAME_FULL_NAME+", "+UsersTable.COLUMN_NAME_ABOUT+
				", "+UsersTable.COLUMN_NAME_IMAGE_URL+", "+UsersTable.COLUMN_NAME_FACULITY_ID+ 
				") VALUES (?, ?, ?, ?, ?,?, ?,?)";
		
		public static final String SQL_INSER_TO_GOOGLE = "INSERT INTO "+GoogleUsersTable.TABLE_NAME+
				" ("+GoogleUsersTable.COLUMN_NAME_IMAGE_URL+", "+GoogleUsersTable.COLUMN_NAME_USER_ID+
				", "+GoogleUsersTable.COLUMN_NAME_GOOGLE_ID+
				") VALUES (?, ? , ?)";
		
		public static final String SQL_INSER_TO_FACEBOOK = "INSERT INTO "+FacebookUsersTable.TABLE_NAME+
				" ("+FacebookUsersTable.COLUMN_NAME_USER_ID+
				", "+FacebookUsersTable.COLUMN_NAME_FACEBOOK_ID+
				") VALUES (?, ? )";
		
		public static final String SQL_GET_ALL_USERS = "SELECT * FROM "+UsersTable.TABLE_NAME;
		public static final String SQL_GET_ALL_USERS_BY_ORG = "SELECT * FROM "+UsersTable.TABLE_NAME+" where "+UsersTable.COLUMN_NAME_ORGANIZATION_ID+" = ";
		public static final String SQL_DELETE_USER = "delete from "+UsersTable.TABLE_NAME+" where "+UsersTable.COLUMN_NAME_EMAIL+"=";
		
		public static final String SQL_UPDATE_USER = "UPDATE "+UsersTable.TABLE_NAME+" set "+ 
						UsersTable.COLUMN_NAME_PASSWORD+" = ?, "+  UsersTable.COLUMN_NAME_FULL_NAME+" = ? ,"+
						UsersTable.COLUMN_NAME_ABOUT+" = ? ,"+  UsersTable.COLUMN_NAME_NAME+" = ?  "+
						" where "+UsersTable.COLUMN_NAME_USER_ID+" = ?";
		
		public static final String SQL_GET_USER_BY_ID = "select * from "+UsersTable.TABLE_NAME+" where "+UsersTable.COLUMN_NAME_USER_ID+"= ";
		
		
		public static final String SQL_ORGANIZATION_GET_ALL = "SELECT * FROM "+OrganizationsTable.TABLE_NAME;

		public static final String SQL_GET_ORGANIZATION_BY_NAME = "select * from "+OrganizationsTable.TABLE_NAME+" where "+OrganizationsTable.COUMN_NAME_NAME+"= '";

		public static final String SQL_INSERT_ORGANIZATION = "INSERT INTO "+OrganizationsTable.TABLE_NAME+
				" ("+OrganizationsTable.COUMN_NAME_NAME+", "+
				     OrganizationsTable.COLUMN_NAME_TYPE+", "+
				     OrganizationsTable.COLUMN_NAME_LONGITUDE+", "+
				     OrganizationsTable.COLUMN_NAME_LATITUDE+
				") VALUES (?, ? ,? , ?)";


		/* Posts */
		public static final String SQL_SELECT_POSTS_FROM_INDEX =  "SELECT * FROM "+GlobalFeedTable.TABLE_NAME+
				" WHERE "+GlobalFeedTable.COLUMN_NAME_ORG_ID+" = ";
		
		
		public static final String SQL_INSERT_POST = "INSERT INTO "+GlobalFeedTable.TABLE_NAME+
				" ("+GlobalFeedTable.COLUMN_NAME_USER_ID+", "+GlobalFeedTable.COLUMN_NAME_POST_IMAGE_URL+
				", "+GlobalFeedTable.COLUMN_NAME_STATUS+", "+GlobalFeedTable.COLUMN_NAME_EXTERNAL_URL+" , "+ GlobalFeedTable.COLUMN_NAME_ORG_ID+
				") VALUES (?, ?, ?, ?, ?)";
		public static final String SQL_DELETE_POST = "DELETE FROM "+GlobalFeedTable.TABLE_NAME+
				" WHERE "+GlobalFeedTable.COLUMN_NAME_ID+" "+"= ? ";
		/* Facilities */
		public static final String SQL_SELECT_FACILITIES_BY_ORG = "select * from "+FaculityTable.TABLE_NAME+
												" where "+FaculityTable.COLUMN_NAME_ORG_ID+"= ";
		public static final String SQL_SELECT_FACILITIE_BY_ID = "select * from "+FaculityTable.TABLE_NAME+
				" where "+FaculityTable.COLUMN_NAME_ID+"= ";

		public static final String SQL_SELECT_FACILITIE_BY_NAME = "select * from "+
				FaculityTable.TABLE_NAME+
				" where "+FaculityTable.COLUMN_NAME_NAME+"='";

		public static final String SQL_INSERT_FACILITY = "INSERT INTO "+FaculityTable.TABLE_NAME+
				" ("+FaculityTable.COLUMN_NAME_ID+", "+
				FaculityTable.COLUMN_NAME_NAME+", "+
				FaculityTable.COLUMN_NAME_ORG_ID+
				") VALUES (?, ?, ?)";

		/* Courses */
		public static final String SQL_SELECT_COURSES_BY_FACULITY = "select * from "+CourseTable.TABLE_NAME+
				" where "+CourseTable.COLUMN_NAME_FACULITY_ID+"= ";
		
		public static final String SQL_SELECT_COURSE_ABOUT = "SELECT "+CourseAboutTable.TABLE_NAME+"."+CourseAboutTable.COLUMN_NAME_POINTS+
				", "+CourseAboutTable.TABLE_NAME+"."+CourseAboutTable.COLUMN_NAME_DESCRIPTION+", "+
				CourseAboutTable.TABLE_NAME+"."+CourseAboutTable.COLUMN_NAME_TEACHER_NAME+", "+
				CourseAboutTable.TABLE_NAME+"."+CourseAboutTable.COLUMN_NAME_COURSE_ID+", "+
				CourseAboutTable.TABLE_NAME+"."+CourseAboutTable.COLUMN_NAME_ID+", "+
				CourseTable.TABLE_NAME+"."+CourseTable.COLUMN_NAME_NAME+", "+
				FaculityTable.TABLE_NAME+"."+FaculityTable.COLUMN_NAME_NAME+
				" FROM "+CourseTable.TABLE_NAME+", "+CourseAboutTable.TABLE_NAME+", "+FaculityTable.TABLE_NAME+
				" WHERE "+CourseTable.TABLE_NAME+"."+CourseTable.COLUMN_NAME_ID+
				"="+CourseAboutTable.TABLE_NAME+"."+CourseAboutTable.COLUMN_NAME_ID+
				" AND "+FaculityTable.TABLE_NAME+"."+FaculityTable.COLUMN_NAME_ID+
				"="+CourseTable.TABLE_NAME+"."+CourseTable.COLUMN_NAME_FACULITY_ID+";";
		
		public static final String SQL_SELECT_COURSE_MATERIAL = "SELECT "+CourseMaterialTable.TABLE_NAME+"."+CourseMaterialTable.COLUMN_NAME_TITLE+
				", "+CourseMaterialTable.TABLE_NAME+"."+CourseMaterialTable.COLUMN_NAME_IMAGE_URL+", "+
				CourseMaterialTable.TABLE_NAME+"."+CourseMaterialTable.COLUMN_NAME_COURSE_ID+", "+
				CourseMaterialTable.TABLE_NAME+"."+CourseMaterialTable.COLUMN_NAME_EXTERNAL_LINK+
				" FROM "+CourseMaterialTable.TABLE_NAME+", "+CourseTable.TABLE_NAME+
				" WHERE "+CourseTable.TABLE_NAME+"."+CourseTable.COLUMN_NAME_ID+
				"="+CourseMaterialTable.TABLE_NAME+"."+CourseMaterialTable.COLUMN_NAME_COURSE_ID+" AND "+
				CourseTable.TABLE_NAME+"."+CourseTable.COLUMN_NAME_ID+" = ";
		
		
		public static final String SQL_INSERT_COURSE_MATERIAL = "INSERT INTO "+CourseMaterialTable.TABLE_NAME+
				" ("+CourseMaterialTable.COLUMN_NAME_TITLE+", "+CourseMaterialTable.COLUMN_NAME_IMAGE_URL+
				", "+CourseMaterialTable.COLUMN_NAME_COURSE_ID+", "+CourseMaterialTable.COLUMN_NAME_EXTERNAL_LINK+ 
				") VALUES (?, ?, ?, ?)";
		
		public static final String SQL_GET_COURSE_BY_ID = "select * from "+CourseTable.TABLE_NAME+
												" where "+CourseTable.COLUMN_NAME_ID+" = '";

		public static final String SQL_GET_COURSE_BY_NAME = "select * from "+CourseTable.TABLE_NAME+
				" where "+CourseTable.COLUMN_NAME_NAME+" = '";

		/* Tutors */
		public static final String SQL_SELECT_TO_FIND_REQUEST_PENDING = "select COUNT(*) from "+TutorRequestsTable.TABLE_NAME+
										" where "+TutorRequestsTable.COLUMN_NAME_TUTOR_ID+" = ? "+
										" AND "+TutorRequestsTable.COLUMN_NAME_STUDENT_ID+"= ? "+
										" AND "+TutorRequestsTable.COLUMN_NAME_COURSE_ID+"= ? "+
										" AND "+TutorRequestsTable.COLUMN_NAME_STATUS+"= "+AppConstants.ConnectionConstants.REQUEST_PENDING;
		public static final String SQL_UPDATE_TUTOR_PROFILE = "UPDATE "+TutorTable.TABLE_NAME+
				" SET "+TutorTable.COLUMN_NAME_ABOUT_TUTOR+"= ?, " +TutorTable.COLUMN_NAME_COMMUNICATION_STR+"= ? "+
				"WHERE "+TutorTable.COLUMN_NAME_ID+"= ? ;";
		
		public static final String SQL_ADD_LIKE_TO_TUTOR =  "INSERT INTO "+TutorLikesTable.TABLE_NAME+
				" ("+TutorLikesTable.COLUMN_NAME_USER_ID+", "+TutorLikesTable.COLUMN_NAME_TUTOR_ID+ 
					") VALUES (?, ?)";
		public static final String SQL_GET_TUTORS_BY_ORG  = 
				"SELECT "+UsersTable.TABLE_NAME+".*"+
				", "+TutorTable.TABLE_NAME+".*"+
				" FROM "+UsersTable.TABLE_NAME+", "+TutorTable.TABLE_NAME+
				" WHERE "+TutorTable.TABLE_NAME+"."+TutorTable.COLUMN_NAME_ID+
				"="+UsersTable.TABLE_NAME+"."+UsersTable.COLUMN_NAME_USER_ID+ 
				" AND "+UsersTable.TABLE_NAME+"."+UsersTable.COLUMN_NAME_ORGANIZATION_ID+
				"=";

		public static final String SQL_INSERT_COURSE = "INSERT INTO "+CourseTable.TABLE_NAME+
				" ("+CourseTable.COLUMN_NAME_NAME+", "+
					CourseTable.COLUMN_NAME_FACULITY_ID+
				") VALUES ( ?, ?)";


		public static final String SQL_GET_TUTORED_COURSES = "SELECT "+
				CourseTable.TABLE_NAME+".*"+", "+TutoredCoursesTable.TABLE_NAME+".*"+
				" FROM "+TutoredCoursesTable.TABLE_NAME+", "+CourseTable.TABLE_NAME+
				" WHERE "+TutoredCoursesTable.TABLE_NAME+"."+TutoredCoursesTable.COLUMN_NAME_COURSE_ID+
				"="+CourseTable.TABLE_NAME+"."+CourseTable.COLUMN_NAME_ID+ 
				" AND "+TutoredCoursesTable.TABLE_NAME+"."+TutoredCoursesTable.COLUMN_NAME_TUTOR_ID+
				"=";
		
		public static final String SQL_GET_TUTORED_STUDENTS = "SELECT "+
				UsersTable.TABLE_NAME+".*"+", "+TutoredStudentsTable.TABLE_NAME+".*"+", "+CourseTable.TABLE_NAME+"."+CourseTable.COLUMN_NAME_NAME+
				" FROM "+UsersTable.TABLE_NAME+", "+TutoredStudentsTable.TABLE_NAME+", "+CourseTable.TABLE_NAME+
				" WHERE "+UsersTable.TABLE_NAME+"."+UsersTable.COLUMN_NAME_USER_ID+
				"="+TutoredStudentsTable.TABLE_NAME+"."+TutoredStudentsTable.COLUMN_NAME_STUDENT_ID+ 
				" AND "+TutoredStudentsTable.TABLE_NAME+"."+TutoredStudentsTable.COLUMN_NAME_COURSE_ID+
				"="+CourseTable.TABLE_NAME+"."+CourseTable.COLUMN_NAME_ID+ 
				" AND "+TutoredStudentsTable.TABLE_NAME+"."+TutoredStudentsTable.COLUMN_NAME_TUTOR_ID+
				"=";
		
		public static final String SQL_GET_TUTOR_BY_ID =
				"SELECT "+TutorTable.TABLE_NAME+".*"+
				", "+UsersTable.TABLE_NAME+"."+UsersTable.COLUMN_NAME_NAME+", "+
				UsersTable.TABLE_NAME+"."+UsersTable.COLUMN_NAME_IMAGE_URL+
				" FROM "+TutorTable.TABLE_NAME+", "+UsersTable.TABLE_NAME+
				" WHERE "+TutorTable.TABLE_NAME+"."+TutorTable.COLUMN_NAME_ID+
				"="+UsersTable.TABLE_NAME+"."+UsersTable.COLUMN_NAME_USER_ID+ 
				" AND "+TutorTable.TABLE_NAME+"."+TutorTable.COLUMN_NAME_ID+
				"='";
		 
		public static final String SQL_GET_REQUESTS_FOR_TUTOR = 
				"SELECT "+TutorRequestsTable.TABLE_NAME+".*"+
						", "+UsersTable.TABLE_NAME+"."+ UsersTable.COLUMN_NAME_NAME + 
						", "+UsersTable.TABLE_NAME+"."+ UsersTable.COLUMN_NAME_IMAGE_URL + 
						", "+CourseTable.TABLE_NAME + "." + CourseTable.COLUMN_NAME_NAME +
						" FROM "+TutorRequestsTable.TABLE_NAME+", "+UsersTable.TABLE_NAME+ ", "+CourseTable.TABLE_NAME+
						" WHERE "+TutorRequestsTable.TABLE_NAME+"."+TutorRequestsTable.COLUMN_NAME_STUDENT_ID+
						"="+UsersTable.TABLE_NAME+"."+UsersTable.COLUMN_NAME_USER_ID+ 
						" AND "+TutorRequestsTable.TABLE_NAME+"."+TutorRequestsTable.COLUMN_NAME_COURSE_ID+
						"="+CourseTable.TABLE_NAME+"."+CourseTable.COLUMN_NAME_ID +
						" AND "+TutorRequestsTable.COLUMN_NAME_TUTOR_ID+"= ";		
		
		
		public static final String SQL_INSERT_TUTOR = "INSERT INTO "+TutorTable.TABLE_NAME+
				" ("+TutorTable.COLUMN_NAME_ID+", "+TutorTable.COLUMN_NAME_COMMUNICATION_STR+", "+TutorTable.COLUMN_NAME_ABOUT_TUTOR+
				") VALUES (?, ?, ?)";
		
		
		public static final String SQL_INSERT_TUTORED_STUDENT = "INSERT INTO "+TutoredStudentsTable.TABLE_NAME+
									" ("+TutoredStudentsTable.COLUMN_NAME_STUDENT_ID+", "+
									TutoredStudentsTable.COLUMN_NAME_TUTOR_ID+", "+TutoredStudentsTable.COLUMN_NAME_COURSE_ID+ 
									") VALUES (?, ?, ?)";
				
		 
		public static final String SQL_INSERT_TUTORED_COURSE =  "INSERT INTO "+TutoredCoursesTable.TABLE_NAME+
				" ("+TutoredCoursesTable.COLUMN_NAME_COURSE_ID+", "+TutoredCoursesTable.COLUMN_NAME_TUTOR_ID+
				") VALUES (?, ?)";
				
		
		public static final String SQL_INSERT_TUTOR_REQUEST = "INSERT INTO "+TutorRequestsTable.TABLE_NAME+
				" ("+TutorRequestsTable.COLUMN_NAME_TUTOR_ID+", "+TutorRequestsTable.COLUMN_NAME_STUDENT_ID+
				", "+TutorRequestsTable.COLUMN_NAME_COURSE_ID+", "+TutorRequestsTable.COLUMN_NAME_STATUS+
				") VALUES (?, ?, ?, ?)";
 
        
		public static final String SQL_UPDATE_TUTOR_REQUEST = "UPDATE "+TutorRequestsTable.TABLE_NAME+
						" SET "+TutorRequestsTable.COLUMN_NAME_STATUS+"= ? WHERE "+TutorRequestsTable.COLUMN_NAME_TUTOR_ID+"= ?  AND "+
						TutorRequestsTable.COLUMN_NAME_STUDENT_ID+"= ?  AND "+TutorRequestsTable.COLUMN_NAME_COURSE_ID+"= ?   AND "+
						TutorRequestsTable.COLUMN_NAME_TIME+"= ? ";
		
		public static final String SQL_UPDATE_FIREBASE_TOKEN = "UPDATE "+UsersTable.TABLE_NAME+
				" SET "+UsersTable.COLUMN_NAME_FIREBASE_TOKEN+"= ? WHERE "+UsersTable.COLUMN_NAME_USER_ID+"= ? ";
		
		public static final String SQL_COUNT_NUM_LIKES_FOR_TUTOR = " SELECT COUNT("+TutorLikesTable.COLUMN_NAME_TUTOR_ID+") "+
																	" FROM "+TutorLikesTable.TABLE_NAME+" WHERE "+
																	TutorLikesTable.COLUMN_NAME_TUTOR_ID + " = ?";
	}
	
	
	
}
