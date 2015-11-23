package unuuu.com.photoapart.network

import android.support.v7.app.AppCompatActivity
import retrofit.http.GET
import retrofit.http.Query
import rx.Observable
import unuuu.com.photoapart.entities.PhotoEntity
import unuuu.com.photoapart.views.activities.MainActivity

/**
 *
 */
public interface  ApiService {
    @GET("/get_photo.json")
    public fun getPhoto(@Query("time") time : String) : Observable<PhotoEntity>
}