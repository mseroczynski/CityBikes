package pl.ches.citybikes.mvp.screen.main

import pl.ches.citybikes.data.api.cb.CityBikesApiService
import pl.ches.citybikes.data.disk.entity.Area
import pl.ches.citybikes.data.disk.entity.Station
import pl.ches.citybikes.mvp.common.base.host.HostPresenter
import rx.Observable
import rx.functions.FuncN
import v
import java.util.*
import javax.inject.Inject

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
class MainPresenter
@Inject
constructor(private val cityBikesApiService: CityBikesApiService) : HostPresenter<MainView>() {

  override fun attachView(view: MainView?) {
    super.attachView(view)
    // TODO under development
//    cityBikesApiService.getAreas()
//        .compose(applyScheduler<List<Area>>())
//        .subscribe({
//          val observables = ArrayList<Observable<*>>()
//
//          it.forEach {
//            observables.add(    cityBikesApiService.getStations(it.id)
//                .compose(applyScheduler<List<Station>>()))
//          }
//
//          Observable.zip(observables, {})
//              .subscribe {
//                v { "$it" }
//
//              }
//
//          v { "$it" }
//        },{
//          v { "$it" }
//        },{
//          v { "" }
//        })
  }

}

