import static spark.Spark.get;

import static spark.Spark.port;
import static spark.Spark.post;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import binance.Binance;
import configuration.Settings;
import messenger.Custom;
import messenger.Messenger;
import spark.Spark;
import coinsph.Coinsph;
public class Main {
	private static final String something = null;

	public static void main(String[] args) throws Exception {
		Coinsph coinsph = new Coinsph();
		//coinsph.transfer("1Eaizt9XqWYY7NBR67kAxN6JEmn8sP4Sf2", 4);
		//System.out.println(coinsph.check_balance());
		//coinsph.load("+639953274805", "10");
		//coinsph.rates();
		//coinsph.transfer("testing", "1");
		//coinsph.php_to_btc(200.0);
		//coinsph.btc_to_php(0.000001);
		//coinsph.seven_eleven();
		//coinsph.payin_outlets();
		Binance binance = new Binance();
		//binance.view_funds();
		//binance.sell_market_order("BTCUSDT", "3000");
		//binance.user_data_stream();
		//binance.market_data_stream();
		port(getHerokuAssignedPort());
		
		Spark.staticFiles.location("/public");
        // Static files caching is disabled by default
        // staticFiles.expireTime(600L);
		get("/", (req, res) -> "Hello, I am using this for my pet feeder. If you love/own a puppy please leave.");
		get("/secureHello", (req, res) -> "Hello Secure World");
		get("/webhook", (request, response) -> {
			Messenger messenger = new Messenger();
			String Challenge = Messenger.verify_webhook(request, response);
			return Challenge;
		});
		post("/webhook", (request, response) -> {
			Messenger messenger = new Messenger();
			String res = messenger.event_reciever(request, response);
			return res;
		});
		post("/custom", (request, response) -> {
			Custom custom = new Custom();
			custom.handler(request, response);
			return "OK";
		});
	}
	
	static int getHerokuAssignedPort() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT") != null) {
			return Integer.parseInt(processBuilder.environment().get("PORT"));
		}
		return Settings.PORT; // return default port if heroku-port isn't set (i.e. on localhost)
	}
	
	
}