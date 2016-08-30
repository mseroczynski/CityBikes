package pl.ches.citybikes.mvp.screen.main

import pl.ches.citybikes.mvp.common.base.host.HostPresenter
import javax.inject.Inject

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class MainPresenter
@Inject
constructor() : HostPresenter<MainView>() {

  override fun attachView(view: MainView?) {
    super.attachView(view)
  }

}

