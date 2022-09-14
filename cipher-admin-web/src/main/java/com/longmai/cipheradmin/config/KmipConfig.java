package com.longmai.cipheradmin.config;

import ch.ntb.inf.kmip.process.decoder.KMIPDecoder;
import ch.ntb.inf.kmip.process.decoder.KMIPDecoderInterface;
import ch.ntb.inf.kmip.process.encoder.KMIPEncoder;
import ch.ntb.inf.kmip.process.encoder.KMIPEncoderInterface;
import ch.ntb.inf.kmip.stub.KMIPStubInterface;
import ch.ntb.inf.kmip.stub.transport.KMIPStubTransportLayerHTTP;
import ch.ntb.inf.kmip.stub.transport.KMIPStubTransportLayerInterface;
import ch.ntb.inf.kmip.test.UCStringCompare;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class KmipConfig {

    @Value("kmip.targetHostname")
    private String targetHostname;
    @Value("kmip.keyStoreLocation")
    private String keyStoreLocation;
    @Value("kmip.keyStorePW")
    private String keyStorePW;
    @Value("kmip.testing")
    private Integer testing;

    @Bean
    public KMIPEncoderInterface getEncoder() {
        return new KMIPEncoder();
    }

    @Bean
    public KMIPDecoderInterface getDecoder() {
        return new KMIPDecoder();
    }

    @Bean
    public KMIPStubTransportLayerInterface getTransportLayer(){
        KMIPStubTransportLayerHTTP kmipStubTransportLayerHTTP = new KMIPStubTransportLayerHTTP();
        kmipStubTransportLayerHTTP.setKeyStoreLocation(keyStoreLocation);
        kmipStubTransportLayerHTTP.setKeyStorePW(keyStorePW);
        kmipStubTransportLayerHTTP.setTargetHostname(targetHostname);
        return kmipStubTransportLayerHTTP;
    }

    @Bean
    public KMIPStubInterface getKMIPStub(){
        return new KMIPStub(getEncoder(),getDecoder(),getTransportLayer(),testing);
    }
}
