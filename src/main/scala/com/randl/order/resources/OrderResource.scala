package com.randl.order.resources

import javax.ws.rs.core.{Response, Context, HttpHeaders}
import javax.ws.rs._
import scala.Array
import java.util.Locale
import javax.ws.rs.core.MediaType._
import org.elasticsearch.client.Client

@Path("/search")
@Produces(Array(APPLICATION_JSON))
@Consumes(Array(APPLICATION_JSON))
class OrderResource extends SuggestSearch with ItemSearch with Indexer {


  @POST
  @Path("-/insert/")
  def setItem(rentItem: RentItem) = {
    //val locale = if (headers.getLanguage() == null) Locale.US else headers.getLanguage()

    //val rentItem = Json.parse[RentItem](item)
    indexer(rentItem).execute().actionGet()
    Response.ok(rentItem.id).build()
  }

  @GET
  @Path("-/item/{id}")
  def getItem(
               @PathParam("id") id: String,
               @Context headers: HttpHeaders) = {
    val locale = if (headers.getLanguage() == null) Locale.US else headers.getLanguage()
    val result = getItemES(id)
    Response.ok(result).build()
  }


  @GET
  @Path("-/input/{input}")
  def search(
              @PathParam("input") input: String,
              @Context headers: HttpHeaders) = {
    val locale = if (headers.getLanguage() == null) Locale.US else headers.getLanguage()

    val result = suggestSearch(locale, input)

    val suggestResponse = result
    suggestResponse
    Response.ok(suggestResponse).build()
  }

  def client: Client = ElasticSearchClient.client
}
