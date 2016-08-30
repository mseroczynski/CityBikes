package pl.ches.citybikes.mvp.common.base.host

import pl.ches.citybikes.mvp.common.base.presenter.MvpRxPresenter

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
abstract class HostPresenter<V : HostView>() : MvpRxPresenter<V>()