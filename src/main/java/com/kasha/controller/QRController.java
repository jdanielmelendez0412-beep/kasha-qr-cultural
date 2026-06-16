package com.kasha.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;

@RestController
public class QRController {

    @Value("${app.url:http://localhost:5000}")
    private String appUrl;

    @GetMapping(value = "/api/qr", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getQR() throws Exception {
        String qrContent = appUrl + "/formulario";
        QRCodeWriter qrWriter = new QRCodeWriter();
        var matrix = qrWriter.encode(qrContent, BarcodeFormat.QR_CODE, 400, 400);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", bos);
        return ResponseEntity.ok(bos.toByteArray());
    }
}
