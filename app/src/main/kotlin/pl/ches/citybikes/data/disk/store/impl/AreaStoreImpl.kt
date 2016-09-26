package pl.ches.citybikes.data.disk.store.impl

import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.kotlinextensions.eq
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.select
import com.raizlabs.android.dbflow.kotlinextensions.where
import com.raizlabs.android.dbflow.sql.language.SQLite
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction
import pl.ches.citybikes.data.disk.Db
import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.disk.entity.Area_Table
import pl.ches.citybikes.data.disk.enums.SourceApi
import pl.ches.citybikes.data.disk.store.AreaStore

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class AreaStoreImpl
constructor() : AreaStore {

  override fun put(value: List<Area>) {
    FlowManager.getDatabase(Db::class.java).executeTransaction { databaseWrapper ->
      FastStoreModelTransaction
          .saveBuilder(FlowManager.getModelAdapter(Area::class.java))
          .addAll(value)
          .build()
          .execute(databaseWrapper)
    }
  }

  override fun get(key: SourceApi): List<Area>? =
      when (key) {
        SourceApi.ANY -> (select
            from Area::class
            ).queryList()
        else -> (select
            from Area::class
            where (Area_Table.sourceApi eq key)
            ).queryList()
      }

  override fun delete(value: List<Area>) = value.forEach { it.delete() }

  override fun clear() = SQLite.select().from(Area::class.java).queryList().forEach { it.delete() }

}