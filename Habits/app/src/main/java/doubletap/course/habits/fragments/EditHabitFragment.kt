package doubletap.course.habits.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import doubletap.course.habits.R
import doubletap.course.habits.activities.MainActivity
import doubletap.course.habits.models.Habit
import doubletap.course.habits.models.HabitPeriod
import doubletap.course.habits.models.HabitPriority
import doubletap.course.habits.models.HabitType


class EditHabitFragment : Fragment() {
    companion object{
        fun newInstance(habit: Habit): EditHabitFragment {
            val fragment = EditHabitFragment()
            val bundle = Bundle()
            bundle.putParcelable(MainActivity.HABIT, habit)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var callback: EditHabitCallback? = null

    private var habitId: Int = -1
    private lateinit var nameEditor : EditText
    private lateinit var descriptionEditor : EditText
    private lateinit var timesEditor : EditText
    private lateinit var periodditor : Spinner
    private lateinit var priorityEditor : Spinner

    override fun onAttach(context: Context) {
        super.onAttach(context)

        callback = activity as EditHabitCallback
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_habit, container, false)
        initControls(view)
        var habit: Habit? = null
        arguments?.let {
            habit = it.getParcelable(MainActivity.HABIT)
            habitId = it.getInt(MainActivity.HABIT_ID, -1)
        }

        if (habit != null) {
            habitId = habit!!.Id
            nameEditor.setText(habit!!.Name)
            descriptionEditor.setText(habit!!.Description)
            timesEditor.setText(habit!!.Times.toString())
            priorityEditor.setSelection(habit!!.Priority.ordinal)
            periodditor.setSelection(habit!!.Period.ordinal)

            val habitTypeId = when (habit!!.Type) {
                HabitType.Good -> R.id.habit_type_good
                HabitType.Bad -> R.id.habit_type_bad
            }
            val habitType = view.findViewById<RadioButton>(habitTypeId)
            habitType.isChecked = true
        }

        return view
    }

    private fun initControls(view: View) {
        nameEditor = view.findViewById(R.id.habit_name_edit)
        descriptionEditor= view.findViewById(R.id.habit_description_edit)
        timesEditor = view.findViewById(R.id.habit_times_edit)
        priorityEditor = view.findViewById(R.id.habit_priority_edit)
        periodditor = view.findViewById(R.id.habit_period_edit)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fun onSave() {
            val priorityValue = HabitPriority.values()
                .first { x -> x.value == priorityEditor.selectedItem as String}
            val periodValue = HabitPeriod.values()
                .first { x -> x.value == periodditor.selectedItem as String }
            val typeValue = when (view.findViewById<RadioGroup>(R.id.habit_type_edit).checkedRadioButtonId) {
                R.id.habit_type_good -> HabitType.Good
                R.id.habit_type_bad -> HabitType.Bad
                else -> throw IndexOutOfBoundsException()
            }

            val habit = Habit(
                habitId,
                nameEditor.text?.toString() ?: "",
                descriptionEditor.text?.toString() ?: "",
                priorityValue,
                typeValue,
                timesEditor.text?.toString()?.toInt() ?: 1,
                periodValue)

            callback?.onSaveHabit(habit)
        }

        view.findViewById<Button>(R.id.save_habit_button).setOnClickListener {
            onSave()
        }
    }
}

interface EditHabitCallback {
    fun onSaveHabit(habit: Habit)
}