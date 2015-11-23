package unuuu.com.photoapart.events

import com.squareup.otto.Bus
import unuuu.com.photoapart.managers.ModelLocator
import java.util.*

public class BusHolder {
    companion object {
        private val BUS = Bus()

        fun get() : Bus = BUS
    }
}
