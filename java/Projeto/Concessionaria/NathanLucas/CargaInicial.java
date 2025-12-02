package Projeto.Concessionaria.NathanLucas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CargaInicial implements CommandLineRunner {

    private final VeiculoRepository repo;

    public CargaInicial(VeiculoRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) throws Exception {

        if (repo.count() > 0) {
            return;
        }

        // ---------------- CARROS ----------------
        Veiculo civic = new Veiculo(
                "Honda Civic 2013",
                "/Fotos/Carros/civic/CIVIC_FRENTE.png",
                55000
        );
        civic.setTipo("Carro");
        civic.setMarca("Honda");
        civic.setModelo("Civic");
        civic.setAno(2013);
        civic.setCategoria("carro");
        repo.save(civic);

        Veiculo fusion = new Veiculo(
                "Ford Fusion Titanium 2017",
                "/Fotos/Carros/Fusion/FUSION_FRENTE.png",
                82000
        );
        fusion.setTipo("Carro");
        fusion.setMarca("Ford");
        fusion.setModelo("Fusion Titanium");
        fusion.setAno(2017);
        fusion.setCategoria("carro");
        repo.save(fusion);

        Veiculo lexus = new Veiculo(
                "Lexus ES 300H",
                "/Fotos/Carros/LEXUS/lexus-es-300h-2.5-16v-hibrido-cvt-wmimagem15174688716.png",
                130000
        );
        lexus.setTipo("Carro");
        lexus.setMarca("Lexus");
        lexus.setModelo("ES 300H");
        lexus.setAno(2020);
        lexus.setCategoria("carro");
        repo.save(lexus);

        // ---------------- MOTOS ----------------
        Veiculo harley = new Veiculo(
                "Harley Davidson 2022",
                "/Fotos/Motos/HARLEY/harley frente.png",
                76000
        );
        harley.setTipo("Moto");
        harley.setMarca("Harley Davidson");
        harley.setModelo("Fat Boy");
        harley.setAno(2022);
        harley.setCategoria("moto");
        repo.save(harley);

        Veiculo ninja = new Veiculo(
                "Kawasaki Ninja",
                "/Fotos/Motos/Kawasaki/kasawj.jpg",
                48000
        );
        ninja.setTipo("Moto");
        ninja.setMarca("Kawasaki");
        ninja.setModelo("Ninja 400");
        ninja.setAno(2021);
        ninja.setCategoria("moto");
        repo.save(ninja);

        Veiculo vespa = new Veiculo(
                "Vespa PX 200",
                "/Fotos/Motos/VESPA PX200/VESPA PX200.png",
                15000
        );
        vespa.setTipo("Moto");
        vespa.setMarca("Vespa");
        vespa.setModelo("PX 200");
        vespa.setAno(2008);
        vespa.setCategoria("moto");
        repo.save(vespa);

        // ---------------- CAMINHÕES ----------------
        Veiculo daf = new Veiculo(
                "Daf XF 460",
                "/Fotos/Caminhoes/DAF XF 460 2020/DAF_XF.png",
                300000
        );
        daf.setTipo("Caminhão");
        daf.setMarca("DAF");
        daf.setModelo("XF 460");
        daf.setAno(2020);
        daf.setCategoria("caminhao");
        repo.save(daf);

        Veiculo actros = new Veiculo(
                "Mercedes Actros 2653",
                "/Fotos/Caminhoes/Mercedes/Mercedes actros 2653S.png",
                420000
        );
        actros.setTipo("Caminhão");
        actros.setMarca("Mercedes-Benz");
        actros.setModelo("Actros 2653");
        actros.setAno(2019);
        actros.setCategoria("caminhao");
        repo.save(actros);

        Veiculo scania = new Veiculo(
                "Scania R450",
                "/Fotos/Caminhoes/Scania/SCANIA FRENTE.png",
                390000
        );
        scania.setTipo("Caminhão");
        scania.setMarca("Scania");
        scania.setModelo("R450");
        scania.setAno(2018);
        scania.setCategoria("caminhao");
        repo.save(scania);
    }
}
