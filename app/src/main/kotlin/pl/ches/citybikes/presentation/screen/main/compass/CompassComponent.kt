package pl.ches.citybikes.presentation.screen.main.compass

import dagger.Subcomponent

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
@Subcomponent
interface CompassComponent {

  fun inject(target: CompassFragment)

  fun presenter(): CompassPresenter

}
