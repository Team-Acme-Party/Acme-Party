package propietarioAnuncio

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ListarYDetallesAnuncio extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.acme-party.com")
		.inferHtmlResources(BlackList(""".*.jpg""", """.*.png""", """.*.css""", """.*.js""", """.*.ico"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Origin" -> "http://www.acme-party.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

		object Home {
		val home = exec(http("Home")
			.get("/")
			.headers(headers_0))
		.pause(6)
	}

	object Login {
		val login = exec(http("Loggin")
			.get("/login")
			.headers(headers_0)
			.resources(http("request_2")
			.get("/login")
			.headers(headers_2)))
		.pause(19)

	}

	object LoggedPatrocinador{
		val loggedPatrocinador = exec(http("loggedPatrocinador")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "patrocinador1")
			.formParam("password", "patrocinador1")
			.formParam("_csrf", "6574bc23-49e3-4bdf-a50a-594a35a72b16"))
		.pause(19)

	}

	object ListAnuncios {
		val listAnuncios = exec(http("ListAnuncios")
			.get("/patrocinador/anuncios")
			.headers(headers_0))
		.pause(17)
	}

	object DetallesAnuncios {
		val detallesAnuncios = exec(http("DetallesAnuncios")
			.get("/anuncios/1")
			.headers(headers_0))
		.pause(31)
	}

	object LoggedCliente {
		var loggedCliente = exec(http("LoggedCliente")
			.post("/login")
			.headers(headers_3)
			.formParam("username", "cliente2")
			.formParam("password", "cliente2")
			.formParam("_csrf", "288a37cb-651a-450b-9796-ab9957efcbc8"))
		.pause(36)
	}

	object ErrorVerAnuncio {
		val errorVerAnuncio = exec(http("ErrorVerAnuncio")
			.get("/anuncios/1")
			.headers(headers_0))
		.pause(31)
	}
	val ListarYDetallesAnuncioScn = scenario("ListarYDetallesAnuncio").exec(Home.home, Login.login, LoggedPatrocinador.loggedPatrocinador, ListAnuncios.listAnuncios , DetallesAnuncios.detallesAnuncios)
		
	val ListarYDetallesAnuncioErrorScn = scenario("ListarYDetallesAnuncioError").exec(Home.home, Login.login,LoggedCliente.loggedCliente, ErrorVerAnuncio.errorVerAnuncio)
	
	setUp(ListarYDetallesAnuncioScn.inject(atOnceUsers(1)),ListarYDetallesAnuncioErrorScn.inject(atOnceUsers(1))).protocols(httpProtocol)
}