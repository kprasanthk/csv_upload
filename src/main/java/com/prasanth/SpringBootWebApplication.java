package com.prasanth;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;

import com.prasanth.facade.CSVFilterFacade;
import com.prasanth.facade.CSVFilterFacadeImpl;
import com.prasanth.service.CSVFileService;
import com.prasanth.service.CSVFileServiceImpl;

@SpringBootApplication
@EnableAutoConfiguration
public class SpringBootWebApplication {

    private int maxUploadSizeInMb = 10 * 1024 * 1024; // 10 MB

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringBootWebApplication.class, args);
    }
    
    @Bean
    public CSVFilterFacade csvFilterFacade() {
    	return new CSVFilterFacadeImpl();
    }
    
    @Bean
    public CSVFileService csvFileService() {
    	return new CSVFileServiceImpl();
    }
    
    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatEmbedded() {

        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();

        tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
            if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
                //-1 means unlimited
                ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(maxUploadSizeInMb);
            }
        });

        return tomcat;

    }

}