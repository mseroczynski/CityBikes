package pl.ches.citybikes.presentation.common.base.host

import pl.ches.citybikes.presentation.common.base.presenter.MvpRxPresenter

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
abstract class HostPresenter<V : HostView>() : MvpRxPresenter<V>()