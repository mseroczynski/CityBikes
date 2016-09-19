package pl.ches.citybikes.data.disk.entity

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.ForeignKey
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.structure.BaseModel
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer
import pl.ches.citybikes.data.disk.Db
import pl.ches.citybikes.data.disk.enums.SourceApi

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@Table(name = Station.TABLE_NAME, database = Db::class, cachingEnabled = true)
class Station
constructor(

    @PrimaryKey
    @Column(name = Station.ID)
    var id: String = "",

    @Column(name = Station.SOURCE_API)
    var sourceApi: SourceApi = SourceApi.ANY,

    @Column(name = Station.ORIGINAL_ID)
    var originalId: String = "",

    @Column(name = Station.ORIGINAL_NAME)
    var originalName: String = "",

    @Column(name = Station.LATITUDE)
    var latitude: Double = 0.0,

    @Column(name = Station.LONGITUDE)
    var longitude: Double = 0.0,

    @Column(name = Station.FREE_BIKES)
    var freeBikes: String = "",

    @ForeignKey
    var fkArea : ForeignKeyContainer<Area>? = null

) : BaseModel() {

  fun associate(associate : Area) {
    fkArea = FlowManager.getContainerAdapter(associate.javaClass).toForeignKeyContainer(associate)
  }

  companion object {
    const val TABLE_NAME = "Station"

    const val ID = "id"
    const val SOURCE_API = "sourceApi"
    const val ORIGINAL_ID = "originalId"
    const val ORIGINAL_NAME = "originalName"
    const val LATITUDE = "latitude"
    const val LONGITUDE = "longitude"
    const val FREE_BIKES = "freeBikes"
  }

}
