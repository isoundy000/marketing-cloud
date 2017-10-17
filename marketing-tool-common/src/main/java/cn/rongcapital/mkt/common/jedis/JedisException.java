package cn.rongcapital.mkt.common.jedis;

public class JedisException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public JedisException() {
	}

	public JedisException(String message, Throwable cause) {
		super(message, cause);
	}

	public JedisException(String message) {
		super(message);
	}

	public JedisException(Throwable cause) {
		super(cause);
	}
}
