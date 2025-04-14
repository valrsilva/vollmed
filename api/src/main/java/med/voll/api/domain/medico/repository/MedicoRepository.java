package med.voll.api.domain.medico.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.enums.Especialidade;
import med.voll.api.domain.medico.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

	Page<Medico> findAllByAtivoTrue(Pageable page);

	@Query("""
            select m from Medico m
            where
            m.ativo = true
            and m.especialidade = :especialidade
            and m.id not in (
				select c.medico.id from Consulta c where c.data = :data
            )
            order by rand()
            limit 1
            """)
	Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade, @NotNull @Future LocalDateTime data);

	@Query("""
            select m.ativo 
            from Medico m
            where
            m.id = :id
            """)
	Medico findAtivoById(Long idMedico);

}
