package unuuu.com.photoapart.managers;

import java.util.*

public class ModelLocator private constructor() {
    companion object {
        private val showcase = HashMap<Tag, Any>();

        public enum class Tag {
            PHOTO,
        }

        public fun register(tag: Tag, model: Any): Unit {
            showcase.put(tag, model)
        }

        public fun get(tag: Tag): Any = showcase[tag]!!
    }
}
