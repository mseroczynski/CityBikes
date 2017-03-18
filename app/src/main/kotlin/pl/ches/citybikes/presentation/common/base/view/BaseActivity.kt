package pl.ches.citybikes.presentation.common.base.view

import android.content.Context
import android.os.Bundle
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.hannesdorfmann.mosby.mvp.MvpView
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
abstract class BaseActivity<V : MvpView, P : MvpPresenter<V>> : MvpActivity<V, P>() {

  protected abstract val layoutRes: Int

  protected abstract fun injectDependencies()

  override fun onCreate(savedInstanceState: Bundle?) {
    injectDependencies()
    super.onCreate(savedInstanceState)
    setContentView(layoutRes)
  }

  override fun attachBaseContext(newBase: Context) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
  }

}