package com.ruoyi.system.config;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
@Bean
@SuppressWarnings(value = { "unchecked", "rawtypes" })
public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory connectionFactory)
{
  RedisTemplate<Object,Object> template= new RedisTemplate<>();
  template.setConnectionFactory(connectionFactory);
  FastJson2 serializer = new FastJson2(Object.class);
  template.setKeySerializer(new StringRedisSerializer());
  template.setValueSerializer(serializer);
template.setHashKeySerializer(new StringRedisSerializer());
 template.setHashValueSerializer(serializer);
template.afterPropertiesSet();
return template;

}
@Bean
public DefaultRedisScript<Long> limitScript()
{
  DefaultRedisScript<Long> objectDefaultRedisScript = new DefaultRedisScript<>();
   objectDefaultRedisScript.setScriptText(limitScriptText());
   objectDefaultRedisScript.setResultType(Long.class);
   return objectDefaultRedisScript;
}
 public  String limitScriptText()
 {
       return "local  key =  KEYS[1]\n" +
               "local  count =  tonumber(ARGV[1]) \n"+
               "local  time  = tonumber(ARGV[2])\n"+
               "local  current = redis.call('get' , key );\n"+
               "if  current  and  tonumber(current) > count then \n "+
               " return  tonumber(current);\n"+
               "end \n" +
               "current  = redis.call('incr', key)\n"+
               "if tonumber(current) == 1  then \n"+
               " redis.call('expire',key , time)\n"+
               "end \n"+
               "return  tonumber(current);";
 }



}
