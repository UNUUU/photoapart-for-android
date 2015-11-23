package unuuu.com.photoapart.models;

import rx.Observer

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import unuuu.com.photoapart.entities.PhotoEntity
import unuuu.com.photoapart.events.BusHolder
import unuuu.com.photoapart.network.ApiService;
import unuuu.com.photoapart.utils.RequestUtil;

public class PhotoModel {

    public var photoEntity : PhotoEntity? = null

    public fun fetch() {
        val retrofit = RequestUtil.getRestAdapter()
        val apiService = retrofit.create(ApiService::class.java)
        apiService.getPhoto("1048")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<PhotoEntity> {
            override fun onCompleted() {
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

    public class PhotoLoadedEvent {
        public enum class EventType {
            COMPLETE,
            NETWORK_ERROR
        }

        public var eventType: EventType = EventType.COMPLETE

        public constructor(type: PhotoLoadedEvent.EventType) {
           eventType = type
        }
    }
}
