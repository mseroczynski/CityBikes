package pl.ches.citybikes.data.disk.entity

import com.raizlabs.android.dbflow.annotation.*
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.structure.BaseModel
import pl.ches.citybikes.data.disk.Db
import pl.ches.citybikes.data.disk.enums.SourceApi

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@ModelContainer
@Table(name = Area.TABLE_NAME, database = Db::class, cachingEnabled = true)
class Area
constructor(

    @PrimaryKey
    @Column(name = Area.ID)
    var id: String = "",

    @Column(name = Area.SOURCE_API)
    var sourceApi: SourceApi = SourceApi.ANY,

    @Column(name = Area.ORIGINAL_ID)
    var originalId: String = "",

    @Column(name = Area.ORIGINAL_NAME)
    var originalName: String = "",

    @Column(name = Area.LATITUDE)
    var latitude: Double = 0.0,

    @Column(name = Area.LONGITUDE)
    var longitude: Double = 0.0

) : BaseModel() {

  @JvmField
  var stations: List<Station>? = null

  @OneToMany(methods = arrayOf(OneToMany.Method.ALL), variableName = "stations")
  fun getStations(): List<Station> {
    if (stations == null || stations!!.isEmpty()) {
      stations = (
          select
              from Station::class
              where (Station_Table.fkArea_id eq this.id)
          ).list
    }
    return stations!!
  }

  companion object {
    const val TABLE_NAME = "Area"

    const val ID = "id"
    const val SOURCE_API = "sourceApi"
    const val ORIGINAL_ID = "originalId"
    const val ORIGINAL_NAME = "originalName"
    const val LATITUDE = "latitude"
    const val LONGITUDE = "longitude"
  }

}
