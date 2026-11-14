package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.dto.EmployeDTO;

public interface IEmployeService {

    EmployeDTO create(EmployeDTO entite);

    EmployeDTO findById(Long id);

    List<EmployeDTO> findAll();

    EmployeDTO update(Long id, EmployeDTO entite);

    List<EmployeDTO> findByAgence(Long agenceId);

    EmployeDTO affecterAgence(Long employeId, Long agenceId);

    void deleteById(Long id);
}
