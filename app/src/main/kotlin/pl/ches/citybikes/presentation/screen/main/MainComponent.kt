package pl.ches.citybikes.presentation.screen.main

import dagger.Subcomponent

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@Subcomponent
interface MainComponent {

  fun inject(target: MainActivity)

  fun presenter(): MainPresenter

}
