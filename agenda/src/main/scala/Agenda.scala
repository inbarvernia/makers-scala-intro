class Agenda (val meetings: List[Meeting]) {

  def printDaySchedule(day: String): Unit = {
    val meetingsForTheDay = meetings.filter(_.day == day)
    val timeInt = (meeting: Meeting) => meeting.time.dropRight(2).toInt
    val sortByMeetingTime = (meetings: List[Meeting]) => meetings.sortWith((meet1, meet2) => timeInt(meet1) < timeInt(meet2))
    val filterByTimeOfDay = (meetings: List[Meeting], time: String) => meetings.filter(_.time.takeRight(2) == time)
    if (meetingsForTheDay.length == 0) {
      println(s"There are no meeting on $day.")
    } else {
      val morningMeetings = filterByTimeOfDay(meetingsForTheDay, "am")
      val afternoonMeetings = filterByTimeOfDay(meetingsForTheDay, "pm")
      if (morningMeetings.length >= 1) {
        println(s"$day morning:")
        for (meeting <- sortByMeetingTime(morningMeetings)) println(s"  ${meeting.time}: ${meeting.name}")
      }
      if (afternoonMeetings.length >= 1) {
        println(s"$day afternoon:")
        for (meeting <- sortByMeetingTime(afternoonMeetings)) println(s"  ${meeting.time}: ${meeting.name}")
      }
    }

  }

}

class Meeting (val name: String, val day: String, val time: String)

object Main extends App {
  val m1 = new Meeting("Retro", "Friday", "5pm")
  val m2 = new Meeting("Yoga", "Tuesday", "10am")
  val m3 = new Meeting("Team Meeting", "Tuesday", "3pm")
  val m4 = new Meeting("Code Review", "Tuesday", "9am")
  val m5 = new Meeting(name = "Phonecall", day="Tuesday", time="11am")
  val agenda = new Agenda(List(m1, m2, m3, m4, m5))
  agenda.printDaySchedule("Monday")
  agenda.printDaySchedule("Tuesday")
}
