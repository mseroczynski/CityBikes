package pl.ches.citybikes.presentation.common.base.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
abstract class BaseSimpleFragment : Fragment() {

  protected abstract val layoutRes: Int

  protected abstract fun injectDependencies()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(layoutRes, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    injectDependencies()
    super.onViewCreated(view, savedInstanceState)
  }

}