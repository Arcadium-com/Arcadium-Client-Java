package arcadium.com.models;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Log {

    private String logFileName;


    public Log() {

        this.logFileName = getLogFileName(); // Inicializa o nome do arquivo de log
    }

    private String getLogFileName() {
        // Obtenha a data atual para criar a pasta e o nome do arquivo
        SimpleDateFormat folderDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = folderDateFormat.format(new Date());

        // Crie o caminho para a pasta diária
        String dailyFolderPath = "logs/" + currentDate; // "logs" é o diretório raiz para os logs

        // Verifique se a pasta diária já existe, se não, crie-a
        File dailyFolder = new File(dailyFolderPath);
        if (!dailyFolder.exists()) {
            dailyFolder.mkdirs(); // mkdirs() cria a pasta e qualquer pasta pai necessária
        }

            // Crie o nome do arquivo de LOG baseado na data atual
            return dailyFolderPath + "/log_" + new SimpleDateFormat("HH").format(new Date()) + ".txt";

    }

    public void log(String message) {
        // Obtenha a data e hora atual para registrar no log
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = dateFormat.format(new Date());

        // Crie uma mensagem de log formatada
        String logMessage = "[" + timestamp + "] " + message;

        // Registre a mensagem em um arquivo
        writeLogToFile(logMessage);
    }

    private void writeLogToFile(String message) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(logFileName, true));

            // Escreva a mensagem no arquivo de log
            writer.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public void logLast5Records(List<Dados> last5Records) {
        // Chame o método de log com a flag isLast5Records igual a true
        log("Últimos 5 registros:");

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(logFileName, true));

            // Adicione as mensagens dos últimos 5 registros
            for (Dados record : last5Records) {
                writer.println(record);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
