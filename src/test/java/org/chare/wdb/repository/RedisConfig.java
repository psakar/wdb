package org.chare.wdb.repository;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import redis.embedded.RedisServer;

@Configuration
public class RedisConfig {

	private final RedisServer redisServer;
	private final String redisHost;

	public RedisConfig(@Value("${spring.redis.port:6379}") final int redisPort, @Value("${spring.redis.host:localhost}") final String redisHost, @Value("${spring.redis.embedded.max-memory-in-mega-bytes:128}") final int maxMemoryInMegaBytes) throws IOException {
		this.redisHost = redisHost;
		if ("127.0.0.1".equalsIgnoreCase(redisHost) || "localhost".equalsIgnoreCase(redisHost)) {
			System.out.println("Redis server start on port " + redisPort + " max memory " + maxMemoryInMegaBytes + " MB");
			this.redisServer = RedisServer.builder().port(redisPort).setting("maxmemory " + maxMemoryInMegaBytes + "M").build();
		} else {
			System.out.println("External redis server "  + redisHost + " port " + redisPort);
			this.redisServer = null;
		}
	}

	@PostConstruct
	public void startRedis() {
		if (this.redisServer != null) {
			System.out.println("Embedded redis server start");
			this.redisServer.start();
		}
	}

	@PreDestroy
	public void stopRedis() {
		if (this.redisServer != null) {
			System.out.println("Embedded redis server stop");
			this.redisServer.stop();
		}
	}
}
