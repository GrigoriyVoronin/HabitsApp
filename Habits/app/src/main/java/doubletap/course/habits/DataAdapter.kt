package doubletap.course.habits

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import doubletap.course.habits.models.Habit
import doubletap.course.habits.models.HabitPriority
import kotlinx.android.synthetic.main.list_item.view.*

class DataAdapter(private var items: MutableList<Habit>) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false);
        return ViewHolder(view);
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun getHabit(position: Int): Habit {
        return items[position]
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val priorityToColor = mapOf(
            HabitPriority.High to Color.parseColor("#ff0000"),
            HabitPriority.Medium to Color.parseColor("#fde910"),
            HabitPriority.Low to Color.parseColor("#008000")
        )

        fun bind(habit: Habit) {
            val priorityColor = priorityToColor[habit.Priority] ?: R.color.green
            val typeStr = habit.Type.value
            val timesStr = when {
                habit.Times in 11..19 || habit.Times % 10 in 0..1 || habit.Times % 10 in 5..9 -> "раз"
                else -> "раза"
            }
            val periodStr = when {
                habit.Period in 11..19 -> "дней"
                habit.Period % 10 == 1 -> "день"
                habit.Period % 10 in 2..4 -> "дня"
                else -> "дней"
            }
            val repeatValue = "${habit.Times} $timesStr в ${habit.Period} $periodStr"

            itemView.habit_name.text = habit.Name
            itemView.habit_description.text = habit.Description
            itemView.habit_priority.setTextColor(priorityColor)
            itemView.habit_type.text = typeStr
            itemView.habit_repeat.text = repeatValue
        }

    }
}