package model;

public class Documento {
    private Long id;
    private String arquivo;
    private Long idUsuario;

    public Documento() {}
    
    public Documento(String arquivo) {
        this.arquivo = arquivo;
    }

    public Documento(Long id, String arquivo, Long idUsuario) {
        this.id = id;
        this.arquivo = arquivo;
        this.idUsuario = idUsuario;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getArquivo() {
        return arquivo;
    }
    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }
    public Long getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((arquivo == null) ? 0 : arquivo.hashCode());
        result = prime * result + ((idUsuario == null) ? 0 : idUsuario.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Documento other = (Documento) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (arquivo == null) {
            if (other.arquivo != null)
                return false;
        } else if (!arquivo.equals(other.arquivo))
            return false;
        if (idUsuario == null) {
            if (other.idUsuario != null)
                return false;
        } else if (!idUsuario.equals(other.idUsuario))
            return false;
        return true;
    }

    
}
