import kotlinx.coroutines.*

val personList = mutableListOf<Person>()
val resultList = mutableMapOf<Person, Int>()

suspend fun main() {
    var isActive = true
    println("Программа работы с базой данных сотрудников")
    delay(1000L)
    while (isActive) {
        if (personList.isEmpty()) {
            println("Добавить сотрудника:\n1.Да\n2.Нет")
        } else {
            println("Добавить сотрудника:\n1.Да\n2.Прочитать базу данных")
        }
        val choice = readln()
        if (choice == "1") {
            println("Введите имя сотрудника:")
            val name = readln()
            println("Введите зарплату сотрудника:")
            val salary = readln()
            PersonManager().addPerson(Person(name, salary.toInt()))
        } else {
            println(personList)
            isActive = false
        }
    }
    coroutineScope {
        val job1 = launch {
            personList.forEach { addPassword(it) }
            readDataPersonList()
        }
        val job2 = launch {
            if (readln() == "0") job1.cancelAndJoin()
        }
        job1.start()
        job2.start()
    }
    println("Завершение полной работы")
}

suspend fun addPassword(person: Person) {
    delay(500L)
    var password = (0..9).random().toString()
    repeat(5) {password += (0..9).random()}
    resultList[person] = password.toInt()
}

suspend fun readDataPersonList() {
    resultList.forEach {
        delay(1000L)
        println("Сотрудник: ${it.key}; пароль: ${it.value}")
    }
}
