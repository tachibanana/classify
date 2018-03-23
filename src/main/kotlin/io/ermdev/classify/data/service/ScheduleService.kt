package io.ermdev.classify.data.service

import io.ermdev.classify.data.entity.Schedule
import io.ermdev.classify.data.repository.ScheduleRepository
import io.ermdev.classify.exception.EntityException
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

@Service
class ScheduleService(private val scheduleRepository: ScheduleRepository) {

    fun findAll(): List<Schedule> = scheduleRepository.findAll()

    fun findById(id: Long): Schedule {
        return scheduleRepository.findById(id)
                .orElseThrow { EntityException("No schedule entity with id $id exists!") }
    }

    fun save(schedule: Schedule) {
        if (StringUtils.isEmpty(schedule.day)) {
            throw EntityException("day cannot be empty")
        }
        when(schedule.day.toLowerCase().trim()) {
            "monday", "1" -> schedule.day = "Monday"
            "tuesday", "2" -> schedule.day = "Tuesday"
            "wednesday", "3" -> schedule.day = "Wednesday"
            "thursday", "4" -> schedule.day = "Thursday"
            "friday", "5" -> schedule.day = "Friday"
            "saturday", "6" -> schedule.day = "Saturday"
            "sunday", "7", "0" -> schedule.day = "Sunday"
             else -> throw EntityException("day must be valid")
        }
        if (StringUtils.isEmpty(schedule.room)) {
            throw EntityException("room cannot be empty")
        }
        if (!schedule.room.matches(Regex("^[a-zA-Z0-9]+( [a-zA-Z0-9]+)*$"))) {
            throw EntityException("room cannot contain special characters")
        }
        scheduleRepository.save(schedule)
    }

    fun deleteById(id: Long) = scheduleRepository.deleteById(id)
}