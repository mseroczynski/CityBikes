package pl.ches.citybikes.data.disk.enums

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
enum class SourceApi constructor(private val shortName: String) {
  CITY_BIKES("cb"), NEXT_BIKE("nb"), UNKNOWN("??");

  val toPrefix: String by lazy {
    "${shortName}_"
  }

}