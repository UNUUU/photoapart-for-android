package unuuu.com.photoapart.views.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.bindView
import com.squareup.otto.Subscribe
import com.squareup.picasso.Picasso
import unuuu.com.photoapart.R
import unuuu.com.photoapart.events.BusHolder
import unuuu.com.photoapart.models.PhotoModel
import unuuu.com.photoapart.utils.DateUtil
import java.util.*

class MainActivity: AppCompatActivity() {

    val photoModel: PhotoModel = PhotoModel()
    val imageView: ImageView by bindView(R.id.activity_main_frame_001)
    val dateView: TextView by bindView(R.id.activity_main_frame_002)
    val userView: TextView by bindView(R.id.activity_main_frame_004)
    val userLayout: LinearLayout by bindView(R.id.activity_main_layout_002)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userLayout.visibility = View.GONE
        userView.setOnClickListener { openUserLink() }
    }

    override fun onResume() {
        super.onResume()
        BusHolder.get().register(this)
        photoModel.fetch()
    }

    override fun onPause() {
        super.onPause()
        BusHolder.get().unregister(this)
    }

    @SuppressWarnings("unused")
    @Subscribe
    public fun onLoadedPhoto(event: PhotoModel.PhotoLoadedEvent) {
        if (event.eventType == PhotoModel.PhotoLoadedEvent.EventType.COMPLETE) {
            Picasso.with(applicationContext).load(photoModel.photoEntity?.imageUrl).into(imageView)
            dateView.text = DateUtil.stringDateBySimpleDateFormat("HH : mm", photoModel.photoDate as Date)
            userView.text = photoModel.photoEntity?.userName
            userLayout.visibility = View.VISIBLE
        } else if (event.eventType == PhotoModel.PhotoLoadedEvent.EventType.NETWORK_ERROR) {
            println("写真のロードに失敗しました")
        }
        photoModel.pollingFetch()
    }

    /**
     * ユーザーのページを開く
     */
    private fun openUserLink() {
        val uri = Uri.parse(photoModel.photoEntity!!.userUrl);
        val intent = Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
