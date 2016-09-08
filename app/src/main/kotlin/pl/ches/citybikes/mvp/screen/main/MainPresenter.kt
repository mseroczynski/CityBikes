package pl.ches.citybikes.mvp.screen.main

import pl.ches.citybikes.interactor.GetAreasInteractor
import pl.ches.citybikes.interactor.GetAreasResult
import pl.ches.citybikes.interactor.GetAreasResultType
import pl.ches.citybikes.mvp.common.base.host.HostPresenter
import rx.functions.Action1
import v
import javax.inject.Inject

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class MainPresenter
@Inject
constructor(private val getAreasInteractor: GetAreasInteractor) : HostPresenter<MainView>() {

  override fun attachView(view: MainView?) {
    super.attachView(view)
    getAreasInteractor.execute(getAreasNextAction(), Unit)
    // TODO under development
  }

  private fun getAreasNextAction(): Action1<GetAreasResult> = Action1() {
    when (it.type) {
      GetAreasResultType.SUCCESS -> v { "" }
      GetAreasResultType.ERROR -> v { "" }
    }
  }

}

