public class Projeto {
    private int id;
    private String nome;
    private String descricao;
    private String dataInicio;
    private String dataFim;
    private String responsavel;
    private String equipe;
    private String status;

    public Projeto(int id, String nome, String descricao, String dataInicio, String dataFim,
                   String responsavel, String equipe, String status) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.responsavel = responsavel;
        this.equipe = equipe;
        this.status = status;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getDataInicio() { return dataInicio; }
    public String getDataFim() { return dataFim; }
    public String getResponsavel() { return responsavel; }
    public String getEquipe() { return equipe; }
    public String getStatus() { return status; }
    public void setId(int id) { this.id = id; }
}