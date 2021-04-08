package doubletap.course.habits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import doubletap.course.habits.models.Habit
import kotlinx.android.synthetic.main.list_item.view.*

class DataAdapter(private var habits: MutableList<Habit>) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {
    private var currentMaxId = -1
    private lateinit var clickListener: View.OnClickListener

    fun setOnItemClickListener(clickListener: View.OnClickListener) {
        this.clickListener = clickListener

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        currentMaxId = habits.maxOfOrNull { x -> x.Id} ?: 0
        return ViewHolder(view)
    }

    fun getNextItemId() = ++currentMaxId

    override fun getItemCount() = habits.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getHabitByPosition(position))
    }

    fun getHabitByPosition(habitPosition: Int) = habits[habitPosition]

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(habit: Habit) {
            itemView.setOnClickListener(clickListener)
            val timesStr = when {
                habit.Times in 11..19 || habit.Times % 10 in 0..1 || habit.Times % 10 in 5..9 -> itemView.context.getString(R.string.repeat_times)
                else -> itemView.context.getString(R.string.repeat_time)
            }


            itemView.habit_name.text = habit.Name
            itemView.habit_description.text = habit.Description
            itemView.habit_priority.setTextColor(habit.Priority.color)
            itemView.habit_type.text = habit.Type.value
            itemView.habit_repeat.text = "${habit.Times} $timesStr ${habit.Period.value}"
        }
    }
}