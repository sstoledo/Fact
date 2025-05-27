/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Giancarlos
 */
@Entity
@Table(name = "Cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.validar", query = "SELECT c FROM Cliente c WHERE c.codCli = :codCli AND c.pasCli = :pasCli"),
    @NamedQuery(name = "Cliente.findByCodCli", query = "SELECT c FROM Cliente c WHERE c.codCli = :codCli"),
    @NamedQuery(name = "Cliente.findByDniCli", query = "SELECT c FROM Cliente c WHERE c.dniCli = :dniCli"),
    @NamedQuery(name = "Cliente.findByApaCli", query = "SELECT c FROM Cliente c WHERE c.apaCli = :apaCli"),
    @NamedQuery(name = "Cliente.findByAmaCli", query = "SELECT c FROM Cliente c WHERE c.amaCli = :amaCli"),
    @NamedQuery(name = "Cliente.findByNomCli", query = "SELECT c FROM Cliente c WHERE c.nomCli = :nomCli"),
    @NamedQuery(name = "Cliente.findByFchNacCli", query = "SELECT c FROM Cliente c WHERE c.fchNacCli = :fchNacCli"),
    @NamedQuery(name = "Cliente.findByLogCli", query = "SELECT c FROM Cliente c WHERE c.logCli = :logCli"),
    @NamedQuery(name = "Cliente.findByPasCli", query = "SELECT c FROM Cliente c WHERE c.pasCli = :pasCli")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codCli")
    private Integer codCli;
    @Size(max = 8)
    @Column(name = "dniCli")
    private String dniCli;
    @Size(max = 50)
    @Column(name = "apaCli")
    private String apaCli;
    @Size(max = 50)
    @Column(name = "amaCli")
    private String amaCli;
    @Size(max = 50)
    @Column(name = "nomCli")
    private String nomCli;
    @Column(name = "fchNacCli")
    @Temporal(TemporalType.DATE)
    private Date fchNacCli;
    @Size(max = 100)
    @Column(name = "logCli")
    private String logCli;
    @Size(max = 500)
    @Column(name = "pasCli")
    private String pasCli;

    public Cliente() {
    }

    public Cliente(Integer codCli) {
        this.codCli = codCli;
    }

    public Integer getCodCli() {
        return codCli;
    }

    public void setCodCli(Integer codCli) {
        this.codCli = codCli;
    }

    public String getDniCli() {
        return dniCli;
    }

    public void setDniCli(String dniCli) {
        this.dniCli = dniCli;
    }

    public String getApaCli() {
        return apaCli;
    }

    public void setApaCli(String apaCli) {
        this.apaCli = apaCli;
    }

    public String getAmaCli() {
        return amaCli;
    }

    public void setAmaCli(String amaCli) {
        this.amaCli = amaCli;
    }

    public String getNomCli() {
        return nomCli;
    }

    public void setNomCli(String nomCli) {
        this.nomCli = nomCli;
    }

    public Date getFchNacCli() {
        return fchNacCli;
    }

    public void setFchNacCli(Date fchNacCli) {
        this.fchNacCli = fchNacCli;
    }

    public String getLogCli() {
        return logCli;
    }

    public void setLogCli(String logCli) {
        this.logCli = logCli;
    }

    public String getPasCli() {
        return pasCli;
    }

    public void setPasCli(String pasCli) {
        this.pasCli = pasCli;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCli != null ? codCli.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.codCli == null && other.codCli != null) || (this.codCli != null && !this.codCli.equals(other.codCli))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Cliente[ codCli=" + codCli + " ]";
    }

}
