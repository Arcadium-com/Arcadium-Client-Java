package arcadium.com.jira;
import arcadium.com.models.*;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class IntegracaoJira {
    public static void criarChamado(Totem totem, String telefone, String endereco, SistemaOperacional soTotem, Dados dados, Indicador indicador){
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

            String dtFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss").format(dados.getDtHora());

            // Monta o corpo da requisição
            String descricao = String.format(
                "O *Totem* de *Id %d* está com o uso *acima e/ou abaixo* dos Limites Cadastrados." +
                "\\n\\nData e Hora da Ocorrência: *%s*" +
                "\\nTelefone de Contato: *%s*" +
                "\\nLocalização do Totem: *%s*" +
                "\\nSistema Operacional do Totem: *%s* - *%s*" +
                "\\nUso da Memória RAM: *%.2f%%* --- Limite Esperado: *%.2f%%*" +
                "\\nUso da CPU: *%.2f%%* --- Limite Esperado: *%.2f%%*" +
                "\\nUso de Disco: *%.2f%%* --- Limite Esperado: *%.2f%%*" +
                "\\nQuantidade de USBs: *%d* --- Quantidade Esperada: *%d*",
                totem.getId(),
                dtFormatada,
                telefone,
                endereco,
                soTotem.getVersionamento(),
                soTotem.getDistribuicao(),
                dados.getValorMemoriaRAM(),
                indicador.getLimiteRam(),
                dados.getValorCPU(),
                indicador.getLimiteCpu(),
                dados.getValorDisco(),
                indicador.getLimiteDisco(),
                dados.getUSB(),
                indicador.getIndicadorUsb()
            );

            String requestBody = String.format("""
                    {
                      "serviceDeskId": "1",
                      "requestTypeId": "10002",
                      "requestFieldValues": {
                        "summary": "%s",
                        "description": "%s"
                      }
                    }
                    """, "Chamado de Alerta Crítico do Totem " + totem.getId(), descricao);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Obtém a resposta
            int responseCode = connection.getResponseCode();
//            System.out.println("Código de resposta: " + responseCode);

            if(responseCode != 201){
                Log logger = new Log();
                logger.log("Houve um erro ao tentar criar o chamado no Jira.");
                logger.log("Resposta => " + responseCode + " " + connection.getResponseMessage());
            }

            // Fecha a conexão
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            Log logger = new Log();
            logger.log("Houve um erro ao tentar criar chamado no Jira.");
            logger.log("Exceção => " + e);
        }
    }
}