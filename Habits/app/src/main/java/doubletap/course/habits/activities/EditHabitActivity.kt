package doubletap.course.habits.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import doubletap.course.habits.R
import doubletap.course.habits.models.Habit
import doubletap.course.habits.models.HabitPeriod
import doubletap.course.habits.models.HabitPriority
import doubletap.course.habits.models.HabitType
import kotlinx.android.synthetic.main.activity_edit_habit.*


class EditHabitActivity: AppCompatActivity() {
    private val habitTypeToId = mapOf (
        HabitType.Good to R.id.habit_type_good,
        HabitType.Bad to R.id.habit_type_bad,
    )

    private var habitId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_habit)

        val habit = intent.getParcelableExtra<Habit>(MainActivity.HABIT)

        if (habit != null) {
            habitId = habit.Id
            habit_name_edit.setText(habit.Name)
            habit_description_edit.setText(habit.Description)
            habit_times_edit.setText(habit.Times.toString())
            habit_priority_edit.setSelection(habit.Priority.ordinal)
            habit_period_edit.setSelection(habit.Period.ordinal)

            val habitTypeId = habitTypeToId[habit.Type] ?: R.id.habit_type_good
            val habitType = findViewById<RadioButton>(habitTypeId)
            habitType.isChecked = true
        }
        else {
            habitId = intent.getIntExtra(MainActivity.HABIT_ID, -1)
            if (habitId == -1)
                throw IndexOutOfBoundsException()
        }
    }

    fun onSave(view: View) {
        val priorityValue = HabitPriority.values()
                .first { x -> x.value == habit_priority_edit.selectedItem }
        val periodValue = HabitPeriod.values()
                .first { x -> x.value == habit_period_edit.selectedItem }
        val typeValue = when (habit_type_edit.checkedRadioButtonId) {
            R.id.habit_type_good -> HabitType.Good
            R.id.habit_type_bad -> HabitType.Bad
            else -> throw IndexOutOfBoundsException()
        }

        val habit = Habit(
                habitId,
                habit_name_edit.text?.toString() ?: "",
                habit_description_edit.text?.toString() ?: "",
            priorityValue,
            typeValue,
                habit_times_edit?.text?.toString()?.toInt() ?: 1,
                periodValue)

        val intent = Intent()
        intent.putExtra(MainActivity.HABIT, habit)
        setResult(RESULT_OK, intent)
        finish()
    }
}