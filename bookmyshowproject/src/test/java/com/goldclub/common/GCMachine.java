package com.goldclub.common;
/**
 * GoldClub machine class
 * @author ZenQ
 *
 */

public class GCMachine {

		public final static String SAUCELABS = "saucelabs";
		public final static String LOCALHOST = "localhost";
		protected String ip;
		protected int port;
		
		/**
		 * Constructor for default localhost and port 4444
		 */
		public GCMachine() {
			this(LOCALHOST, 4444);
		}


		/**
		 * Constructor to set machine IP address with default port 4444
		 * @param ip	Machine IP address
		 */
		public GCMachine(String ip) {
			this(ip, 4444);
		}
		
		/**
		 * Constructor to set machine IP address and port
		 * @param ip	Machine IP address
		 * @param port	Port
		 */
		public GCMachine(String ip, int port) {
			this.ip = ip;
			this.port = port;
		}
		
		public String getIp(){
			return this.ip;
		}
	}

