package people

import java.time.LocalDate

fun main() {
    data class Person(val name: String, val birthday: LocalDate)

    val people = listOf(
        Person("Miles", LocalDate.of(1979, 3, 28)),
        Person("Toby", LocalDate.of(1986, 4, 26)),
        Person("Tina", LocalDate.of(2011, 3, 11)),
    )

    val fortyYearsAgo = LocalDate.now().minusYears(40)
    val firstSinceThen = people
        // .asSequence()
        .map { person -> println(person); person.birthday }
        .filter { day -> println(day); day.isAfter(fortyYearsAgo) }
        .first()
}
