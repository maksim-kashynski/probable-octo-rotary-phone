package de.objective.jsf.gatling 
import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._
import com.excilys.ebi.gatling.jdbc.Predef._
import com.excilys.ebi.gatling.http.Headers.Names._
import scala.concurrent.duration._
import bootstrap._
import assertions._
import com.excilys.ebi.gatling.http.request.builder._

trait JSF {
  
  val jsfViewStateCheck = regex("""id="javax.faces.ViewState" value="(-?\d+:-?\d+)"""").saveAs("viewState");
  
  def implicitParameters = Map("javax.faces.ViewState" -> "${viewState}")
  
  def extractParam[T <: AbstractHttpRequestBuilder[T]](requestBuilder: T): T = {
    requestBuilder.check(jsfViewStateCheck)
  }
  
  def applyParam[T <: AbstractHttpRequestBuilder[T]](requestBuilder: T): T = {
    requestBuilder match {
      case p: AbstractHttpRequestWithBodyAndParamsBuilder[T] => p.param("javax.faces.ViewState", "${viewState}");
      case _ => requestBuilder
    }
  }
  
  def doApplyParam[T <: AbstractHttpRequestWithBodyAndParamsBuilder[T]] (builder: T, params: Map[String,String]) : T ={
    var b = builder;
    params.foreach({case (key, value) =>
      b = b.param(key, value)
    })
    builder
  } 
}

trait JSFAJAX extends JSF {
  val xpathViewState = xpath("//update[@id='javax.faces.ViewState']/text()").saveAs("viewState")
  
  override def implicitParameters = Map("javax.faces.partial.ajax" -> "true") ++ super.implicitParameters
  
  override def extractParam[T <: AbstractHttpRequestBuilder[T]](requestBuilder: T): T = {
    requestBuilder.check(xpathViewState)
  }
}

class JSFRequestBuilder(requestName: Expression[String]) extends HttpRequestBaseBuilder(requestName) with JSF{
  
  override def post(f: Expression[String]) = {
    extractParam(applyParam(super.post(f)))
  }
  
  override def get(f: Expression[String]) = extractParam(super.get(f))
}

class JSFAJAXRequestBuilder(requestName: Expression[String]) extends JSFRequestBuilder(requestName) with JSFAJAX{
  
}

object JSF {

  def jsf(requestName: Expression[String]) = new JSFRequestBuilder(requestName)
  
  def ajax(requestName: Expression[String]) = new JSFAJAXRequestBuilder(requestName)
}


class RecordedSimulation extends Simulation {

	import JSF._
  
	val httpConf = httpConfig
			.baseURL("http://showcase.richfaces.org")
			.acceptHeader("*/*")
			.acceptEncodingHeader("gzip, deflate")
			.acceptLanguageHeader("en-US,en;q=0.5")
			.userAgentHeader("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:18.0) Gecko/20100101 Firefox/18.0")


	val headers_1 = Map(
			"Accept" -> """text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8""",
			"Connection" -> """keep-alive"""
	)

	val headers_2 = Map(
			"Connection" -> """keep-alive"""
	)

	val headers_5 = Map(
			"Accept" -> """text/css,*/*;q=0.1""",
			"Connection" -> """keep-alive"""
	)

	val headers_13 = Map(
			"Accept" -> """text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8""",
			"Cache-Control" -> """no-cache""",
			"Connection" -> """keep-alive""",
			"Content-Type" -> """application/x-www-form-urlencoded;charset=UTF-8""",
			"Faces-Request" -> """partial/ajax""",
			"Pragma" -> """no-cache"""
	)


	val scn = scenario("Scenario Name")
		.exec(jsf("request_1")
					.get("/richfaces/component-sample.jsf")
					.headers(headers_1)
					.queryParam("""demo""", """repeat""")
					.queryParam("""skin""", """blueSky""")
			)
		//Click right
		.pause(500 milliseconds)
		.exec(ajax("request_13")
					.post("/richfaces/component-sample.jsf")
					.headers(headers_13)
						.param("""j_idt679""", """j_idt679""")
						.param("""javax.faces.source""", """j_idt679:j_idt691""")
						.param("""javax.faces.partial.event""", """rich:datascroller:onscroll""")
						.param("""javax.faces.partial.execute""", """j_idt679:j_idt691 @component""")
						.param("""javax.faces.partial.render""", """@component""")
						.param("""j_idt679:j_idt691:page""", """next""")
						.param("""org.richfaces.ajax.component""", """j_idt679:j_idt691""")
						.param("""j_idt679:j_idt691""", """j_idt679:j_idt691""")
						.param("""AJAX:EVENTS_COUNT""", """1""")
						.param("""javax.faces.partial.ajax""", """true""")
			)
		//click right again
		.pause(1)
		.exec(ajax("request_14")
					.post("/richfaces/component-sample.jsf")
					.headers(headers_13)
						.param("""j_idt679""", """j_idt679""")
						.param("""javax.faces.source""", """j_idt679:j_idt691""")
						.param("""javax.faces.partial.event""", """rich:datascroller:onscroll""")
						.param("""javax.faces.partial.execute""", """j_idt679:j_idt691 @component""")
						.param("""javax.faces.partial.render""", """@component""")
						.param("""j_idt679:j_idt691:page""", """next""")
						.param("""org.richfaces.ajax.component""", """j_idt679:j_idt691""")
						.param("""j_idt679:j_idt691""", """j_idt679:j_idt691""")
						.param("""AJAX:EVENTS_COUNT""", """1""")
						.param("""javax.faces.partial.ajax""", """true""")
			)
		//click twice left
		.pause(18)
		.exec(ajax("request_15")
					.post("/richfaces/component-sample.jsf")
					.headers(headers_13)
						.param("""j_idt679""", """j_idt679""")
						.param("""javax.faces.source""", """j_idt679:j_idt691""")
						.param("""javax.faces.partial.event""", """rich:datascroller:onscroll""")
						.param("""javax.faces.partial.execute""", """j_idt679:j_idt691 @component""")
						.param("""javax.faces.partial.render""", """@component""")
						.param("""j_idt679:j_idt691:page""", """fastrewind""")
						.param("""org.richfaces.ajax.component""", """j_idt679:j_idt691""")
						.param("""j_idt679:j_idt691""", """j_idt679:j_idt691""")
						.param("""AJAX:EVENTS_COUNT""", """1""")
						.param("""javax.faces.partial.ajax""", """true""")
			)
		//click <<< to the left
		.pause(29)
		.exec(ajax("request_16")
					.post("/richfaces/component-sample.jsf")
					.headers(headers_13)
						.param("""j_idt679""", """j_idt679""")
						.param("""javax.faces.source""", """j_idt679:j_idt691""")
						.param("""javax.faces.partial.event""", """rich:datascroller:onscroll""")
						.param("""javax.faces.partial.execute""", """j_idt679:j_idt691 @component""")
						.param("""javax.faces.partial.render""", """@component""")
						.param("""j_idt679:j_idt691:page""", """first""")
						.param("""org.richfaces.ajax.component""", """j_idt679:j_idt691""")
						.param("""j_idt679:j_idt691""", """j_idt679:j_idt691""")
						.param("""AJAX:EVENTS_COUNT""", """1""")
						.param("""javax.faces.partial.ajax""", """true""")
			)

	setUp(scn.users(1).protocolConfig(httpConf))
}