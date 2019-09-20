package github.com.st235.lib_chartio.utils

typealias Listener<T> = (response: T) -> Unit

/**
 * Model to create observer pattern
 * Allows to subscribe and unsibscribe from data source
 * May be used as parent for a class or observers list field,
 * if inheritance is prohibited
 */
open class ObservableModel<T> {

    private val listeners: MutableList<Listener<T>> = mutableListOf()

    /**
     * Add listener to monitor model changes
     *
     * @param listener - smth that would listen updated
     */
    open fun addListener(listener: Listener<T>) {
        listeners.add(listener)
    }

    /**
     * Remove listener from list to stop
     * listen model changed and prevent leaks
     *
     * @param listener - smth have listened updated yet
     */
    open fun removeListener(listener: Listener<T>) {
        listeners.remove(listener)
    }

    /**
     * Notifies all the observers from list
     * that something have happened with result
     *
     * @param result - result of operation
     */
    fun notifyWith(result: T) {
        for (observer in listeners) {
            observer(result)
        }
    }
}
