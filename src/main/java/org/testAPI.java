package org;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class testAPI {

    public static void main(String[] args) {
        try {
            URL url = new URL("http://iqs-gci-stg.walmart.com/search/_sql");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("WM_CONSUMER.ID", "021e89bb-3d17-45f7-8bb4-194b0fbefcad");
            String input = "SELECT limo.offerId, limo.buId, offer.offerId, product.item_id from catalog_index where limo.s2hRestrictionsV2 = RESTRICT_TERRITORY_PR or limo.s2sRestrictionsV2 = RESTRICT_TERRITORY_PR";
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
