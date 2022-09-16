package com.longmai.cipheradmin.config;

import ch.ntb.inf.kmip.config.ContextProperties;
import ch.ntb.inf.kmip.container.KMIPContainer;
import ch.ntb.inf.kmip.process.decoder.KMIPDecoderInterface;
import ch.ntb.inf.kmip.process.encoder.KMIPEncoderInterface;
import ch.ntb.inf.kmip.stub.KMIPStubInterface;
import ch.ntb.inf.kmip.stub.transport.KMIPStubTransportLayerInterface;
import ch.ntb.inf.kmip.test.UCStringCompare;
import ch.ntb.inf.kmip.utils.KMIPUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;

@Slf4j
public class KMIPStub implements KMIPStubInterface {

    private KMIPEncoderInterface encoder;
    private KMIPDecoderInterface decoder;
    private KMIPStubTransportLayerInterface transportLayer;

    public KMIPStub() {
        try {
            String xmlPath = this.getClass().getClassLoader().getResource("kmipConfig/").getPath();
            ContextProperties props = new ContextProperties(xmlPath, "StubConfig.xml");
            this.encoder = (KMIPEncoderInterface) this.getClass(props.getProperty("Encoder"), "ch.ntb.inf.kmip.process.encoder.KMIPEncoder").newInstance();
            this.decoder = (KMIPDecoderInterface) this.getClass(props.getProperty("Decoder"), "ch.ntb.inf.kmip.process.decoder.KMIPDecoder").newInstance();
            this.transportLayer = (KMIPStubTransportLayerInterface) this.getClass(props.getProperty("TransportLayer"), "ch.ntb.inf.kmip.stub.transport.KMIPStubTransportLayerHTTP").newInstance();
            this.transportLayer.setTargetHostname(props.getProperty("TargetHostname"));
            this.transportLayer.setKeyStoreLocation(props.getProperty("KeyStoreLocation"));
            this.transportLayer.setKeyStorePW(props.getProperty("KeyStorePW"));
            UCStringCompare.testingOption = props.getIntProperty("Testing");
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    private Class<?> getClass(String path, String defaultPath) throws ClassNotFoundException {
        return Class.forName(KMIPUtils.getClassPath(path, defaultPath));
    }

    public KMIPContainer processRequest(KMIPContainer c) {
        ArrayList<Byte> ttlv = this.encoder.encodeRequest(c);
        log.info("Encoded Request from Client: (actual/expected)");
        ArrayList<Byte> responseFromServer = this.transportLayer.send(ttlv);
        log.info("Encoded Response from Server: (actual/expected)");
        return this.decodeResponse(responseFromServer);
    }

    public KMIPContainer processRequest(KMIPContainer c, String expectedTTLVRequest, String expectedTTLVResponse) {
        ArrayList<Byte> ttlv = this.encoder.encodeRequest(c);
        log.info("Encoded Request from Client: (actual/expected)");
        KMIPUtils.printArrayListAsHexString(ttlv);
        log.debug(expectedTTLVRequest);
        UCStringCompare.checkRequest(ttlv, expectedTTLVRequest);
        ArrayList<Byte> responseFromServer = this.transportLayer.send(ttlv);
        log.info("Encoded Response from Server: (actual/expected)");
        KMIPUtils.printArrayListAsHexString(responseFromServer);
        log.debug(expectedTTLVResponse);
        UCStringCompare.checkResponse(responseFromServer, expectedTTLVResponse);
        return this.decodeResponse(responseFromServer);
    }

    private KMIPContainer decodeResponse(ArrayList<Byte> responseFromServer) {
        try {
            return this.decoder.decodeResponse(responseFromServer);
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

}
