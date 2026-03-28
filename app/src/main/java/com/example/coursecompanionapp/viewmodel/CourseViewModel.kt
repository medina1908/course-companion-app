package com.example.coursecompanionapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.coursecompanionapp.model.Course
import com.example.coursecompanionapp.model.Note
import com.example.coursecompanionapp.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CourseViewModel : ViewModel() {

    private val _courses = MutableStateFlow<List<Course>>(
        listOf(Course(1, "Mobile Programming", "Prof. Vatres", 6, "#4CAF50"),
            Course(2, "Web Development", "Prof. Mehanovic", 6, "#2196F3"),
            Course(3, "Data System", "Prof. Isakovic", 5, "#FF9800")
        )
    )
    val courses: StateFlow<List<Course>> = _courses.asStateFlow()

    private val _tasks = MutableStateFlow<List<Task>>(
        listOf(
            Task(1, "Assignment 1 - UI Foundations", 1, "29.03.2026", false),
            Task(2, "Database Project", 2, "15.04.2026", false),
            Task(3, "Midterm Exam", 3, "10.04.2026", true)
        )
    )
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    private val _notes = MutableStateFlow<List<Note>>(
        listOf(
            Note(1, "Jetpack Compose Basics", "Column, Row, Box are main layout elements...", 1, "25.03.2026"),
            Note(2, "SQL JOIN Types", "INNER JOIN, LEFT JOIN, RIGHT JOIN...", 2, "26.03.2026"),
            Note(3, "Kotlin Functions", "Functions in Kotlin are first class citizens...", 1, "24.03.2026"),
            Note(4, "MVVM Architecture", "Model View ViewModel pattern separates UI from logic...", 2, "23.03.2026"),
            Note(5, "Algorithms Basics", "Sorting algorithms include bubble sort, merge sort...", 3, "22.03.2026"),
            Note(6, "Database Normalization", "1NF, 2NF, 3NF are normalization forms...", 2, "21.03.2026")
        )
    )
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    private val _courseName = MutableStateFlow("")
    val courseName: StateFlow<String> = _courseName.asStateFlow()

    private val _professor = MutableStateFlow("")
    val professor: StateFlow<String> = _professor.asStateFlow()

    private val _credits = MutableStateFlow("")
    val credits: StateFlow<String> = _credits.asStateFlow()

    fun onCourseNameChange(value: String) { _courseName.value = value }
    fun onProfessorChange(value: String) { _professor.value = value }
    fun onCreditsChange(value: String) {
        if (value.all { it.isDigit() }) _credits.value = value
    }

    fun addCourse() {
        if (_courseName.value.isBlank() || _professor.value.isBlank() || _credits.value.isBlank()) return
        val newCourse = Course(
            id = (_courses.value.size + 1),
            name = _courseName.value.trim(),
            professor = _professor.value.trim(),
            credits = _credits.value.toIntOrNull() ?: 0
        )
        _courses.value = _courses.value + newCourse
        _courseName.value = ""
        _professor.value = ""
        _credits.value = ""
    }

    fun toggleTaskCompleted(taskId: Int) {
        _tasks.value = _tasks.value.map { task ->
            if (task.id == taskId) task.copy(isCompleted = !task.isCompleted)
            else task
        }
    }

    fun isFormValid(): Boolean {
        return _courseName.value.isNotBlank() &&
                _professor.value.isNotBlank() &&
                _credits.value.isNotBlank()
    }

    private val _noteTitle = MutableStateFlow("")
    val noteTitle: StateFlow<String> = _noteTitle.asStateFlow()

    private val _noteContent = MutableStateFlow("")
    val noteContent: StateFlow<String> = _noteContent.asStateFlow()

    fun onNoteTitleChange(value: String) { _noteTitle.value = value }
    fun onNoteContentChange(value: String) { _noteContent.value = value }

    fun addNote() {
        if (_noteTitle.value.isBlank() || _noteContent.value.isBlank()) return
        val newNote = Note(
            id = (_notes.value.size + 1),
            title = _noteTitle.value.trim(),
            content = _noteContent.value.trim(),
            courseId = 1,
            date = "27.03.2026"
        )
        _notes.value = _notes.value + newNote
        _noteTitle.value = ""
        _noteContent.value = ""
    }

    fun isNoteFormValid(): Boolean {
        return _noteTitle.value.isNotBlank() && _noteContent.value.isNotBlank()
    }
    private val _taskTitle = MutableStateFlow("")
    val taskTitle: StateFlow<String> = _taskTitle.asStateFlow()

    private val _taskDueDate = MutableStateFlow("")
    val taskDueDate: StateFlow<String> = _taskDueDate.asStateFlow()

    fun onTaskTitleChange(value: String) { _taskTitle.value = value }
    fun onTaskDueDateChange(value: String) { _taskDueDate.value = value }

    fun isTaskFormValid(): Boolean {
        return _taskTitle.value.isNotBlank() && _taskDueDate.value.isNotBlank()
    }

    fun addTask() {
        if (_taskTitle.value.isBlank() || _taskDueDate.value.isBlank()) return
        val newTask = Task(
            id = (_tasks.value.size + 1),
            title = _taskTitle.value.trim(),
            courseId = 1,
            dueDate = _taskDueDate.value.trim(),
            isCompleted = false
        )
        _tasks.value = _tasks.value + newTask
        _taskTitle.value = ""
        _taskDueDate.value = ""
    }
}
