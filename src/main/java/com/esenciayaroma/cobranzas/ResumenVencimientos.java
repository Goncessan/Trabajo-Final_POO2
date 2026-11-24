
package com.esenciayaroma.cobranzas;

public class ResumenVencimientos {

    private final long vencidos;
    private final long vencenHoy;
    private final long proximos;
    private final long total;

    public ResumenVencimientos(
            long vencidos,
            long vencenHoy,
            long proximos,
            long total) {

        this.vencidos = vencidos;
        this.vencenHoy = vencenHoy;
        this.proximos = proximos;
        this.total = total;
    }

    public long getVencidos() { return vencidos; }
    public long getVencenHoy() { return vencenHoy; }
    public long getProximos() { return proximos; }
    public long getTotal() { return total; }
}
