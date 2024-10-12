package com.example.demo.service.stratum;

import com.example.demo.dao.stratum.Stratum;
import com.example.demo.dao.stratum.StratumRepository;
import com.example.demo.dao.stratum.dto.CreateStratumDTO;
import com.example.demo.dao.stratum.dto.StratumDTO;
import com.example.demo.dao.stratum.dto.UpdateStratumDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class StratumServiceImpl implements StratumService {

    private final StratumRepository stratumRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<StratumDTO> listStrata(Pageable pageable) {
        return stratumRepository.findByActiveTrue(pageable)
                .map(stratum -> new StratumDTO(
                        stratum.getIdStratum(),
                        stratum.getStratumName(),
                        stratum.getDescription(),
                        stratum.isActive()
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StratumDTO> findStratumById(Long id) {
        return stratumRepository.findByIdStratum(id)
                .map(stratum -> new StratumDTO(
                        stratum.getIdStratum(),
                        stratum.getStratumName(),
                        stratum.getDescription(),
                        stratum.isActive()
                ));
    }

    @Override
    public StratumDTO createStratum(CreateStratumDTO request) {
        Stratum stratum = new Stratum();
        stratum.setStratumName(request.stratumName());
        stratum.setDescription(request.description());
        stratum.setActive(true);

        stratum = stratumRepository.save(stratum);
        return new StratumDTO(
                stratum.getIdStratum(),
                stratum.getStratumName(),
                stratum.getDescription(),
                stratum.isActive()
        );
    }

    @Override
    public StratumDTO updateStratum(Long id, UpdateStratumDTO request) {
        return stratumRepository.findById(id)
                .map(stratum -> {
                    stratum.setStratumName(request.stratumName());
                    stratum.setDescription(request.description());

                    Stratum updatedStratum = stratumRepository.save(stratum);
                    return new StratumDTO(
                            updatedStratum.getIdStratum(),
                            updatedStratum.getStratumName(),
                            updatedStratum.getDescription(),
                            updatedStratum.isActive()
                    );
                }).orElseThrow(() -> new RuntimeException("Stratum not found with ID: " + id));
    }

    @Override
    public void deleteStratum(Long id) {
        stratumRepository.findById(id)
                .ifPresentOrElse(stratum -> {
                    stratum.setActive(false);
                    stratumRepository.save(stratum);
                }, () -> {
                    throw new RuntimeException("Stratum not found with ID: " + id);
                });
    }
}

