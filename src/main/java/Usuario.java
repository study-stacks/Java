public class Usuario {
    private String id;
    private String nome;
    private String cpf;
    private String email;
    private String login;
    private String cargo;
    private String perfil;

    public Usuario(String id, String nome, String cpf, String email, String login, String cargo, String perfil) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.login = login;
        this.cargo = cargo;
        this.perfil = perfil;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getEmail() { return email; }
    public String getLogin() { return login; }
    public String getCargo() { return cargo; }
    public String getPerfil() { return perfil; }

    public void setId(String id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setEmail(String email) { this.email = email; }
    public void setLogin(String login) { this.login = login; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public void setPerfil(String perfil) { this.perfil = perfil; }

    @Override
    public String toString() {
        return nome;
    }
}
