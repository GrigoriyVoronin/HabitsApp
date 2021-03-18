package doubletap.course.habits.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import doubletap.course.habits.R
import doubletap.course.habits.models.Habit
import doubletap.course.habits.models.HabitPriority
import doubletap.course.habits.models.HabitType
import kotlinx.android.synthetic.main.activity_edit_habit.*


class EditHabitActivity: AppCompatActivity() {
    private val habitPriorities = mapOf(
        HabitPriority.Low to 0,
        HabitPriority.Medium to 1,
        HabitPriority.High to 2
    )

    private val priorityIndexToEnum = mapOf(
        HabitPriority.Low.value to HabitPriority.Low,
        HabitPriority.Medium.value to HabitPriority.Medium,
        HabitPriority.High.value to HabitPriority.High
    )

    private val habitTypeToId = mapOf (
        HabitType.Good to R.id.habit_type_good,
        HabitType.Bad to R.id.habit_type_bad
    )

    private var habitPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_edit_habit)
        val habit = intent.getParcelableExtra<Habit>("habit")
        val habitPosition = intent.getIntExtra("habitPosition", -1)
        this.habitPosition = habitPosition

        if (habit != null) {
            habit_name_edit.setText(habit.Name)
            habit_description_edit.setText(habit.Description)
            habit_times_edit.setText(habit.Times)
            habit_period_edit.setText(habit.Period)

            val selection = habitPriorities[habit.Priority]
            if (selection != null) {
                habit_priority_edit.setSelection(selection)
            }

            val habitTypeId = habitTypeToId[habit.Type] ?: R.id.habit_type_good
            val habitType = findViewById<RadioButton>(habitTypeId)
            habitType.isChecked = true

        }
    }

    fun onSave(view: View) {
        val priorityValue = priorityIndexToEnum[habit_priority_edit.selectedItem.toString()] ?: HabitPriority.High;

        val typeValue = when (habit_type_edit.checkedRadioButtonId) {
            R.id.habit_type_good -> HabitType.Good
            R.id.habit_type_bad -> HabitType.Bad
            else -> throw Throwable("Out of Range")
        }

        val habit = Habit(
                habit_name_edit.text?.toString() ?: "",
                habit_description_edit.text?.toString() ?: "",
            priorityValue,
            typeValue,
                habit_times_edit?.text?.toString()?.toInt() ?: 1,
                habit_period_edit?.text?.toString()?.toInt() ?: 1)

        val intent = Intent()
        intent.putExtra("habit", habit)
        intent.putExtra("habitPosition", habitPosition);
        setResult(RESULT_OK, intent)
        finish()
    }
}