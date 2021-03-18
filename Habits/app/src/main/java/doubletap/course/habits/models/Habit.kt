package doubletap.course.habits.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Habit(
    var Name: String,
    var Description: String,
    var Priority: HabitPriority,
    var Type: HabitType,
    var Times: Int,
    var Period: Int,
): Parcelable {
}
