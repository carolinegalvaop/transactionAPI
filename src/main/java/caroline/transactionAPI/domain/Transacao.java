package caroline.transactionAPI.domain;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class Transacao {

    public double valor;

    public ZonedDateTime dataHora;

}
