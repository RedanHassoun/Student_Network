package com.mohred.studentnetwork.common;

/**
 * Created by Redan on 11/25/2016.
 */
public class AppConstants
{
    private AppConstants(){}

    public static final class GeneralConstants
    {
        public static final String DIR_NAME_FIREBASE = "posts_images";
        public static final String NAME = "name";
        public static final String IMAGE_TYPE_FIREBASE = ".png";
        public static final String ID_NULL = "-1";
    }

    public static final class UserStatusConstants
    {
        public static final String USER_OK = "USER_OK";
        public static final String USER_NOT_EXIST= "USER_NOT_EXIST";
        public static final String WRONG_PASSWORD = "WRONG_PASSWORD";
        public static final String REGISTER_OK = "REGISTER_OK";
        public static final String REGISTER_NOT_OK = "REGISTER_NOT_OK";
    }

    public static final class FragmentNames
    {
        public static final String FRAGMENT_LOGIN = "Login fragment";
        public static final String FRAGMENT_REGISTER = "Register fragment";
        public static final String FRAGMENT_CHOOSE_ORGANIZATION = "choose organization fragment";
        public static final String FRAGMENT_OTHER_USER_PROFILE = "other user profile fragment";
        public static final String FRAGMENT_TUTOR_PROFILE = "fragment tutor profile";
        public static final String FRAGMENT_SINGLE_COURSE = "fragment single course";
    }

    public static final class SharedPrefsKeys
    {
        public static final String PREF_FIRST_LAUNCH = "first_run_pref_indicator";
        public static final String PREF_USER_SESSION = "user_prefs";
        public static final String PREF_CURRENT_USER_OBJECT = "current_user_value";
        public static final String PREF_FIREBASE_TOKEN = "firebase_token_pref";
        public static final String PREF_FIREBASE_TOKEN_CHANGED_FLAG = "firebase_token_changed_pref";
    }

    public static final class URLs
    {
        public static final String IP = "10.0.0.10";
        public static final String BASE_URL = "http://"+IP+":8080/";
        public static final String CHECK_USER = "checkUser";
        public static final String REGISTER_USER = "registerUser";
        public static final String GET_ALL_ORGANIZATIONS = "getAllOrgs";
        public static final String URL_USER_REGISTERED_BEFORE_FG = "checkUserRegisteredBeforeFG";
        public static final String URL_LOGIN = "login";
        public static final String URL_LOGIN_GOOGLE = "loginGoogle";
        public static final String URL_LOGIN_FACEBOOK = "loginFacebook";
        public static final String URL_CHECK_USER_STATUS = "checkUserStatus";
        public static final String URL_GET_NEWSFEED_FROM_INDEX = "getFeedFromIndex";
        public static final String URL_ADD_NEW_POST = "addPost";
        public static final String URL_DELETE_POST = "deletePost";
        public static final String URL_GET_FACULITIES = "getFaculitiesByOrgID";
        public static final String URL_GET_COURSES_BY_FACULITY = "getAllCoursesOnFaculity";
        public static final String URL_GET_COURSE_ABOUT = "getCourseAbout";
        public static final String URL_GET_COURSE_MATERIAL = "getCourseMaterial";
        public static final String URL_POST_COURSE_MATERIAL = "addCourseMaterial";
        public static final String UPDATE_USER_DETAILS = "updateUserDetails";
        public static final String URL_GET_ALL_PEOPLE_FOR_ORG = "getllPeopleForOrg";
        public static final String URL_GET_USER_BY_MAIL = "getUserByMail";
        public static final String URL_GET_ALL_TUTORS_BY_ORG = "getAllTutorsByORG";
        public static final String URL_GET_TUTORED_COURSES = "getTutoredCourses";
        public static final String URL_GET_TUTORED_STUDENTS = "getTutoredStudents";
        public static final String URL_GET_TUTOR_BY_ID = "getTutorByID";
        public static final String URL_GET_TUTOR_REQUESTS = "getRequestsForTutor";
        public static final String URL_ADD_TUTOR = "addTutor";
        public static final String URL_MAKE_TUTOR_REQUEST = "makeTutorRequest";
        public static final String URL_UPDATE_TUTPR_REQUEST = "updateTutorRequest";
        public static final String URL_UPDATE_FIREBASE_TOKEN =  "updateFirebaseToken";
        public static final String URL_LIKE_TUTOR = "likeTutor";
        public static final String URL_UPDATE_TUTOR_PROFILE = "updateTutorProfile";
        public static final String URL_UPDATE_USER_IMAGE = "updateImageURL";
    }

    public static final class ConcurrentConstants
    {
        public static final int THREAD_POOL_SIZE = 10;
    }

    public static final class ScreenArguments
    {
        public static final String ARG_USER_FULL_NAME = "user full name";
        public static final String ARG_USERNAME = "username";
        public static final String ARG_USER_EMAIL = "user email";
        public static final String ARG_USER_PASSWORD = "user password";
        public static final String ARG_USER_IMAGE_URL = "user image url";
        public static final String ARG_LOGIN_METHOD  = "user login method";
        public static final String ARG_USER_ID = "user id argument";
        public static final String ARG_USER_DOMAIN_ID = "user domain id argument";

        /* Splash screen */
        public static final String TYPE_RUN = "splash type run";
        public static final String ARG_FIRST_RUN = "splash first run";
        public static final String ARG_OPEN_APP = "splash open app";

        /* Full screen image */
        public static final String ARG_IMAGE_URL = "the_image_url";
        public static final String ARG_IS_PROFILE = "is_profile_image";

        /* Single course fragment */
        public static final String ARG_ID_COURSE = "course_id";

        /*  profile arguments */
        public static final String ARG_PROGILE_USER_MAIL =  "foriegn_user_id";

        /* Activity tutors */
        public static final String ARG_STUDENT_TYPE = "student_type";
        public static final int ARG_TYPE_REGULAR = 0;
        public static final int ARG_TYPE_TUTOR = 1;
        public static final String ARG_TUTOR_ID = "tutor_id";
        public static final String ARG_GOTO_REQUESTS = "goto_requests";
        public static final String ARG_HOW_TO_CONTACT = "how_to_contact";
        public static final String ARG_ABOUT = "arg_about";
    }

    public static final class ConnectionConstants
    {
        /* Register */
        public static final String REGISTER_STATUS_EXIST = "err_user_already_exist";
        public static final String REGISTER_UNKNOWN_ERROR = "err_cant_register_unknown";
        public static final String REGISTER_STATIS_OK = "register_ok";
        public static final String REGISTER_TYPE_REGULAR = "register regualar";
        public static final String REGISTER_TYPE_GOOGLE = "register google";
        public static final String REGISTER_TYPE_FACEBOOK = "register facebook";

        /* Login */
        public static final String LOGIN_OK = "login ok";
        public static final String LOGIN_USER_NOT_EXIST = "login user not exist";
        public static final String LOGIN_WRONG_PASSWORD = "login wrong password";
        public static final String USER_NOT_EXIST1 = "user not exists";

        /* Status */
        public static final String STATUS_OK = "status ok";
        public static final String STATUS_NOT_EXIST = "status user not exist";
        public static final String STATUS_WRONG_PASSWORD = "status wrong password";
        public static final String STATUS_NOT_OK = "status not ok";
        public static final String STATUS_ALREADY_PENDING = "status_already_pending";
        public static final String USER_EXIST_IN_FACEBOOK = "user exists in facebook";
        public static final String USER_EXIST_IN_GOOGLE = "user exists in google";
        /* Posts */

        public static final int NUM_POSTS_TO_GET = 15;

        /* Tutors */
        public static final int REQUEST_PENDING = 0;
        public static final int REQUEST_ACCEPTED = 1;
        public static final int REQUEST_DECLINED = 2;
        public static final String JSON_KEY_TUTOR_ID = "tutor_id";
        public static final String JSON_KEY_COURSE_NAME = "course_name";
        public static final String JSON_KEY_IS_ACCEPTED = "is_accepted";
        public static final String JSON_KEY_TARGET_USER_ID = "target_user_id";
        public static final String ACCEPTED_YES = "yes";
        public static final String ACCEPTED_NO = "no";

    }


}
