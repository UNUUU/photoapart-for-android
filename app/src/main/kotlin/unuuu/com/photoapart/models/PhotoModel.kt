package unuuu.com.photoapart.models;

import android.os.Handler
import rx.Observer

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import unuuu.com.photoapart.entities.PhotoEntity
import unuuu.com.photoapart.events.BusHolder
import unuuu.com.photoapart.network.ApiService;
import unuuu.com.photoapart.utils.DateUtil
import unuuu.com.photoapart.utils.RequestUtil;
import java.util.*

public class PhotoModel {

    public var photoEntity: PhotoEntity? = null
    public var isBusy: Boolean = false
    private val handler: Handler = Handler()
    public var photoDate: Date? = null

    /**
     * 画像を取得する
     */
    public fun fetch() {
        if (isBusy) {
            return
        }
        isBusy = true

        val date = Calendar.getInstance().time
        if (DateUtil.stringDateBySimpleDateFormat("HHmm", photoDate)
                .equals(DateUtil.stringDateBySimpleDateFormat("HHmm", date))) {
            isBusy = false
            BusHolder.get().post(PhotoLoadedEvent(PhotoLoadedEvent.EventType.POLLING))
            return
        }

        val retrofit = RequestUtil.getRestAdapter()
        val apiService = retrofit.create(ApiService::class.java)
        apiService.getPhoto(DateUtil.stringDateBySimpleDateFormat("HHmm", date))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .finallyDo { isBusy = false }
                .subscribe(object: Observer<PhotoEntity> {
            override fun onCompleted() {
                photoDate = date
                BusHolder.get().post(PhotoLoadedEvent(PhotoLoadedEvent.EventType.COMPLETE))
            }

            override fun onError(e: Throwable?) {
                println("サーバエラー: " + e?.message)
                BusHolder.get().post(PhotoLoadedEvent(PhotoLoadedEvent.EventType.NETWORK_ERROR))
            }

            override fun onNext(p: PhotoEntity) {
                photoEntity = p
            }
        })
    }

    /**
     * ポーリングして画像を取得する
     */
    public fun pollingFetch() {
        handler.postDelayed({ fetch() }, 1000)
    }

    public class PhotoLoadedEvent {
        public enum class EventType {
            COMPLETE,
            NETWORK_ERROR,
            POLLING
        }

        public var eventType: EventType = EventType.COMPLETE

        public constructor(type: PhotoLoadedEvent.EventType) {
           eventType = type
        }
    }
}
