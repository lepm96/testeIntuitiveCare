package com.example.operadorasapi.service;
import com.example.operadorasapi.model.Operadora;
import com.example.operadorasapi.repository.OperadoraRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Service
public class OperadoraService {

    @Autowired
    private OperadoraRepository repository;

    public void carregarDadosIniciais() {
        try (Reader reader = new BufferedReader(new InputStreamReader(
                new ClassPathResource("operadoras.csv").getInputStream()))) {

            CsvToBean<Operadora> csvToBean = new CsvToBeanBuilder<Operadora>(reader)
                    .withType(Operadora.class)
                    .withSeparator(';')
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<Operadora> operadoras = csvToBean.parse();
            repository.saveAll(operadoras);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Operadora> buscarOperadoras(String termo) {
        return repository.buscarPorTermo(termo);
    }
}