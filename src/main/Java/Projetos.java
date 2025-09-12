public class Projetos {
    private String nome;
    private String descricao;
    private String dataInicio;
    private String dataFim;
    private String status;
    private String responsavel;

    public Projetos(String nome, String descricao, String dataInicio, String dataFim, String status, String responsavel) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = status;
        this.responsavel = responsavel;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getDataInicio() { return dataInicio; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }

    public String getDataFim() { return dataFim; }
    public void setDataFim(String dataFim) { this.dataFim = dataFim; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }

    @Override
    public String toString() {
        return "Projeto: " + nome +
                " | Descrição: " + descricao +
                " | Início: " + dataInicio +
                " | Fim: " + dataFim +
                " | Status: " + status +
                " | Responsável: " + responsavel;
    }
}
