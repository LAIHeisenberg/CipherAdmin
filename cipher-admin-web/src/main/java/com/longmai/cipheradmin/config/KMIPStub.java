package com.longmai.cipheradmin.config;

import ch.ntb.inf.kmip.container.KMIPContainer;
import ch.ntb.inf.kmip.process.decoder.KMIPDecoderInterface;
import ch.ntb.inf.kmip.process.encoder.KMIPEncoderInterface;
import ch.ntb.inf.kmip.stub.KMIPStubInterface;
import ch.ntb.inf.kmip.stub.transport.KMIPStubTransportLayerInterface;
import ch.ntb.inf.kmip.test.UCStringCompare;
import ch.ntb.inf.kmip.utils.KMIPUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
public class KMIPStub implements KMIPStubInterface {

    private KMIPEncoderInterface encoder;
    private KMIPDecoderInterface decoder;
    private KMIPStubTransportLayerInterface transportLayer;

    public KMIPStub(KMIPEncoderInterface encoder,KMIPDecoderInterface decoder,KMIPStubTransportLayerInterface transportLayer,int testing) {
        this.encoder = encoder;
        this.decoder = decoder;
        this.transportLayer = transportLayer;
        UCStringCompare.testingOption = testing;
    }

    @Override
    public KMIPContainer processRequest(KMIPContainer c) {
        ArrayList<Byte> ttlv = this.encoder.encodeRequest(c);
        ArrayList<Byte> responseFromServer = this.transportLayer.send(ttlv);
        return this.decodeResponse(responseFromServer);
    }

    @Override
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
