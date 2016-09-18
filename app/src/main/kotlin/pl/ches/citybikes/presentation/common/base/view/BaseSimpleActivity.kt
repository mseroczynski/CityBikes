package pl.ches.citybikes.presentation.common.base.view

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
abstract class BaseSimpleActivity : AppCompatActivity() {

  protected abstract fun injectDependencies()

  override fun onCreate(savedInstanceState: Bundle?) {
    injectDependencies()
    super.onCreate(savedInstanceState)
  }

  override fun attachBaseContext(newBase: Context) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
  }

}