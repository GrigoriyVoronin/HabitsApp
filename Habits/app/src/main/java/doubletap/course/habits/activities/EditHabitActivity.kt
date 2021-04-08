package doubletap.course.habits.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import doubletap.course.habits.R
import doubletap.course.habits.fragments.EditHabitFragment


class EditHabitActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_habit)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.activity_edit_habit, EditHabitFragment())
                .commit()
        }
    }
}