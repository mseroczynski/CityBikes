package pl.ches.citybikes.mvp.screen.main

import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.disk.enums.SourceApi
import pl.ches.citybikes.interactor.*
import pl.ches.citybikes.mvp.common.base.host.HostPresenter
import rx.functions.Action1
import v
import javax.inject.Inject

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class MainPresenter
@Inject
constructor(private val getAreasInteractor: GetAreasInteractor, private val getStationsInteractor: GetStationsInteractor) : HostPresenter<MainView>() {

  override fun attachView(view: MainView?) {
    super.attachView(view)
    // TODO under development
  }

  fun refresh() {
//    getAreasInteractor.execute(getAreasNextAction(), Unit)
    val param = arrayListOf(Area("", SourceApi.NEXT_BIKE, "8", "", 0.0, 0.0))
    getStationsInteractor.execute(getStationsNextAction(), param)
  }

  private fun getAreasNextAction(): Action1<GetAreasResult> = Action1() {
    when (it.type) {
      GetAreasResultType.SUCCESS -> v { "" }
      GetAreasResultType.ERROR -> v { "" }
    }
  }

  private fun getStationsNextAction(): Action1<GetStationsResult> = Action1() {
    when (it.type) {
      GetStationsResultType.SUCCESS -> v { "" }
      GetStationsResultType.ERROR -> v { "" }
    }
  }
}

