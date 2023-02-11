//package com.blog.gateway.config;
//
//import org.apache.catalina.Context;
//import org.apache.catalina.connector.Connector;
//import org.apache.tomcat.util.descriptor.web.SecurityCollection;
//import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @Author: lxk
// * @date 2023/2/10 16:20
// * @description:
// */
//
//@Configuration
//public class HttpsConfiguration {
//    //如果没有使用默认值80
//    @Value("${http.port:80}")
//    Integer httpPort;
//
//    //正常启用的https端口 如443
//    @Value("${server.port}")
//    Integer httpsPort;
//
//    @Bean
//    @ConditionalOnProperty(name = "condition.http2https", havingValue = "true", matchIfMissing = false)
//    public TomcatServletWebServerFactory servletContainer() {
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint constraint = new SecurityConstraint();
//                constraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                constraint.addCollection(collection);
//                context.addConstraint(constraint);
//            }
//        };
//        // 添加Connector对象
//        tomcat.addAdditionalTomcatConnectors(httpConnector());
//        return tomcat;
//    }
//
//    @Bean
//    @ConditionalOnProperty(name = "condition.http2https", havingValue = "true", matchIfMissing = false)
//    public Connector httpConnector() {
//        System.out.println("启用http转https协议，http端口：" + this.httpPort + "，https端口：" + this.httpsPort);
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        // Connector监听的http的端口号
//        connector.setPort(httpPort);
//        connector.setSecure(false);
//        // 监听到http的端口号后转向到的https的端口号
//        connector.setRedirectPort(httpsPort);
//        return connector;
//    }
//}
