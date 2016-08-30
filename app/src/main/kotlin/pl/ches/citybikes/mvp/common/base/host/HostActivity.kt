package pl.ches.citybikes.mvp.common.base.host

import android.support.v4.app.DialogFragment
import android.widget.Toast
import pl.ches.citybikes.mvp.common.base.host.enums.ToastKind
import pl.ches.citybikes.R
import pl.ches.citybikes.mvp.common.base.view.BaseActivity

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
abstract class HostActivity<V : HostView, P : HostPresenter<V>> : BaseActivity<V, P>(), HostView {

  //region HostView
  override fun showToast(toastKind: ToastKind) {
    val contentRes = when (toastKind) {
      ToastKind.SUCCESS -> R.string.toast_success
      ToastKind.ERROR -> R.string.toast_error
    }
    showToast(getString(contentRes, ""))
  }

  override fun showToast(content: String) = Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
  //endregion

  private fun showDialog(dialogFragment: DialogFragment, tag: String) {
    dialogFragment.show(supportFragmentManager, tag)
  }

  private fun dismissDialog(tag: String) {
    supportFragmentManager.findFragmentByTag(tag)?.let {
      if(it is DialogFragment) it.dismiss()
    }
  }

}