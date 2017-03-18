package pl.ches.citybikes.domain.common.diagnostic

import android.util.Log
import pl.ches.citybikes.di.scope.AppScope
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@AppScope
class CrashlyticsTree
@Inject
constructor() : Timber.Tree() {

  override fun log(priority: Int, tag: String?, message: String?, t: Throwable?) {
    if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
      return
    }

    // TODO Crashlytics
//    Crashlytics.setInt(CRASHLYTICS_KEY_PRIORITY, priority)
//    Crashlytics.setString(CRASHLYTICS_KEY_TAG, tag)
//    Crashlytics.setString(CRASHLYTICS_KEY_MESSAGE, message)

    if (t == null) {
//      Crashlytics.logException(Exception(message))
    } else {
//      Crashlytics.logException(t)
    }
  }

  companion object {
    private val CRASHLYTICS_KEY_PRIORITY = "priority"
    private val CRASHLYTICS_KEY_TAG = "tag"
    private val CRASHLYTICS_KEY_MESSAGE = "message"
  }
}