package pl.ches.citybikes.presentation.common.navigation

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

/**
 * @author Michał Seroczyński <michal.seroczynski@gmail.com>
 */
object Fragments {

  object Args {
  }

  class Operator
  private constructor(activity: FragmentActivity) {
    private val fragmentManager: FragmentManager
    private var enterRes: Int = 0
    private var exitRes: Int = 0

    init {
      this.fragmentManager = activity.supportFragmentManager
    }

    private val fragmentTransaction: FragmentTransaction
      get() {
        if (enterRes != 0 && exitRes != 0) {
          return fragmentManager.beginTransaction().setCustomAnimations(enterRes, exitRes)
        } else {
          return fragmentManager.beginTransaction()
        }
      }

    fun withCustomAnimations(enterRes: Int, exitRes: Int): Operator {
      this.enterRes = enterRes
      this.exitRes = exitRes
      return this
    }

    fun reset(fragment: Fragment, fragmentContainerId: Int) {
      fragmentTransaction.replace(fragmentContainerId, fragment).commit()
    }

    operator fun set(fragment: Fragment, fragmentContainerId: Int) {
      if (!isSet(fragmentContainerId)) {
        fragmentTransaction.add(fragmentContainerId, fragment).commit()
      }
    }

    private fun isSet(fragmentContainerId: Int): Boolean {
      return get(fragmentContainerId) != null
    }

    private operator fun get(fragmentContainerId: Int): Fragment? {
      return fragmentManager.findFragmentById(fragmentContainerId)
    }

    fun remove(fragmentContainerId: Int) {
      if (isSet(fragmentContainerId)) {
        fragmentTransaction.remove(get(fragmentContainerId)).commit()
      }
    }

    fun hide(fragmentContainerId: Int) {
      if (isSet(fragmentContainerId)) {
        fragmentTransaction.hide(get(fragmentContainerId)).commit()
      }
    }

    fun show(fragmentContainerId: Int) {
      if (isSet(fragmentContainerId)) {
        fragmentTransaction.show(get(fragmentContainerId)).commit()
      }
    }

    companion object {
      fun at(activity: FragmentActivity): Operator {
        return Operator(activity)
      }
    }
  }
}
