
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class DashboardTest extends Simulation {

	val httpProtocol = http
		.baseUrl("http://www.dp2.com")
		.inferHtmlResources(BlackList(""".*.css""", """.*.js""", """.*.ico""", """.*.png""", """.*.jpeg""", """.*.jpg"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
		.upgradeInsecureRequestsHeader("1")
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:76.0) Gecko/20100101 Firefox/76.0")


	val headers_2 = Map("Origin" -> "http://www.dp2.com")

	val headers_0 = Map("Upgrade-Insecure-Requests" -> "1")

	val headers_3 = Map(
		"Origin" -> "http://www.dp2.com",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_4 = Map("Accept" -> "image/webp,*/*")

	object Home{
		val home = exec(http("Home")
			.get("/"))
		.pause(9)
	}

	object Login{
		val login = exec(http("Login")
			.get("/login"))
		.pause(16)
	}

	object Logged2{
		val logged2 = exec(http("Logged2")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "cliente1")
			.formParam("password", "cliente1")
			.formParam("_csrf", "4fa2c88a-3962-424b-a8b3-4f556a9ecfff"))
		.pause(15)
	}

	object Logged{
		val logged = exec(http("Logged")
			.post("/login")
			.headers(headers_2)
			.formParam("username", "admin")
			.formParam("password", "admin")
			.formParam("_csrf", "74f2d9f8-f3ac-4c46-9346-8e05c9d3fba7"))
		.pause(15)
	}

	object Dashboard{
		val dashboard = exec(http("dashboard")
			.get("/administrador/dashboard"))
		.pause(6)
	}

	object Dashboard2{
		val dashboard2 = exec(http("dashboard2")
			.get("/administrador/dashboard")
			.check(status.is(200)))
	}


	val positivo = scenario("VerDashboardPositivo").exec(
		Home.home,
		Login.login,
		Logged.logged,
		Dashboard.dashboard
	)

	val negativo = scenario("VerDashboardNegativo").exec(
		Home.home,
		Dashboard2.dashboard2
	)

	setUp(
		positivo.inject(rampUsers(5000) during(100 seconds)),
		negativo.inject(rampUsers(5000) during(100 seconds))
	)
	.protocols(httpProtocol)
	.assertions(
		global.responseTime.max.lt(5000),
		global.responseTime.mean.lt(1000),
		global.successfulRequests.percent.gt(95)
	)
}