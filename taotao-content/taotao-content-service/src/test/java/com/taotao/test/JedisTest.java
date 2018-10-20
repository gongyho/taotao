package com.taotao.test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.common.jedis.JedisClient;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class JedisTest {
	@Test
	public void testJedisCluster() throws IOException {
		Set<HostAndPort> nodes=new HashSet<>();
		nodes.add(new HostAndPort("192.168.37.135", 7001));
		nodes.add(new HostAndPort("192.168.37.135", 7002));
		nodes.add(new HostAndPort("192.168.37.135", 7003));
		nodes.add(new HostAndPort("192.168.37.135", 7004));
		nodes.add(new HostAndPort("192.168.37.135", 7005));
		nodes.add(new HostAndPort("192.168.37.135", 7006));
		JedisCluster jedisCluster=new JedisCluster(nodes);
		
		//jedisCluster.set("aaa", "111");
		//System.out.println(jedisCluster.get("aaa"));
		jedisCluster.del("aaa");
		jedisCluster.close();
	}
	
	
	@Test
	public void testJedisClient() {
		ApplicationContext context =new ClassPathXmlApplicationContext("classpath:spring/applicationContext-jedis.xml");
		JedisClient c=context.getBean(JedisClient.class);
		//c.set("aaa", "111");
		//System.out.println(c.get("aaa"));
		System.out.println(c.del("aaa"));
		
	}
}
