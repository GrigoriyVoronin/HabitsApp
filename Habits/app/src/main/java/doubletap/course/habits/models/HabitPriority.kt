package doubletap.course.habits.models

import doubletap.course.habits.R

enum class HabitPriority(val value: String, val color: Int) {
    Low("Низкий", R.color.green),
    Medium("Средний", R.color.yellow),
    High("Высокий", R.color.red)
}