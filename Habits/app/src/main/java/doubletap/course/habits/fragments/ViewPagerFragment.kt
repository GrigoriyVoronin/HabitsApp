package doubletap.course.habits.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import doubletap.course.habits.R
import doubletap.course.habits.activities.MainActivity
import doubletap.course.habits.models.Habit
import doubletap.course.habits.models.HabitType

class ViewPagerFragment : Fragment() {
    private val goodHabits = mutableListOf<Habit>()
    private val badHabits = mutableListOf<Habit>()
    val goodListFragment = HabitsListFragment.newInstance(HabitType.Good)
    val badListFragment =  HabitsListFragment.newInstance(HabitType.Bad)
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_pager, container, false)
        viewPager = view.findViewById(R.id.view_pager)
        pagerAdapter = ViewPagerAdapter(childFragmentManager)
        viewPager.adapter = pagerAdapter

        view.findViewById<TabLayout>(R.id.tab_layout)
                .setupWithViewPager(viewPager)

        var habit: Habit? = null
        arguments?.let {
            habit = it.getParcelable(MainActivity.HABIT)
        }

        if (habit != null) {
            when (habit!!.Type) {
                HabitType.Good -> {
                    goodHabits.add(habit!!)
                }
                HabitType.Bad -> {
                    badHabits.add(habit!!)
                }
            }

            val bundleWithBadHabits = Bundle()
            val bundleWithGoodHabits = Bundle()
            bundleWithBadHabits.putParcelableArrayList(MainActivity.HABITS_ARR, ArrayList<Habit>(badHabits))
            bundleWithGoodHabits.putParcelableArrayList(MainActivity.HABITS_ARR, ArrayList<Habit>(goodHabits))

            goodListFragment.arguments = bundleWithGoodHabits
            badListFragment.arguments = bundleWithBadHabits
        }

        return view
    }

    inner class ViewPagerAdapter(fragmentManager: FragmentManager)
        : FragmentStatePagerAdapter(fragmentManager) {

        override fun getCount(): Int {
            return 2
        }

        override fun getItem(position: Int): Fragment {
            return when(position) {
                0 -> goodListFragment
                1 -> badListFragment
                else -> throw IndexOutOfBoundsException()
            }
        }

        override fun getPageTitle(position: Int): CharSequence {
            return when(position) {
                0 -> "Хорошие"
                1 -> "Плохие"
                else -> throw IndexOutOfBoundsException()
            }
        }
    }
}