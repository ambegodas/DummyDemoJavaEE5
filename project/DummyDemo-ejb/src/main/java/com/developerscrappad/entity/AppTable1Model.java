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
@Table( name = "APP_TABLE1" )
@NamedQueries( {
    @NamedQuery( name = "AppTable1Model.findAll", query = "SELECT a FROM AppTable1Model a" ),
    @NamedQuery( name = "AppTable1Model.findByPk", query = "SELECT a FROM AppTable1Model a WHERE a.pk = :pk" ),
    @NamedQuery( name = "AppTable1Model.findByContents", query = "SELECT a FROM AppTable1Model a WHERE a.contents = :contents" ) } )
public class AppTable1Model implements Serializable {

    private static final long serialVersionUID = -1447051578332788777L;

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Basic( optional = false )
    @Column( name = "PK" )
    private Long pk;

    @Basic( optional = false )
    @Column( name = "CONTENTS", unique = true )
    private String contents;

    public AppTable1Model() {
    }

    public AppTable1Model( Long pk ) {
        this.pk = pk;
    }

    public AppTable1Model( Long pk, String contents ) {
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
        if ( !( object instanceof AppTable1Model ) ) {
            return false;
        }
        AppTable1Model other = ( AppTable1Model ) object;
        if ( ( this.pk == null && other.pk != null ) || ( this.pk != null && !this.pk.equals( other.pk ) ) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.developerscrappad.entity.AppTable1Model[ pk=" + pk + " ]";
    }
}
