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

    void "test something"() {
        when:
            Thing thing1 = new Thing(hello: 1, world: 2).save(flush: true)
            sessionFactory.currentSession.flush()
            Thing thing2 = new Thing(hello: 1, world: 2).save(flush: true)

        then:
            !thing1.hasErrors()
            thing2.hasErrors()

    }
}
