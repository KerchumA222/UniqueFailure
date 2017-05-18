package uniquefailure

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = ['hello', 'world'])
class Thing implements Serializable {
    Long hello
    Long world

    static constraints = {
        hello unique: 'world'
    }
    static mapping = {
        version false
        id composite: ['hello', 'world']
    }
}
