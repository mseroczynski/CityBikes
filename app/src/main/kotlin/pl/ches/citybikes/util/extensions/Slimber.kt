import android.util.Log
import timber.log.Timber

inline fun ifPlanted(action: () -> Any) {
    if (Timber.forest().size > 0) {
        action.invoke()
    }
}

inline fun e(message: () -> String) = ifPlanted { Timber.e(message.invoke()) }
inline fun e(throwable: Throwable, message: () -> String) = ifPlanted { Timber.e(throwable, message.invoke()) }

inline fun w(message: () -> String) = ifPlanted { Timber.w(message.invoke()) }
inline fun w(throwable: Throwable, message: () -> String) = ifPlanted { Timber.w(throwable, message.invoke()) }

inline fun i(message: () -> String) = ifPlanted { Timber.i(message.invoke()) }
inline fun i(throwable: Throwable, message: () -> String) = ifPlanted { Timber.i(throwable, message.invoke()) }

inline fun d(message: () -> String) = ifPlanted { Timber.d(message.invoke()) }
inline fun d(throwable: Throwable, message: () -> String) = ifPlanted { Timber.d(throwable, message.invoke()) }

inline fun v(message: () -> String) = ifPlanted { Timber.v(message.invoke()) }
inline fun v(throwable: Throwable, message: () -> String) = ifPlanted { Timber.v(throwable, message.invoke()) }

inline fun wtf(message: () -> String) = ifPlanted { Timber.wtf(message.invoke()) }
inline fun wtf(throwable: Throwable, message: () -> String) = ifPlanted { Timber.wtf(throwable, message.invoke()) }

inline fun log(priority: Int, t: Throwable, message: () -> String) {
    when (priority) {
        Log.ERROR -> e(t, message)
        Log.WARN -> w(t, message)
        Log.INFO -> i(t, message)
        Log.DEBUG -> d(t, message)
        Log.VERBOSE -> v(t, message)
        Log.ASSERT -> wtf(t, message)
    }
}

inline fun log(priority: Int, message: () -> String) {
    when (priority) {
        Log.ERROR -> e(message)
        Log.WARN -> w(message)
        Log.INFO -> i(message)
        Log.DEBUG -> d(message)
        Log.VERBOSE -> v(message)
        Log.ASSERT -> wtf(message)
    }
}