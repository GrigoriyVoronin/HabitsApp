package doubletap.course.habits.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import doubletap.course.habits.R
import doubletap.course.habits.fragments.*
import doubletap.course.habits.models.Habit
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), HabitsListCallBack, EditHabitCallback, NavigationView.OnNavigationItemSelectedListener {
    companion object{
        const val HABITS_ARR = "HabitsArr"
        const val HABIT = "Habit"
        const val HABIT_ID = "HabitId"
        const val HABITS_LIST_TAG = "HabitsListFragment"
        const val EDIT_HABIT_TAG = "EditHabitFragment"
        const val ABOUT_TAG = "AboutPageFragment"
        const val HABIT_TYPE = "HabitType"
    }

    private val viewPagerFragment = ViewPagerFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView = findViewById<NavigationView>(R.id.navigation_drawer)
        navView.setNavigationItemSelectedListener(this)
        if (savedInstanceState == null)
            setFragment(viewPagerFragment, HABITS_LIST_TAG)
    }

    override fun onAddHabit() {
        setFragment(EditHabitFragment(), EDIT_HABIT_TAG)
    }

    override fun onEditHabit(habit: Habit) {
        val fragment =EditHabitFragment.newInstance(habit)
        setFragment(fragment, EDIT_HABIT_TAG)
    }

    override fun onSaveHabit(habit: Habit) {
        val bundle = Bundle()
        bundle.putParcelable(HABIT, habit)
        viewPagerFragment.arguments = bundle
        onBackPressed()
    }

    private fun setFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        val fragment = selectDrawerItem(menuItem.itemId)
        val tag = selectDrawerItemTag(menuItem.itemId)
        setFragment(fragment, tag)
        main_activity.closeDrawer(GravityCompat.START)
        return true
    }

    private fun selectDrawerItem(itemId: Int): Fragment {
        return when (itemId) {
            R.id.menu_item_main -> viewPagerFragment
            R.id.menu_item_about -> AboutPageFragment()
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun selectDrawerItemTag(itemId: Int): String {
        return when (itemId) {
            R.id.menu_item_main -> HABITS_LIST_TAG
            R.id.menu_item_about -> ABOUT_TAG
            else -> throw IndexOutOfBoundsException()
        }
    }
}
