package br.com.treinaweb.twprojetos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.treinaweb.twprojetos.entities.Cargo;
import br.com.treinaweb.twprojetos.exceptions.CargoNaoEncontradoException;
import br.com.treinaweb.twprojetos.exceptions.CargoPossuiFuncionariosException;
import br.com.treinaweb.twprojetos.repositories.CargoRepository;
import br.com.treinaweb.twprojetos.repositories.FuncionarioRepository;

@Service
public class CargoService {

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public List<Cargo> findAll() {
        return cargoRepository.findAll();
    }

    public Cargo findById(Long id) {
        Cargo cargoEncontrado = cargoRepository.findById(id)
            .orElseThrow(() -> new CargoNaoEncontradoException(id));
        
        return cargoEncontrado;
    }

    public Cargo save(Cargo cargo) {
        return cargoRepository.save(cargo);
    }

    public Cargo update(Cargo cargo, Long id) {
        findById(id);

        return cargoRepository.save(cargo);
    }

    public void deleteById(Long id) {
        Cargo cargoEncontrado = findById(id);

        if (funcionarioRepository.findByCargo(cargoEncontrado).isEmpty()) {
            cargoRepository.delete(cargoEncontrado);
        } else {
            throw new CargoPossuiFuncionariosException(id);
        }
    }
}
