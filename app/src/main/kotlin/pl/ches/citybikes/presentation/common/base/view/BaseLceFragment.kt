package pl.ches.citybikes.presentation.common.base.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.ches.citybikes.presentation.common.base.host.HostView
import pl.ches.citybikes.presentation.common.base.host.HostedMvpLceView
import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.hannesdorfmann.mosby.mvp.lce.MvpLceFragment
import pl.ches.citybikes.presentation.common.base.ErrorMessageDeterminer

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
abstract class BaseLceFragment<CV : View, M, V : HostedMvpLceView<M>, P : MvpPresenter<V>> : MvpLceFragment<CV, M, V, P>(), HostedMvpLceView<M> {

  override var hostView: HostView? = null

  protected abstract val layoutRes: Int

  protected abstract fun injectDependencies()

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    try {
      hostView = context as HostView
    } catch (e: ClassCastException) {
      throw ClassCastException(activity.toString() + " must implement ${HostView::class.java.simpleName}")
    }
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater!!.inflate(layoutRes, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    injectDependencies()
    super.onViewCreated(view, savedInstanceState)
  }

  override fun getErrorMessage(e: Throwable, pullToRefresh: Boolean): String {
    return ErrorMessageDeterminer.getErrorMessage(activity, e, pullToRefresh)
  }

}
