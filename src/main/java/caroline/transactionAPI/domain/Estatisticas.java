package caroline.transactionAPI.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Estatisticas {

        public int count;

        public double sum;

        public double avg;

        public double min;

        public double max;

}
