package com.cleber.financeiro.api.dto;


import java.time.LocalDate;
/*optei por nao usar lombok*/
public class UsuarioCadastroDTO {
    
    private String nomeCompleto;
    private String cpf;
    private String nomeUsuario;
    private String email;
    private String senha;
    private LocalDate dataCadastro;
    
    public UsuarioCadastroDTO() {
		
	}
    
   private UsuarioCadastroDTO(UsuarioBuilder builder) {
        
        this.nomeCompleto = builder.nomeCompleto;
        this.cpf = builder.cpf;
        this.nomeUsuario = builder.nomeUsuario;
        this.email = builder.email;
        this.senha = builder.senha;
        this.dataCadastro = builder.dataCadastro;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getCpf(){
        return cpf;
    }

    public String getNomeUsuario(){
        return nomeUsuario;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public static class UsuarioBuilder{
        private String nomeCompleto;
        private String cpf;
        private String nomeUsuario;
        private String email;
        private String senha;
        private LocalDate dataCadastro;
        
        public UsuarioBuilder setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
        return this;
        }

        public UsuarioBuilder setCpf(String cpf) {
            this.cpf = cpf;
            return this;
        }

        public UsuarioBuilder setNomeUsuario(String nomeUsuario) {
            this.nomeUsuario = nomeUsuario;
            return this;
        }

        public UsuarioBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UsuarioBuilder setSenha(String senha){
            this.senha = senha;
            return this;
        }

        public UsuarioBuilder setDataCadastro(LocalDate dataCadastro) {
            this.dataCadastro = dataCadastro;
            return this;
        }

        public UsuarioCadastroDTO build() {
            return new UsuarioCadastroDTO(this);
        }
    }
}
