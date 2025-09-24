import java.time.LocalDate;

public class Tarefas {
    private String titulo;
    private String descricao;
    private Projeto projetoVinculado;
    private Usuario responsavel;
    private String status;
    private LocalDate dataInicioPrevista;
    private LocalDate dataFimPrevista;
    private LocalDate dataInicioReal;
    private LocalDate dataFimReal;

    public Tarefas(String titulo, String descricao, Projeto projetoVinculado, Usuario responsavel, String status, LocalDate dataInicioPrevista, LocalDate dataFimPrevista) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.projetoVinculado = projetoVinculado;
        this.responsavel = responsavel;
        this.status = status;
        this.dataInicioPrevista = dataInicioPrevista;
        this.dataFimPrevista = dataFimPrevista;
    }

    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public Projeto getProjetoVinculado() { return projetoVinculado; }
    public Usuario getResponsavel() { return responsavel; }
    public String getStatus() { return status; }
    public LocalDate getDataInicioPrevista() { return dataInicioPrevista; }
    public LocalDate getDataFimPrevista() { return dataFimPrevista; }
    public LocalDate getDataInicioReal() { return dataInicioReal; }
    public LocalDate getDataFimReal() { return dataFimReal; }

    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setProjetoVinculado(Projeto projetoVinculado) { this.projetoVinculado = projetoVinculado; }
    public void setResponsavel(Usuario responsavel) { this.responsavel = responsavel; }
    public void setStatus(String status) { this.status = status; }
    public void setDataInicioPrevista(LocalDate dataInicioPrevista) { this.dataInicioPrevista = dataInicioPrevista; }
    public void setDataFimPrevista(LocalDate dataFimPrevista) { this.dataFimPrevista = dataFimPrevista; }
    public void setDataInicioReal(LocalDate dataInicioReal) { this.dataInicioReal = dataInicioReal; }
    public void setDataFimReal(LocalDate dataFimReal) { this.dataFimReal = dataFimReal; }
}