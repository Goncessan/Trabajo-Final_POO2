
package com.esenciayaroma.cobranzas;

import java.util.List;

public interface EstrategiaPeriodoCobranza {

    String getCodigo();

    List<ResumenCobroPorPeriodo> agrupar(
            List<Abono> abonos
    );
}
