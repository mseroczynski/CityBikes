package pl.ches.citybikes.data.disk.store

import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.SQLite
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction
import pl.ches.citybikes.data.disk.Db
import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.disk.entity.Area_Table
import pl.ches.citybikes.data.disk.enums.SourceApi
import pl.ches.citybikes.data.disk.store.base.DataStore
import pl.ches.citybikes.di.scope.AppScope
import javax.inject.Inject

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@AppScope
class AreaStore
@Inject
constructor() : DataStore<SourceApi, List<Area>>() {

  override fun put(value: List<Area>) = FlowManager.getDatabase(Db::class.java).executeTransaction { databaseWrapper ->
    FastStoreModelTransaction
        .insertBuilder(FlowManager.getModelAdapter(Area::class.java))
        .addAll(value)
        .build()
        .execute(databaseWrapper)
  }

  override fun get(key: SourceApi): List<Area>? {
    val whereClause = when (key) {
      SourceApi.ANY -> Area_Table.sourceApi `in` SourceApi.values()
      else -> Area_Table.sourceApi eq key
    }
    return (select
        from Area::class
        where whereClause
        ).queryList()
  }

  override fun delete(value: List<Area>) = value.forEach { it.delete() }

  override fun clear() = SQLite.select().from(Area::class.java).queryList().forEach { it.delete() }

}