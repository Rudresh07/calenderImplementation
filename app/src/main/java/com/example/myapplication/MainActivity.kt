package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.CalendarView
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var calenderView: CalendarView
    private var selectedDate:LocalDate? =null
    val daysOfWeek = daysOfWeek()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        calenderView = binding.calendarView

        binding.HourPicker.minValue = 1
        binding.HourPicker.maxValue = 12
        binding.HourPicker.value = 12

        binding.MinutePicker.minValue = 0
        binding.MinutePicker.maxValue = 59
       binding.MinutePicker.value = 0

        binding.AmPmPicker.minValue = 0
        binding.AmPmPicker.maxValue = 1
        binding.AmPmPicker.displayedValues = arrayOf("AM", "PM")
        binding.AmPmPicker.value = 0


        //setup the listener to handle calender item click events
        calenderView.dayBinder = object: MonthDayBinder<DayViewContainer>{
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.textView.text = data.date.dayOfMonth.toString()

                container.textView.setOnClickListener {
                    //Handle day click event
                    val selectedDate = data.date
                }
            }

            override fun create(view: View): DayViewContainer {
                return DayViewContainer(LayoutInflater.from(view.context).inflate(R.layout.calendar_day_layout, null))
            }

        }

        calenderView.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthTitleViewContainer> {
            override fun bind(container: MonthTitleViewContainer, data: CalendarMonth) {
                container.textView.text = data.yearMonth.month.name
            }

            override fun create(view1: View): MonthTitleViewContainer {
                return MonthTitleViewContainer(LayoutInflater.from(view1.context).inflate(R.layout.calender_month_title, null))
            }

        }

        // Set up other configurations as needed
        // For example, set the start and end dates of the calendar
        calenderView.setup(
            YearMonth.of(2024,Month.JANUARY),
            YearMonth.of(2024,Month.DECEMBER),
            firstDayOfWeek = daysOfWeek().first()
        )


        calenderView.scrollToMonth(YearMonth.now())



    }
    class DayViewContainer(view: View) : ViewContainer(view) {
        val textView: TextView = view.findViewById(R.id.calendarDayText)
    }

    class MonthTitleViewContainer(view1: View) : ViewContainer(view1){
        val textView: TextView = view1.findViewById(R.id.monthTitleText)
    }


}



