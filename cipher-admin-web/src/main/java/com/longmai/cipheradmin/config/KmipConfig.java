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

//    @Value("${kmipServer.targetHostname}")
    private String targetHostname="http://192.168.1.126:5696/";

//    @Value("${kmipServer.keyStoreLocation}")
    private String keyStoreLocation;

//    @Value("${kmipServer.keyStorePW}")
    private String keyStorePW="password";

//    @Value("${kmipServer.testing}")
    private String testing="1";

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
        return new KMIPStub(getEncoder(),getDecoder(),getTransportLayer(),Integer.valueOf(testing));
    }
}
