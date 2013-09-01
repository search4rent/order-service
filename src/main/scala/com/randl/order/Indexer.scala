package com.randl.order

import com.codahale.jerkson.Json
import org.elasticsearch.action.bulk.BulkRequestBuilder

/**
 * Created with IntelliJ IDEA.
 * User: cgonzalez
 * Date: 8/2/13
 * Time: 4:32 PM
 * To change this template use File | Settings | File Templates.
 */
trait Indexer extends ESClient {
  def indexer(item: RentItem): BulkRequestBuilder = {
    val writeRequest = client.prepareIndex()
      .setIndex("rendl")
      .setType("item")
      .setId(item.id.toString)
      .setSource(Json.generate(item))
    val bulk = client.prepareBulk()

    bulk.add(writeRequest.setId(item.id.toString).setSource(Json.generate(item)))

  }

}
