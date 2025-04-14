package med.voll.api.domain.medico.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import med.voll.api.domain.endereco.model.Endereco;
import med.voll.api.domain.medico.enums.Especialidade;
import med.voll.api.domain.medico.record.DadosAtualizacaoMedico;
import med.voll.api.domain.medico.record.DadosMedico;

@Entity
@Table(name = "medicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    public Medico(DadosMedico dados) {
		this.crm = dados.crm();
		this.email = dados.email();
		if(dados.endereco() != null) {
			this.endereco = new Endereco(dados.endereco());
		}
		this.especialidade = dados.especialidade();
		this.nome = dados.nome();
		this.telefone = dados.telefone();
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String telefone;
    private String crm;
    
    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    @Column(columnDefinition = "TINYINT")
    private Boolean ativo = true;
    
	public void atualizarDados(@Valid DadosAtualizacaoMedico dados) {
		if(dados.nome() != null) {
			this.nome = dados.nome();
		}
		if(dados.email() != null) {
			this.email = dados.email();
		}
		if(dados.telefone() != null) {
			this.telefone = dados.telefone();
		}
		if(dados.endereco() != null) {
			this.endereco.atualizarDados(dados.endereco());
		}
		
	}

	public void excluir() {
		this.ativo =false;
	}
}