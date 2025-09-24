public class Projeto {
    private int id;
    private String nome;
    private String descricao;
    private String dataInicio;
    private String dataFim;
    private Usuario responsavel;
    private String status;

    public Projeto(int id, String nome, String descricao,
                   String dataInicio, String dataFim,
                   Usuario responsavel, String status) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.responsavel = responsavel;
        this.status = status;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getDataInicio() { return dataInicio; }
    public String getDataFim() { return dataFim; }
    public Usuario getResponsavel() { return responsavel; }
    public String getStatus() { return status; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }
    public void setDataFim(String dataFim) { this.dataFim = dataFim; }
    public void setResponsavel(Usuario responsavel) { this.responsavel = responsavel; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return nome;
    }
}
