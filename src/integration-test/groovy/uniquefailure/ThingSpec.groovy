package uniquefailure


import grails.test.mixin.integration.Integration
import grails.transaction.*
import org.hibernate.SessionFactory
import spock.lang.*

@Integration
@Rollback
class ThingSpec extends Specification {
    SessionFactory sessionFactory

    def setup() {
    }

    def cleanup() {
    }

    void "test insert"() {
        when:
            Thing thing1 = new Thing(hello: 1, world: 2).insert(flush: true)
            sessionFactory.currentSession.flush()
            Thing thing2 = new Thing(hello: 1, world: 2).insert(flush: true)

        then:
            !thing1.hasErrors()
            thing2.hasErrors()

    }

    void "test save"() {
        when:
            Thing thing1 = new Thing(hello: 1, world: 2).save(insert: true, flush: true)
            sessionFactory.currentSession.flush()
            Thing thing2 = new Thing(hello: 1, world: 2).save(insert: true, flush: true)

        then:
            !thing1.hasErrors()
            thing2.hasErrors()

    }

    void "test validate"() {
        when:
            Thing thing1 = new Thing(hello: 1, world: 2).save(insert: true, flush: true)
            sessionFactory.currentSession.flush()
            Thing thing2 = new Thing(hello: 1, world: 2)

        then:
            !thing1.hasErrors()
            !thing2.validate()
            thing2.errors.getFieldError('hello').code == 'unique'
    }
}
