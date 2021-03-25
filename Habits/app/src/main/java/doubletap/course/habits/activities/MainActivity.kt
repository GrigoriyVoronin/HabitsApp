package doubletap.course.habits.activities

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import doubletap.course.habits.DataAdapter
import doubletap.course.habits.R
import doubletap.course.habits.models.Habit
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object{
        const val HABITS_ARR = "HabitsArr"
        const val HABIT = "Habit"
        const val HABIT_ID = "HabitId"
    }

    private lateinit var viewAdapter: DataAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var habits = mutableListOf<Habit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null)
            habits = savedInstanceState.getParcelableArrayList<Parcelable>(HABITS_ARR) as MutableList<Habit>

        viewManager = LinearLayoutManager(this)
        viewAdapter = DataAdapter(habits)

        habits_list_recycler_view.layoutManager = viewManager
        habits_list_recycler_view.adapter = viewAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val habit = data?.getParcelableExtra<Habit>(HABIT)
        if (habit != null) {
            if (requestCode == 1) {
                habits.add(habit)
                viewAdapter.notifyItemInserted(habits.size - 1)
            }
            if (requestCode == 2) {
                val oldHabit = habits.first { x -> x.Id == habit.Id }
                val habitPosition = habits.indexOf(oldHabit)
                habits[habitPosition] = habit
                viewAdapter.notifyItemChanged(habitPosition)
            }
        }
    }

    fun onAddHabit(view: View) {
        val intent = Intent(this, EditHabitActivity::class.java)
        intent.putExtra(HABIT_ID, viewAdapter.getNextItemId())
        startActivityForResult(intent, 1)
    }

    fun onEditHabit(view: View) {
        val intent = Intent(this, EditHabitActivity::class.java)
        val position = viewManager.getPosition(view)
        val habit = viewAdapter.getHabitByPosition (position)
        intent.putExtra(HABIT, habit)
        startActivityForResult(intent, 2)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelableArrayList(HABITS_ARR, ArrayList<Parcelable>(habits));
    }
}
