package arcadium.com.jira;
import arcadium.com.models.Totem;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class IntegracaoJira {
    public static void criarChamado(Totem totem, Double disco, Double cpu, Double ram, Integer usb ){
        try {
            URL url = new URL("https://arcadium.atlassian.net/rest/servicedeskapi/request");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");

            String username = "carlos.benecke@sptech.school";
            String password = "ATATT3xFfGF0OmNqCCM5IMOO-dSGZxEpwi4IOtA65TGUDxHIZ2n3ZvKyguhTAtMQiG-FmNFcjQBVPPAUV33lJ1F5Q-sqml2htbNvecFIW_rlGDH7hM9GDSmAn-ZWIind2txNXPeWELaUihingHcpqJzE3CMhYW2zid0sushyMzeLT4pbHwUuuZg=75E54795";
            String authString = username + ":" + password;
            String encodedAuth = java.util.Base64.getEncoder().encodeToString(authString.getBytes(StandardCharsets.UTF_8));
            connection.setRequestProperty("Authorization", "Basic " + encodedAuth);

            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            // Monta o corpo da requisição
            String descricao = String.format("""
                    ID da Máquina : %d
                    ID da Empresa: %d
                    
                    """, totem.getId(), totem.getFkEmpresa());
            String requestBody = String.format("""
                    {
                      "serviceDeskId": "1",
                      "requestTypeId": "10002",
                      "requestFieldValues": {
                        "summary": "%s",
                        "description": "%s"
                      }
                    }
                    """, "Chamado de Alerta Crítico", descricao);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Obtém a resposta
            int responseCode = connection.getResponseCode();
            System.out.println("Código de resposta: " + responseCode);

            // Fecha a conexão
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}