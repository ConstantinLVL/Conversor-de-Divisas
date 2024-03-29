package com.example.conversordivisasfx;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


/**
 * Implementación de la API ExchangeRate.
 */
public class APIExchangeRateImplementacion {

    private static CloseableHttpClient httpClient;

    /**
     * Inicia la conexión con la API de tipo de cambio.
     */
    public static void iniciarConection(){
        httpClient = HttpClients.createDefault();
    }

    /**
     * Convierte una cantidad de una divisa base a una divisa de destino haciendo uso
     * de los servicios de la API ExchangeRate.
     *
     * @param base     La divisa base.
     * @param destino  La divisa de destino.
     * @param importe  El importe a convertir.
     * @return El resultado de la conversión, JSON.
     */
    public static String convertir(String base, String destino, String importe) {
        String endpoint = "https://v6.exchangerate-api.com/v6/a971433c2a9afdd0f4a3ccae/pair/" + base + "/" + destino + "/" + importe;

        try {
            HttpGet request = new HttpGet(endpoint);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    HttpEntity entity = response.getEntity();
                    return EntityUtils.toString(entity);
                } else {
                    System.out.println("Error en la solicitud. Código de respuesta: " + statusCode);
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error en la conversión";
    }
}

