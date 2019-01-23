import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity

/*Extensions for adding fragment on Main Activity, haven't tested them yet*/

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}