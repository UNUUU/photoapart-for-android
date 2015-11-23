package unuuu.com.photoapart

import android.app.Application
import unuuu.com.photoapart.managers.ModelLocator
import unuuu.com.photoapart.models.PhotoModel

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        ModelLocator.register(ModelLocator.Companion.Tag.PHOTO, PhotoModel())
    }
}


