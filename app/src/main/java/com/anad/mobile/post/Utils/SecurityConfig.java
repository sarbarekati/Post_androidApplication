package com.anad.mobile.post.Utils;

import android.content.Context;

import com.anad.mobile.post.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by elias.mohammadi on 2018/07/10
 */

public class SecurityConfig {

    public static SSLSocketFactory createSSLFactory(Context context) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        CertificateFactory cerf = CertificateFactory.getInstance("X509");
        InputStream input = context.getResources()
                .openRawResource(context.getResources().getIdentifier("cert","raw",context.getPackageName()));
        Certificate ca = cerf.generateCertificate(input);
        input.close();

        String keyStoreType = KeyStore.getDefaultType();
        KeyStore trusted = KeyStore.getInstance(keyStoreType);
        trusted.load(null,null);
        trusted.setCertificateEntry("ca",ca);

        String tmfAlgorithm  = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(trusted);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null,tmf.getTrustManagers(),null);
        return sslContext.getSocketFactory();

    }

}
