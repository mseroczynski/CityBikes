package pl.ches.citybikes.presentation.common.util

/**
 * TODO To delete?
 *
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
object ColorGenerator {

  private val colors: List<Long> by lazy {
    arrayListOf(
        0xffe57373,
        0xfff06292,
        0xffba68c8,
        0xff9575cd,
        0xff7986cb,
        0xff64b5f6,
        0xff4fc3f7,
        0xff4dd0e1,
        0xff4db6ac,
        0xff81c784,
        0xffaed581,
        0xffff8a65,
        0xffd4e157,
        0xffffd54f,
        0xffffb74d,
        0xffa1887f,
        0xff90a4ae
    )
  }

  fun getColor(key: Any): Int = colors[Math.abs(key.hashCode()) % colors.size].toInt()

}