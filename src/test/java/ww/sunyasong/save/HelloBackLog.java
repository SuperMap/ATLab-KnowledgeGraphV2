package ww.sunyasong.save;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloBackLog {

	public HelloBackLog() {
		// TODO Auto-generated constructor stub
	}

	private static final Logger logger = LoggerFactory.getLogger(HelloBackLog.class);

	public static void main(String[] args) {
		logger.debug("Hello from Logback");

		logger.debug("getNumber() : {}", getNumber());

	}

	static int getNumber() {
		return 5;
	}

}
