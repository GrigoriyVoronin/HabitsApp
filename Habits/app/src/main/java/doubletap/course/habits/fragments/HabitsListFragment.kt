package doubletap.course.habits.fragments

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import doubletap.course.habits.DataAdapter
import doubletap.course.habits.R
import doubletap.course.habits.activities.MainActivity
import doubletap.course.habits.models.Habit
import doubletap.course.habits.models.HabitType

class HabitsListFragment : Fragment() {
    companion object {
        fun newInstance(habitType: HabitType): HabitsListFragment {
            val fragment = HabitsListFragment()
            val bundle = Bundle()
            bundle.putSerializable(MainActivity.HABIT_TYPE, habitType)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var callback: HabitsListCallBack? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var habitType: HabitType
    private var habits = mutableListOf<Habit>()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        callback = activity as HabitsListCallBack
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            habits = savedInstanceState.getParcelableArrayList<Parcelable>(MainActivity.HABITS_ARR) as MutableList<Habit>
            habitType = savedInstanceState.getSerializable(MainActivity.HABIT_TYPE) as HabitType
        }
        else {
            arguments?.let {
                habitType = it.getSerializable(MainActivity.HABIT_TYPE) as HabitType
            }
        }

        viewAdapter = DataAdapter(habits)
        (viewAdapter as DataAdapter).setOnItemClickListener { v -> onEditHabit(v) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_habits_list, container, false)

        viewManager = LinearLayoutManager(context)
        viewAdapter = DataAdapter(habits)
        recyclerView = view.findViewById(R.id.habits_list_recycler_view)
        recyclerView.layoutManager = viewManager
        recyclerView.adapter = viewAdapter

        val button = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        button.setOnClickListener {
            callback?.onAddHabit()
        }

        var habits: List<Habit>? = null
        arguments?.let {
            habits = it.getParcelableArrayList(MainActivity.HABITS_ARR)
        }

        if (habits != null) {
            this.habits.clear()
            this.habits.addAll(habits!!)
            viewAdapter.notifyDataSetChanged()
        }

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelableArrayList(MainActivity.HABITS_ARR, ArrayList<Parcelable>(habits))
        outState.putSerializable(MainActivity.HABIT_TYPE, habitType)
    }

    private fun onEditHabit(view: View?) {
        if (view != null) {
            val position = viewManager.getPosition(view)
            val habit = (viewAdapter as DataAdapter).getHabitByPosition(position)
            callback?.onEditHabit(habit)
        }
    }
}

interface HabitsListCallBack {
    fun onAddHabit()

    fun onEditHabit(habit: Habit)
}