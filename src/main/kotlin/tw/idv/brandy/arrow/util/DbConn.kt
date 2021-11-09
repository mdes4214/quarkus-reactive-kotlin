package tw.idv.brandy.arrow.util

import io.quarkus.mongodb.reactive.ReactiveMongoClient
import io.quarkus.runtime.ShutdownEvent
import io.quarkus.runtime.StartupEvent
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes
import javax.inject.Inject
import javax.inject.Named

@ApplicationScoped
class DbConn {

    @Inject
    @Named("EmbeddedMongoClient")
    lateinit var client: ReactiveMongoClient

    companion object {
        lateinit var dbPool: ReactiveMongoClient
    }

    fun onStart(@Observes ev: StartupEvent) {
        EmbeddedMongoResource.mongodExecutable.start()
        dbPool = client
    }

    fun onStop(@Observes ev: ShutdownEvent) {
        EmbeddedMongoResource.mongodExecutable.stop()
    }

}