import groovy.time.TimeCategory

setName 'Judith'
greet()

use(TimeCategory)  {
    println 1.minute.from.now
    println 10.hours.ago

    def someDate = new Date()
    println someDate - 3.months
}

email {
    from 'dsl-guru@mycompany.com'
    to 'john.doe@waitaminute.com'
    subject 'The pope has resigned!'
}