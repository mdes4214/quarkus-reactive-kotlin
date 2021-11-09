package tw.idv.brandy.arrow.util

import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.MongoCmdOptions
import de.flapdoodle.embed.mongo.config.MongodConfig
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import org.eclipse.microprofile.config.ConfigProvider
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

import javax.enterprise.context.ApplicationScoped
import javax.inject.Named


@ApplicationScoped
class EmbeddedMongoResource {
    companion object {
        const val ip = "localhost"
        const val port = 27017
        const val connectionString = "mongodb://$ip:$port"

        val mongodConfig = MongodConfig
            .builder()
            .version(Version.Main.PRODUCTION)
            .cmdOptions(
                MongoCmdOptions.builder()
                    .useNoJournal(false)
                    .build()
            )
            .net(Net(ip, port, Network.localhostIsIPv6()))
            .build()

        val mongodStarter = MongodStarter.getDefaultInstance()
        val mongodExecutable = mongodStarter.prepare(mongodConfig)

        @Named("EmbeddedMongoClient")
        val client = KMongo.createClient(connectionString).coroutine

        val fruitStore = client.getDatabase("fruits")
    }

}