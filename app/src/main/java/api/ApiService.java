package api;

import com.example.myapplication.viewmodel.AnnouncementResponse;
import com.example.myapplication.viewmodel.CheckupResponse;
import com.example.myapplication.viewmodel.DoctorSchedule;
import com.example.myapplication.viewmodel.LeaveResponse;
import com.example.myapplication.viewmodel.LoginResponse;
import com.example.myapplication.viewmodel.LogoutResponse;
import com.example.myapplication.viewmodel.NewsResponse;
import com.example.myapplication.viewmodel.NotificationResponse;
import com.example.myapplication.viewmodel.SearchResponse;
import com.example.myapplication.viewmodel.TodaysPatientCountResponse;
import com.example.myapplication.viewmodel.TotalCheckupResponse;
import com.example.myapplication.viewmodel.Totalbirthing;
import com.example.myapplication.viewmodel.TotalbiteResponse;
import com.example.myapplication.viewmodel.Totalimmunization;
import com.example.myapplication.viewmodel.Totalprenatal;
import com.example.myapplication.viewmodel.UpdatePassword;
import com.example.myapplication.viewmodel.UpdateprofileResponse;
import com.example.myapplication.viewmodel.animalbite.AnimalbiteResponse;
import com.example.myapplication.viewmodel.prenatal.PrenatalResponse;

import com.example.myapplication.viewmodel.applyforleave;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> loginUser(
            @Field("username") String username,
            @Field("password") String password


    );

    @FormUrlEncoded
    @POST("logout.php")
        // This is the logout API endpoint
    Call<LogoutResponse> logoutUser(
            @Field("userID") String userID
    );

    @GET("doctor_class.php")
    Call<List<DoctorSchedule>> getDoctorSchedule(@Query("userID") int userID);

    @GET("fetch_announcements.php")
    Call<AnnouncementResponse> getAnnouncements(@Query("fetch") String fetchType);

    @FormUrlEncoded
    @POST("doctor_class.php")
    Call<LeaveResponse> applyLeave(
            @Field("action") String action,        // Action type to handle leave request
            @Field("userID") int userID,           // User ID
            @Field("leaveDate") String leaveDate,  // Leave Date
            @Field("message") String message,      // Leave message (reason)
            @Field("startTime") String startTime,  // Start Time
            @Field("endTime") String endTime
    );

    @GET("notification.php")
    Call<NotificationResponse> getNotificationCount(@Query("userID") String userID);

    @GET("notifications-view.php")
    Call<NotificationResponse> getNotifications(@Query("userID") int userID);

    @FormUrlEncoded
    @POST("apply_leave.php")
    Call<applyforleave> Applyforleave(
            @Field("userID") int userID,
            @Field("start_date") String start_date,
            @Field("end_date") String end_date,
            @Field("reason") String reason

    );

    @PUT("notifications-decrement.php")
    Call<Void> decrementNotificationCount(@Query("userID") String userID);


    @GET("doctor_class.php")
    Call<TodaysPatientCountResponse> getTodaysPatientCount(@Query("count") String count);

    @GET("doctor_class.php")
    Call<TotalCheckupResponse> getTotalCheckup(@Query("total_checkups") String count);

    @GET("doctor_class.php")
    Call<TotalbiteResponse> getTotalbite(@Query("total_bite") String count);

    @GET("doctor_class.php")
    Call<Totalprenatal> getTotalprenatal(@Query("total_prenatal") String count);

    @GET("doctor_class.php")
    Call<Totalbirthing> getTotalbirthing(@Query("total_birth") String count);

    @GET("doctor_class.php")
    Call<Totalimmunization> getTotalimuunization(@Query("total_vaccine") String count);


    @FormUrlEncoded
    @POST("update-password.php")
    Call<UpdatePassword> updatePassword(
            @Field("userID") int userID,
            @Field("username") String username,
            @Field("newPassword") String newPassword,
            @Field("confirmPassword") String confirmPassword
    );

    @GET("medical-news.php")
        // Ensure this path is correct
    Call<NewsResponse> getHealthNews(@Query("query") String query);

    @Multipart
    @POST("updateprofile.php")
    Call<UpdateprofileResponse> updateProfile(
            @Part("userID") RequestBody userID,
            @Part("first_name") RequestBody firstName,
            @Part("middlename") RequestBody middleName,
            @Part("lastname") RequestBody lastName,
            @Part("email") RequestBody email,
            @Part("specialty") RequestBody specialty,
            @Part("ProfessionalType") RequestBody professionalType,
            @Part("LicenseNo") RequestBody licenseNo,
            @Part("address") RequestBody address,
            @Part("position_id") RequestBody positionID,
            @Part("personnel_id") RequestBody personnelID,
            @Part MultipartBody.Part profilePicture
    );


    @GET("records-check-up.php")
    Call<CheckupResponse> getCheckupList();

    @GET("records-animalbite.php")
    Call<AnimalbiteResponse> getAnimalbiteList();


    @GET("records-prenatal.php")
    Call<PrenatalResponse> getPrenatalList();





    @GET("search.php")
    Call<SearchResponse> fetchAllData(@Query("search") String searchTerm);

}










