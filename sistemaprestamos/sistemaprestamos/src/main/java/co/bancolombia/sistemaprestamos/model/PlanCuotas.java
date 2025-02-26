package co.bancolombia.sistemaprestamos.model;

import java.math.BigDecimal;

public class PlanCuotas {
    private Integer nrocuota;
    private BigDecimal capital;
    private BigDecimal interes;
    private BigDecimal cuota;
    private BigDecimal saldo;

    public PlanCuotas(Integer nrocuota, BigDecimal capital, BigDecimal interes, BigDecimal cuota, BigDecimal saldo) {
        this.nrocuota = nrocuota;
        this.capital = capital;
        this.interes = interes;
        this.cuota = cuota;
        this.saldo = saldo;
    }

    public Integer getNrocuota() {
        return nrocuota;
    }

    public void setNrocuota(Integer nrocuota) {
        this.nrocuota = nrocuota;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getInteres() {
        return interes;
    }

    public void setInteres(BigDecimal interes) {
        this.interes = interes;
    }

    public BigDecimal getCuota() {
        return cuota;
    }

    public void setCuota(BigDecimal cuota) {
        this.cuota = cuota;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

}
