package com.developerscrappad.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table( name = "APP_TABLE3" )
@NamedQueries( {
    @NamedQuery( name = "AppTable3Model.findAll", query = "SELECT a FROM AppTable3Model a" ),
    @NamedQuery( name = "AppTable3Model.findByPk", query = "SELECT a FROM AppTable3Model a WHERE a.pk = :pk" ),
    @NamedQuery( name = "AppTable3Model.findByContents", query = "SELECT a FROM AppTable3Model a WHERE a.contents = :contents" ) } )
public class AppTable3Model implements Serializable {

    private static final long serialVersionUID = -4823264622679146071L;

   @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Basic( optional = false )
    @Column( name = "PK" )
    private Long pk;

    @Basic( optional = false )
    @Column( name = "CONTENTS", unique = true )
    private String contents;

    public AppTable3Model() {
    }

    public AppTable3Model( Long pk ) {
        this.pk = pk;
    }

    public AppTable3Model( Long pk, String contents ) {
        this.pk = pk;
        this.contents = contents;
    }

    public Long getPk() {
        return pk;
    }

    public void setPk( Long pk ) {
        this.pk = pk;
    }

    public String getContents() {
        return contents;
    }

   public void setContents( String contents ) {
        this.contents = contents;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += ( pk != null ? pk.hashCode() : 0 );
        return hash;
    }

    @Override
    public boolean equals( Object object ) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if ( !( object instanceof AppTable3Model ) ) {
            return false;
        }
        AppTable3Model other = ( AppTable3Model ) object;
        if ( ( this.pk == null && other.pk != null ) || ( this.pk != null && !this.pk.equals( other.pk ) ) ) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "com.developerscrappad.entity.AppTable3Model[ pk=" + pk + " ]";
    }
}
