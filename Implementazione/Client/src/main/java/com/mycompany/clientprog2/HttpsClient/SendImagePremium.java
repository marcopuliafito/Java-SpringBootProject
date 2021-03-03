package com.mycompany.clientprog2.HttpsClient;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import static javax.swing.JOptionPane.showMessageDialog;

public class SendImagePremium extends HttpsClient {

    private String token;
    private String postmapping = "filterImagePremium";
    private String charset = "UTF-8";
    private byte[] img;
    private String filter;
    private String addText;

    public SendImagePremium(byte[] img, String filter, String token) {
        super();
        this.token = token;
        this.img = img;
        this.filter = filter;
    }

    public SendImagePremium(byte[] img, String filter, String addText, String token) {
        super();
        this.token = token;
        this.img = img;
        this.filter = filter;
        this.addText = addText;
    }

    @Override
    public String post_request_image() {
        try {
            URL url = new URL("https://" + super.address + ":" + super.port + "/" + this.postmapping);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "image/jpeg");
            connection.setRequestProperty("Filter", this.filter);
            if (this.addText != null)
                connection.setRequestProperty("addText", this.addText);
            connection.setRequestProperty("Authorization", this.token);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            try (OutputStream output = connection.getOutputStream()) {
                output.write(this.img, 0, this.img.length);
                output.flush();
            }
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream()), this.charset))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                connection.disconnect();
            }
            if (response != null)
                return response.toString();
            else
                return null;
        } catch (IOException e) {
            System.out.println(e);
            showMessageDialog(null, "Errore invio al server! Riprova.");
            return null;
        }
    }

    @Override
    public boolean get_request() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BufferedImage get_image() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean post_request() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
