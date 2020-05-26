package dp2

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class CrearLocalTest extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.ico""", """.*.js""", """.*.png""", """.*.jpg""", """.*.jpeg"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.9,en;q=0.8")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "image/webp,image/apng,image/*,*/*;q=0.8",
		"Proxy-Connection" -> "keep-alive")

	val headers_3 = Map(
		"Origin" -> "http://www.dp2.com",
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	object Welcome{
		val welcome = exec(http("Welcome")
			.get("/")
			.headers(headers_0))
		.pause(10)
	}

	object Login{
        val login = exec(
            http("Login")
                .get("/login")
                .headers(headers_0)
                .check(css("input[name=_csrf]", "value").saveAs("stoken"))
        ).pause(22)
            .exec(
                http("Logged")
                    .post("/login")
                    .headers(headers_3)
                    .formParam("username", "propietario1")
                    .formParam("password", "propietario1")
                    .formParam("_csrf", "${stoken}")
            ).pause(26)
    }
	
	object MyLocales{
		val myLocales = exec(http("MyLocales")
			.get("/propietario/locales")
			.headers(headers_0))
		.pause(11)
	}

	object NewLocal{
        val newLocal = exec(http("FormLocal")
            .get("/locales/new")
            .headers(headers_0)
            .check(css("input[name=_csrf]", "value").saveAs("stoken"))
        ).pause(31)
        .exec(http("LocalCreated")
            .post("/locales/new")
            .headers(headers_3)
			.formParam("direccion", "Direccion Test")
			.formParam("capacidad", "100")
			.formParam("condiciones", "Ninguna")
			.formParam("imagen", "https://www.laguiago.com/wp-content/uploads/2015/09/dsc_03842.jpg")          
			.formParam("_csrf", "${stoken}"))
        .pause(42)

		val newLocalWrong = exec(http("FormLocal")
            .get("/locales/new")
            .headers(headers_0)
            .check(css("input[name=_csrf]", "value").saveAs("stoken"))
        ).pause(31)
        .exec(http("FieldsEmpty")
            .post("/locales/new")
            .headers(headers_3)
			.formParam("direccion", "")
			.formParam("capacidad", "")
			.formParam("condiciones", "")
			.formParam("imagen", "")          
			.formParam("_csrf", "${stoken}"))
        .pause(7)
    }

	val positivo = scenario("CrearLocalTestPositivo").exec(
		Welcome.welcome,
		Login.login,
		MyLocales.myLocales,
		NewLocal.newLocal
	)

	val negativo = scenario("CrearLocalTestNegativo").exec(
		Welcome.welcome,
		Login.login,
		MyLocales.myLocales,
		NewLocal.newLocalWrong
	)

	setUp(
        positivo.inject(rampUsers(2000) during(100 seconds)),
        negativo.inject(rampUsers(2000) during(100 seconds))
    )
    .protocols(httpProtocol)
    .assertions(
        global.responseTime.max.lt(5000),
        global.responseTime.mean.lt(1000),
        global.successfulRequests.percent.gt(95)
    )
}