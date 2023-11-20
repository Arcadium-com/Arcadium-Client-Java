package arcadium.com.models;

import arcadium.com.dao.DadosDao;

import java.util.List;


public class Shutdown implements Runnable {
    private int totemId; // Altere o tipo para o tipo de dados correto, se necessário
    private DadosDao dadosDao;
    private Log logger;

    public Shutdown(int totemId, DadosDao dadosDao, Log logger) {
        this.totemId = totemId;
        this.dadosDao = dadosDao;
        this.logger = logger;
    }

    @Override
    public void run() {
        // Se há um totem associado ao login, registre os últimos 5 registros no arquivo de log
        if (totemId > 0) {
            List<Dados> last5Records = dadosDao.getUltimos5RegistrosDoTotemPorId(totemId);
            logger.logLast5Records(last5Records);
        }
    }
}