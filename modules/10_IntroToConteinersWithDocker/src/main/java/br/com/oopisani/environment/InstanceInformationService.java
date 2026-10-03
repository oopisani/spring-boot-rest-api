package br.com.oopisani.environment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InstanceInformationService {

    // nome da var de ambiente que queremos consultar
    private static final String HOST_NAME = "HOSTNAME";
    // valor padrão caso HOSTNAME não exista
    private static final String DEFAULT_ENV_INSTANCE_GUID = "LOCAL";

    // @Value -> Anotação do Spring usada para colocar um valor dentro de uma variavél.
    // -> injeta o valor de HOSTNAME em hostName. Se HOSTNAME não existir, usa "LOCAL" como valor padrão.
    // Essa "equação" montada fica: "${HOSTNAME:LOCAL}"
    @Value("${" + HOST_NAME + ":" + DEFAULT_ENV_INSTANCE_GUID + "}")
    private String hostName;


    // substring() pega os últimos 5 caracteres; Se for "LOCAL" já possui exatamente 5.
    // "LOCAL".length() - 5 = 0 || "LOCAL".substring(0) = "LOCAL"
    public String retrieveInstanceInfo() {
        return hostName.substring(hostName.length()-5);
    }
}
