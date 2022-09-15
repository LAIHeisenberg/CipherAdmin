package com.longmai.cipheradmin.config;

import ch.ntb.inf.kmip.stub.KMIPStubInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KmipConfig {

    @Bean
    public KMIPStubInterface KMIPStub(){
        return new KMIPStub();
    }

}
