package br.com.fiap.controller;

import br.com.fiap.model.entity.Mapa;
import br.com.fiap.model.model.dto.MapaDTO;
import br.com.fiap.model.repository.MapaRepository;
import br.com.fiap.model.repository.RegiaoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import br.com.fiap.model.entity.FonteMapa;
import br.com.fiap.model.repository.FonteMapaRepository;
import br.com.fiap.model.entity.Regiao;

import java.util.List;

@RestController
@RequestMapping("/mapas")
public class MapaController {

    @Autowired
    private MapaRepository mapaRepository;

    @Autowired
    private RegiaoRepository regiaoRepository;

    @Autowired
    private FonteMapaRepository fonteMapaRepository;

    @GetMapping
    public ModelAndView consultar() {
        List<Mapa> mapas = mapaRepository.findAll();

        ModelAndView mv = new ModelAndView("mapas");
        mv.addObject("mapas", mapas);
        return mv;
    }

    @GetMapping("/novo")
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView("formulario");
        mv.addObject("regioes", regiaoRepository.findAll());
        return mv;
    }

    @PostMapping
    public ModelAndView cadastrar(@Valid MapaDTO mapaDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("redirect:/mapas");
        }

        if (!regiaoRepository.existsById(mapaDTO.idRegiao())) {
            return new ModelAndView("redirect:/mapas/novo");
        }

        Mapa mapa = new Mapa(mapaDTO);
        mapaRepository.save(mapa);

        return new ModelAndView("redirect:/mapas");
    }
    @GetMapping("/editar/{idMapa}")
    public ModelAndView consultarPorCodigo(@PathVariable Long idMapa) {
        Mapa mapa = mapaRepository.findByIdMapa(idMapa);
        if (mapa == null) {
            return new ModelAndView("redirect:/mapas");
        }
        ModelAndView mv = new ModelAndView("formulario");
        mv.addObject("mapa", mapa);
        mv.addObject("regioes", regiaoRepository.findAll());
        mv.addObject("acao", "/mapas/atualizar/" + idMapa);
        return mv;
    }

    @PostMapping("/atualizar/{idMapa}")
    public ModelAndView atualizar(@PathVariable Long idMapa, @Valid MapaDTO mapaDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("redirect:/mapas");
        }
        Mapa mapa = mapaRepository.findByIdMapa(idMapa);
        if (mapa == null) {
            return new ModelAndView("redirect:/mapas");
        }
        if (!regiaoRepository.existsById(mapaDTO.idRegiao())) {
            return new ModelAndView("redirect:/mapas");
        }
        mapa.setIdRegiao(mapaDTO.idRegiao());
        mapa.setTitulo(mapaDTO.titulo());
        mapa.setObjetivo(mapaDTO.objetivo());
        mapaRepository.save(mapa);
        return new ModelAndView("redirect:/mapas");
    }

    @GetMapping("/excluir/{idMapa}")
    public ModelAndView excluir(@PathVariable Long idMapa) {
        try {
            if (!mapaRepository.existsById(idMapa)) {
                List<Mapa> mapas = mapaRepository.findAll();
                ModelAndView mv = new ModelAndView("mapas");
                mv.addObject("mapas", mapas);
                mv.addObject("mensagem", "O estudo informado não foi encontrado.");
                return mv;
            }
            List<FonteMapa> fontes = fonteMapaRepository.findByIdMapa(idMapa);
            if (fontes.size() > 0) {
                List<Mapa> mapas = mapaRepository.findAll();
                ModelAndView mv = new ModelAndView("mapas");
                mv.addObject("mapas", mapas);
                mv.addObject("mensagem", "Este estudo possui fontes cadastradas e não pode ser excluído.");
                return mv;
            }
            mapaRepository.deleteById(idMapa);
            return new ModelAndView("redirect:/mapas");
        } catch (Exception e) {
            ModelAndView mv = new ModelAndView("erro");
            mv.addObject("mensagem", "Não foi possível excluir o estudo. Tente novamente mais tarde.");
            return mv;
        }
    }

    @GetMapping("/detalhes/{idMapa}")
    public ModelAndView detalhar(@PathVariable Long idMapa) {
        Mapa mapa = mapaRepository.findByIdMapa(idMapa);
        if (mapa == null) {
            return new ModelAndView("redirect:/mapas");
        }
        Regiao regiao = regiaoRepository.findByIdRegiao(mapa.getIdRegiao());
        List<FonteMapa> fontes = fonteMapaRepository.findByIdMapa(idMapa);
        ModelAndView mv = new ModelAndView("detalhe-mapa");
        mv.addObject("mapa", mapa);
        mv.addObject("regiao", regiao);
        mv.addObject("fontes", fontes);
        return mv;
    }
}