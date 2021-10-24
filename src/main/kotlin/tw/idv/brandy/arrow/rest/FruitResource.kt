package tw.idv.brandy.arrow.rest


import tw.idv.brandy.arrow.KaqAppError
import tw.idv.brandy.arrow.bean.Fruit
import tw.idv.brandy.arrow.bean.Greeting
import tw.idv.brandy.arrow.bean.NewFruit
import tw.idv.brandy.arrow.repo.FruitRepo
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/")
class FruitResource {

    @GET
    @Path("/greeting")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun hello() = Greeting("hello")

    @GET
    @Path("/fruits")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getAllFruits(): Response =
        FruitRepo.findAll().fold(
            ifRight = { fruits -> Response.ok(fruits).build() },
            ifLeft = { err -> KaqAppError.toResponse(err) }
        )

    @GET
    @Path("/fruits/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getSingle(name:String): Response =
        FruitRepo.findByName(name).fold(
            ifRight = { fruit -> Response.ok(fruit).build() },
            ifLeft = { err -> KaqAppError.toResponse(err) }
        )

    @POST
    @Path("/fruits")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun create(fruit: NewFruit): Response =
        FruitRepo.create(Fruit(name=fruit.name,desc = fruit.desc)).fold(
            ifRight = {  Response.ok(it).status(201).build()},
            ifLeft = { err -> KaqAppError.toResponse(err) }
        )

}