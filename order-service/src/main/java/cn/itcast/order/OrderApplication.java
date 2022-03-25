package cn.itcast.order;

import cn.itcast.feign.clients.UserClient;
import cn.itcast.feign.config.DefaultFeignConfiguration;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@MapperScan("cn.itcast.order.mapper")
@SpringBootApplication
//开启feign
//clients在有feign-api时指定加载的clients,也可以写basePackages = "cn.itcast.feign.clients"，这种会加载该包下的所有
@EnableFeignClients(clients = UserClient.class)
//使用注解设置feign日志级别，启动类上加全局有效
//@EnableFeignClients(defaultConfiguration = DefaultFeignConfiguration.class)
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    /**
     * 创建RestTemplate并注入Spring容器
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /*
    * eureka采用Ribbon来实现负载均衡，IRule决定负载均衡策略，默认是轮换，现在修改为随机。
    * 这里修改为全局修改，该service对任何其他service都会以这种负载均衡策略进行，若要只指定特定的service负载策略，可修改application.xml
    * */
//    @Bean
//    public IRule randomRule() {
//        return new RandomRule();
//    }


}