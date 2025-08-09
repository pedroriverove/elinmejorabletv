package com.elinmejorabletv.ui.mobile.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class ScheduleViewModel : ViewModel() {

    private val _scheduleState = MutableLiveData<ScheduleState>()
    val scheduleState: LiveData<ScheduleState> = _scheduleState

    private val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    private val trackNames = listOf(
        "Gulfstream Park", "Santa Anita", "Churchill Downs", "Belmont Park",
        "Saratoga", "Aqueduct", "Hipódromo de las Américas", "Hipódromo de San Isidro",
        "Hipódromo Chile", "La Rinconada", "Palermo", "Ascot", "Longchamp"
    )

    private val locations = listOf(
        "Florida, USA", "California, USA", "Kentucky, USA", "New York, USA",
        "New York, USA", "New York, USA", "México DF, MEX", "Buenos Aires, ARG",
        "Santiago, CHI", "Caracas, VEN", "Buenos Aires, ARG", "London, UK", "Paris, FRA"
    )

    init {
        loadScheduleForToday()
    }

    fun loadScheduleForToday() {
        val calendar = Calendar.getInstance()
        loadScheduleForDate(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    fun loadScheduleForDate(year: Int, month: Int, dayOfMonth: Int) {
        viewModelScope.launch {
            _scheduleState.value = ScheduleState.Loading

            // Simulate network delay
            delay(1000)

            try {
                val calendar = Calendar.getInstance()
                calendar.set(year, month, dayOfMonth)
                val date = calendar.time
                val formattedDate = dateFormat.format(date)

                // For demo purposes, we'll generate random schedule data
                val currentDate = Calendar.getInstance().time

                // If selected date is today or in the future, generate active tracks
                val isCurrentOrFutureDate = !date.before(currentDate) || isSameDay(date, currentDate)

                if (isCurrentOrFutureDate) {
                    val eventCount = Random.nextInt(5, 13)
                    val scheduledTracks = generateScheduledTracks(date, eventCount)

                    _scheduleState.value = ScheduleState.Success(
                        formattedDate = formattedDate,
                        eventCount = eventCount,
                        scheduledTracks = scheduledTracks
                    )
                } else {
                    _scheduleState.value = ScheduleState.NoEvents(
                        formattedDate = formattedDate,
                        message = "No hay eventos programados para esta fecha pasada"
                    )
                }
            } catch (e: Exception) {
                _scheduleState.value = ScheduleState.Error(
                    message = e.message ?: "Error desconocido al cargar la programación"
                )
            }
        }
    }

    private fun isSameDay(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance().apply { time = date1 }
        val cal2 = Calendar.getInstance().apply { time = date2 }
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

    private fun generateScheduledTracks(date: Date, count: Int): List<ScheduledTrack> {
        val calendar = Calendar.getInstance().apply { time = date }
        calendar.set(Calendar.HOUR_OF_DAY, 12)  // Start at noon

        return List(count) { index ->
            val trackIndex = index % trackNames.size
            val trackId = "TRACK${trackIndex+1}"

            // Set start time between 12:00 and 20:00
            calendar.set(Calendar.HOUR_OF_DAY, 12 + index % 9)
            calendar.set(Calendar.MINUTE, (index * 7) % 60)
            val startTime = calendar.time

            // Set end time 2-3 hours later
            calendar.add(Calendar.HOUR_OF_DAY, 2 + index % 2)
            val endTime = calendar.time

            // Reset calendar for next iteration
            calendar.time = date

            ScheduledTrack(
                id = trackId,
                trackName = trackNames[trackIndex],
                location = locations[trackIndex],
                imageUrl = "https://example.com/logos/$trackId.png",
                startTime = startTime,
                endTime = endTime,
                racesCount = 8 + index % 5,
                isAvailable = index % 4 != 3  // 75% available
            )
        }.sortedBy { it.startTime }
    }

    sealed class ScheduleState {
        object Loading : ScheduleState()
        data class Success(
            val formattedDate: String,
            val eventCount: Int,
            val scheduledTracks: List<ScheduledTrack>
        ) : ScheduleState()
        data class NoEvents(val formattedDate: String, val message: String) : ScheduleState()
        data class Error(val message: String) : ScheduleState()
    }
}