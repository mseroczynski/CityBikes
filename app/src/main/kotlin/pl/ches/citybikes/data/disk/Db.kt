package pl.ches.citybikes.data.disk

import com.raizlabs.android.dbflow.annotation.Database

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@Database(name = Db.DB_NAME, version = Db.DB_CURRENT_VERSION)
class Db {

  companion object {
    const val DB_NAME: String = "citybikes"
    const val DB_CURRENT_VERSION: Int = 1
  }

}