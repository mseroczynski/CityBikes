package pl.ches.citybikes.data.disk.store.impl

import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.sql.language.SQLite
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction
import pl.ches.citybikes.data.disk.Db
import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.disk.entity.Station
import pl.ches.citybikes.data.disk.store.StationStore

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class StationStoreImpl
constructor() : StationStore {

  override fun put(value: List<Station>) {
    FlowManager.getDatabase(Db::class.java).executeTransaction { databaseWrapper ->
      FastStoreModelTransaction
          .insertBuilder(FlowManager.getModelAdapter(Station::class.java))
          .addAll(value)
          .build()
          .execute(databaseWrapper)
    }
  }

  override fun get(key: Area): List<Station>? = key.getStations()

  override fun delete(value: List<Station>) = value.forEach { it.delete() }

  override fun clear() = SQLite.select().from(Station::class.java).queryList().forEach { it.delete() }

}